package com.lnsoft.auth.controller;


import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;

import java.security.interfaces.RSAPublicKey;
import java.util.Collection;
import java.util.Map;

/**
 * @author Louyp
 * @version 1.0
 * @data 2020/01/06 16:04
 */
@RestController
public class UserController {
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private KeyPair keyPair;
    @GetMapping("/oauth/publicKey")
    public Map<String, Object> getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }
    @GetMapping("/user/login")
    public Boolean login(){
        final RedisConnection connection = redisConnectionFactory.getConnection();
        final Boolean set = connection.set("ok".getBytes(), "2222222".getBytes());
        System.out.println(111222);
        return set;
    }
    @GetMapping("/oauth/remove_token")
    public Boolean removeKey(String userName,String token){
        final Long del = redisConnectionFactory.getConnection().hDel(userName.getBytes(),token.getBytes());
        if (del==0){
            throw new InvalidTokenException("Token is not exists");

        }
         return true;
    }
}
