package top.yunshu.shw.server.controller.teacher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.yunshu.shw.server.entity.Group;
import top.yunshu.shw.server.entity.LoginUser;
import top.yunshu.shw.server.entity.RestModel;
import top.yunshu.shw.server.service.group.GroupService;
import top.yunshu.shw.server.util.JwtUtils;

/**
 * 教师控制器
 *
 * @author itning
 * @author shulu
 * @date 2018/12/21
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    private static final Logger logger = LoggerFactory.getLogger(TeacherController.class);
    private final GroupService groupService;

    @Autowired
    public TeacherController(GroupService groupService) {
        this.groupService = groupService;
    }

    /**
     * 教师获取创建的所有群组
     *
     * @return ResponseEntity
     */
    @GetMapping("/groups")
    public ResponseEntity<RestModel> getTeacherCreateGroups(@RequestHeader("Authorization") String authorization) {
        logger.debug("get all teacher groups");
        LoginUser loginUser = JwtUtils.getLoginUser(authorization);
        logger.info("get login user: " + loginUser);
        return ResponseEntity.ok(new RestModel<>(groupService.findTeacherAllGroups(loginUser.getNo())));
    }

    /**
     * 新建群组
     *
     * @param groupName 教师添加的组名
     * @return ResponseEntity
     */
    @PostMapping("/group")
    public ResponseEntity<Group> addGroup(@RequestHeader("Authorization") String authorization, String groupName) {
        logger.debug("add group , groupName: " + groupName);
        LoginUser loginUser = JwtUtils.getLoginUser(authorization);
        logger.info("get login user: " + loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.createGroup(groupName, loginUser.getName(), loginUser.getNo()));
    }

    /**
     * 更新群组
     *
     * @param name 修改的新群组名
     * @param id   群组id
     * @return ResponseEntity
     */
    @PatchMapping("/group/{id}/{name}")
    public ResponseEntity<Void> updateGroupName(@RequestHeader("Authorization") String authorization, @PathVariable String id, @PathVariable String name) {
        logger.debug("update group , name: " + name);
        LoginUser loginUser = JwtUtils.getLoginUser(authorization);
        logger.info("get login user: " + loginUser);
        groupService.updateGroup(id, name);
        return ResponseEntity.noContent().build();
    }

    /**
     * 删除群组
     *
     * @param id 删除的群组名
     */
    @DeleteMapping("/group/{id}")
    public ResponseEntity<Void> deleteGroup(@RequestHeader("Authorization") String authorization, @PathVariable String id) {
        logger.debug("delete group , id: " + id);
        LoginUser loginUser = JwtUtils.getLoginUser(authorization);
        logger.info("get login user: " + loginUser);
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }
}
