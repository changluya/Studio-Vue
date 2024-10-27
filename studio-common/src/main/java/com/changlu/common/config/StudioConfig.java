package com.changlu.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName StudioConfig
 * @Author ChangLu
 * @Date 4/6/2022 10:03 AM
 * @Description 工作室配置信息
 */
@Component
@ConfigurationProperties(prefix = "studio")
@Data
public class StudioConfig {

    public String resetPassword;

}
