package cn.lframe.sell.service;

import cn.lframe.sell.dataobject.ProductInfo;
import cn.lframe.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    /**
     * 根据产品的productId查询产品的信息。
     * @param productId
     * @return
     */
    ProductInfo findOne(String productId);

    /**
     * 查询所有在架商品列表
     * @return
     */
    List<ProductInfo> findUpAll();


    /**
     * 管理端直接查询所有
     * @param pageable 为了分页而使用的参数
     * @return
     */
    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);


    /**
     * 上架
     * @return
     */
    ProductInfo onSale(String productId);


    /**
     * 下架
     * @param productId
     * @return
     */
    ProductInfo offSale(String productId);

}
