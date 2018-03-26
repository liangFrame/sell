package cn.lframe.sell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品类目
 * @author home-pc
 * @create2018 -03 -11 -18:21
 */
@Data
public class ProductVO implements Serializable{

    private static final long serialVersionUID = 1421513298957283398L;

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;



}
