package top.itning.server.shwgroup.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import top.itning.server.common.exception.NoSuchFiledValueException;
import top.itning.server.shwgroup.entity.Group;

import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupRepositoryTest {
    @Autowired
    private GroupRepository groupRepository;

    @Test
    public void testSave() {
        String s = UUID.randomUUID().toString();
        Group group = new Group();
        group.setId(s);
        group.setGroupName("setGroupName");
        group.setTeacherName("setTeacherName");
        group.setTeacherNumber("setTeacherNumber");
        group.setCode("setCode");
        group.setGmtCreate(new Date());
        group.setGmtModified(new Date());
        Mono<Group> save = groupRepository.save(group);
        Group block = save.block();
        assertNotNull(block);
        assertEquals(block.getId(), s);
        System.out.println(save.block());
    }

    @Test
    public void testDel() {
        groupRepository.deleteAll().block();
        Long block = groupRepository.findAll().count().block();
        assertNotNull(block);
        long count = block;
        assertEquals(count, 0L);
    }

    @Test
    public void testUpOne() {
        groupRepository.findById("aa8d328832-3e1c-4423-810d-4e1f789d5548")
                .switchIfEmpty(Mono.error(new NoSuchFiledValueException("123", HttpStatus.ACCEPTED)))
                .flatMap(group -> {
                    System.out.println(1);
                    group.setGroupName("11");
                    return groupRepository.save(group);
                })
                .block();
    }
}