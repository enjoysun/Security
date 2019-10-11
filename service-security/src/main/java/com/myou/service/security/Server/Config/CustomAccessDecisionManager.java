package com.myou.service.security.Server.Config;

import com.myou.service.security.Common.Exception.AuthenticationNotExitException;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;
import java.util.Iterator;

/*
 * @Time    : 2019/10/11 10:04 AM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : CustomAccessDecisionManager.java
 * @Software: IntelliJ IDEA
 */

/**
 * 在自定义的SecurityMetaDadaSource中加载了权限资源，在该模块进行资源决策
 */
public class CustomAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        Iterator<ConfigAttribute> configAttributeIterator = collection.iterator();
        while (configAttributeIterator.hasNext()) {
            if (authentication == null) {
                throw new AccessDeniedException("用户信息加载失败，权限不足");
            }
            ConfigAttribute attribute = configAttributeIterator.next();
            String needCode = attribute.getAttribute();
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (StringUtils.equals(authority.getAuthority(), needCode)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
