package com.lnsoft.gateway.handler;

import com.alibaba.nacos.client.utils.JSONUtils;
import com.lnsoft.gateway.enums.ErrorCode;
import net.minidev.json.JSONObject;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

/**
 * 无权访问
 * @author Louyp
 * @version 1.0
 * @data 2021/01/05 15:04
 */
@Component
public class ConsumerServerAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException e) {
        final ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getHeaders().set("Access-Control-Allow-Origin","*");
        response.getHeaders().set("Cache-Control","no-cache");
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("error_description",ErrorCode.ACCESS_DENIED.getErrorDescription());
        jsonObject.put("error",ErrorCode.ACCESS_DENIED.getError());
        final DataBuffer wrap = response.bufferFactory().wrap(jsonObject.toJSONString().getBytes(Charset.forName("UTF-8")));

        return response.writeWith(Mono.just(wrap));
    }
}
