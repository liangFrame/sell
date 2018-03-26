package cn.lframe.sell.service;

import cn.lframe.sell.dto.OrderDTO;

/**
 * 我们可以把查订单和取消订单的逻辑放到这里来做。
 */
public interface BuyerService {

    /**
     * 查询一个订单
     * @param openid  用户的openid
     * @param orderId 订单的id
     * @return
     */
    OrderDTO findOrderOne(String openid , String orderId);


    /**
     * 取消订单
     * @param openid 用户的openid
     * @param orderId 订单的id
     * @return
     */
    OrderDTO cancelOrder(String openid , String orderId);
}
