package top.yunshu.shw.server.controller.student;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.debug("get all done works");
        LoginUser loginUser = SessionUtils.getAttributeValueFromSession("loginUser", LoginUser.class);
        logger.info("get login user: " + loginUser);
        List<WorkModel> workModels = modelMapper.map(workService.getStudentDoneWork(loginUser.getNo()), new TypeToken<List<WorkModel>>() {
        }.getType());
        return ResponseEntity.ok(new RestModel<>(workModels));
    }

    @GetMapping("/upload/{workId}")
    public ResponseEntity<RestModel> getUpLoadInfoByWorkId(@PathVariable String workId) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.debug("get upload info by work id");
        LoginUser loginUser = SessionUtils.getAttributeValueFromSession("loginUser", LoginUser.class);
        logger.info("get login user: " + loginUser);
        return ResponseEntity.ok(new RestModel<>(uploadService.getUploadInfoByWorkId(loginUser.getNo(), workId)));
    }
}
