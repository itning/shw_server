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
import top.yunshu.shw.server.entity.LoginUser;
import top.yunshu.shw.server.entity.RestModel;
import top.yunshu.shw.server.model.WorkModel;
import top.yunshu.shw.server.service.group.GroupService;
import top.yunshu.shw.server.service.upload.UploadService;
import top.yunshu.shw.server.service.work.WorkService;
import top.yunshu.shw.server.util.SessionUtils;

import java.util.List;


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

    @Autowired
    public StudentController(GroupService groupService, WorkService workService, ModelMapper modelMapper, UploadService uploadService) {
        this.groupService = groupService;
        this.workService = workService;
        this.modelMapper = modelMapper;
        this.uploadService = uploadService;
    }

    /**
     * 获取学生所有群组
     *
     * @return ResponseEntity
     */
    @GetMapping("/groups")
    public ResponseEntity<RestModel> getAllGroups() {
        logger.debug("get all student groups");
        LoginUser loginUser = SessionUtils.getAttributeValueFromSession("loginUser", LoginUser.class);
        logger.info("get login user: " + loginUser);
        return ResponseEntity.ok(new RestModel<>(groupService.findStudentAllGroups(loginUser.getNo())));
    }

    /**
     * 获取学生所有未上传作业
     *
     * @return ResponseEntity
     */
    @GetMapping("/works/un_done")
    public ResponseEntity<RestModel> getAllUnDoneWorks() {
        logger.debug("get all un done works");
        LoginUser loginUser = SessionUtils.getAttributeValueFromSession("loginUser", LoginUser.class);
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
    public ResponseEntity<RestModel> getAllDoneWorks() {
        logger.debug("get all done works");
        LoginUser loginUser = SessionUtils.getAttributeValueFromSession("loginUser", LoginUser.class);
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
    public ResponseEntity<RestModel> getUpLoadInfoByWorkId(@PathVariable String workId) {
        logger.debug("get upload info by work id");
        LoginUser loginUser = SessionUtils.getAttributeValueFromSession("loginUser", LoginUser.class);
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
    public ResponseEntity<Void> addGroup(String code) {
        logger.debug("add group , code: " + code);
        LoginUser loginUser = SessionUtils.getAttributeValueFromSession("loginUser", LoginUser.class);
        logger.info("get login user: " + loginUser);
        groupService.joinGroup(code, loginUser.getNo());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 退出群组
     *
     * @param groupId 群组ID
     * @return ResponseEntity
     */
    @DeleteMapping("/group/{groupId}")
    public ResponseEntity<Void> dropOutGroup(@PathVariable String groupId) {
        logger.debug("del group , id: " + groupId);
        LoginUser loginUser = SessionUtils.getAttributeValueFromSession("loginUser", LoginUser.class);
        logger.info("get login user: " + loginUser);
        groupService.dropOutGroup(groupId, loginUser.getNo());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/work/{workId}")
    public ResponseEntity<Void> uploadWork(@PathVariable String workId, @RequestParam("file") MultipartFile file) {
        System.out.println(workId);
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
