package com.lhf.gateway.test.component.config;

import com.lhf.gateway.test.component.route.ZookeeperRouteDefinitionListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AutoConfig
 *
 * @author lhf
 * @date 2020/5/27
 */
@Configuration
public class AutoConfiguration {

//    @Bean
//    public IntegrationRoutePredicateFactory integrationRoutePredicateFactory(
//            ZookeeperRouteApiRule zookeeperRouteApiRule, CompositeBrandRule compositeBrandRule) {
//        return new IntegrationRoutePredicateFactory(new RuleChain().addRule(zookeeperRouteApiRule)
//                .addRule(compositeBrandRule));
//    }

    @Bean
    public ZookeeperRouteDefinitionListener zookeeperRouteDefinitionListener(
            ApplicationEventPublisher publisher, IntegrationProperties integrationProperties) {
        return new ZookeeperRouteDefinitionListener(publisher, integrationProperties);
    }

    @Bean
    public IntegrationProperties integrationProperties() {
        return new IntegrationProperties();
    }

//    @Bean
//    public ZookeeperRouteApiRule zookeeperRouteApiRule(IntegrationProperties integrationProperties) {
//        return new ZookeeperRouteApiRule(integrationProperties);
//    }

//    @Bean
//    public RedisBrandRule redisBrandRule(IntegrationProperties integrationProperties,
//                                         StringRedisTemplate redisTemplate) {
//        return new RedisBrandRule(integrationProperties, redisTemplate);
//    }
}
