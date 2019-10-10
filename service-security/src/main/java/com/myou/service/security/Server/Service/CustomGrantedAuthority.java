package com.myou.service.security.Server.Service;

import com.myou.service.security.Domain.TbPermission;
import com.myou.service.security.Domain.TbRole;
import com.myou.service.security.Server.Domain.Authority;
import com.myou.service.security.Service.TbPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

/*
 * @Time    : 2019/10/9 4:27 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : CustomGrantedAuthority.java
 * @Software: IntelliJ IDEA
 */
public class CustomGrantedAuthority implements GrantedAuthority {
    private TbRole role;

    private final Authority authority;

    public CustomGrantedAuthority(TbRole role, List<TbPermission> permissions) {
        this.role = role;
        authority = Authority.builder().role(role.getName()).enRole(role.getEnname()).
                permission(permissions.stream().map(s -> s.getEnname()).collect(Collectors.toList())).build();
    }

    @Override
    public String getAuthority() {
        return authority.getEnRole();
    }

    public Authority getModel() {
        return authority;
    }

    public String toString() {
        return this.authority.getEnRole();
    }
}
