package cn.lframe.sell.service.impl;

import cn.lframe.sell.config.ProjectUrl;
import cn.lframe.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private ProjectUrl projectUrl;

    @Test
    public void findOne() throws Exception{
        ProductCategory productCategory=categoryService.findOne(1);
        Assert.assertEquals(new Integer(1),productCategory.getCategoryId());
    }

    @Test
    public void findAll() {

        List<ProductCategory> productCategories = categoryService.findAll();
        for (ProductCategory productCategory : productCategories) {
            System.out.println(productCategory.getCategoryId());

        }
    }

    @Test
    public void findByCategoryTypeIn() {
        List<Integer> integers= Arrays.asList(1,2);
        List<ProductCategory> productCategory = categoryService.findByCategoryTypeIn(integers);
        System.out.println(productCategory);
    }

    @Test
    public void save() {

        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("女生最爱");
        productCategory.setCategoryType(4);
        ProductCategory result = categoryService.save(productCategory);
        Assert.assertNotNull(result);
    }

    @Test
    public void test(){
        System.out.println(projectUrl.getUrl());
    }

}