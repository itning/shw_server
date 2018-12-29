package top.yunshu.shw.server.service.group.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.yunshu.shw.server.ShwServerApplication;
import top.yunshu.shw.server.service.group.GroupService;

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
       //for (int i = 0; i < 10000; i++) {
       //    groupService.createGroup(
       //            UUID.randomUUID().toString().replace("-", "")
       //            , UUID.randomUUID().toString().replace("-", "")
       //            , UUID.randomUUID().toString().replace("-", ""));
       //}
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