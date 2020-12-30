/*
package com.lnsoft.qxgd;

import com.lnsoft.qxgd.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
class QxgdApplicationTests {
    @Autowired
    private RestTemplate restTemplate;

    @Test
    void contextLoads() {
        Map requestMap = new HashMap<String, Object>();
        requestMap.put("userName", "salaboy");
        final ResponseEntity<Response> salaboy = restTemplate.exchange("http://ACTIVITI/activiti/getProcessDefinition", HttpMethod.GET, new HttpEntity<>(getHeaders()), Response.class);
        final Response body = salaboy.getBody();
        System.out.println(body.getResult());
    }

    @Test
    public void postRibbon() {
        Map requestMap = new HashMap<String, Object>();
        requestMap.put("userGroups2", "otherTeam");
        final ResponseEntity<Response> salaboy = restTemplate.exchange("http://ACTIVITI/activiti/completeTaskWithAssignee/e2274729-2c9b-11eb-ae75-6431509aeee9", HttpMethod.POST, new HttpEntity<>(requestMap,getHeaders()), Response.class);
        final Response body = salaboy.getBody();
        System.out.println(body.getResult());
    }

    public HttpHeaders getHeaders() { // 要进行一个Http头信息配置
        HttpHeaders headers = new HttpHeaders(); // 定义一个HTTP的头信息
        String auth = "salaboy:password"; // 认证的原始信息
        byte[] encodedAuth = Base64.getEncoder()
                .encode(auth.getBytes(Charset.forName("US-ASCII"))); // 进行一个加密的处理
        // 在进行授权的头信息内容配置的时候加密的信息一定要与“Basic”之间有一个空格
        String authHeader = "Basic " + new String(encodedAuth);

        headers.set("Content-Type", "application/json");
        headers.set("Authorization", authHeader);
        return headers;
    }

}
*/
