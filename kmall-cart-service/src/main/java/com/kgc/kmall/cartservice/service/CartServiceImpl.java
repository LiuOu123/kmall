package com.kgc.kmall.cartservice.service;

import com.alibaba.fastjson.JSON;
import com.kgc.kmall.bean.OmsCartItem;
import com.kgc.kmall.bean.OmsCartItemExample;
import com.kgc.kmall.cartservice.mapper.OmsCartItemMapper;
import com.kgc.kmall.service.CartService;
import com.kgc.kmall.util.RedisUtil;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.*;


@Component
@Service
public class CartServiceImpl implements CartService {
    @Resource
    OmsCartItemMapper omsCartItemMapper;
    @Resource
    RedisUtil redisUtil;

    @Override
    public OmsCartItem ifCartExistByUser(String memberId, long skuId) {
        OmsCartItemExample example=new OmsCartItemExample();
        OmsCartItemExample.Criteria criteria = example.createCriteria();
        criteria.andMemberIdEqualTo(Long.parseLong(memberId));
        criteria.andProductSkuIdEqualTo(skuId);
        List<OmsCartItem> omsCartItems = omsCartItemMapper.selectByExample(example);
        if (omsCartItems!=null&&omsCartItems.size()>0){
            return omsCartItems.get(0);
        }else {
            return null;
        }

    }

    @Override
    public void addCart(OmsCartItem omsCartItem) {
        omsCartItemMapper.insertSelective(omsCartItem);
    }

    @Override
    public void updateCart(OmsCartItem omsCartItemFromDb) {
        omsCartItemMapper.updateByPrimaryKeySelective(omsCartItemFromDb);
    }

    @Override
    public void flushCartCache(String memberId) {
        OmsCartItemExample example=new OmsCartItemExample();
        OmsCartItemExample.Criteria criteria = example.createCriteria();
        criteria.andMemberIdEqualTo(Long.parseLong(memberId));
        List<OmsCartItem> omsCartItems = omsCartItemMapper.selectByExample(example);
        //同步到jedis中
        Jedis jedis=redisUtil.getJedis();
        Map<String,String> map=new HashMap<>();
        for (OmsCartItem omsCartItem : omsCartItems) {
            map.put(omsCartItem.getProductId().toString(), JSON.toJSONString(omsCartItem));
        }
        jedis.del("usr"+memberId+":card");
        jedis.hmset("user"+memberId+"cart",map);
        Random random=new Random();
        int i = random.nextInt(300);
        jedis.expire("user"+memberId+"cart",i);
        jedis.close();

    }

    @Override
    public List<OmsCartItem> cartList(String memberId) {
        List<OmsCartItem> omsCartItems=new ArrayList<>();
        Jedis jedis=redisUtil.getJedis();
        try {
            List<String> hvals = jedis.hvals("ussr" + memberId + "card");
            if(hvals.size()!=0){
                for (String hval : hvals) {
                    OmsCartItem omsCartItem = JSON.parseObject(hval, OmsCartItem.class);
                    omsCartItems.add(omsCartItem);
                }
            }else{
                OmsCartItemExample example=new OmsCartItemExample();
                OmsCartItemExample.Criteria criteria = example.createCriteria();
                criteria.andMemberIdEqualTo(Long.parseLong(memberId));
                omsCartItems = omsCartItemMapper.selectByExample(example);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
        }
        return omsCartItems;
    }
}
