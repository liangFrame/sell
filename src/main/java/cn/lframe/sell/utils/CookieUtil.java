package cn.lframe.sell.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * cookie工具类
 *
 * @author home-pc
 * @create2018 -03 -21 -23:23
 */
public class CookieUtil {

    /**
     * 设置cookie
     * s
     *
     * @param response 设置cookie在HttpServletResponse中设置
     * @param name     cookie的名字
     * @param value    cookie 名字对应的值
     * @param maxAge   过期时间
     */
    public static void set(HttpServletResponse response,
                           String name,
                           String value,
                           int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);

    }

    /**
     * @param request 查询cookie从HttpServletRequest 中查询
     * @param name    代表要获取什么值
     */
    public static Cookie get(HttpServletRequest request,
                             String name) {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        if (cookieMap.containsKey(name)) {
            return cookieMap.get(name);
        } else
            return null;
    }

    /**
     * 将cookie封装成Map
     *
     * @param request 查询cookie从HttpServletRequest中查询，获取搭配的是一个数组
     * @return 返回一个封装了cookie的map。x`
     */
    private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
}
