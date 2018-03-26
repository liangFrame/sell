package cn.lframe.sell.repository;

import cn.lframe.sell.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {

    /**
     * findByCategoryTypeIn中的映射到SQL语句如下:
     *  select *  from productCategory where categoryType in (List<Integer> categoryTypeList)
     *  说白了就是categoryType 的内容必须是List集合中的一个元素。
     * @param categoryTypeList
     * @return
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);




}
