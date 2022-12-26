package com.lhf.common.spring.extension.config.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.zookeeper.ConditionalOnZookeeperEnabled;
import org.springframework.cloud.zookeeper.ZookeeperAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * ZookeeperConfigBootstrapConfiguration
 *
 * @author lhf
 * @date 2020/5/28
 */
@Configuration
@ConditionalOnZookeeperEnabled
@Import(ZookeeperAutoConfiguration.class)
public class ZookeeperConfigBootstrapConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ZookeeperPropertySourceLocator zookeeperPropertySourceLocator(
            CuratorFramework curator, ZookeeperConfigProperties properties) {
        return new ZookeeperPropertySourceLocator(curator, properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public ZookeeperConfigProperties zookeeperConfigProperties() {
        return new ZookeeperConfigProperties();
    }

}
