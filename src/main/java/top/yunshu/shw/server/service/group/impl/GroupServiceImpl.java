package top.yunshu.shw.server.service.group.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yunshu.shw.server.dao.GroupDao;
import top.yunshu.shw.server.dao.StudentGroupDao;
import top.yunshu.shw.server.entity.StudentGroup;
import top.yunshu.shw.server.entity.StudentGroupPrimaryKey;
import top.yunshu.shw.server.entity.Group;
import top.yunshu.shw.server.exception.NoCodeException;
import top.yunshu.shw.server.exception.NoSuchIdException;
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
    public List<Group> findStudentAllGroups(String id) {
        return studentGroupDao.findAllByStudentNumber(id).parallelStream()
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
                , teacherId, UUID.randomUUID().toString().replace("-", ""));
    }

    @Override
    public Group joinGroup(String code, String studentId) throws NoCodeException {
        if (!groupDao.existsAllByCode(code)) {
            throw new NoCodeException("NO CODE");
        }
        Group group = groupDao.findByCode(code);
        StudentGroup studentGroup = new StudentGroup(studentId, group.getId());
        studentGroupDao.save(studentGroup);
        return group;
    }

    @Override
    public void dropOutGroup(String groupId, String studentId) {
        StudentGroupPrimaryKey studentGroupPrimaryKey = new StudentGroupPrimaryKey(groupId, studentId);
        studentGroupDao.deleteById(studentGroupPrimaryKey);
    }


    @Override
    public void deleteGroup(String id) {
        groupDao.deleteById(id);
    }

    @Override
    public Group updateGroup(String id, String name) {
        Group group = groupDao.findById(id).orElseThrow(() -> new NoSuchIdException("id: " + id + " not found"));
        group.setGroupName(name);
        return groupDao.save(group);
    }
}
