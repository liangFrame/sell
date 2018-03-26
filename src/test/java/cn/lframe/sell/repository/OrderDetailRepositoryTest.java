package cn.lframe.sell.repository;

import cn.lframe.sell.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {


    @Autowired
    private OrderDetailRepository repository;


    @Test
    public void saveTest(){
        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setDetailId("1234567810");
        orderDetail.setOrderId("11111112");
        orderDetail.setProductIcon("http://xxx.jpg");
        orderDetail.setProductId("111111112");
        orderDetail.setProductName("皮蛋粥");
        orderDetail.setProductPrice(new BigDecimal(2.2));
        orderDetail.setProductQuantity(3);
        OrderDetail orderDetail1=repository.save(orderDetail);
        Assert.assertNotNull(orderDetail);
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> orderDetailList=repository.findByOrderId("123456");
        Assert.assertNotEquals(0,orderDetailList.size());
    }
}