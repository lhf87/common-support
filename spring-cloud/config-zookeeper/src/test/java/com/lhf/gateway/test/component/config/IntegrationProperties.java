package com.lhf.gateway.test.component.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties
 *
 * @author lhf
 * @date 2020/6/1
 */

@Data
@ConfigurationProperties("integration.namespace")
public class IntegrationProperties {

    private String routeConfig;

    private String integrationApi;

    private String brandCacheKeyPrefix;

    private String whiteApi;
}