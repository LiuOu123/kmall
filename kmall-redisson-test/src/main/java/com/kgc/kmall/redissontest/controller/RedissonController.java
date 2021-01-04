package com.kgc.kmall.redissontest.controller;

import com.kgc.kmall.redissontest.util.RedisUtil;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.concurrent.locks.Lock;

@RestController
public class RedissonController {
   RedisUtil redisUtil=new RedisUtil();
    @Resource
    RedissonClient redissonClient;

    @RequestMapping("/test")
    public String testRedisson(){
        redisUtil.initPool("192.168.88.130",6379,0);
        Lock lock=redissonClient.getLock("lock");//声明锁
        lock.lock();//上锁
        Jedis jedis=redisUtil.getJedis();
        try {
            String v=jedis.get("k");
            if(v==null){
                v="1";
            }
            System.out.println("->"+v);
            jedis.set("k",(Integer.parseInt(v)+1)+"");
        }finally {
            jedis.close();
            lock.unlock();//上锁
        }
        return "sussess";
    }
}
