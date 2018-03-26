package cn.lframe.sell.repository;

import cn.lframe.sell.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {


    /**
     * 该方法是按照买家的openId(即微信的openId)来查，并且有分页
     * 如果不传Pageable，则会把某个人的所有订单都查出来了，可能致使订单量太大
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
