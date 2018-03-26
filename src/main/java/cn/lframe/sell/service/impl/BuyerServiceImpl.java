package cn.lframe.sell.service.impl;

import cn.lframe.sell.dto.OrderDTO;
import cn.lframe.sell.enums.ResultEnum;
import cn.lframe.sell.exception.SellException;
import cn.lframe.sell.service.BuyerService;
import cn.lframe.sell.service.OrderService;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author home-pc
 * @create2018 -03 -14 -17:57
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService{

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid,orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid,orderId);
        if (orderDTO == null){
            log.error("【取消订单】查不到该订单，orderId={}",orderId);
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIT);
        }
        return orderService.cancel(orderDTO);
    }

    private OrderDTO checkOrderOwner(String openid, String orderId){
        OrderDTO orderDTO=orderService.findOne(orderId);
        if (orderDTO == null){
            return null;
        }
//        判断是否是自己的订单
        if (orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("【查询订单】订单的openid不一致.openid={}, orderDTO={}",openid,orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
