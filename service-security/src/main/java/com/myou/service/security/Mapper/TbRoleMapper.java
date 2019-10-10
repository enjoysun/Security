package com.myou.service.security.Mapper;

import com.myou.service.security.Domain.TbRole;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.Mymapper;

import java.util.List;

public interface TbRoleMapper extends Mymapper<TbRole> {
    int insertRole(TbRole role);

    List<TbRole> selectRoles(@Param("rid") Long rid);

    List<TbRole> selectRoleById(@Param("uid") Long uid);

    List<TbRole> selectRolesAndPermission();
}