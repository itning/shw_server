package top.itning.server.shwstudentgroup.repository;

import java.util.Date;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.itning.server.shwstudentgroup.entity.StudentGroup;

import static org.junit.Assert.*;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentGroupRepositoryTest {
    @Autowired
    private StudentGroupRepository studentGroupRepository;

    @Test
    public void testSave() {
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setStudentNumber(UUID.randomUUID().toString());
        studentGroup.setGroupID(UUID.randomUUID().toString());
        studentGroup.setGmtCreate(new Date());
        studentGroup.setGmtModified(new Date());
        studentGroupRepository.save(studentGroup).block();
    }
}