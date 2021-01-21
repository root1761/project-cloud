package com.lnsoft.auth.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.bootstrap.encrypt.KeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.security.KeyPair;
import java.util.Arrays;
import java.util.List;

/**
 * @author Louyp
 * @version 1.0
 * @data 2021/01/05 9:35
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Value("${spring.redis.expira.time}")
    private Long expiraTime;
    @Autowired
    private KeyProperties keyProperties;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").
                checkTokenAccess("permitAll()").
                allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("password").
                authorizedGrantTypes("authorization_code","client_credentials","implicit","password", "refresh_token").
                accessTokenValiditySeconds(24*60*60*30*12).//
                refreshTokenValiditySeconds(24*60*60*30*12*10).
                resourceIds("rid").
                scopes("all").
                secret(passwordEncoder.encode("123"))
                .autoApprove(false)
                .redirectUris("http://www.baidu.com");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
   /*     endpoints.tokenStore(new RedisTokenStore(redisConnectionFactory))
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);*/
        TokenEnhancerChain enhancerChain=new TokenEnhancerChain();
        final List<TokenEnhancer> objects = Arrays.asList(jwtAccessTokenConverter(),consumerTokenEnhancer());
        enhancerChain.setTokenEnhancers(objects);
        endpoints.tokenEnhancer(enhancerChain).accessTokenConverter(jwtAccessTokenConverter())
                .authenticationManager(authenticationManager)/*认证管理器*/
                .tokenStore(tokenStore)/*令牌存储*/
                .userDetailsService(userDetailsService)/*用户信息ser参数
                // refresh_token有两种使用方式：重复使用(true)、非重复vice*/
                //                .tokenEnhancer(enhancerChain)//自定义jwt令牌中的携带使用(false)，默认为true
                //      1.重复使用：access_token过期刷新时， refresh token过期时间未改变，仍以初次生成的时间为准
                //      2.非重复使用：access_token过期刷新时， refresh_token过期时间延续，在refresh_token有效期内刷新而无需失效再次登录
                .reuseRefreshTokens(false);
    }
    public ConsumerTokenEnhancer consumerTokenEnhancer(){
        return new ConsumerTokenEnhancer(expiraTime,redisConnectionFactory);
}
    @Bean
    public TokenStore tokenStore() {
       // return new JwtTokenStore(jwtAccessTokenConverter());
        return new RedisTokenStore(redisConnectionFactory);
    }
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair()); //对称秘钥，资源服务器使用该秘钥来验证
        return converter;
    }

    @Bean
    public KeyPair keyPair(){
    return  (new KeyStoreKeyFactory(this.keyProperties.getKeyStore().getLocation(), this.keyProperties.getKeyStore().getSecret().toCharArray())).getKeyPair(this.keyProperties.getKeyStore().getAlias(), this.keyProperties.getKeyStore().getPassword().toCharArray());

}

}
