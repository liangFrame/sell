package cn.lframe.sell.service.impl;

import cn.lframe.sell.dataobject.ProductInfo;
import cn.lframe.sell.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findOne() {
        ProductInfo productInfo=productService.findOne("123456");
        Assert.assertEquals("123456",productInfo.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfos=productService.findUpAll();
        Assert.assertNotEquals(0,productInfos.size());
    }

    @Test
    public void findAll() {

        PageRequest pageRequest=new PageRequest(0,2);
        Page<ProductInfo> productInfos=productService.findAll(pageRequest);
//        System.out.println(productInfos.getTotalElements());
        Assert.assertNotEquals(0,productInfos.getTotalElements());
    }

    @Test
    public void save() {
        ProductInfo productInfo=new ProductInfo();
        productInfo.setProductId("123457");
        productInfo.setProductName("皮蛋虾");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("很好吃的虾");
        productInfo.setProductIcon("www.:///xxx.jpg");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(2);
        ProductInfo result=productService.save(productInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void onSaleTest(){
        ProductInfo result=productService.onSale("123457");
        Assert.assertEquals(ProductStatusEnum.UP,result.getProductStatusEnum());
    }

    @Test
    public void offSaleTest(){
        ProductInfo result=productService.offSale("123457");
        Assert.assertEquals(ProductStatusEnum.DOWN,result.getProductStatusEnum());
    }

}