package com.lnsoft.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Louyp
 * @version 1.0
 * @data 2021/01/12 17:09
 */
@Component
public class SecurityGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        SecurityContext ctx = SecurityContextHolder.getContext();

        System.out.println(ctx);
        return chain.filter(exchange);
    }


        @Override
    public int getOrder() {
        return 0;
    }
}
