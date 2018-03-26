package cn.lframe.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 测试属性
 *
 * @author home-pc
 * @create2018 -03 -23 -15:37
 */
@Data
@ConfigurationProperties(prefix = "project")
@Component
public class ProjectUrl {

    private String url;
}
