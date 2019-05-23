package top.yunshu.shw.server.service.group.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import top.yunshu.shw.server.dao.GroupDao;
import top.yunshu.shw.server.dao.StudentGroupDao;
import top.yunshu.shw.server.dao.UploadDao;
import top.yunshu.shw.server.dao.WorkDao;
import top.yunshu.shw.server.entity.Group;
import top.yunshu.shw.server.entity.StudentGroup;
import top.yunshu.shw.server.entity.Upload;
import top.yunshu.shw.server.entity.Work;
import top.yunshu.shw.server.exception.NoSuchFiledValueException;
import top.yunshu.shw.server.exception.NullFiledException;
import top.yunshu.shw.server.service.group.GroupService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 群组服务实现类
 *
 * @author shulu
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class GroupServiceImpl implements GroupService {
    private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

    private final StudentGroupDao studentGroupDao;
    private final GroupDao groupDao;
    private final WorkDao workDao;
    private final UploadDao uploadDao;

    @Autowired
    public GroupServiceImpl(GroupDao groupDao, StudentGroupDao studentGroupDao, WorkDao workDao, UploadDao uploadDao) {
        this.groupDao = groupDao;
        this.studentGroupDao = studentGroupDao;
        this.workDao = workDao;
        this.uploadDao = uploadDao;
    }

    @Cacheable(cacheNames = "groupOfStudent", key = "#studentNumber+#pageable")
    @Override
    public Page<Group> findStudentAllGroups(String studentNumber, Pageable pageable) {
        List<Group> groupList = studentGroupDao.findAllByStudentNumber(studentNumber, pageable)
                .getContent()
                .parallelStream()
                .map(s -> groupDao.findById(s.getGroupID()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .peek(group -> {
                    //学生获取所有群组时间应为加入群组的时间而不是群组创建的时间
                    StudentGroup studentGroup = studentGroupDao.findByStudentNumberAndGroupID(studentNumber, group.getId());
                    group.setGmtCreate(studentGroup.getGmtCreate());
                    group.setGmtModified(studentGroup.getGmtModified());
                })
                .collect(Collectors.toList());
        return new PageImpl<>(groupList, pageable, studentGroupDao.countAllByStudentNumber(studentNumber));
    }

    @Cacheable(cacheNames = "groupOfTeacher", key = "#teacherNumber+#pageable")
    @Override
    public Page<Group> findTeacherAllGroups(String teacherNumber, Pageable pageable) {
        return groupDao.findByTeacherNumber(teacherNumber, pageable);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "groupOfTeacher", key = "'regex:'+#teacherId+'*'"),
            @CacheEvict(cacheNames = "isHaveAnyGroup", key = "#teacherId")
    })
    @Override
    public Group createGroup(String groupName, String teacherName, String teacherId) {
        String id = UUID.randomUUID().toString().replace("-", "");
        return groupDao.save(new Group(id, groupName, teacherName, teacherId, id));
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "groupOfStudent", key = "'regex:'+#studentId+'*'"),
            //学生加入群组,教师作业详情缓存清空
            @CacheEvict(cacheNames = "workDetail", allEntries = true),
            @CacheEvict(cacheNames = "studentDoneWork", key = "'regex:'+#studentId+'*'"),
            @CacheEvict(cacheNames = "studentUndoneWork", key = "'regex:'+#studentId+'*'")
    })
    @Override
    public Group joinGroup(String code, String studentId) {
        if (!groupDao.existsAllByCode(code)) {
            throw new NoSuchFiledValueException("邀请码过期或不存在", HttpStatus.NOT_FOUND);
        }
        if (studentGroupDao.findByStudentNumberAndGroupID(studentId, code) != null) {
            throw new NoSuchFiledValueException("已加入过该群", HttpStatus.CONFLICT);
        }
        Group group = groupDao.findByCode(code);
        StudentGroup studentGroup = new StudentGroup(studentId, group.getId());
        studentGroupDao.save(studentGroup);
        return group;
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "groupOfStudent", key = "'regex:'+#studentId+'*'"),
            //学生退出群组,教师作业详情缓存清空
            @CacheEvict(cacheNames = "workDetail", allEntries = true),
            @CacheEvict(cacheNames = "studentDoneWork", key = "'regex:'+#studentId+'*'"),
            @CacheEvict(cacheNames = "studentUndoneWork", key = "'regex:'+#studentId+'*'"),
    })
    @Override
    public void dropOutGroup(String groupId, String studentId) {
        if (!groupDao.existsById(groupId)) {
            throw new NoSuchFiledValueException("ID为: " + groupId + " 的群不存在", HttpStatus.NOT_FOUND);
        }
        StudentGroup studentGroup = studentGroupDao.findByStudentNumberAndGroupID(studentId, groupId);
        if (studentGroup == null) {
            throw new NullFiledException("找不到群记录,刷新重试", HttpStatus.NOT_FOUND);
        }
        //学生群组删除
        studentGroupDao.delete(studentGroup);
        //TODO 历史上传文件删除
        //上传记录删除
        List<Upload> willDeleteUploadList = workDao.findAllByGroupId(groupId).stream()
                .map(work -> uploadDao.findUploadByStudentIdAndWorkId(studentId, work.getId()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        uploadDao.deleteAll(willDeleteUploadList);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "groupOfStudent", allEntries = true),
            @CacheEvict(cacheNames = "groupOfTeacher", key = "'regex:'+#teacherNumber+'*'"),
            @CacheEvict(cacheNames = "isHaveAnyGroup", key = "#teacherNumber"),
            @CacheEvict(cacheNames = "findGroupNameByGroupId", key = "#id"),
            @CacheEvict(cacheNames = "findTeacherNameById", key = "#id"),
            @CacheEvict(cacheNames = "studentDoneWork", allEntries = true),
            @CacheEvict(cacheNames = "studentUndoneWork", allEntries = true),
            @CacheEvict(cacheNames = "work", key = "'regex:'+#teacherNumber+'*'")
    })
    @Override
    public void deleteGroup(String id, String teacherNumber) {
        Group group = groupDao.findById(id).orElseThrow(() -> new NoSuchFiledValueException("id: " + id + " not found", HttpStatus.NOT_FOUND));
        if (!group.getTeacherNumber().equals(teacherNumber)) {
            throw new NoSuchFiledValueException("id: " + id + " not found", HttpStatus.NOT_FOUND);
        }
        List<Work> workList = workDao.findAllByGroupId(id);
        List<Upload> willDeleteUploadList = workList.stream()
                .flatMap(work -> uploadDao.findAllByWorkId(work.getId()).stream())
                .collect(Collectors.toList());
        uploadDao.deleteAll(willDeleteUploadList);
        studentGroupDao.findAllByGroupID(id).forEach(studentGroupDao::delete);
        workList.forEach(workDao::delete);
        groupDao.delete(group);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "groupOfStudent", allEntries = true),
            @CacheEvict(cacheNames = "groupOfTeacher", key = "'regex:'+#teacherNumber+'*'"),
            @CacheEvict(cacheNames = "findGroupNameByGroupId", key = "#id"),
            @CacheEvict(cacheNames = "studentDoneWork", allEntries = true),
            @CacheEvict(cacheNames = "studentUndoneWork", allEntries = true),
            @CacheEvict(cacheNames = "work", key = "'regex:'+#teacherNumber+'*'")
    })
    @Override
    public Group updateGroup(String id, String name, String teacherNumber) {
        Group group = groupDao.findById(id).orElseThrow(() -> new NoSuchFiledValueException("id: " + id + " not found ", HttpStatus.NOT_FOUND));
        if (!group.getTeacherNumber().equals(teacherNumber)) {
            throw new NoSuchFiledValueException("Forbidden", HttpStatus.FORBIDDEN);
        }
        group.setGroupName(name);
        return groupDao.save(group);
    }

    @Cacheable(cacheNames = "findGroupNameByGroupId", key = "#groupId")
    @Override
    public String findGroupNameByGroupId(String groupId) {
        if (!groupDao.existsById(groupId)) {
            throw new NoSuchFiledValueException("群ID: " + groupId + "不存在", HttpStatus.NOT_FOUND);
        }
        return groupDao.findNameById(groupId);
    }

    @Cacheable(cacheNames = "findTeacherNameById", key = "#groupId")
    @Override
    public String findTeacherNameById(String groupId) {
        if (!groupDao.existsById(groupId)) {
            throw new NoSuchFiledValueException("群ID: " + groupId + "不存在", HttpStatus.NOT_FOUND);
        }
        return groupDao.findTeacherNameById(groupId);
    }

    @Cacheable(cacheNames = "isHaveAnyGroup", key = "#teacherNumber")
    @Override
    public boolean isHaveAnyGroup(String teacherNumber) {
        return !groupDao.findByTeacherNumber(teacherNumber).isEmpty();
    }
}
