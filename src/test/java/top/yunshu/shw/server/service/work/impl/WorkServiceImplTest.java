package top.yunshu.shw.server.service.work.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.yunshu.shw.server.ShwServerApplication;
import top.yunshu.shw.server.service.work.WorkService;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShwServerApplication.class)
public class WorkServiceImplTest {
    @Autowired
    private WorkService workService;

    @Test
    public void getStudentUnDoneWork() {
        assertNotNull(workService.getStudentUnDoneWork("1"));
    }

    @Test
    public void getStudentUnDoneWork1() {
        workService.getStudentUnDoneWork("201601010317").forEach(System.out::println);
    }

    @Test
    public void getStudentDoneWork() {
        workService.getStudentDoneWork("201601010317").forEach(System.out::println);
    }
}