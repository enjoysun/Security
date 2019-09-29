package com.myou.service.security.Common.Service.Impl;

import com.myou.service.security.Common.Jwt.JwtUserDetail;
import com.myou.service.security.Common.States.UserState;
import com.myou.service.security.Domain.TbPermission;
import com.myou.service.security.Domain.TbUser;
import com.myou.service.security.Service.TbPermissionService;
import com.myou.service.security.Service.TbUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

/*
 * @Time    : 2019/9/26 5:41 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : JwtUserDetailImpl.java
 * @Software: IntelliJ IDEA
 */
@Service
public class JwtUserDetailImpl implements UserDetailsService {
    @Autowired
    private TbUserService userService;

    @Autowired
    private TbPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (StringUtils.isBlank(s))
            throw new UsernameNotFoundException("用户名为空");
        TbUser tbUser = userService.findByUserName(s);
        if (tbUser == null)
            throw new UsernameNotFoundException("不存在用户");
        List<TbPermission> tbPermissions = permissionService.selectByUserId(tbUser.getId());
        // 构造userDetail实例:user+permission
        HashSet<GrantedAuthority> hashSet = new HashSet<>();
        tbPermissions.forEach(item -> {
            hashSet.add(
                    new SimpleGrantedAuthority(item.getName())
            );
        });
        JwtUserDetail jwtUserDetail = new JwtUserDetail(
                tbUser.getUsername(),
                tbUser.getPassword(),
                hashSet,
                UserState.NORMAL.getState()
        );
        return jwtUserDetail;
    }
}
