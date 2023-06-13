package io.playdata.security.coupon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class controller {
    private final RedisExample redisExample;
    @Autowired
    public controller(RedisExample redisExample) {
        this.redisExample = redisExample;
    }

    @PostMapping("/redis-data")
    public String issueCoupon(Authentication authentication) {
        String username = authentication.getName();
        redisExample.saveUsername(username);
        return "redis-data";
    }

    @GetMapping("redis-data")
    public String showRedisData(){
        return "redis-data"; // 출력할 HTML 템플릿 파일명
    }
}
