package com.kgc.kmall;

import com.kgc.kmall.bean.OmsCartItem;
import com.kgc.kmall.bean.OmsCartItemExample;
import com.kgc.kmall.cartservice.mapper.OmsCartItemMapper;
import com.kgc.kmall.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class KmallCartServiceApplicationTests {
    @Resource
    CartService cartService;
    @Resource
    OmsCartItemMapper omsCartItemMapper;

    @Test
    void contextLoads() {
        System.out.println(omsCartItemMapper.selectByExample(null).toString());
    }

}
