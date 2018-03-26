package cn.lframe.sell.dataobject;

import cn.lframe.sell.utils.serializer.Date2LongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * 类目
 *在数据库中我们创建的表名是product_category，spring的jpa能够自动映射到我们的ProductCategory类上。
 * @author home-pc
 * @create2018 -03 -11 -10:34
 */
@Entity
@DynamicUpdate//写上@DynamicUpdate后，会动态地更新数据库。
@Data//这个是lombok的注解，用于自动生成get 、set方法

public class ProductCategory {
    /**
     * 类目id
     */
    @Id
    @GeneratedValue
    private Integer categoryId;

    /**
     * 类目名字
     */
    private String categoryName;

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

    public ProductCategory() {

    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
