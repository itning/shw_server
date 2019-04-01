package top.yunshu.shw.server.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;
import top.yunshu.shw.server.ShwServerApplication;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShwServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommonTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test1() {
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        Set keys = redisTemplate.keys("work::00027*");
        redisTemplate.delete(keys);
    }
}
