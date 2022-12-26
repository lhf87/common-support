package com.lhf.common.spring.extension.config.zookeeper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.logging.log4j.util.Strings;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ZookeeperPropertySourceLocator
 *
 * @author lhf
 * @date 2020/5/28
 */
public class ZookeeperPropertySourceLocator implements PropertySourceLocator {

    private ZookeeperConfigProperties properties;

    private CuratorFramework curator;

    private ConfigContext configContext;

    private static final Log log = LogFactory.getLog(ZookeeperPropertySourceLocator.class);

    public ZookeeperPropertySourceLocator(CuratorFramework curator,
                                          ZookeeperConfigProperties properties) {
        this.curator = curator;
        this.properties = properties;
        configContext = new ConfigContext();
    }

    public ConfigContext getConfigContext() {
        return this.configContext;
    }

    @Override
    public PropertySource<?> locate(Environment environment) {
        if (!(environment instanceof ConfigurableEnvironment)) {
            return null;
        }

        CompositePropertySource composite = new CompositePropertySource("zookeeper");
        ConfigurableEnvironment env = (ConfigurableEnvironment) environment;
        String appName = env.getProperty("spring.application.name");
        if (appName == null) {
            if (this.properties.isFailFast()) {
                ReflectionUtils.rethrowRuntimeException(new RuntimeException("spring.application.name can't be null"));
            }
            else {
                log.warn("Unable to load zookeeper config");
                return composite;
            }
        }

        configContext.contextSeparator(this.properties.getContextSeparator());

        String root = this.properties.getRoot();
        List<String> contexts = configContext.contexts();
        List<String> includes = Arrays.asList(this.properties.getIncludes().split(","));
        String commonContext = this.properties.getCommonContext();
        if (Strings.isNotBlank(commonContext)) {
            commonContext = root + "/" + this.properties.getCommonContext();
            contexts.add(commonContext);
            addProfiles(contexts, commonContext, includes);
        }

        StringBuilder appContext = new StringBuilder(root);
        if (!appName.startsWith("/")) {
            appContext.append("/");
        }
        appContext.append(appName);
        contexts.add(appContext.toString());
        addProfiles(contexts, appContext.toString(), includes);

        Collections.reverse(contexts);
        configContext.configRoot(root).commonContext(commonContext).appContext(appContext.toString()).contexts(contexts);

        for (String propertySourceContext : contexts) {
            try {
                PropertySource propertySource = create(propertySourceContext);
                composite.addPropertySource(propertySource);
                // TODO: howto call close when /refresh
            }
            catch (Exception e) {
                if (this.properties.isFailFast()) {
                    ReflectionUtils.rethrowRuntimeException(e);
                }
                else {
                    log.warn("Unable to load zookeeper config from " + propertySourceContext, e);
                }
            }
        }

        return composite;
    }

    @PreDestroy
    public void destroy() {
    }

    private PropertySource<CuratorFramework> create(String context) {
        return new ZookeeperPropertySource(context, this.curator);
    }

    private void addProfiles(List<String> contexts, String baseContext, List<String> profiles) {
        for (String profile : profiles) {
            contexts.add(this.configContext.profileContext(baseContext, profile));
        }
    }

}