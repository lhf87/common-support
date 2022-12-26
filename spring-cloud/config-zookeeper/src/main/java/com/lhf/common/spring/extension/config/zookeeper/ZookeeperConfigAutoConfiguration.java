package com.lhf.common.spring.extension.config.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.zookeeper.ConditionalOnZookeeperEnabled;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ZookeeperConfigAutoConfiguration
 *
 * @author lhf
 * @date 2020/5/28
 */
@Configuration
@ConditionalOnZookeeperEnabled
@ConditionalOnProperty(value = "spring.cloud.zookeeper.keruyun.loyalty.config.enabled", matchIfMissing = true)
public class ZookeeperConfigAutoConfiguration {

    @Configuration
    protected static class ZkRefreshConfiguration {

        @Bean
        @ConditionalOnProperty(name = "spring.cloud.zookeeper.keruyun.loyalty.config.watcher.enabled")
        public ZookeeperConfigWatcher configWatcher(ZookeeperConfigProperties properties,
                                                    ZookeeperPropertySourceLocator locator, CuratorFramework curator) {
            return new ZookeeperConfigWatcher(properties, locator.getConfigContext(), curator);
        }

    }
}