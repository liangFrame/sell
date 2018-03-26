package cn.lframe.sell.VO;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * http请求返回的最外层对象
 * @author home-pc
 * @create2018 -03 -11 -18:12
 */
@Data
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = -8584236264929458768L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 具体内容
     */
    private T data;




}
