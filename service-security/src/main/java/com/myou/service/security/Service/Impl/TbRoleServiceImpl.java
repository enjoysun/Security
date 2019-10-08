package com.myou.service.security.Service.Impl;

import com.myou.service.security.Domain.TbRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.myou.service.security.Mapper.TbRoleMapper;
import com.myou.service.security.Service.TbRoleService;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class TbRoleServiceImpl implements TbRoleService {

    @Resource
    private TbRoleMapper tbRoleMapper;


    @Override
    public int insertRole(TbRole role) {
        return tbRoleMapper.insertRole(role);
    }

    @Override
    public List<TbRole> selectRoleById(Long id) {
        return tbRoleMapper.selectRoles(id);
    }

    @Override
    public List<TbRole> RolesById(Long id) {
        return tbRoleMapper.selectRoleById(id);
    }
}
