package cn.lframe.sell.service.impl;

import cn.lframe.sell.converter.OrderMaster2OrderDTOConverter;
import cn.lframe.sell.dataobject.OrderDetail;
import cn.lframe.sell.dataobject.OrderMaster;
import cn.lframe.sell.dataobject.ProductInfo;
import cn.lframe.sell.dto.CartDTO;
import cn.lframe.sell.dto.OrderDTO;
import cn.lframe.sell.enums.OrderStatusEnum;
import cn.lframe.sell.enums.PayStatusEnum;
import cn.lframe.sell.enums.ResultEnum;
import cn.lframe.sell.exception.ResponseBankException;
import cn.lframe.sell.exception.SellException;
import cn.lframe.sell.repository.OrderDetailRepository;
import cn.lframe.sell.repository.OrderMasterRepository;
import cn.lframe.sell.service.OrderService;
import cn.lframe.sell.service.ProductService;
import cn.lframe.sell.service.WebSocket;
import cn.lframe.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author home-pc
 * @create2018 -03 -12 -11:10
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private WebSocket webSocket;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        /**
         * 订单的id在创建订单的一开始就必须生成了。
         */
        String orderId = KeyUtil.genUniqueKey();
        //初始化一个总价格
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

//        List<CartDTO> cartDTOList = new ArrayList<>();
        //        1:查询商品（数量，价格）
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
//                throw new ResponseBankException();
            }
//        2:计算订单总价
            orderAmount = productInfo.getProductPrice().
                    multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
//            3：订单详情入库(orderDetail)

            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailRepository.save(orderDetail);

//            CartDTO cartDTO =new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());
//            cartDTOList.add(cartDTO);
        }
//        4:订单用户信息入库（OrderMaster）
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

//        5:扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);

//        发送websocket消息
        webSocket.sendMessage(orderDTO.getOrderId());

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIT);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIT);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        //orderMasterPage的getContent()方法能够获取存储于其中的List<OrderMaster>.
        return new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }


    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
//        判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【取消订单】 订单状态不正确，orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
//        修改订单状态
//        我们需要先修改OrderDTO的订单状态，然后在把orderDTO的属性复制到orderMaster上。然后把orderMaster入库。
//        因为我们的orderDTO只是我们的数据传输的中间层。
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【取消订单】更新失败, orderMater={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
//        返回库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【取消订单】订单中无商品详情,orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList()
                .stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);
//        如果已支付，需要退款

        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
//            如果你写了TODO标志，就可以在IDEA的左下角打开TODO，就能一下子找到项目没写的位置。
//            TODO

        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
//        判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】 订单状态不正确，orderId={} , orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
//        修改状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【完结订单】更新失败, orderMater={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
//        判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【订单支付成功】 订单状态不正确，orderId={} , orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
//        判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("【订单支付完成】订单支付状态不正确，orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);

        }
//        修改支付状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            log.error("【订单支付完成】更新失败, orderMater={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {

        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        //orderMasterPage的getContent()方法能够获取存储于其中的List<OrderMaster>.
        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());

    }
}
