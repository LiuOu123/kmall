package com.kgc.kmall.redissontest.config;


import com.kgc.kmall.redissontest.util.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {
    @Value("${spring.redis.host:disabled}")
    private String host;
    @Value("${spring.redis.port:6379}")
    private Integer port;
    @Value("${spring.redis.database:0}")
    private Integer database;

    @Bean
    public RedisUtil getRedisUtil(){
        if(host.equals("disabled")){
            return null;
        }
        RedisUtil redisUtil=new RedisUtil();
        redisUtil.initPool(host,port,database);
        return redisUtil;
    }
}
