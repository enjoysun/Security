package com.myou.gateway.security.oauth.Grant.Service.Impl;

import com.myou.gateway.security.oauth.Grant.Enums.UserState;
import com.myou.gateway.security.oauth.Grant.Model.UserDetailExtension;
import com.myou.gateway.security.oauth.Model.TbPermission;
import com.myou.gateway.security.oauth.Model.TbRole;
import com.myou.gateway.security.oauth.Model.TbUser;
import com.myou.gateway.security.oauth.Service.TbUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/*
 * @Time    : 2019/10/14 5:39 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : UserDetailImpl.java
 * @Software: IntelliJ IDEA
 */

/**
 * 初始化security链中用户信息类(用户信息、用户角色)
 * */
@Service
public class UserDetailImpl implements UserDetailsService {

    @Autowired
    private TbUserService tbUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username) || StringUtils.isBlank(username))
            throw new UsernameNotFoundException("用户名不存在");
        TbUser tbUser = tbUserService.selectUserRolePermissionById(username);
        if (null == tbUser)
            throw new UsernameNotFoundException("用户信息有误");
        HashSet<GrantedAuthority> hashSet = new HashSet<>();
        tbUser.getRoles().forEach(item -> {
            String roleName = item.getEnname();
            List<String> collect = item.getPermissions().stream().map(TbPermission::getEnname).collect(Collectors.toList());
            hashSet.add(
                    new GrantedAuthorityExtension(roleName, collect)
            );
        });
        UserDetailExtension userDetailExtension = new UserDetailExtension(
                tbUser.getUsername(),
                tbUser.getPassword(),
                hashSet,
                tbUser.getPhone(),
                tbUser.getEmail(),
                UserState.NORMAL);
        return userDetailExtension;
    }
}
