package top.yunshu.shw.server.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.yunshu.shw.server.ShwServerApplication;
import top.yunshu.shw.server.entity.Work;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShwServerApplication.class)
public class WorkDaoTest {
    @Autowired
    private WorkDao workDao;

    @Test
    public void findAllByGroupIdAndEnabled() {
        List<Work> list = workDao.findAllByGroupIdAndEnabled("1", false);
        System.out.println(list);
    }
}
