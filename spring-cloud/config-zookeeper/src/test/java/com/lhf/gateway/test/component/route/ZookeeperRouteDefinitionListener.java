package com.lhf.gateway.test.component.route;

import com.lhf.common.spring.extension.config.zookeeper.ZookeeperConfigRefreshEvent;
import com.lhf.gateway.test.component.AbstractZookeeperConfigRefreshListener;
import com.lhf.gateway.test.component.config.IntegrationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.context.ApplicationEventPublisher;

/**
 * ZookeeperRouteDefinitionRepository
 *
 * @author lhf
 * @date 2020/5/27
 */
@Slf4j
public class ZookeeperRouteDefinitionListener extends AbstractZookeeperConfigRefreshListener {

    private ApplicationEventPublisher publisher;

    public ZookeeperRouteDefinitionListener(ApplicationEventPublisher publisher,
                                            IntegrationProperties integrationProperties) {
        super(integrationProperties.getRouteConfig());
        this.publisher = publisher;
    }

    @Override
    protected void dispatch(ZookeeperConfigRefreshEvent event) {
        // todo 多个子节点配置同时修改会收到多条，组合成一条RefreshEvent
        log.info("received refresh event : {}", event);
        // 发送RefreshEvent是为了让spring刷新gateway的route配置
        publisher.publishEvent(new RefreshEvent(this, event.getEvent(), null));
    }
}
