package top.itning.server.shwwork.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.itning.server.shwwork.client.entity.Upload;

import java.util.Optional;

import static org.junit.Assert.*;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RunWith(SpringRunner.class)
@SpringBootTest
public class UploadClientTest {
    @Autowired
    private UploadClient uploadClient;

    @Test
    public void testFindOneById() {
        Optional<Upload> uploadOptional = uploadClient.findOneById("1");
        System.out.println(uploadOptional.isPresent());
    }
}