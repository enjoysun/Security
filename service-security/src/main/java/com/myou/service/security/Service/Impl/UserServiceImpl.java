package com.myou.service.security.Service.Impl;

import com.myou.service.security.Common.Exception.UserExitsException;
import com.myou.service.security.Common.Exception.UserNotExitException;
import com.myou.service.security.Common.Jwt.JwtUtil;
import com.myou.service.security.Domain.TbPermission;
import com.myou.service.security.Domain.TbUser;
import com.myou.service.security.Domain.UserInfo;
import com.myou.service.security.Service.TbPermissionService;
import com.myou.service.security.Service.TbUserService;
import com.myou.service.security.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;

/*
 * @Time    : 2019/9/27 5:00 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : UserServiceImpl.java
 * @Software: IntelliJ IDEA
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbPermissionService permissionService;

    @Autowired
    private TbUserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserInfo findUserInfo(String name) throws UserNotExitException {
        TbUser user = userService.findByUserName(name);
        if (null == user)
            throw new UserNotExitException("用户不存在");
        List<TbPermission> permissions = permissionService.selectByUserId(user.getId());
        return UserInfo.builder().permission(permissions).user(user).build();
    }

    @Override
    public String login(TbUser user) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        return jwtUtil.generateToken(userDetails);
    }

    @Override
    public TbUser register(TbUser user) throws Exception {
        TbUser tbUser = userService.findByUserName(user.getUsername());
        if (null != tbUser)
            throw new UserExitsException(String.format("用户名%s已存在", user.getUsername()));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        TbUser insertTbUser = userService.insertTbUser(user);
        return insertTbUser;
    }

    @Override
    public String refresh_token(String old_token) {
        return !jwtUtil.isTokenExpired(old_token) ? jwtUtil.refreshToken(old_token) : null;
    }
}
