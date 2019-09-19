package com.myou.spring.cloud.zuul.Filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/*
 * @Time    : 2019/9/18 10:28 AM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : AuthFilter.java
 * @Software: IntelliJ IDEA
 */
@Component
public class AuthFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        // 获取netflix模块的HttpServletRequest截获请求信息
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String token = request.getParameter("token");
        if (StringUtils.isNotBlank(token)){
            context.setSendZuulResponse(true);
            context.setResponseStatusCode(200);
        }else {
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(400);
            context.setResponseBody("error");
//            throw new ZuulException("ss", 200, "ss");
        }
        return null;
    }
}
