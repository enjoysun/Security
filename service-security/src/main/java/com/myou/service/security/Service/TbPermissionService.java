package com.myou.service.security.Service;

import com.myou.service.security.Domain.TbPermission;

import java.util.List;

public interface TbPermissionService {

    List<TbPermission> selectByUserId(Long uid);

    List<TbPermission> selectByRoleId(Long rid);
}
