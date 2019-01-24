package top.yunshu.shw.server.controller.student;

import org.apache.catalina.connector.ClientAbortException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.yunshu.shw.server.entity.Group;
import top.yunshu.shw.server.entity.LoginUser;
import top.yunshu.shw.server.entity.RestModel;
import top.yunshu.shw.server.entity.Upload;
import top.yunshu.shw.server.exception.FileException;
import top.yunshu.shw.server.model.WorkModel;
import top.yunshu.shw.server.service.file.FileService;
import top.yunshu.shw.server.service.group.GroupService;
import top.yunshu.shw.server.service.student.group.StudentGroupService;
import top.yunshu.shw.server.service.upload.UploadService;
import top.yunshu.shw.server.service.work.WorkService;
import top.yunshu.shw.server.util.JwtUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;


/**
 * 学生控制器
 *
 * @author itning
 * @date 2018/12/21
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    private static final String RANGE_SEPARATOR = "-";
    private static final String RANGE_CONTAINS = "bytes=";
    private static final int RANGE_BYTES_ALL = 2;
    private static final int RANGE_BYTES_ONE = 1;

    private final GroupService groupService;

    private final WorkService workService;

    private final ModelMapper modelMapper;

    private final UploadService uploadService;

    private final StudentGroupService studentGroupService;

    private final FileService fileService;

    @Autowired
    public StudentController(GroupService groupService, WorkService workService, ModelMapper modelMapper, UploadService uploadService, StudentGroupService studentGroupService, FileService fileService) {
        this.groupService = groupService;
        this.workService = workService;
        this.modelMapper = modelMapper;
        this.uploadService = uploadService;
        this.studentGroupService = studentGroupService;
        this.fileService = fileService;
    }

    /**
     * 获取学生所有群组
     *
     * @return ResponseEntity
     */
    @GetMapping("/groups")
    public ResponseEntity<RestModel> getAllGroups(@RequestHeader("Authorization") String authorization) {
        logger.debug("get all student groups");
        LoginUser loginUser = JwtUtils.getLoginUser(authorization);
        logger.info("get login user: " + loginUser);
        return ResponseEntity.ok(new RestModel<>(groupService.findStudentAllGroups(loginUser.getNo())));
    }

    /**
     * 获取学生所有未上传作业
     *
     * @return ResponseEntity
     */
    @GetMapping("/works/un_done")
    public ResponseEntity<RestModel> getAllUnDoneWorks(@RequestHeader("Authorization") String authorization) {
        logger.debug("get all un done works");
        LoginUser loginUser = JwtUtils.getLoginUser(authorization);
        logger.info("get login user: " + loginUser);
        List<WorkModel> workModels = modelMapper.map(workService.getStudentUnDoneWork(loginUser.getNo()), new TypeToken<List<WorkModel>>() {
        }.getType());
        return ResponseEntity.ok(new RestModel<>(workModels));
    }

    /**
     * 获取学生所有已上传作业
     *
     * @return ResponseEntity
     */
    @GetMapping("/works/done")
    public ResponseEntity<RestModel> getAllDoneWorks(@RequestHeader("Authorization") String authorization) {
        logger.debug("get all done works");
        LoginUser loginUser = JwtUtils.getLoginUser(authorization);
        logger.info("get login user: " + loginUser);
        List<WorkModel> workModels = modelMapper.map(workService.getStudentDoneWork(loginUser.getNo()), new TypeToken<List<WorkModel>>() {
        }.getType());
        return ResponseEntity.ok(new RestModel<>(workModels));
    }

    /**
     * 根据作业ID获取上传信息
     *
     * @param workId 作业ID
     * @return ResponseEntity
     */
    @GetMapping("/upload/{workId}")
    public ResponseEntity<RestModel> getUpLoadInfoByWorkId(@RequestHeader("Authorization") String authorization, @PathVariable String workId) {
        logger.debug("get upload info by work id");
        LoginUser loginUser = JwtUtils.getLoginUser(authorization);
        logger.info("get login user: " + loginUser);
        return ResponseEntity.ok(new RestModel<>(uploadService.getUploadInfoByWorkId(loginUser.getNo(), workId)));
    }

    /**
     * 学生加入群组
     *
     * @param code 邀请码
     * @return ResponseEntity
     */
    @PostMapping("/group")
    public ResponseEntity<Group> addGroup(@RequestHeader("Authorization") String authorization, String code) {
        logger.debug("add group , code: " + code);
        LoginUser loginUser = JwtUtils.getLoginUser(authorization);
        logger.info("get login user: " + loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.joinGroup(code, loginUser.getNo()));
    }

    /**
     * 退出群组
     *
     * @param groupId 群组ID
     * @return ResponseEntity
     */
    @DeleteMapping("/group/{groupId}")
    public ResponseEntity<Void> dropOutGroup(@RequestHeader("Authorization") String authorization, @PathVariable String groupId) {
        logger.debug("del group , id: " + groupId);
        LoginUser loginUser = JwtUtils.getLoginUser(authorization);
        logger.info("get login user: " + loginUser);
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
    @PostMapping("/work/{workId}")
    public ResponseEntity<Void> uploadWork(@RequestHeader("Authorization") String authorization, @PathVariable String workId, @RequestParam("file") MultipartFile file) {
        logger.debug("upload file , work id: " + workId);
        LoginUser loginUser = JwtUtils.getLoginUser(authorization);
        logger.info("get login user: " + loginUser);
        System.out.println(workId);
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
        fileService.uploadFile(file, loginUser.getNo(), workId);
        uploadService.uploadFile(file, loginUser.getNo(), workId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 学生删除已上传作业
     *
     * @param workId 作业ID
     * @return ResponseEntity
     */
    @DeleteMapping("/work/{workId}")
    public ResponseEntity<Void> deleteUploadWork(@RequestHeader("Authorization") String authorization, @PathVariable String workId) {
        logger.debug("delete Upload Work , workId: " + workId);
        LoginUser loginUser = JwtUtils.getLoginUser(authorization);
        logger.info("get login user: " + loginUser);
        fileService.delFile(loginUser.getNo(), workId);
        uploadService.delUploadInfoByWorkId(loginUser.getNo(), workId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 查询学生是否有学生群组
     *
     * @return ResponseEntity
     */
    @GetMapping("/group/exist")
    public ResponseEntity<RestModel> isStudentJoinAnyStudentGroup(@RequestHeader("Authorization") String authorization) {
        logger.debug("is Student Join Any StudentGroup");
        LoginUser loginUser = JwtUtils.getLoginUser(authorization);
        logger.info("get login user: " + loginUser);
        return ResponseEntity.ok(new RestModel<>(studentGroupService.isHaveGroup(loginUser.getNo())));
    }

    @GetMapping("/down/{studentNumber}/{workId}")
    public void downloadFile(@RequestHeader(required = false) String range, @PathVariable String studentNumber, @PathVariable String workId, HttpServletResponse response) throws IOException {
        logger.debug("down file, work id: " + workId);
        Optional<File> fileOptional = fileService.getFile(studentNumber, workId);
        if (fileOptional.isPresent()) {
            File file = fileOptional.get();
            Upload upload = uploadService.getUploadInfoByWorkId(studentNumber, workId);
            long startByte = 0;
            long endByte = file.length() - 1;
            if (range != null && range.contains(RANGE_CONTAINS) && range.contains(RANGE_SEPARATOR)) {
                range = range.substring(range.lastIndexOf("=") + 1).trim();
                String[] ranges = range.split(RANGE_SEPARATOR);
                try {
                    //判断range的类型
                    if (ranges.length == RANGE_BYTES_ONE) {
                        if (range.startsWith(RANGE_SEPARATOR)) {
                            //类型一：bytes=-2343
                            endByte = Long.parseLong(ranges[0]);
                        } else if (range.endsWith(RANGE_SEPARATOR)) {
                            //类型二：bytes=2343-
                            startByte = Long.parseLong(ranges[0]);
                        }
                    } else if (ranges.length == RANGE_BYTES_ALL) {
                        //类型三：bytes=22-2343
                        startByte = Long.parseLong(ranges[0]);
                        endByte = Long.parseLong(ranges[1]);
                    }

                } catch (NumberFormatException e) {
                    startByte = 0;
                    endByte = file.length() - 1;
                }
            }
            long contentLength = endByte - startByte + 1;
            String contentType = upload.getMime();
            response.setHeader("Accept-Ranges", "bytes");
            if (range == null) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                response.setHeader("Content-Range", "bytes " + startByte + "-" + endByte + "/" + file.length());
            }
            response.setContentType(contentType);
            response.setHeader("Content-Type", contentType);
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes(), StandardCharsets.ISO_8859_1));
            response.setHeader("Content-Length", String.valueOf(contentLength));
            long transmitted = 0;
            try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
                 BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream())) {
                byte[] buff = new byte[4096];
                int len = 0;
                randomAccessFile.seek(startByte);
                while ((transmitted + len) <= contentLength && (len = randomAccessFile.read(buff)) != -1) {
                    outputStream.write(buff, 0, len);
                    transmitted += len;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (transmitted < contentLength) {
                    len = randomAccessFile.read(buff, 0, (int) (contentLength - transmitted));
                    outputStream.write(buff, 0, len);
                    transmitted += len;
                }
                outputStream.flush();
                response.flushBuffer();
                randomAccessFile.close();
                logger.debug("下载完毕：" + startByte + "-" + endByte + "：" + transmitted);
            } catch (ClientAbortException e) {
                logger.debug("用户停止下载：" + startByte + "-" + endByte + "：" + transmitted);
            } catch (IOException e) {
                throw new FileException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            response.sendError(HttpStatus.NOT_FOUND.value(), "文件没有找到");
        }
    }
}
