package com.lhf.common.spring.extension.config.zookeeper;

import com.lhf.common.spring.extension.config.ConfigRefreshEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;

/**
 * ZookeeperConfigRefreshEvent
 *
 * @author lhf
 * @date 2020/5/29
 */
public class ZookeeperConfigRefreshEvent extends ConfigRefreshEvent<String, String> {

    private TreeCacheEvent event;

    public ZookeeperConfigRefreshEvent(Object source, TreeCacheEvent event, ConfigContext configContext) {
        super(source);
        this.event = event;
        String eventPath = event.getData().getPath();
        // 暂时只监听appContext下的，不监听commonContext
        String context = configContext.appContext();
        String key = eventPath.substring(configContext.appContext().length());
        if (key.indexOf(configContext.contextSeparator()) == 0) {
            String[] split = key.split("/", 2);
            context = context + split[0];
            if (key.contains("/")) {
                key = split[1];
            }
        }
        this.context(context);
        this.key(key);
        this.value(new String(event.getData().getData()));
    }

    public TreeCacheEvent getEvent() {
        return this.event;
    }
}
