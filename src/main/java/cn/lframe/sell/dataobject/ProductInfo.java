package cn.lframe.sell.dataobject;

import cn.lframe.sell.enums.ProductStatusEnum;
import cn.lframe.sell.utils.EnumUtil;
import cn.lframe.sell.utils.serializer.Date2LongSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author home-pc
 * @create2018 -03 -11 -15:41
 */
@Entity
@Data
@DynamicUpdate
public class ProductInfo {

    /**
     * 产品信息的id，因为我们的数据库的id使用的是varchar类型，所以这里的id不选用Integer类型。
     */
    @Id
    private String productId;

    /**
     * 产品名字
     */
    private String productName;

    /**
     *单价使用了math包下的BigDecimal类型
     */
    private BigDecimal productPrice;

    /**
     * 库存
     */
    private int productStock;

    /**
     * 产品描述
     */
    private String productDescription;

    /**
     * 小图
     * 这里的商品小图我们使用的是一个链接地址。
     */
    private String productIcon;

    /**
     * 商品的状态   0：正常  1：下架
     */
    private Integer productStatus = ProductStatusEnum.UP.getCode();

    /**
     * 类目编号
     */
    private Integer categoryType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;


    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum(){
        return EnumUtil.getByCode(productStatus,ProductStatusEnum.class);
    }
}
