package com.ssafy.mata.util;

import com.ssafy.mata.entity.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Validation {
    private final StringRedisTemplate redisTemplate;

    public void setTokenToRedis(String uuid, Project project){
        redisTemplate.opsForValue().append(uuid, project.getProjectId().toString());
    }

    public boolean checkRedisValidation(String uuid){
        if(redisTemplate.opsForValue().get(uuid)==null){
            return false;
        }
        return true;
    }
}
