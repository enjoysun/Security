package com.myou.spring.cloud.zuul.Filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/*
 * @Time    : 2019/9/18 10:56 AM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : ErrorFilter.java
 * @Software: IntelliJ IDEA
 */
@Component
public class ErrorFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        Throwable throwable = context.getThrowable();
        return null;
//        return new ClientHttpResponse() {
//            @Override
//            public HttpStatus getStatusCode() throws IOException {
//                return HttpStatus.OK;
//            }
//
//            @Override
//            public int getRawStatusCode() throws IOException {
//                return HttpStatus.OK.value();
//            }
//
//            @Override
//            public String getStatusText() throws IOException {
//                return HttpStatus.OK.getReasonPhrase();
//            }
//
//            @Override
//            public void close() {
//
//            }
//
//            @Override
//            public InputStream getBody() throws IOException {
//
//                ObjectMapper objectMapper = new ObjectMapper();
//                HashMap<Object, Object> map = new HashMap<>();
//                map.put("status", 200);
//                map.put("message", "服务错误");
//                return new ByteArrayInputStream(objectMapper.writeValueAsBytes(map));
//            }
//
//            @Override
//            public HttpHeaders getHeaders() {
//                HttpHeaders headers = new HttpHeaders();
//                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//                return headers;
//            }
//        };
    }
}
