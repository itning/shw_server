package top.itning.shw_server.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.yunshu.shw.server.ShwServerApplication;
import top.yunshu.shw.server.entity.Group;
import top.yunshu.shw.server.service.group.GroupService;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShwServerApplication.class)
public class GroupServiceTest {
    @Autowired
    private GroupService groupService;
    @Test
    public void test() {
        List<Group> studentAllGroups = groupService.findStudentAllGroups("2");
        System.out.println(studentAllGroups);
    }
    @Test
    public void test1() {
        List<Group> teacherAllGroups = groupService.findTeacherAllGroups("2");
        System.out.println(teacherAllGroups);
    }
}
