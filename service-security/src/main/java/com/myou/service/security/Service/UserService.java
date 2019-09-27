package com.myou.service.security.Service;

import com.myou.service.security.Common.Exception.UserExitsException;
import com.myou.service.security.Common.Exception.UserNotExitException;
import com.myou.service.security.Domain.TbUser;
import com.myou.service.security.Domain.UserInfo;

import javax.naming.AuthenticationException;

/*
 * @Time    : 2019/9/27 4:51 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : UserService.java
 * @Software: IntelliJ IDEA
 */
public interface UserService {
    UserInfo findUserInfo(String name) throws UserNotExitException;

    String login(TbUser user) throws AuthenticationException;

    TbUser register(TbUser user) throws Exception;

    String refresh_token(String old_token);
}
