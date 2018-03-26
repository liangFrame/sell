package cn.lframe.sell.utils;

import cn.lframe.sell.VO.ResultVO;

/**
 * @author home-pc
 * @create2018 -03 -11 -20:43
 */
public class ResultVOUtil {

    public static ResultVO success(Object object){
        ResultVO resultVO=new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success(){
        return success(null);
    }

    public static ResultVO error(Integer code,String meg){
        ResultVO resultVO=new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(meg);
        return resultVO;
    }
}
