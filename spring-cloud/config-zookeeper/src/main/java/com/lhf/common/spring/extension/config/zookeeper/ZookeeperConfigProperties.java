package com.lhf.common.spring.extension.config.zookeeper;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ZookeeperConfigProperties
 *
 * @author lhf
 * @date 2020/5/28
 */

@Data
@ConfigurationProperties("spring.cloud.zookeeper.keruyun.loyalty.config")
public class ZookeeperConfigProperties {

    private boolean enabled = true;

    /**
     * Root folder where the configuration for Zookeeper is kept.
     */
    private String root = "config";

    /**
     * The name of the common context.
     */
    private String commonContext;

    /**
     * Separator for context appended to the application name.
     */
    private String contextSeparator = ",";

    /**
     * 子context，暂时以逗号分割
     */
    private String includes = "";

    /**
     * Throw exceptions during config lookup if true, otherwise, log warnings.
     */
    private boolean failFast = true;

    private Watcher watcher;

    @Data
    public static class Watcher {

        private Boolean enabled;

        /**
         * 监听的context，暂时以逗号分割
         */
        private String contexts = "*";
    }
}
