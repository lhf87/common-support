//package com.lhf.gateway.test.component.predicate;
//
//import com.google.common.collect.Lists;
//import com.keruyun.loyalty.gateway.RouteContext;
//import com.keruyun.loyalty.gateway.integration.BusinessRule;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
//import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
//import org.springframework.http.HttpHeaders;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.server.ServerWebExchange;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.function.Predicate;
//
///**
// * IntegrationRoutePredicateFactory
// * fixme forceRoute应该放在另一个Predicate中？
// *
// * @author lhf
// * @date 2020/5/26
// */
//@Slf4j
//public class IntegrationRoutePredicateFactory
//        extends AbstractRoutePredicateFactory<IntegrationRoutePredicateFactory.Config> {
//
//    public IntegrationRoutePredicateFactory() {
//        super(Config.class);
//    }
//
//    @Override
//    public List<String> shortcutFieldOrder() {
//        return Lists.newArrayList("brandIdKey", "forceRouteKey");
//    }
//
//    @Override
//    public Predicate<ServerWebExchange> apply(Config config) {
//        return new GatewayPredicate() {
//            @Override
//            public boolean test(ServerWebExchange exchange) {
//                String path = exchange.getRequest().getPath().value();
//                HttpHeaders httpHeaders = exchange.getRequest().getHeaders();
//                List<String> brands = httpHeaders.getOrDefault(config.getBrandIdKey(), Collections.emptyList());
//                List<String> forceRoutes = httpHeaders.getOrDefault(config.getForceRouteKey(), Collections.emptyList());
//                if (log.isDebugEnabled()) {
//                    log.debug("request headers: {}; path: {}", exchange.getRequest().getHeaders(), path);
//                }
//
//
//            }
//
//            @Override
//            public String toString() {
//                return String.format("brandId header key: %s; forceRouteKey: %s",
//                        config.getBrandIdKey(), config.getForceRouteKey());
//            }
//        };
//    }
//
//    @Validated
//    public static class Config {
//
//        private String brandIdKey;
//
//        private String forceRouteKey;
//
//        public String getBrandIdKey() {
//            return brandIdKey;
//        }
//
//        public Config setBrandIdKey(String brandIdKey) {
//            this.brandIdKey = brandIdKey;
//            return this;
//        }
//
//        public String getForceRouteKey() {
//            return forceRouteKey;
//        }
//
//        public Config setForceRouteKey(String forceRouteKey) {
//            this.forceRouteKey = forceRouteKey;
//            return this;
//        }
//    }
//
//}
