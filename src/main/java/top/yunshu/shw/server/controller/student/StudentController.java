package top.yunshu.shw.server.controller.student;

import org.jasig.cas.client.validation.AssertionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import top.yunshu.shw.server.entity.LoginUser;
import top.yunshu.shw.server.entity.RestModel;
import top.yunshu.shw.server.service.group.GroupService;
import top.yunshu.shw.server.util.SessionUtils;

import java.util.Arrays;


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

    @Autowired
    public StudentController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/groups")
    public ResponseEntity<RestModel> getAllGroups() {
        logger.debug("get all student groups");
        LoginUser loginUser = SessionUtils.getAttributeValueFromSession("loginUser", LoginUser.class);
        logger.info("get login user: " + loginUser);
        return ResponseEntity.ok(new RestModel<>(HttpStatus.OK.value(), "查询成功", groupService.findStudentAllGroups(loginUser.getId())));
    }
}
