package com.myou.gateway.security.oauth.Grant.Service.Impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
 * @Time    : 2019/10/14 5:39 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : UserDetailImpl.java
 * @Software: IntelliJ IDEA
 */
@Service
public class UserDetailImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)||StringUtils.isBlank(username))
            throw new UsernameNotFoundException("用户名不存在");
        
        return null;
    }
}
