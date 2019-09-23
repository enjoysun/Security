package com.myou.service.security.Service.Impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.myou.service.security.Mapper.TbUserRoleMapper;
import com.myou.service.security.Service.TbUserRoleService;
@Service
public class TbUserRoleServiceImpl implements TbUserRoleService{

    @Resource
    private TbUserRoleMapper tbUserRoleMapper;

}
