package top.yunshu.shw.server.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.yunshu.shw.server.ShwServerApplication;
import top.yunshu.shw.server.entity.Upload;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShwServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UploadDaoTest {
    @Autowired
    private UploadDao uploadDao;

    @Test
    public void existsByStudentIdAndWorkId() {
        boolean b = uploadDao.existsByStudentIdAndWorkId("201601010317", "1");
        System.out.println(b);
    }

    @Test
    public void findUploadByStudentIdAndWorkId() {
        Upload upload = uploadDao.findUploadByStudentIdAndWorkId("201601010317", "1");
        Assert.assertNull(upload);
    }
}
