package com.lnsoft.gateway.manager;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.naming.spi.DirStateFactory;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Louyp
 * @version 1.0
 * @data 2021/01/14 15:49
 */
@Component
@Slf4j
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Value("${spring.redis.expira.time}")
    private long expiraTime;


    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        log.info("AuthorizationManager[]check[]mono:{}authorizationContext:{}", mono, authorizationContext);
        final RedisConnection connection = redisConnectionFactory.getConnection();
        final ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        final HttpHeaders headers = request.getHeaders();

        if (!headers.containsKey("Authorization")) {
            return Mono.just(new AuthorizationDecision(true));
        }
        final Mono<AuthorizationDecision> authorizationDecisionMono = mono
                .filter(iter -> {

                    final Jwt principal = (Jwt) iter.getPrincipal();
                    final String user_name = (String) principal.getClaims().get("user_name");
                    final String authorization = headers.get("Authorization").get(0).replace("Bearer", "").trim();
                    final Boolean exists = connection.hExists(user_name.getBytes(), authorization.getBytes());
                    if (!exists) {

                        throw new AuthenticationServiceException("Invaild Token");
                    }
                    final Boolean expire = connection.expire(user_name.getBytes(), expiraTime);
                    if (!expire) {
                        return false;
                    }
                    return true;
                }).flatMapIterable(Authentication::getAuthorities).
                        map(GrantedAuthority::getAuthority).
                        any(roleId -> true).
                        map(AuthorizationDecision::new).switchIfEmpty(Mono.defer(() -> {
                    return Mono.error(new AuthenticationServiceException("Invaild Token"));
                }));
        ;
        return authorizationDecisionMono;
    }

}
