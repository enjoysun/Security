package com.myou.service.security.Server.Config;

import com.myou.service.security.Domain.TbRole;
import com.myou.service.security.Service.TbRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import java.util.*;

/*
 * @Time    : 2019/10/10 5:08 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : CustomFilterInvocationSecurityMetaDataSource.java
 * @Software: IntelliJ IDEA
 */

/**
 * 动态加载权限并验证
 * SecurityMetadataSource:访问权限资源中心(加载所有权限至系统)
 * 权限资源:
 * url:role（一个url对应多个角色、一个角色对应多个url）
 */
public class CustomFilterInvocationSecurityMetaDataSource implements FilterInvocationSecurityMetadataSource {

    private FilterInvocationSecurityMetadataSource superMetadataSource;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private TbRoleService roleService;

    // 权限资源集合
    private Map<String, Collection<ConfigAttribute>> map = new HashMap<>();

    public CustomFilterInvocationSecurityMetaDataSource(FilterInvocationSecurityMetadataSource expressionBasedFilterInvocationSecurityMetadataSource) {
        superMetadataSource = expressionBasedFilterInvocationSecurityMetadataSource;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        List<TbRole> roles = roleService.selectRolesAndPermission();
        roles.forEach(item -> {
            item.getPermissions().forEach(per -> {
                map.put(per.getUrl(), new ArrayList<ConfigAttribute>(Arrays.asList(new SecurityConfig(item.getEnname()))));
            });
        });
        FilterInvocation invocation = (FilterInvocation) o;
        String url = invocation.getRequestUrl();
        String method = invocation.getRequest().getMethod();
        for (Map.Entry<String, Collection<ConfigAttribute>> entry : map.entrySet()) {
            if (antPathMatcher.match(entry.getKey(), url)) {
                return entry.getValue();
            }
        }
        // 若匹配失败则默认为游客权限
        return SecurityConfig.createList("ROLE_LOGIN");
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
