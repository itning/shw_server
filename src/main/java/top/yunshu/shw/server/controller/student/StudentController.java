package top.yunshu.shw.server.controller.student;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import top.yunshu.shw.server.entity.*;
import top.yunshu.shw.server.model.WorkModel;
import top.yunshu.shw.server.service.config.ConfigService;
import top.yunshu.shw.server.service.file.FileService;
import top.yunshu.shw.server.service.group.GroupService;
import top.yunshu.shw.server.service.notice.NoticeService;
import top.yunshu.shw.server.service.student.group.StudentGroupService;
import top.yunshu.shw.server.service.upload.UploadService;
import top.yunshu.shw.server.service.work.WorkService;
import top.yunshu.shw.server.util.FileUtils;
import top.yunshu.shw.server.util.Office2PdfUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.concurrent.Callable;

import static top.yunshu.shw.server.util.Office2PdfUtils.OFFICE_EXTENSION_NAME;


/**
 * 学生控制器
 *
 * @author itning
 * @date 2018/12/21
 */
@Api(tags = {"学生接口"})
@RestController
@RequestMapping("/student")
public class StudentController {
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    private final GroupService groupService;

    private final WorkService workService;

    private final UploadService uploadService;

    private final StudentGroupService studentGroupService;

    private final FileService fileService;

    private final NoticeService noticeService;

    private final ConfigService configService;

    @Autowired
    public StudentController(GroupService groupService, WorkService workService, UploadService uploadService, StudentGroupService studentGroupService, FileService fileService, NoticeService noticeService, ConfigService configService) {
        this.groupService = groupService;
        this.workService = workService;
        this.uploadService = uploadService;
        this.studentGroupService = studentGroupService;
        this.fileService = fileService;
        this.noticeService = noticeService;
        this.configService = configService;
    }

    /**
     * 获取学生所有群组
     *
     * @return ResponseEntity
     */
    @ApiOperation(value = "获取学生所有群组", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            response = Group.class, responseContainer = "List")
    @GetMapping("/groups")
    public Callable<ResponseEntity<RestModel>> getAllGroups(@ApiIgnore LoginUser loginUser,
                                                            @ApiParam("分页信息") @PageableDefault(size = 20, sort = {"gmtCreate"}, direction = Sort.Direction.DESC)
                                                                    Pageable pageable) {
        logger.debug("get all student groups");
        return () -> ResponseEntity.ok(new RestModel<>(groupService.findStudentAllGroups(loginUser.getNo(), pageable)));
    }

    /**
     * 获取学生所有未上传作业
     *
     * @return ResponseEntity
     */
    @ApiOperation(value = "获取学生所有未上传作业", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            response = WorkModel.class, responseContainer = "List")
    @GetMapping("/works/un_done")
    public Callable<ResponseEntity<RestModel>> getAllUnDoneWorks(@ApiIgnore LoginUser loginUser,
                                                                 @ApiParam("分页信息") @PageableDefault(size = 20, sort = {"gmtCreate"}, direction = Sort.Direction.DESC)
                                                                         Pageable pageable) {
        logger.debug("get all un done works");
        return () -> ResponseEntity.ok(new RestModel<>(workService.getStudentUnDoneWork(loginUser.getNo(), pageable)));
    }

    /**
     * 获取学生所有已上传作业
     *
     * @return ResponseEntity
     */
    @ApiOperation(value = "获取学生所有已上传作业", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            response = WorkModel.class, responseContainer = "List")
    @GetMapping("/works/done")
    public Callable<ResponseEntity<RestModel>> getAllDoneWorks(@ApiIgnore LoginUser loginUser,
                                                               @ApiParam("分页信息") @PageableDefault(size = 20, sort = {"gmtCreate"}, direction = Sort.Direction.DESC)
                                                                       Pageable pageable) {
        logger.debug("get all done works");
        return () -> ResponseEntity.ok(new RestModel<>(workService.getStudentDoneWork(loginUser.getNo(), pageable)));
    }

    /**
     * 根据作业ID获取上传信息
     *
     * @param workId 作业ID
     * @return ResponseEntity
     */
    @ApiOperation(value = "根据作业ID获取上传信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = Upload.class)
    @GetMapping("/upload/{workId}")
    public Callable<ResponseEntity<RestModel>> getUpLoadInfoByWorkId(@ApiIgnore LoginUser loginUser,
                                                                     @ApiParam(value = "作业ID", required = true) @PathVariable String workId) {
        logger.debug("get upload info by work id");
        return () -> ResponseEntity.ok(new RestModel<>(uploadService.getUploadInfoByWorkId(loginUser.getNo(), workId)));
    }

    /**
     * 学生加入群组
     *
     * @param code 邀请码
     * @return ResponseEntity
     */
    @ApiOperation(value = "学生加入群组", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = Group.class)
    @PostMapping("/group")
    public ResponseEntity<Group> addGroup(@ApiIgnore LoginUser loginUser,
                                          @ApiParam(value = "邀请码", required = true) @RequestParam String code) {
        logger.debug("add group , code: {}", code);
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.joinGroup(code, loginUser.getNo()));
    }

    /**
     * 退出群组
     *
     * @param groupId 群组ID
     * @return ResponseEntity
     */
    @ApiOperation("退出群组")
    @DeleteMapping("/group/{groupId}")
    public ResponseEntity<Void> dropOutGroup(@ApiIgnore LoginUser loginUser,
                                             @ApiParam(value = "群组ID", required = true) @PathVariable String groupId) {
        logger.debug("del group , id: {}", groupId);
        groupService.dropOutGroup(groupId, loginUser.getNo());
        return ResponseEntity.noContent().build();
    }

    /**
     * 学生上传作业
     *
     * @param workId 作业ID
     * @param file   文件
     * @return ResponseEntity
     */
    @ApiOperation("学生上传作业")
    @PostMapping("/work/{workId}")
    public ResponseEntity<Void> uploadWork(@ApiIgnore LoginUser loginUser,
                                           @ApiParam(value = "作业ID", required = true) @PathVariable String workId,
                                           @ApiParam(value = "上传的作业", required = true) @RequestParam("file") MultipartFile file) {
        logger.debug("upload file , work id: {}", workId);
        uploadService.uploadFile(file, loginUser.getNo(), workId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 学生删除已上传作业
     *
     * @param workId 作业ID
     * @return ResponseEntity
     */
    @ApiOperation("学生删除已上传作业")
    @DeleteMapping("/work/{workId}")
    public ResponseEntity<Void> deleteUploadWork(@ApiIgnore LoginUser loginUser,
                                                 @ApiParam(value = "作业ID", required = true) @PathVariable String workId) {
        logger.debug("delete Upload Work , workId: {}", workId);
        uploadService.delUploadInfoByWorkId(loginUser.getNo(), workId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 查询学生是否有学生群组
     *
     * @return ResponseEntity
     */
    @ApiOperation(value = "查询学生是否有学生群组", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = RestModel.class)
    @GetMapping("/group/exist")
    public Callable<ResponseEntity<RestModel>> isStudentJoinAnyStudentGroup(@ApiIgnore LoginUser loginUser) {
        logger.debug("is Student Join Any StudentGroup");
        return () -> ResponseEntity.ok(new RestModel<>(studentGroupService.isHaveGroup(loginUser.getNo())));
    }

    /**
     * 学生作业下载
     *
     * @param range         请求头
     * @param studentNumber 学号
     * @param workId        作业ID
     * @param response      {@link HttpServletResponse}
     * @throws IOException 文件没有找到
     */
    @ApiOperation("学生作业下载")
    @GetMapping("/down/{studentNumber}/{workId}")
    public void downloadFile(@ApiParam(value = "Range请求头(用于判断是否需要支持多线程下载和断点续传)", required = true) @RequestHeader(required = false) String range,
                             @ApiParam(value = "学号", required = true) @PathVariable String studentNumber,
                             @ApiParam(value = "作业ID", required = true) @PathVariable String workId,
                             @ApiIgnore HttpServletResponse response) throws IOException {
        logger.debug("down file, work id: {}", workId);
        Optional<File> fileOptional = fileService.getFile(studentNumber, workId);
        if (fileOptional.isPresent()) {
            File file = fileOptional.get();
            Upload upload = uploadService.getUploadInfoByWorkId(studentNumber, workId);
            FileUtils.breakpointResume(file, upload.getMime(), range, response);
        } else {
            response.sendError(HttpStatus.NOT_FOUND.value(), "文件没有找到");
        }
    }

    /**
     * 预览文件
     *
     * @param studentNumber 学号
     * @param workId        作业ID
     * @param encoding      编码
     * @param response      {@link HttpServletResponse}
     */
    @ApiOperation("预览文件")
    @GetMapping("/down_preview/{studentNumber}/{workId}")
    public void preview(@ApiParam(value = "学号", required = true) @PathVariable String studentNumber,
                        @ApiParam(value = "作业ID", required = true) @PathVariable String workId,
                        @ApiParam(value = "编码", defaultValue = "UTF-8") @RequestParam(defaultValue = "UTF-8") String encoding,
                        HttpServletResponse response) {
        response.setCharacterEncoding(encoding);
        fileService.getFile(studentNumber, workId).ifPresent(file -> {
            try (ServletOutputStream outputStream = response.getOutputStream();
                 FileInputStream fileInputStream = new FileInputStream(file)) {
                String extensionName = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                if (OFFICE_EXTENSION_NAME.contains(extensionName.toLowerCase())) {
                    String tempFilePath = configService.getConfig(Config.ConfigKey.TEMP_DIR).orElse(System.getProperty("java.io.tmpdir")) + File.separator + FileUtils.getFileMD5(file) + ".pdf";
                    File tempFile = new File(tempFilePath);
                    if (!tempFile.exists()) {
                        logger.debug("start convert {} to pdf", file.getPath());
                        long lastTimeMillis = System.currentTimeMillis();
                        Office2PdfUtils.convert2Pdf(file, tempFile);
                        logger.debug("end convert and use time: {}", (System.currentTimeMillis() - lastTimeMillis));
                    }
                    if (tempFile.length() == 0) {
                        boolean delete = tempFile.delete();
                        logger.debug("delete {}", delete);
                        throw new RuntimeException("转换失败");
                    }

                    response.setContentType(MediaType.APPLICATION_PDF_VALUE);
                    FileInputStream tempFileInputStream = new FileInputStream(tempFilePath);
                    IOUtils.copy(tempFileInputStream, outputStream);

                    fileInputStream.close();
                    tempFileInputStream.close();
                    return;
                }
                String contentTypeByExtensionName = FileUtils.getContentTypeByExtensionName(extensionName);
                String setContentType;
                if (contentTypeByExtensionName == null) {
                    String contentType = Files.probeContentType(file.toPath());
                    if (contentType == null || contentType.equals(MediaType.TEXT_HTML_VALUE)) {
                        setContentType = MediaType.TEXT_PLAIN_VALUE;
                    } else {
                        setContentType = contentType;
                    }
                } else {
                    setContentType = contentTypeByExtensionName;
                }
                logger.debug("get contentType: {}", setContentType);
                response.setContentType(setContentType);
                IOUtils.copy(fileInputStream, outputStream);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 获取学生所有通知
     *
     * @return ResponseEntity
     */
    @ApiOperation(value = "获取学生所有通知", produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            response = Notice.class, responseContainer = "List")
    @GetMapping("/notices")
    public Callable<ResponseEntity<RestModel>> getAllNotices(@ApiIgnore LoginUser loginUser) {
        logger.debug("get all notices");
        return () -> ResponseEntity.ok(new RestModel<>(noticeService.getAllNoticeByStudentId(loginUser.getNo())));
    }

    /**
     * 根据作业ID获取批阅信息
     *
     * @param workId 作业ID
     * @return ResponseEntity
     */
    @ApiOperation(value = "根据作业ID获取批阅信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, response = String.class)
    @GetMapping("/review/{workId}")
    public Callable<ResponseEntity<RestModel>> getReviewByStudentId(@ApiIgnore LoginUser loginUser,
                                                                    @ApiParam(value = "作业ID", required = true) @PathVariable String workId) {
        logger.debug("get all notices");
        return () -> ResponseEntity.ok(new RestModel<>(uploadService.reviewWork(workId, loginUser.getNo())));
    }

    /**
     * 根据通知ID清除通知
     *
     * @param noticeId 通知ID
     * @return ResponseEntity
     */
    @SuppressWarnings("unused")
    @ApiOperation("根据通知ID清除通知")
    @DeleteMapping("/notice/{noticeId}")
    public ResponseEntity<Void> delNoticeById(@ApiIgnore LoginUser loginUser,
                                              @ApiParam(value = "通知ID", required = true) @PathVariable String noticeId) {
        logger.debug("del notice id {}", noticeId);
        noticeService.delNoticeById(noticeId);
        return ResponseEntity.noContent().build();
    }
}
