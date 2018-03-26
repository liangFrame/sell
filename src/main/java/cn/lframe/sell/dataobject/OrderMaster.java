package cn.lframe.sell.dataobject;

import cn.lframe.sell.enums.OrderStatusEnum;
import cn.lframe.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单所属人的表
 * @author home-pc
 * @create2018 -03 -11 -20:57
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    /**
     * 订单id
     */
    @Id
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
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();


    /**
     * 支付状态，默认为0，未支付
     */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();


    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

//    /**
//     *
//     */
////    @Transient //如果加上这个注解，在数据库对应的时候会把这个字段忽略掉
//    private List<OrderDetail> orderDetailList;


}
