package top.yunshu.shw_server.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
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