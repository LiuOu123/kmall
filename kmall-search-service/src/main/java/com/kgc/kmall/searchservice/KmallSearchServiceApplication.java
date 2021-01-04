package com.kgc.kmall.searchservice;

import com.kgc.kmall.service.SkuService;
import io.searchbox.client.JestClient;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class KmallSearchServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(KmallSearchServiceApplication.class, args);
    }

}
