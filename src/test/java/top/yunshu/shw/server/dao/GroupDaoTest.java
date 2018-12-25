package top.yunshu.shw.server.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.yunshu.shw.server.ShwServerApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShwServerApplication.class)
public class GroupDaoTest {
    @Autowired
    private GroupDao groupDao;

    @Test
    public void findByCode() {
    }

    @Test
    public void findByTeacherNumber() {
    }

    @Test
    public void existsAllByCode() {
        boolean b = groupDao.existsAllByCode("2");
        System.out.println(b);
    }
}