package com.myou.gateway.security.oauth.Grant.Service.Impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.List;

/*
 * @Time    : 2019/10/15 2:38 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : GrantedAuthorityExtension.java
 * @Software: IntelliJ IDEA
 */
public class GrantedAuthorityExtension implements GrantedAuthority {

    private static final long serialVersionUID = 1111292114004161324L;
    private final String role;
    private final List<String> permissions;

    public GrantedAuthorityExtension(String role, List<String> permissions) {
        Assert.hasText(role, "A granted authority textual representation is required");
        this.role = role;
        this.permissions = permissions;
    }

    @Override
    public String getAuthority() {
        return role;
    }

    public List<String> getPermissions() {
        return permissions;
    }
}
