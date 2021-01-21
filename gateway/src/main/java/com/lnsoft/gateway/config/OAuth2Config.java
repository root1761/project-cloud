package com.lnsoft.gateway.config;

import com.google.common.base.Converter;
import com.lnsoft.gateway.handler.ConsumerServerAccessDeniedHandler;
import com.lnsoft.gateway.handler.CustomServerAuthenticationEntryPoint;
import com.lnsoft.gateway.manager.AuthorizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.server.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import reactor.core.publisher.Mono;

/**
 * @author Louyp
 * @version 1.0
 * @data 2021/01/12 17:34
 */
@EnableWebFluxSecurity
@Configuration
public class OAuth2Config {
//    @Autowired
//    private ReactiveRedisAuthenticationManager reactiveRedisAuthenticationManager;
    @Autowired
    private AuthorizationManager authorizationManager;
    @Autowired
    private CustomServerAuthenticationEntryPoint customServerAuthenticationEntryPoint;
    @Autowired
    private ConsumerServerAccessDeniedHandler consumerServerAccessDeniedHandler;
    @Bean
    SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) throws Exception{
        http.oauth2ResourceServer().jwt();
                //.jwtAuthenticationConverter();
        http
              .httpBasic().disable()
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS)
                .permitAll().anyExchange()
                .access(authorizationManager)
                .and().exceptionHandling().authenticationEntryPoint(customServerAuthenticationEntryPoint)
                .accessDeniedHandler(consumerServerAccessDeniedHandler) ;
        return http.build();
    }

   /* @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AuthConstants.AUTHORITY_PREFIX);
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(AuthConstants.AUTHORITY_CLAIM_NAME);

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }*/
}
