package com.lhf.common.spring.extension.config;

import org.springframework.context.ApplicationListener;

/**
 * ConfigRefreshListener
 *
 * @author lhf
 * @date 2020/6/1
 */
public abstract class AbstractConfigRefreshListener<K, V> implements ApplicationListener<ConfigRefreshEvent<K, V>> {

    protected abstract boolean interesting(ConfigRefreshEvent<K, V> event);

    @Override
    public void onApplicationEvent(ConfigRefreshEvent<K, V> event) {
        if (interesting(event)) {
            dispatch(event);
        }
    }

    protected abstract void dispatch(ConfigRefreshEvent<K, V> event);
}
