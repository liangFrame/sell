package cn.lframe.sell.handler;

import cn.lframe.sell.VO.ResultVO;
import cn.lframe.sell.exception.ResponseBankException;
import cn.lframe.sell.exception.SellException;
import cn.lframe.sell.exception.SellerAuthorizeException;
import cn.lframe.sell.utils.ResultVOUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用于捕获切面中的异常
 * @author home-pc
 * @create2018 -03 -22 -8:40
 */
@ControllerAdvice
public class SellExceptionHandler {

    /**
     * 拦截登录异常
     * 拦截完成后，让它跳转到一个界面
     */
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException(){
        return new ModelAndView("redirect:"
        .concat("/seller/login"));
    }

    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO handlerSellerException(SellException e){
        return ResultVOUtil.error(e.getCode(),e.getMessage());
    }

    /**
     * 这个是用来演示浏览器返回403状态
     * 当然啦，这个是调用了@RespnseStatus.FORBIDDEN，所以当商品不存在时，
     * 抛出 ResponseBankException异常，然后在这里捕获，
     * 最后返回给浏览器403状态码.
     */
    @ExceptionHandler(value = ResponseBankException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleResponseBankException(){
    }
}
