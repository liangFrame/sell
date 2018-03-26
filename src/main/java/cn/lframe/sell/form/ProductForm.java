package cn.lframe.sell.form;

import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author home-pc
 * @create2018 -03 -19 -22:07
 */
@Data
public class ProductForm {


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
     * 类目编号
     */
    private Integer categoryType;

}
