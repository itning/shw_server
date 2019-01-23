package top.yunshu.shw.server.controller.teacher;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.yunshu.shw.server.entity.Group;
import top.yunshu.shw.server.entity.LoginUser;
import top.yunshu.shw.server.entity.RestModel;
import top.yunshu.shw.server.entity.Work;
import top.yunshu.shw.server.model.WorkModel;
import top.yunshu.shw.server.service.group.GroupService;
import top.yunshu.shw.server.service.work.WorkService;
import top.yunshu.shw.server.util.JwtUtils;

import java.util.List;

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

    private final WorkService workService;

    private final ModelMapper modelMapper;

    @Autowired
    public TeacherController(GroupService groupService, WorkService workService, ModelMapper modelMapper) {
        this.groupService = groupService;
        this.workService = workService;
        this.modelMapper = modelMapper;
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
        groupService.updateGroup(id, name, loginUser.getNo());
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
        groupService.deleteGroup(id, loginUser.getNo());
        return ResponseEntity.noContent().build();
    }

    /**
     * 查询教师是否有群组
     *
     * @return ResponseEntity
     */
    @GetMapping("/group/exist")
    public ResponseEntity<RestModel> isTeacherHaveAnyGroup(@RequestHeader("Authorization") String authorization) {
        logger.debug("is Teacher Have Any Group");
        LoginUser loginUser = JwtUtils.getLoginUser(authorization);
        logger.info("get login user: " + loginUser);
        return ResponseEntity.ok(new RestModel<>(groupService.isHaveAnyGroup(loginUser.getNo())));
    }

    /**
     * 获取教师所有作业
     *
     * @return ResponseEntity
     */
    @GetMapping("/works")
    public ResponseEntity<RestModel> getTeacherWorks(@RequestHeader("Authorization") String authorization) {
        logger.debug("get teacher works");
        LoginUser loginUser = JwtUtils.getLoginUser(authorization);
        logger.info("get login user: " + loginUser);
        List<WorkModel> workModels = modelMapper.map(workService.getTeacherAllWork(loginUser.getNo()), new TypeToken<List<WorkModel>>() {
        }.getType());
        return ResponseEntity.ok(new RestModel<>(workModels));
    }

    /**
     * 根据群ID获取作业
     *
     * @param groupId 群ID
     * @return ResponseEntity
     */
    @GetMapping("/work/{groupId}")
    public ResponseEntity<RestModel> getTeacherWork(@RequestHeader("Authorization") String authorization, @PathVariable String groupId) {
        logger.debug("get teacher work");
        LoginUser loginUser = JwtUtils.getLoginUser(authorization);
        logger.info("get login user: " + loginUser);
        List<WorkModel> workModels = modelMapper.map(workService.getTeacherWork(loginUser.getNo(), groupId), new TypeToken<List<WorkModel>>() {
        }.getType());
        return ResponseEntity.ok(new RestModel<>(workModels));
    }

    /**
     * 添加作业
     *
     * @param workName 作业名
     * @param groupId  群ID
     * @return ResponseEntity
     */
    @PostMapping("/work")
    public ResponseEntity<Work> addWork(@RequestHeader("Authorization") String authorization, String workName, String groupId) {
        logger.debug("add work , workName: " + workName);
        LoginUser loginUser = JwtUtils.getLoginUser(authorization);
        logger.info("get login user: " + loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(workService.createWork(workName, groupId, "", true));
    }

    /**
     * 更新作业启用状态
     *
     * @param workId  作业ID
     * @param enabled 开启状态
     * @return ResponseEntity
     */
    @PatchMapping("/work/{workId}/{enabled}")
    public ResponseEntity<Void> updateWorkEnabled(@RequestHeader("Authorization") String authorization, @PathVariable String workId, @PathVariable String enabled) {
        logger.debug("up work , work id: " + workId + " enabled: " + enabled);
        LoginUser loginUser = JwtUtils.getLoginUser(authorization);
        logger.info("get login user: " + loginUser);
        workService.changeEnabledWord(workId, Boolean.parseBoolean(enabled));
        return ResponseEntity.noContent().build();
    }

    /**
     * 删除作业
     *
     * @param workId 作业ID
     * @return ResponseEntity
     */
    @DeleteMapping("/work/{workId}")
    public ResponseEntity<Void> deleteWork(@RequestHeader("Authorization") String authorization, @PathVariable String workId) {
        logger.debug("del work , work id: " + workId);
        LoginUser loginUser = JwtUtils.getLoginUser(authorization);
        logger.info("get login user: " + loginUser);
        workService.delWork(workId, loginUser.getNo());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/work_detail/{workId}")
    public ResponseEntity<RestModel> getTeacherWorkDetails(@RequestHeader("Authorization") String authorization, @PathVariable String workId) {
        logger.debug("get teacher work detail, work id " + workId);
        LoginUser loginUser = JwtUtils.getLoginUser(authorization);
        logger.info("get login user: " + loginUser);
        return ResponseEntity.ok(new RestModel<>(workService.getWorkDetailByWorkId(loginUser.getNo(), workId)));
    }
}
