package com.myou.service.security.Service.Impl;

import com.myou.service.security.Domain.TbRole;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.myou.service.security.Mapper.TbRoleMapper;
import com.myou.service.security.Service.TbRoleService;
import tk.mybatis.mapper.entity.Example;

@Service
public class TbRoleServiceImpl implements TbRoleService{

    @Resource
    private TbRoleMapper tbRoleMapper;



}
