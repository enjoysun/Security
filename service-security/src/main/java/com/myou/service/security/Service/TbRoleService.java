package com.myou.service.security.Service;

import com.myou.service.security.Domain.TbRole;

import java.util.List;

public interface TbRoleService {
    int insertRole(TbRole role);

    List<TbRole> selectRoleById(Long id);

    List<TbRole> RolesById(Long id);

    List<TbRole> selectRolesAndPermission();
}
