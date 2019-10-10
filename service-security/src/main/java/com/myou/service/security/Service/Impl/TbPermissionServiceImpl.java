package com.myou.service.security.Service.Impl;

import com.myou.service.security.Domain.TbPermission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.myou.service.security.Mapper.TbPermissionMapper;
import com.myou.service.security.Service.TbPermissionService;

import java.util.List;

@Service
public class TbPermissionServiceImpl implements TbPermissionService {

    @Resource
    private TbPermissionMapper tbPermissionMapper;

    @Override
    public List<TbPermission> selectByUserId(Long uid) {
        return tbPermissionMapper.selectPermissionByUserId(uid);
    }

    @Override
    public List<TbPermission> selectByRoleId(Long rid) {
        return tbPermissionMapper.selectPermissionByRoleId(rid);
    }
}
