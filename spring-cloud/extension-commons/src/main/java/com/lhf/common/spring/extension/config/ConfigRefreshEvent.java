package com.lhf.common.spring.extension.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.context.ApplicationEvent;

/**
 * ConfigRefreshEvent
 *
 * @author lhf
 * @date 2020/5/29
 */
@Data
@Accessors(chain = true, fluent = true)
public class ConfigRefreshEvent<K, V> extends ApplicationEvent {

    /**
     * 一般是配置路径
     */
    private String context;

    private K key;

    private V value;

    public ConfigRefreshEvent(Object source) {
        super(source);
    }
}
