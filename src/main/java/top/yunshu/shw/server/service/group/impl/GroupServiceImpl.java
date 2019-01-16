package top.yunshu.shw.server.service.group.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import top.yunshu.shw.server.dao.GroupDao;
import top.yunshu.shw.server.dao.StudentGroupDao;
import top.yunshu.shw.server.entity.Group;
import top.yunshu.shw.server.entity.StudentGroup;
import top.yunshu.shw.server.exception.NoSuchFiledValueException;
import top.yunshu.shw.server.exception.NullFiledException;
import top.yunshu.shw.server.service.group.GroupService;

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
    private final StudentGroupDao studentGroupDao;
    private final GroupDao groupDao;

    @Autowired
    public GroupServiceImpl(GroupDao groupDao, StudentGroupDao studentGroupDao) {
        this.groupDao = groupDao;
        this.studentGroupDao = studentGroupDao;
    }

    @Override
    public List<Group> findStudentAllGroups(String studentNumber) {
        return studentGroupDao.findAllByStudentNumber(studentNumber).parallelStream()
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
    }

    @Override
    public List<Group> findTeacherAllGroups(String id) {
        return groupDao.findByTeacherNumber(id);
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
            throw new NoSuchFiledValueException("id: " + id + " not found", HttpStatus.NOT_FOUND);
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
