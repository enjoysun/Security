package com.myou.service.security.Server.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
 * @Time    : 2019/9/23 5:40 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : CustomUserDetailService.java
 * @Software: IntelliJ IDEA
 */
/**
 * 待完成
 * */
@Service
public class CustomUserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
