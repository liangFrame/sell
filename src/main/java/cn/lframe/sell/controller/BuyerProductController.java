package cn.lframe.sell.controller;

import cn.lframe.sell.VO.ProductInfoVO;
import cn.lframe.sell.VO.ProductVO;
import cn.lframe.sell.VO.ResultVO;
import cn.lframe.sell.dataobject.ProductCategory;
import cn.lframe.sell.dataobject.ProductInfo;
import cn.lframe.sell.service.CategoryService;
import cn.lframe.sell.service.ProductService;
import cn.lframe.sell.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * 买家商品
 * @author home-pc
 * @create2018 -03 -11 -17:50
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/list")
//    @Cacheable(cacheNames = "product", key = "#sellId" ,condition = "#sellId.length() > 3",
//            unless = "#result.getCode()==0")
    public ResultVO list(){
////        1:查询所有的上架商品
//        List<ProductInfo> productInfoList=productService.findUpAll();
////        查询类目（一次性查询）
////        List<Integer> categoryTypeList= new ArrayList<>();
////        传统方法
////        for (ProductInfo productInfo:productInfoList){
////            categoryTypeList.add(productInfo.getCategoryType());
////        }
////        精简做法（java8, lambda）(和上面的传统做法的代码作用一样)
//        List<Integer> categoryTypeList=productInfoList.stream()
//                .map(e -> e.getCategoryType())
//                .collect(Collectors.toList());
//        List<ProductCategory> productCategoryList=categoryService.findByCategoryTypeIn(categoryTypeList);
//        数据拼装
//        List<ProductVO> productVOList=new ArrayList<>();
//
//        for(ProductCategory productCategory:productCategoryList){
//            ProductVO productVO=new ProductVO();
//            productVO.setCategoryName(productCategory.getCategoryName());
//            productVO.setCategoryType(productCategory.getCategoryType());
//
//
//            List<ProductInfoVO> productInfoVOList=new ArrayList<>();
//            for (ProductInfo productInfo:productInfoList){
//                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())){
//                    ProductInfoVO productInfoVO=new ProductInfoVO();
//                    BeanUtils.copyProperties(productInfo,productInfoVO);
//                    productInfoVOList.add(productInfoVO);
//                }
//            }
//            productVO.setProductInfoVOList(productInfoVOList);
//            productVOList.add(productVO);
//        }
//        ResultVO resultVO=new ResultVO();
//        resultVO.setData(productVOList);
//        resultVO.setCode(0);
//        resultVO.setMsg("成功");
//        return resultVO;
        /**
         * 查询所有类目
         */
        ////        1:查询所有的上架商品
//        List<ProductInfo> productInfoList=productService.findUpAll();
////        查询类目（一次性查询）
////        List<Integer> categoryTypeList= new ArrayList<>();
////        传统方法
////        for (ProductInfo productInfo:productInfoList){
////            categoryTypeList.add(productInfo.getCategoryType());
////        }
////        精简做法（java8, lambda）(和上面的传统做法的代码作用一样)
//        List<Integer> categoryTypeList=productInfoList.stream()
//                .map(e -> e.getCategoryType())
//                .collect(Collectors.toList());
//        List<ProductCategory> productCategoryList=categoryService.findByCategoryTypeIn(categoryTypeList);

        /**
         * 对于商品的展示，我们首选条件是要满足商品时上架状态。
         * 然后才去查询它所属的类目
         */

        /**
         * 查询所有上架商品
         */
        List<ProductInfo> productInfoList=productService.findUpAll();

        /**
         * 查询所有类目，切记要一次性全部查询，否则如果在for循环中要查询for循环的次数的查询
         *
         */
        List<Integer> categoryTypeList=new ArrayList<>();
        for (ProductInfo productInfo:productInfoList){
            categoryTypeList.add(productInfo.getCategoryType());
        }

        List<ProductCategory> productCategoryList=categoryService.findByCategoryTypeIn(categoryTypeList);

        /**
         * 调整数据格式
         */

        List<ProductVO> productVOList=new ArrayList<>();
        for (ProductCategory productCategory:productCategoryList){
            ProductVO productVO=new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());

            List<ProductInfoVO> productInfoVOList=new ArrayList<>();
            for (ProductInfo productInfo:productInfoList){
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO=new ProductInfoVO();
                    /**
                     * Spring提供的复制对象的内容
                     */
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);

            productVOList.add(productVO);
        }
        return ResultVOUtil.success(productVOList);
    }

}
