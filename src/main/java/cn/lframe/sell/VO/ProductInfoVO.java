package cn.lframe.sell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**商品详情
 * @author home-pc
 * @create2018 -03 -11 -18:26
 */
@Data
public class ProductInfoVO implements Serializable {

    private static final long serialVersionUID = 3100672156542151260L;

    @JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("icon")
    private String productIcon;


}
