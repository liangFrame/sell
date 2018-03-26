package cn.lframe.sell.repository;

import cn.lframe.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Test
    public void findOneTest(){
        ProductCategory productCategory=productCategoryRepository.findOne(1);
        System.out.println(productCategory.toString());
    }
    @Test
    @Transactional//这个注解的意思是我们在方法中执行的内容在调用完方法后，完全回滚，不插入数据库中数据。
    public void saveTest(){
        ProductCategory productCategory=new ProductCategory("女生最爱",4);
        ProductCategory result=productCategoryRepository.save(productCategory);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list= Arrays.asList(2,3,4);
        List<ProductCategory> productCategories=productCategoryRepository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,productCategories.size());
    }



}