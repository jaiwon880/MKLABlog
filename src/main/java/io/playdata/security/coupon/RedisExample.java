package io.playdata.security.coupon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisExample {
    private RedisTemplate redisTemplate;
    @Autowired
    public RedisExample(RedisTemplate<String, Long> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveUsername(String username) {
        redisTemplate.opsForZSet().add("usernames", username, System.currentTimeMillis());
    }

}
