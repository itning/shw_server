package top.yunshu.shw.server.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.yunshu.shw.server.ShwServerApplication;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShwServerApplication.class)
public class StudentGroupDaoTest {
    @Autowired
    private StudentGroupDao studentGroupDao;

    @Test
    public void findGroupIdByStudentNumber() {
        assertNotNull(studentGroupDao.findGroupIdByStudentNumber("201601010317"));
    }
}