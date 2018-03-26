package cn.lframe.sell.service.impl;

import cn.lframe.sell.dataobject.ProductInfo;
import cn.lframe.sell.dto.CartDTO;
import cn.lframe.sell.enums.ProductStatusEnum;
import cn.lframe.sell.enums.ResultEnum;
import cn.lframe.sell.exception.SellException;
import cn.lframe.sell.repository.ProductInfoRepository;
import cn.lframe.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author home-pc
 * @create2018 -03 -11 -16:22
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {
        return repository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo=repository.getOne(cartDTO.getProductId());
            if (productInfo == null ){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
            }
            Integer result= productInfo.getProductStock()+cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }

    }

    /**
     * 这里的扣库存可能由于同一时间多个用户同时访问时，导致的库存多扣了。
     * 也就是我们常说的"超卖"。即卖的东西超过了库存。
     * 我们后面会使用Redis的锁机制来避免这个问题。
     * 在后面的项目优化部分会再次做。
     * @param cartDTOList
     */
    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList){
            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
            if (productInfo == null ){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
            }
           Integer result= productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (result < 0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = repository.findOne(productId);
        if (productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

//        更新
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return repository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = repository.findOne(productId);
        if (productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

//        更新
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return repository.save(productInfo);

    }
}
