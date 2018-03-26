package cn.lframe.sell.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author home-pc
 * @create2018 -03 -20 -20:05
 */
@Data
@Entity
public class SellerInfo {

    @Id
    private String sellerId;

    private String username;

    private String password;

    private String openid;


}
