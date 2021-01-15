package com.lnsoft.gateway.manager;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Louyp
 * @version 1.0
 * @data 2021/01/14 15:49
 */
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {


    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        final ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        final HttpHeaders headers = request.getHeaders();

        if (!headers.containsKey("Authorization")){
            return Mono.just(new AuthorizationDecision(true));
        }
        final Mono<AuthorizationDecision> authorizationDecisionMono = mono
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority).any(roleId -> {
                    System.out.println(roleId);
                    return true;
                }).map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));

        return Mono.just(new AuthorizationDecision(true));
    }



}
