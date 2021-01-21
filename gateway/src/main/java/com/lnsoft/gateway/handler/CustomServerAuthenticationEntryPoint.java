package com.lnsoft.gateway.handler;

import com.lnsoft.gateway.enums.ErrorCode;
import net.minidev.json.JSONObject;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

/**
 * 无效token过期
 * @author Louyp
 * @version 1.0
 * @data 2021/01/15 15:29
 *
 */
@Component
public class CustomServerAuthenticationEntryPoint  implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        final ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getHeaders().set("Access-Control-Allow-Origin","*");
        response.getHeaders().set("Cache-Control","no-cache");
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("error_description",ErrorCode.AUTHENTICATION_ENTRYPOIN.getErrorDescription());
        jsonObject.put("error", ErrorCode.AUTHENTICATION_ENTRYPOIN.getError());
        final DataBuffer wrap = response.bufferFactory().wrap(jsonObject.toJSONString().getBytes(Charset.forName("UTF-8")));

        return response.writeWith(Mono.just(wrap));
    }
}
