package top.itning.server.shwstudentgroup.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentGroupServiceTest {
    @Autowired
    private StudentGroupService studentGroupService;

    @Test
    public void testJoinGroup() {
        studentGroupService.joinGroup("2a867ab2efc04d5f912b0e9cd25f0c85", "").block();
    }
}