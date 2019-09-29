package com.myou.service.security.Common.Jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

/*
 * @Time    : 2019/9/26 5:02 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : JwtUserDetail.java
 * @Software: IntelliJ IDEA
 */

/**
 * 定制JWT用户信息实体
 */
public class JwtUserDetail implements UserDetails {
    private String UserName;
    private String UserPassword;
    // 用户权限集合
    private Collection<? extends GrantedAuthority> Authorities;
    private int state;

    public JwtUserDetail() {
    }

    public JwtUserDetail(String userName, String userPassword, Collection<? extends GrantedAuthority> authorities, int state) {
        UserName = userName;
        UserPassword = userPassword;
        Authorities = authorities;
        this.state = state;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Authorities;
    }

    @Override
    public String getPassword() {
        return UserPassword;
    }

    @Override
    public String getUsername() {
        return UserName;
    }

    // 账号是否过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 账号是否锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 凭证是否过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 是否可用
    @Override
    public boolean isEnabled() {
        return true;
    }
}
