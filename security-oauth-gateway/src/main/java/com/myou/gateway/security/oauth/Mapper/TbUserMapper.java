package com.myou.gateway.security.oauth.Mapper;

import com.myou.gateway.security.oauth.Model.TbUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.Mymapper;

public interface TbUserMapper extends Mymapper<TbUser> {
    TbUser selectUserRolePermission(@Param("uid") Long uid);
}