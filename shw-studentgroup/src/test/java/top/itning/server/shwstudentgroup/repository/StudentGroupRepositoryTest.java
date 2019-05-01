package top.itning.server.shwstudentgroup.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.itning.server.shwstudentgroup.entity.StudentGroup;

import java.util.UUID;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentGroupRepositoryTest {
    @Autowired
    private StudentGroupRepository studentGroupRepository;

    @Test
    public void testSave() {
        StudentGroup studentGroup = new StudentGroup(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        studentGroupRepository.save(studentGroup).block();
    }
}