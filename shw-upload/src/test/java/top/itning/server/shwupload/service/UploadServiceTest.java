package top.itning.server.shwupload.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.itning.server.common.exception.NoSuchFiledValueException;
import top.itning.server.shwupload.entity.Upload;

import static org.junit.Assert.assertNotNull;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RunWith(SpringRunner.class)
@SpringBootTest
public class UploadServiceTest {
    @Autowired
    private UploadService uploadService;

    @Test
    public void getUploadInfoByWorkId() {
        Upload upload = uploadService.getUploadInfoByWorkId("201601010317", "b62f93d695a74420852f3f0a8aabfd60").block();
        assertNotNull(upload);
        System.out.println(upload);
    }

    @Test
    public void delUploadInfoByWorkId() {
        String studentId = "201601010317";
        String workId = "b62f93d695a74420852f3f0a8aabfd60";
        uploadService.delUploadInfoByWorkId(studentId, workId).block();
        try {
            uploadService.getUploadInfoByWorkId(studentId, workId).block();
        } catch (NoSuchFiledValueException e) {
            return;
        }
        throw new RuntimeException("error");
    }

    @Test
    public void reviewWork() {
        String studentId = "201601010317";
        String workId = "b62f93d695a74420852f3f0a8aabfd60";
        String s = uploadService.reviewWork(studentId, workId).block();
        assertNotNull(s);
        System.out.println(s);
    }

    @Test
    public void testFindOneById() {
        Upload upload = uploadService.findOneById("1").block();
        System.out.println(upload);
    }
}