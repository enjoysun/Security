package com.myou.service.security.Server.Service;

import com.myou.service.security.Domain.TbUser;
import com.myou.service.security.Service.TbPermissionService;
import com.myou.service.security.Service.TbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/*
 * @Time    : 2019/9/24 5:57 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : CustomPermissionEvaluator.java
 * @Software: IntelliJ IDEA
 */
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {
    @Autowired
    private TbUserService tbUserService;
    @Autowired
    private TbPermissionService tbPermissionService;

    @Override
    public boolean hasPermission(Authentication authentication, Object o, Object o1) {
//        String userName = authentication.getName();
//        TbUser user = tbUserService.findByUserName(userName);
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
