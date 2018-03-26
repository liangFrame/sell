package cn.lframe.sell.controller;

import cn.lframe.sell.constant.CookieConstant;
import cn.lframe.sell.constant.RedisConstant;
import cn.lframe.sell.dataobject.SellerInfo;
import cn.lframe.sell.enums.ResultEnum;
import cn.lframe.sell.service.SellerService;
import cn.lframe.sell.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 卖家用户
 *
 * @author home-pc
 * @create2018 -03 -21 -21:44
 */
@Controller
@RequestMapping("/seller")
public class SellerUserController {


    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse response,
                              Map<String, Object> map) {

//        1:openid和数据库里的数据匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if (sellerInfo == null) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error");
        }
//        2:设置token 至Redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
//        在redis中分别设置了key ，value, 过期时间，时间格式
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid, expire, TimeUnit.SECONDS);


//        3.设置token到cookie（因为我们在校验的时候，我们是从cookie中拿到token，拿到之后再从后端的Redis再校验一次）
        CookieUtil.set(response, CookieConstant.TOKEN, token, expire);

        /**
         * 我们到时候如何校验用户有没有登录呢？
         * 首先我们在cookie里面根据tName=token取到value值，然后在Redis里面根据cookie里面的value值查一下该key对应的值，
         * 如果查到就是openid，则说明用户已经登录了，这个是微信登录涉及的openid.
         */

        return new ModelAndView("redirect:/seller/order/list");
    }

    /**
     * 把cookie和Redis清掉
     *
     * */
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Map<String, Object> map) {
//        1：从cookie里面查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie!=null) {
//            2：清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
//        3: 清除cookie
//            把过期时间设置为0
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        }
        map.put("msg",ResultEnum.LOGOUT_SUCCESS);
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",map);
    }
}
