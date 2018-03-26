package cn.lframe.sell.repository;

import cn.lframe.sell.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {


    @Autowired
    private OrderMasterRepository repository;


    private final String OPENID = "110110";

    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("12345678");
        orderMaster.setBuyerName("师兄");
        orderMaster.setBuyerPhone("123456789123");
        orderMaster.setBuyerAddress("幕课网");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(2.8));
        OrderMaster result = repository.save(orderMaster);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByBuyerOpenId() {
        /**
         * PageRequest的构造器的第一个参数是第几页，(默认第0页开始)第二个参数是设置每一页有多少项
         * 我们在写接口定义的时候使用的Pageable，现在却使用的是PageRequest，是因为PageRequest继承的抽象类，
         * 而抽象类实现了Pageable接口。
         *
         */
        PageRequest request =new PageRequest(0,3);
        Page<OrderMaster> result=repository.findByBuyerOpenid(OPENID,request);
        List<OrderMaster> orderMasterList=result.getContent();
        for (OrderMaster orderMaster:orderMasterList){
            System.out.println(orderMaster);
        }
        Assert.assertNotEquals(0,result.getTotalElements());

    }


}