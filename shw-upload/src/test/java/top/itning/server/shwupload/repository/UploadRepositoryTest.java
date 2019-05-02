package top.itning.server.shwupload.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.itning.server.shwupload.entity.Upload;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RunWith(SpringRunner.class)
@SpringBootTest
public class UploadRepositoryTest {
    @Autowired
    private UploadRepository uploadRepository;

    @Test
    public void testSave() {
        uploadRepository.save(
                new Upload("201601010317",
                        "769ca1696d9941c39b44033c34a6689a",
                        "text/html",
                        ".html",
                        200L)).block();
    }
}