package com.myou.spring.cloud.zuul.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/*
 * @Time    : 2019/9/17 5:51 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : WebLoginFallbackProvider.java
 * @Software: IntelliJ IDEA
 */
@Component
public class WebLoginFallbackProvider implements FallbackProvider {
    @Override
    public String getRoute() {
        // 指定service-id即application-name
        return "CONSUMER-FEIGN";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.OK.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.OK.getReasonPhrase();
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                ObjectMapper objectMapper = new ObjectMapper();
                HashMap<Object, Object> map = new HashMap<>();
                map.put("status", 200);
                map.put("message", "网络震荡");
                return new ByteArrayInputStream(objectMapper.writeValueAsBytes(map));
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                return headers;
            }
        };
    }
}
