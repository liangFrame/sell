package cn.lframe.sell.service.impl;

import cn.lframe.sell.dataobject.SellerInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerServiceImplTest {


    private static final String openid = "abc";

    @Autowired
    private SellerServiceImpl sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void findSellerInfoByOpenid() {
        SellerInfo result = sellerService.findSellerInfoByOpenid(openid);
        Assert.assertEquals(openid, result.getOpenid());
    }

    @Test
    public void test(){
//        String token = UUID.randomUUID().toString();
//        System.out.println(token);
        redisTemplate.opsForValue().set("aaa","111");
        Assert.assertEquals("111",redisTemplate.opsForValue().get("aaa"));
    }
}