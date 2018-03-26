package cn.lframe.sell.converter;

import cn.lframe.sell.dataobject.OrderMaster;
import cn.lframe.sell.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author home-pc
 * @create2018 -03 -12 -17:00
 */
public class OrderMaster2OrderDTOConverter {

    public static OrderDTO convert(OrderMaster orderMaster){
        OrderDTO orderDTO= new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList){
//        List<OrderDTO> orderDTOList=new ArrayList<>();
//        for (OrderMaster orderMaster:orderMasterList){
//            orderDTOList.add(convert(orderMaster));
//        }
//        return orderDTOList;
        /**
         * 我们需要把List<OrderMaster>中的所有orderMaster转换为orderDTO,
         * 而转换的方法是convert(),所以从下面的方法可以看出
         */
        return orderMasterList.stream()
                .map(e-> convert(e))
                .collect(Collectors.toList());
    }
}
