package cn.lframe.sell.dto;

import lombok.Data;

/**
 * 购物车
 * @author home-pc
 * @create2018 -03 -12 -14:54
 */
@Data
public class CartDTO {

    /**
     * 商品id
     */
    private String productId;

    /**
     * 数量
     */
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
