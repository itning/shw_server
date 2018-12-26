package top.yunshu.shw.server.service.group.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.yunshu.shw.server.ShwServerApplication;
import top.yunshu.shw.server.service.group.GroupService;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShwServerApplication.class)
public class GroupServiceImplTest {
    @Autowired
    private GroupService groupService;

    @Test
    public void findStudentAllGroups() {
    }

    @Test
    public void findTeacherAllGroups() {
    }

    @Test
    public void createGroup() {
        groupService.createGroup("2", "2", "2");
    }

    @Test
    public void joinGroup() {
    }

    @Test
    public void dropOutGroup() {
    }

    @Test
    public void deleteGroup() {
    }

    @Test
    public void updateGroup() {
    }
}