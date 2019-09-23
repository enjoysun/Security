package com.myou.service.security.Mapper;

import com.myou.service.security.Domain.TbPermission;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.Mymapper;

import java.util.List;

public interface TbPermissionMapper extends Mymapper<TbPermission> {
    List<TbPermission> selectPermissionByUserId(@Param("uid") Long uid);
}