package top.yunshu.shw.server.controller.student;

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
import top.yunshu.shw.server.model.WorkModel;
import top.yunshu.shw.server.service.file.FileService;
import top.yunshu.shw.server.service.group.GroupService;
import top.yunshu.shw.server.service.student.group.StudentGroupService;
import top.yunshu.shw.server.service.upload.UploadService;
import top.yunshu.shw.server.service.work.WorkService;
import top.yunshu.shw.server.util.FileUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static top.yunshu.shw.server.util.RoleUtils.checkRoleIsStudent;


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
    public ResponseEntity<RestModel> getAllGroups(LoginUser loginUser) {
        logger.debug("get all student groups");
        checkRoleIsStudent(loginUser);
        return ResponseEntity.ok(new RestModel<>(groupService.findStudentAllGroups(loginUser.getNo())));
    }

    /**
     * 获取学生所有未上传作业
     *
     * @return ResponseEntity
     */
    @GetMapping("/works/un_done")
    public ResponseEntity<RestModel> getAllUnDoneWorks(LoginUser loginUser) {
        logger.debug("get all un done works");
        checkRoleIsStudent(loginUser);
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
    public ResponseEntity<RestModel> getAllDoneWorks(LoginUser loginUser) {
        logger.debug("get all done works");
        checkRoleIsStudent(loginUser);
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
    public ResponseEntity<RestModel> getUpLoadInfoByWorkId(LoginUser loginUser, @PathVariable String workId) {
        logger.debug("get upload info by work id");
        checkRoleIsStudent(loginUser);
        return ResponseEntity.ok(new RestModel<>(uploadService.getUploadInfoByWorkId(loginUser.getNo(), workId)));
    }

    /**
     * 学生加入群组
     *
     * @param code 邀请码
     * @return ResponseEntity
     */
    @PostMapping("/group")
    public ResponseEntity<Group> addGroup(LoginUser loginUser, @RequestParam String code) {
        logger.debug("add group , code: " + code);
        checkRoleIsStudent(loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.joinGroup(code, loginUser.getNo()));
    }

    /**
     * 退出群组
     *
     * @param groupId 群组ID
     * @return ResponseEntity
     */
    @DeleteMapping("/group/{groupId}")
    public ResponseEntity<Void> dropOutGroup(LoginUser loginUser, @PathVariable String groupId) {
        logger.debug("del group , id: " + groupId);
        checkRoleIsStudent(loginUser);
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
    public ResponseEntity<Void> uploadWork(LoginUser loginUser, @PathVariable String workId, @RequestParam("file") MultipartFile file) {
        logger.debug("upload file , work id: " + workId);
        checkRoleIsStudent(loginUser);
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
    public ResponseEntity<Void> deleteUploadWork(LoginUser loginUser, @PathVariable String workId) {
        logger.debug("delete Upload Work , workId: " + workId);
        checkRoleIsStudent(loginUser);
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
    public ResponseEntity<RestModel> isStudentJoinAnyStudentGroup(LoginUser loginUser) {
        logger.debug("is Student Join Any StudentGroup");
        checkRoleIsStudent(loginUser);
        return ResponseEntity.ok(new RestModel<>(studentGroupService.isHaveGroup(loginUser.getNo())));
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
    @GetMapping("/down/{studentNumber}/{workId}")
    public void downloadFile(@RequestHeader(required = false) String range, @PathVariable String studentNumber, @PathVariable String workId, HttpServletResponse response) throws IOException {
        logger.debug("down file, work id: " + workId);
        Optional<File> fileOptional = fileService.getFile(studentNumber, workId);
        if (fileOptional.isPresent()) {
            File file = fileOptional.get();
            Upload upload = uploadService.getUploadInfoByWorkId(studentNumber, workId);
            FileUtils.breakpointResume(file, upload.getMime(), range, response);
        } else {
            response.sendError(HttpStatus.NOT_FOUND.value(), "文件没有找到");
        }
    }
}
