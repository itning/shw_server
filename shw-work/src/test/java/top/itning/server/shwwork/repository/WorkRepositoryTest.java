package top.itning.server.shwwork.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.junit4.SpringRunner;
import top.itning.server.shwwork.entity.Work;

import java.util.List;

import static org.junit.Assert.*;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkRepositoryTest {
    @Autowired
    private WorkRepository workRepository;

    @Test
    public void testSave() {
        Work work = new Work("2a867ab2efc04d5f912b0e9cd25f0c85", "作业名", true, "");
        workRepository.save(work).block();

        Work work1 = new Work("2a867ab2efc04d5f912b0e9cd25f0c85", "未开启作业名", false, "");
        workRepository.save(work1).block();
    }

    @Test
    public void testFindAll() {
        Work w = new Work();
        w.setGroupId("2a867ab2efc04d5f912b0e9cd25f0c85");
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("is_enabled");
        List<Work> workList = workRepository.findAll(Example.of(w,matcher)).collectList().block();
        workList.forEach(System.out::println);
    }
}