package com.lhf.common.spring.extension.config.zookeeper;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Context
 *
 * @author lhf
 * @date 2020/5/28
 */
@Data
@Accessors(chain = true, fluent = true)
public class ConfigContext {

    public ConfigContext() {
        this.contexts = Lists.newArrayList();
    }

    private String configRoot;

    private String appContext;

    private String commonContext;

    private String contextSeparator;

    private List<String> contexts;

    public String profileContext(String baseContext, String profile) {
        return baseContext + contextSeparator + profile;
    }
}
