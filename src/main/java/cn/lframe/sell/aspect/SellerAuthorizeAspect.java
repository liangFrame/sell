package cn.lframe.sell.aspect;

import cn.lframe.sell.constant.CookieConstant;
import cn.lframe.sell.constant.RedisConstant;
import cn.lframe.sell.exception.SellerAuthorizeException;
import cn.lframe.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 卖家授权
 *
 * @author home-pc
 * @create2018 -03 -22 -8:14
 */
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 切入点
     * 拦截那些在controller包下以Seller开头的类，因为这些都是卖家端。
     * 查询卖家端的商品信息需要登录验证，所以除了登录登出以外，全部需要拦截。
     */
//    @Pointcut("execution(public * cn.lframe.sell.controller.Seller*.*(..))" +
//            "&& !execution(public * cn.lframe.sell.controller.SellerUserController.*(..))")
//    public void verify() {
//    }

    /**
     * 在切入点之前执行验证操作
     */
//    @Before("verify()")
    public void doVerify() {
//        在方法里面获取我们需要的HttpServletRequest
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
//        查询cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
//        如果cookie等于null，说明没有登录
        if (cookie == null) {
            log.warn("【登录校验】 Cookie中查不到token");
//            这里我们不使用ModelAndView做了，而是使用异常，然后捕捉处理它。
            throw new SellerAuthorizeException();
        }

//        去Redis中查
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)){
            log.warn("【登录校验】 Redis中查不到token");
            throw new SellerAuthorizeException();
        }
    }
}
