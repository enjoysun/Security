package com.myou.gateway.security.oauth.Service;

import com.myou.gateway.security.oauth.Model.TbUser;

public interface TbUserService {
    TbUser selectUserRolePermissionById(Long uid);
}
