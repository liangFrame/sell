package cn.lframe.sell.utils;

import cn.lframe.sell.enums.CodeEnum;

/**
 * @author home-pc
 * @create2018 -03 -18 -15:23
 */
public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code , Class<T> enumClass){
        for (T each: enumClass.getEnumConstants()){
            if (code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }
}
