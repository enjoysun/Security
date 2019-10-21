package com.myou.gateway.security.oauth.Service.Impl;

import com.myou.gateway.security.oauth.Model.TbUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.myou.gateway.security.oauth.Mapper.TbUserMapper;
import com.myou.gateway.security.oauth.Service.TbUserService;

@Service
public class TbUserServiceImpl implements TbUserService {

    @Resource
    private TbUserMapper tbUserMapper;

    @Override
    public TbUser selectUserRolePermissionById(String uid) {
        return tbUserMapper.selectUserRolePermission(uid);
    }
}
