package top.itning.shw_server.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.yunshu.shw_server.ShwServerApplication;
import top.yunshu.shw_server.dao.StudentDao;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShwServerApplication.class)
public class StudentDaoTest {
    @Autowired
    private StudentDao studentDao;

    @Test
    public void test() {
        studentDao.findAllByStudentNumber("1").forEach(System.out::println);
    }

}
