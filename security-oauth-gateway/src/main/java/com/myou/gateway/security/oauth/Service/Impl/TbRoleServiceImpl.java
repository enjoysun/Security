package com.myou.gateway.security.oauth.Service.Impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.myou.gateway.security.oauth.Mapper.TbRoleMapper;
import com.myou.gateway.security.oauth.Service.TbRoleService;
@Service
public class TbRoleServiceImpl implements TbRoleService{

    @Resource
    private TbRoleMapper tbRoleMapper;

}
