package cn.lframe.sell.service.impl;

import cn.lframe.sell.dataobject.SellerInfo;
import cn.lframe.sell.repository.SellerInfoRepository;
import cn.lframe.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author home-pc
 * @create2018 -03 -20 -21:11
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository repository;



    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }
}
