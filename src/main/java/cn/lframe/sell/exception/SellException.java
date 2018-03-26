package cn.lframe.sell.exception;

import cn.lframe.sell.enums.ResultEnum;
import lombok.Getter;

/**
 * @author home-pc
 * @create2018 -03 -12 -12:05
 */
@Getter
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        /**
         * 在RuntimeException中有一个message系统属性。所以通过super()把信息传到父类的构造方法里面。
         */
        super(resultEnum.getMessage());
        this.code=resultEnum.getCode();
    }

    public SellException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
