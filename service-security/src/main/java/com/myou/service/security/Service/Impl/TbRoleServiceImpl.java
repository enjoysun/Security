package com.myou.service.security.Service.Impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.myou.service.security.Mapper.TbRoleMapper;
import com.myou.service.security.Service.TbRoleService;
@Service
public class TbRoleServiceImpl implements TbRoleService{

    @Resource
    private TbRoleMapper tbRoleMapper;

}
