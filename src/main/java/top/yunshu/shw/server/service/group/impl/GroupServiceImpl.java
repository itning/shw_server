package top.yunshu.shw.server.service.group.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import top.yunshu.shw.server.dao.GroupDao;
import top.yunshu.shw.server.dao.StudentGroupDao;
import top.yunshu.shw.server.entity.Group;
import top.yunshu.shw.server.entity.StudentGroup;
import top.yunshu.shw.server.exception.BaseException;
import top.yunshu.shw.server.exception.NoSuchFiledValueException;
import top.yunshu.shw.server.exception.NullFiledException;
import top.yunshu.shw.server.service.group.GroupService;
import top.yunshu.shw.server.util.PageContentUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 群组服务实现类
 *
 * @author shulu
 */
@Service
public class GroupServiceImpl implements GroupService {
    private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

    private final StudentGroupDao studentGroupDao;
    private final GroupDao groupDao;

    @Autowired
    public GroupServiceImpl(GroupDao groupDao, StudentGroupDao studentGroupDao) {
        this.groupDao = groupDao;
        this.studentGroupDao = studentGroupDao;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<Group> findStudentAllGroups(String studentNumber, Pageable pageable) {
        Page<?> studentGroupPage = studentGroupDao.findAllByStudentNumber(studentNumber, pageable);
        try {
            List<StudentGroup> studentGroupList = (List<StudentGroup>) PageContentUtils.getContentField().get(studentGroupPage);
            List<Group> groupList = studentGroupList.parallelStream()
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
            PageContentUtils.getContentField().set(studentGroupPage, groupList);
        } catch (IllegalAccessException e) {
            logger.error("findStudentAllGroups By Pageable Error: ", e);
            throw new BaseException("获取失败,联系管理员", HttpStatus.INTERNAL_SERVER_ERROR) {
            };
        }
        return (Page<Group>) studentGroupPage;
    }

    @Override
    public Page<Group> findTeacherAllGroups(String teacherNumber, Pageable pageable) {
        return groupDao.findByTeacherNumber(teacherNumber, pageable);
    }

    @Override
    public Group createGroup(String groupName, String teacherName, String teacherId) {
        String id = UUID.randomUUID().toString().replace("-", "");
        return groupDao.save(new Group(id, groupName, teacherName, teacherId, id));
    }

    @Override
    public Group joinGroup(String code, String studentId) {
        if (!groupDao.existsAllByCode(code)) {
            throw new NoSuchFiledValueException("群ID不存在", HttpStatus.NOT_FOUND);
        }
        if (studentGroupDao.findByStudentNumberAndGroupID(studentId, code) != null) {
            throw new NoSuchFiledValueException("已加入过该群", HttpStatus.CONFLICT);
        }
        Group group = groupDao.findByCode(code);
        StudentGroup studentGroup = new StudentGroup(studentId, group.getId());
        studentGroupDao.save(studentGroup);
        return group;
    }

    @Override
    public void dropOutGroup(String groupId, String studentId) {
        if (!groupDao.existsById(groupId)) {
            throw new NoSuchFiledValueException("ID为: " + groupId + " 的群不存在", HttpStatus.NOT_FOUND);
        }
        StudentGroup studentGroup = studentGroupDao.findByStudentNumberAndGroupID(studentId, groupId);
        if (studentGroup == null) {
            throw new NullFiledException("找不到群记录,刷新重试", HttpStatus.NOT_FOUND);
        }
        studentGroupDao.delete(studentGroup);
    }


    @Override
    public void deleteGroup(String id, String teacherNumber) {
        Group group = groupDao.findById(id).orElseThrow(() -> new NoSuchFiledValueException("id: " + id + " not found", HttpStatus.NOT_FOUND));
        if (!group.getTeacherNumber().equals(teacherNumber)) {
            throw new NoSuchFiledValueException("id: " + id + " not found", HttpStatus.NOT_FOUND);
        }
        groupDao.delete(group);
    }

    @Override
    public Group updateGroup(String id, String name, String teacherNumber) {
        Group group = groupDao.findById(id).orElseThrow(() -> new NoSuchFiledValueException("id: " + id + " not found ", HttpStatus.NOT_FOUND));
        if (!group.getTeacherNumber().equals(teacherNumber)) {
            throw new NoSuchFiledValueException("Forbidden", HttpStatus.FORBIDDEN);
        }
        group.setGroupName(name);
        return groupDao.save(group);
    }

    @Override
    public String findGroupNameByGroupId(String groupId) {
        if (!groupDao.existsById(groupId)) {
            throw new NoSuchFiledValueException("群ID: " + groupId + "不存在", HttpStatus.NOT_FOUND);
        }
        return groupDao.findNameById(groupId);
    }

    @Override
    public String findTeacherNameById(String groupId) {
        if (!groupDao.existsById(groupId)) {
            throw new NoSuchFiledValueException("群ID: " + groupId + "不存在", HttpStatus.NOT_FOUND);
        }
        return groupDao.findTeacherNameById(groupId);
    }

    @Override
    public boolean isHaveAnyGroup(String teacherNumber) {
        return !groupDao.findByTeacherNumber(teacherNumber).isEmpty();
    }
}
