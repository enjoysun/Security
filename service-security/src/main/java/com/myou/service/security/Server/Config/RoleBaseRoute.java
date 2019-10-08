package com.myou.service.security.Server.Config;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

import java.util.Collection;

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
        return 0;
    }
}
