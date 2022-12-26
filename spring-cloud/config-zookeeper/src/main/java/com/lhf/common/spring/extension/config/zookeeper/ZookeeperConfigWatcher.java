package com.lhf.common.spring.extension.config.zookeeper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.zookeeper.KeeperException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import javax.annotation.PostConstruct;
import java.io.Closeable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static org.apache.curator.framework.recipes.cache.TreeCacheEvent.Type.*;

/**
 * ConfigWatcher
 * 暂时只监听appContext下的，不监听commonContext
 *
 * @author lhf
 * @date 2020/5/28
 */
public class ZookeeperConfigWatcher
        implements Closeable, TreeCacheListener, ApplicationEventPublisherAware {

    private static final Log log = LogFactory.getLog(ZookeeperConfigWatcher.class);

    private AtomicBoolean running = new AtomicBoolean(false);

    private ZookeeperConfigProperties properties;

    private ConfigContext configContext;

    private CuratorFramework source;

    private ApplicationEventPublisher publisher;

    private HashMap<String, TreeCache> caches;

    public ZookeeperConfigWatcher(ZookeeperConfigProperties properties, ConfigContext configContext,
                                  CuratorFramework source) {
        this.properties = properties;
        this.configContext = configContext;
        this.source = source;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @PostConstruct
    public void start() {
        if (this.running.compareAndSet(false, true)) {
            this.caches = new HashMap<>();
            String watcherContexts = properties.getWatcher().getContexts();
            List<String> watchContexts;
            if (!"*".equals(watcherContexts)) {
                watchContexts = Arrays.stream(watcherContexts.split(","))
                        .map(profile -> configContext.profileContext(configContext.appContext(), profile))
                        .collect(Collectors.toList());
            } else {
                watchContexts = this.configContext.contexts();
            }
            for (String context : watchContexts) {
                if (!context.startsWith("/")) {
                    context = "/" + context;
                }
                try {
                    TreeCache cache = TreeCache.newBuilder(this.source, context).build();
                    cache.getListenable().addListener(this);
                    cache.start();
                    this.caches.put(context, cache);
                    // no race condition since ZookeeperAutoConfiguration.curatorFramework
                    // calls curator.blockUntilConnected
                }
                catch (KeeperException.NoNodeException e) {
                    // no node, ignore
                }
                catch (Exception e) {
                    log.error("Error initializing listener for context " + context, e);
                }
            }
        }
    }

    @Override
    public void close() {
        if (this.running.compareAndSet(true, false)) {
            for (TreeCache cache : this.caches.values()) {
                cache.close();
            }
            this.caches = null;
        }
    }

    @Override
    public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
        TreeCacheEvent.Type eventType = event.getType();
        // 发布spring-RefreshEvent事件
        // 推送给AbstractConfigRefreshListener子类
        if (eventType == NODE_ADDED || eventType == NODE_REMOVED || eventType == NODE_UPDATED) {
            this.publisher.publishEvent(new ZookeeperConfigRefreshEvent(this, event, configContext));
        }
    }
}