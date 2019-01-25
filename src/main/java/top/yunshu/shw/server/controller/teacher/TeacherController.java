package top.yunshu.shw.server.controller.teacher;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.yunshu.shw.server.entity.*;
import top.yunshu.shw.server.exception.FileException;
import top.yunshu.shw.server.model.WorkModel;
import top.yunshu.shw.server.service.config.ConfigService;
import top.yunshu.shw.server.service.file.FileService;
import top.yunshu.shw.server.service.group.GroupService;
import top.yunshu.shw.server.service.upload.UploadService;
import top.yunshu.shw.server.service.work.WorkService;
import top.yunshu.shw.server.util.FileUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static top.yunshu.shw.server.util.RoleUtils.checkRoleIsTeacher;

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
    public static volatile Map<String, String> packMap = new HashMap<>(16);

    private final GroupService groupService;

    private final WorkService workService;

    private final ModelMapper modelMapper;

    private final FileService fileService;

    private final UploadService uploadService;

    private final ConfigService configService;

    @Autowired
    public TeacherController(GroupService groupService, WorkService workService, ModelMapper modelMapper, FileService fileService, UploadService uploadService, ConfigService configService) {
        this.groupService = groupService;
        this.workService = workService;
        this.modelMapper = modelMapper;
        this.fileService = fileService;
        this.uploadService = uploadService;
        this.configService = configService;
    }

    /**
     * 教师获取创建的所有群组
     *
     * @return ResponseEntity
     */
    @GetMapping("/groups")
    public ResponseEntity<RestModel> getTeacherCreateGroups(LoginUser loginUser) {
        checkRoleIsTeacher(loginUser);
        return ResponseEntity.ok(new RestModel<>(groupService.findTeacherAllGroups(loginUser.getNo())));
    }

    /**
     * 新建群组
     *
     * @param groupName 教师添加的组名
     * @return ResponseEntity
     */
    @PostMapping("/group")
    public ResponseEntity<Group> addGroup(LoginUser loginUser, @RequestParam String groupName) {
        logger.debug("add group , groupName: " + groupName);
        checkRoleIsTeacher(loginUser);
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
    public ResponseEntity<Void> updateGroupName(LoginUser loginUser, @PathVariable String id, @PathVariable String name) {
        logger.debug("update group , name: " + name);
        checkRoleIsTeacher(loginUser);
        groupService.updateGroup(id, name, loginUser.getNo());
        return ResponseEntity.noContent().build();
    }

    /**
     * 删除群组
     *
     * @param id 删除的群组名
     */
    @DeleteMapping("/group/{id}")
    public ResponseEntity<Void> deleteGroup(LoginUser loginUser, @PathVariable String id) {
        logger.debug("delete group , id: " + id);
        checkRoleIsTeacher(loginUser);
        groupService.deleteGroup(id, loginUser.getNo());
        return ResponseEntity.noContent().build();
    }

    /**
     * 查询教师是否有群组
     *
     * @return ResponseEntity
     */
    @GetMapping("/group/exist")
    public ResponseEntity<RestModel> isTeacherHaveAnyGroup(LoginUser loginUser) {
        logger.debug("is Teacher Have Any Group");
        checkRoleIsTeacher(loginUser);
        return ResponseEntity.ok(new RestModel<>(groupService.isHaveAnyGroup(loginUser.getNo())));
    }

    /**
     * 获取教师所有作业
     *
     * @return ResponseEntity
     */
    @GetMapping("/works")
    public ResponseEntity<RestModel> getTeacherWorks(LoginUser loginUser) {
        logger.debug("get teacher works");
        checkRoleIsTeacher(loginUser);
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
    public ResponseEntity<RestModel> getTeacherWork(LoginUser loginUser, @PathVariable String groupId) {
        logger.debug("get teacher work");
        checkRoleIsTeacher(loginUser);
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
    public ResponseEntity<Work> addWork(LoginUser loginUser, @RequestParam String workName, @RequestParam String groupId) {
        logger.debug("add work , workName: " + workName);
        checkRoleIsTeacher(loginUser);
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
    public ResponseEntity<Void> updateWorkEnabled(LoginUser loginUser, @PathVariable String workId, @PathVariable String enabled) {
        logger.debug("up work , work id: " + workId + " enabled: " + enabled);
        checkRoleIsTeacher(loginUser);
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
    public ResponseEntity<Void> deleteWork(LoginUser loginUser, @PathVariable String workId) {
        logger.debug("del work , work id: " + workId);
        checkRoleIsTeacher(loginUser);
        workService.delWork(workId, loginUser.getNo());
        return ResponseEntity.noContent().build();
    }

    /**
     * 获取作业详情
     *
     * @param workId 作业ID
     * @return ResponseEntity
     */
    @GetMapping("/work_detail/{workId}")
    public ResponseEntity<RestModel> getTeacherWorkDetails(LoginUser loginUser, @PathVariable String workId) {
        logger.debug("get teacher work detail, work id " + workId);
        checkRoleIsTeacher(loginUser);
        return ResponseEntity.ok(new RestModel<>(workService.getWorkDetailByWorkId(loginUser.getNo(), workId)));
    }

    /**
     * 打包
     *
     * @param workId 作业ID
     * @return ResponseEntity
     */
    @GetMapping("/pack/{workId}")
    public ResponseEntity<RestModel> pack(LoginUser loginUser, @PathVariable String workId) {
        logger.debug("get teacher work detail, work id " + workId);
        checkRoleIsTeacher(loginUser);
        String s = packMap.get(workId);
        if (s == null) {
            fileService.unpackFiles(workId);
            return ResponseEntity.ok(new RestModel<>("START"));
        } else {
            if ("OK".equals(s)) {
                packMap.remove(workId);
                return ResponseEntity.ok(new RestModel<>("OK"));
            } else if (s.contains("ERROR")) {
                packMap.remove(workId);
                return ResponseEntity.ok(new RestModel<>(s));
            } else {
                return ResponseEntity.ok(new RestModel<>(s));
            }
        }
    }

    /**
     * 下载所有
     *
     * @param range    请求头
     * @param workId   作业ID
     * @param response {@link HttpServletResponse}
     */
    @GetMapping("/down/{workId}")
    public void downloadAllFile(@RequestHeader(required = false) String range, @PathVariable String workId, HttpServletResponse response) {
        logger.debug("down file, work id: " + workId);
        long sum = uploadService.getUploadSum(workId);
        String tempFilePath = configService.getConfig(Config.ConfigKey.TEMP_DIR).orElse(System.getProperty("java.io.tmpdir")) + File.separator + workId + sum + ".zip";
        File file = new File(tempFilePath);
        if (file.canRead()) {
            FileUtils.breakpointResume(file, "application/octet-stream", range, response);
        } else {
            throw new FileException("文件不可读", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
