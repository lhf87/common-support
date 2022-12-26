package com.lhf.gateway.test.component;

import com.lhf.common.spring.extension.config.AbstractConfigRefreshListener;
import com.lhf.common.spring.extension.config.ConfigRefreshEvent;
import com.lhf.common.spring.extension.config.zookeeper.ZookeeperConfigRefreshEvent;

/**
 * ZookeeperConfigRefreshListener
 *
 * @author lhf
 * @date 2020/6/1
 */
public abstract class AbstractZookeeperConfigRefreshListener extends AbstractConfigRefreshListener<String, String> {

    private String interesting;

    public AbstractZookeeperConfigRefreshListener(String interesting) {
        this.interesting = interesting;
    }

    @Override
    protected boolean interesting(ConfigRefreshEvent<String, String> event) {
        return event.context().contains(interesting);
    }

    @Override
    protected void dispatch(ConfigRefreshEvent<String, String> event) {
        if (event instanceof ZookeeperConfigRefreshEvent) {
            dispatch((ZookeeperConfigRefreshEvent) event);
        }
    }

    protected abstract void dispatch(ZookeeperConfigRefreshEvent event);
}
