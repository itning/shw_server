package top.itning.server.shwgroup.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.itning.server.shwgroup.entity.Group;

import java.util.Collections;

import static org.junit.Assert.assertNotNull;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReactiveMongoHelperTest {
    @Autowired
    private ReactiveMongoHelper reactiveMongoHelper;

    @Test
    public void getAllWithCriteriaByPagination() {
    }

    @Test
    public void getAllWithCriteriaAndDescSortByPagination() {
    }

    @Test
    public void getAllByPagination() {
    }

    @Test
    public void getAllWithDescSortByPagination() {
    }

    @Test
    public void getPage() {
    }

    @Test
    public void find() {
    }

    @Test
    public void findOne() {
    }

    @Test
    public void findOneFieldsByQuery() {
        Group group = reactiveMongoHelper.findOneFieldsByQuery(Collections.singletonMap("id", "2a867ab2efc04d5f912b0e9cd25f0c85"), Group.class, "groupName").block();
        assertNotNull(group);
        System.out.println(group);
    }

    @Test
    public void findFieldsByQuery() {
    }

    @Test
    public void findFieldsByQuery1() {
    }
}