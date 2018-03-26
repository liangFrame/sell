package cn.lframe.sell.service.impl;

import cn.lframe.sell.dataobject.OrderDetail;
import cn.lframe.sell.dataobject.OrderMaster;
import cn.lframe.sell.dto.OrderDTO;
import cn.lframe.sell.enums.OrderStatusEnum;
import cn.lframe.sell.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String BUYER_OPENID = "1101110";

    private final String ORDER_ID="1520841459898369774";

    @Test
    public void create() {

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("苗世雄");
        orderDTO.setBuyerAddress("慕课网");
        orderDTO.setBuyerPhone("123456789012");
        orderDTO.setBuyerOpenid(BUYER_OPENID);
//        购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();

        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setProductId("123458");
        orderDetail.setProductQuantity(1);
        OrderDetail orderDetail1=new OrderDetail();
        orderDetail1.setProductId("123457");
        orderDetail1.setProductQuantity(2);

        orderDetailList.add(orderDetail);
        orderDetailList.add(orderDetail1);
        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);
        log.info("【创建订单】 result={}",result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() {
        OrderDTO result=orderService.findOne(ORDER_ID);
        log.info("【查询单个订单】 result={}",result);
        Assert.assertEquals(ORDER_ID,result.getOrderId());
    }

    @Test
    public void findList() {
        PageRequest pageRequest=new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID,pageRequest);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());
    }

    @Test
    public void list(){
        PageRequest pageRequest=new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(pageRequest);
//        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
        Assert.assertTrue("查询所有订单列表",orderDTOPage.getTotalElements() >0);
    }
}