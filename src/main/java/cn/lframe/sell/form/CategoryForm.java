package cn.lframe.sell.form;

import lombok.Data;

/**
 * @author home-pc
 * @create2018 -03 -19 -23:12
 */
@Data
public class CategoryForm {
    private Integer categoryId;

    /**
     * 类目名字
     */
    private String categoryName;

    /**
     * 类目编号
     */
    private Integer categoryType;

}
