package cn.lframe.sell.converter;

import cn.lframe.sell.dataobject.OrderDetail;
import cn.lframe.sell.dto.OrderDTO;
import cn.lframe.sell.enums.ResultEnum;
import cn.lframe.sell.exception.SellException;
import cn.lframe.sell.form.OrderForm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 从前端传进来的OrderForm 转换为 我们业务层用来创建订单的OrderDTO。
 * @author home-pc
 * @create2018 -03 -14 -15:12
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm){
//        Gson是google出的一个json转换工具。
        Gson gson =new Gson();

        OrderDTO orderDTO = new OrderDTO();
//        这里为什么我们不使用BeanUtils.copyProperties()？
//        OrderForm 和 OrderDTO这两个类中，虽然的字段意思是一样的，
//        但是它们的属性字段名不一样，所以我们不能使用该方法复制属性。
//        因为复制的前提是属性字段必须一样。
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
//        我们需要把json转换成List。
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>(){}.getType());
        }catch (Exception e){
            log.error("【对象转换】错误，string={}",orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
