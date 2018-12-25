package top.yunshu.shw_server.service.group.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yunshu.shw_server.dao.GroupDao;
import top.yunshu.shw_server.dao.StudentDao;
import top.yunshu.shw_server.entity.group.Group;
import top.yunshu.shw_server.entity.student.Student;
import top.yunshu.shw_server.entity.student.StudentPrimaryKey;
import top.yunshu.shw_server.exception.NoCodeException;
import top.yunshu.shw_server.service.group.GroupService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author shulu
 */
@Service
public class GroupServiceImpl implements GroupService {
    private final StudentDao studentDao;
    private final GroupDao groupDao;

    @Autowired
    public GroupServiceImpl(GroupDao groupDao, StudentDao studentDao) {
        this.groupDao = groupDao;
        this.studentDao = studentDao;
    }

    @Override
    public List<Group> findStudentAllGroups(String id) {
        return studentDao.findAllByStudentNumber(id).parallelStream()
                .map(s -> groupDao.findById(s.getGroupID()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public List<Group> findTeacherAllGroups(String id) {
        return groupDao.findByTeacherNumber(id);
    }

    @Override
    public Group createGroup(String groupName, String teacherName, String teacherId) {
        return new Group(UUID.randomUUID().toString().replace("-", "")
                , groupName
                , teacherName
                , teacherId, UUID.randomUUID().toString().replace("-", "")
                , new Date()
                , new Date());
    }

    @Override
    public Group joinGroup(String code, String studentId) throws NoCodeException {
        if (!groupDao.existsAllByCode(code)) {
            throw new NoCodeException("NO CODE");
        }
        Group group = groupDao.findByCode(code);
        Student student = new Student(studentId, group.getId(), new Date());
        studentDao.save(student);
        return group;
    }

    @Override
    public void dropOutGroup(String groupId, String studentId) {
        StudentPrimaryKey studentPrimaryKey = new StudentPrimaryKey(groupId, studentId);
        studentDao.deleteById(studentPrimaryKey);
    }


    @Override
    public void deleteGroup(String id) {
        groupDao.deleteById(id);
    }

    @Override
    public Group updateGroup(String id, String name) {
        Group group = groupDao.findById(id).get();
        group.setGroupName(name);
        return groupDao.save(group);
    }
}
