package cn.lframe.sell.dto;

import cn.lframe.sell.dataobject.OrderDetail;
import cn.lframe.sell.enums.OrderStatusEnum;
import cn.lframe.sell.enums.PayStatusEnum;
import cn.lframe.sell.utils.EnumUtil;
import cn.lframe.sell.utils.serializer.Date2LongSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * dto （data transfer object）数据传输对象
 * OrderDTO就是我们的订单数据传输对象
 * 为什么要是使用数据传输对象呢？
 * 一个OrderMaster（订单主表，也称为订单用户表），需要对应多个订单详情表，
 * 而我们的OrderMaster的数据库中没有List<OrderDetail>这个字段，而我们又不能直接在OrderMaster
 * 中直接添加这个字段，所以才有了这里的数据传输对象，相当于多包裹了一层。其中就涉及到了对象的转换，
 * 也就是我们的OrderDTO和OrderMaster之间的转换。
 * 数据传输层也利于我们在扩展老的项目的时候，而不用去修改老的项目的一个好方法，
 *
 * @author home-pc
 * @create2018 -03 -12 -10:54
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonInclude(JsonInclude.Include.NON_NULL)该注解如果添加到某个类上时，就表示
//当把该类作为Json数据返回的时候，如果其中有字段为空，则不返回该字段。
public class OrderDTO {

    /**
     * 订单id
     */
    private String orderId;


    /**
     * 买家名字
     */
    private String buyerName;

    /**
     * 买家电话号码
     */
    private String buyerPhone;

    /**
     * 买家地址
     */
    private String buyerAddress;

    /**
     * 买家微信openId
     */
    private String buyerOpenid;


    /**
     * 订单总金额
     */
    private BigDecimal orderAmount;


    /**
     * 订单状态，默认为新订单
     */
    private Integer orderStatus;


    /**
     * 支付状态，默认为0，未支付
     */
    private Integer payStatus;


    /**
     * 创建时间
     */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    List<OrderDetail> orderDetailList;

    /**
     * 添加了@JsonIgnore会在返回json格式的数据的时候，会忽略该属性
     * @return
     */
    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus,OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getByCode(payStatus,PayStatusEnum.class);
    }
}
