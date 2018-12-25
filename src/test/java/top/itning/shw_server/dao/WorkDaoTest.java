package top.itning.shw_server.dao;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.yunshu.shw_server.ShwServerApplication;
import top.yunshu.shw_server.dao.StudentDao;
import top.yunshu.shw_server.dao.WorkDao;
import top.yunshu.shw_server.entity.Work;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShwServerApplication.class)
public class WorkDaoTest {
    @Autowired
    private WorkDao workDao;

    @Test
    public void test() {
        Work work = new Work();
        work.setId("1");
        work.setGroupId("2");
        work.setWorkName("3");
        workDao.save(work);
    }

}
