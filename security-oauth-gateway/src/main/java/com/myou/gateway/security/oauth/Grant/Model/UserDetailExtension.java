package com.myou.gateway.security.oauth.Grant.Model;

import com.myou.gateway.security.oauth.Grant.Enums.UserState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/*
 * @Time    : 2019/10/14 5:26 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : UserDetailExtension.java
 * @Software: IntelliJ IDEA
 */

/**
 * 用户信息定制
 * */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailExtension implements UserDetails {
    private String userName;
    private String passWord;
    private List<String> permissions;
    private String phone;
    private String email;
    private UserState state;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return passWord;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
