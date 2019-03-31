package top.yunshu.shw.server.service.student.group.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.yunshu.shw.server.dao.StudentGroupDao;
import top.yunshu.shw.server.service.student.group.StudentGroupService;

import javax.transaction.Transactional;

/**
 * 学生群组服务接口实现
 *
 * @author shulu
 * @author itning
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class StudentGroupServiceImpl implements StudentGroupService {

    private final StudentGroupDao studentGroupDao;

    @Autowired
    public StudentGroupServiceImpl(StudentGroupDao studentGroupDao) {
        this.studentGroupDao = studentGroupDao;
    }

    @Override
    public boolean isHaveGroup(String studentNumber) {
        return studentGroupDao.existsAllByStudentNumber(studentNumber);
    }
}
