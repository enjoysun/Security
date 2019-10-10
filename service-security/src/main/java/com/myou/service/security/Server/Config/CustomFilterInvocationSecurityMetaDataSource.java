package com.myou.service.security.Server.Config;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;

/*
 * @Time    : 2019/10/10 5:08 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : CustomFilterInvocationSecurityMetaDataSource.java
 * @Software: IntelliJ IDEA
 */

/**
 * 动态加载权限并验证
 */
public class CustomFilterInvocationSecurityMetaDataSource implements FilterInvocationSecurityMetadataSource {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
