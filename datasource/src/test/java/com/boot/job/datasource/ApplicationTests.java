package com.boot.job.datasource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

/**
 * @author Xingyu Sun
 * @date 2019/6/14 9:14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void test(){
        assertNotNull(context);
        redisTemplate.opsForValue().set("key","value");
        System.out.println(redisTemplate.opsForValue().get("key"));
        redisTemplate.opsForList().rightPushAll("list",1,2,3,4,5,6,7);
        System.out.println(redisTemplate.opsForList().size("list"));
        redisTemplate.opsForHash().put("user::123","name","sxy");
        redisTemplate.opsForHash().put("user::123","age",12);
        redisTemplate.opsForHash().put("user::123","sex","man");
        System.out.println(redisTemplate.opsForHash().values("user::123"));
    }
}
