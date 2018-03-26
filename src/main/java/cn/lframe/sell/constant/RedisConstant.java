package cn.lframe.sell.constant;

/**
 * redis常量
 * 设置Redis缓存的时候一定要设置过期时间，如果不设置的话，它将一直占用Redis资源。
 * @author home-pc
 * @create2018 -03 -21 -22:53
 */
public interface RedisConstant {

    /**
     * token前缀
     * 我们希望其是以token_开头的字符串
     */
    String TOKEN_PREFIX= "token_%s";

    /**
     * 过期时间
     */
    Integer EXPIRE = 7200;
}
