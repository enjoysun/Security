package com.myou.service.security.Server.Config;

import com.myou.service.security.Domain.TbRole;
import com.myou.service.security.Service.TbRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private FilterInvocationSecurityMetadataSource superMetadataSource;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private TbRoleService roleService;

    private Map<String, String> roleMap = new HashMap<>();

    public CustomFilterInvocationSecurityMetaDataSource(FilterInvocationSecurityMetadataSource expressionBasedFilterInvocationSecurityMetadataSource) {
        superMetadataSource = expressionBasedFilterInvocationSecurityMetadataSource;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        // 加载角色树可以再初始化登录完成并缓冲
        List<TbRole> roles = roleService.selectRolesAndPermission();
        roles.forEach(item -> {
            item.getPermissions().forEach(per -> {
                roleMap.put(per.getEnname(), item.getEnname());
            });
        });
        FilterInvocation invocation = (FilterInvocation) o;
        String url = invocation.getRequestUrl();
        for (Map.Entry<String, String> entry : roleMap.entrySet()) {
            if (antPathMatcher.match(entry.getKey(), url)) {
                return SecurityConfig.createList(entry.getValue());
            }
        }
        // 使用默认的superMetadataSource角色验证成功
        return SecurityConfig.createList("ROLE_USER");
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
