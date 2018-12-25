package top.yunshu.shw.server.controller.teacher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import top.yunshu.shw.server.service.group.GroupService;

/**
 * 教师控制器
 *
 * @author itning
 * @date 2018/12/21
 */
@RestController
public class TeacherController {
    private static final Logger logger = LoggerFactory.getLogger(TeacherController.class);
    private final GroupService groupService;

    @Autowired
    public TeacherController(GroupService groupService) {
        this.groupService = groupService;
    }
}
