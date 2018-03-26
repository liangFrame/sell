package cn.lframe.sell.service;

import cn.lframe.sell.dataobject.SellerInfo;

/**
 * 买家端
 */
public interface SellerService {

    /**
     * 通过openid查询买家端信息
     * @param openid
     * @return
     */
    SellerInfo findSellerInfoByOpenid(String openid);


}
