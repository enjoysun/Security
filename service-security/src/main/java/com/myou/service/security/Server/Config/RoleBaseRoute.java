package com.myou.service.security.Server.Config;

import com.myou.service.security.Server.Domain.Authority;
import com.myou.service.security.Server.Service.CustomGrantedAuthority;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;
import java.util.HashMap;

/*
 * @Time    : 2019/10/8 6:00 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : RoleBaseRoute.java
 * @Software: IntelliJ IDEA
 */
// 实现动态路由权限配置验证
public class RoleBaseRoute implements AccessDecisionVoter<Object> {
    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object o, Collection<ConfigAttribute> collection) {
        // todo 验证动态配置路由
        if (authentication == null)
            return ACCESS_DENIED;
//        HashMap
        int result = ACCESS_ABSTAIN;
        FilterInvocation invocation = (FilterInvocation) o;
        String url = invocation.getRequestUrl();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (ConfigAttribute attribute : collection) {
            if (attribute.getAttribute() == null)
                continue;
            if (this.supports(attribute)) {
                result = ACCESS_DENIED;
                for (GrantedAuthority authority : authorities) {
                    if (attribute.getAttribute().equals(authority.getAuthority())) {
                        return ACCESS_GRANTED;
                    }
                }
            }
        }
        return result;
    }
}
