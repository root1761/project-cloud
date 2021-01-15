package com.lnsoft.auth.authorization;

import lombok.Data;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

/**
 * @author Louyp
 * @version 1.0
 * @data 2021/01/13 14:39
 */
@Slf4j
public class ConsumerTokenEnhancer implements TokenEnhancer {

    private Long expiraTime;

    private RedisConnectionFactory redisConnectionFactory;

    public ConsumerTokenEnhancer(Long expiraTime, RedisConnectionFactory redisConnectionFactory) {
        this.expiraTime = expiraTime;
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @SneakyThrows
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        log.info("ConsumerTokenEnhancer[]enhance[]accessToken:{}authentication:{}", accessToken, authentication);
        final RedisConnection connection = redisConnectionFactory.getConnection();
        final User principal = (User) authentication.getPrincipal();
        final Long del = connection.del(principal.getUsername().getBytes());
        final Boolean token = connection.hSet(principal.getUsername().getBytes(),accessToken.getValue().getBytes(), authentication.toString().getBytes());
        final Boolean expire = connection.expire((principal.getUsername()).getBytes(), expiraTime);

        if (!(token&&expire)) {
            throw new InvalidTokenException("Encoded token is inMemory failed");
        }
        System.out.println(accessToken + "   " + authentication);
        return accessToken;
    }
}
