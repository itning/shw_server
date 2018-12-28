package top.yunshu.shw.server.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.yunshu.shw.server.ShwServerApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShwServerApplication.class)
public class UploadDaoTest {
    @Autowired
    private UploadDao uploadDao;

    @Test
    public void existsByStudentIdAndWorkId() {
        boolean b = uploadDao.existsByStudentIdAndWorkId("201601010317", "1");
        System.out.println(b);
    }
}
