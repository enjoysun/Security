package com.myou.service.security.Service.Impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.myou.service.security.Mapper.TbRolePermissionMapper;
import com.myou.service.security.Service.TbRolePermissionService;
@Service
public class TbRolePermissionServiceImpl implements TbRolePermissionService{

    @Resource
    private TbRolePermissionMapper tbRolePermissionMapper;

}
