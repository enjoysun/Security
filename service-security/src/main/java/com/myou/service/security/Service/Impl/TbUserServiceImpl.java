package com.myou.service.security.Service.Impl;

import com.myou.service.security.Domain.TbUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.myou.service.security.Mapper.TbUserMapper;
import com.myou.service.security.Service.TbUserService;
import tk.mybatis.mapper.entity.Example;

@Service
public class TbUserServiceImpl implements TbUserService {

    @Resource
    private TbUserMapper tbUserMapper;

    @Override
    public TbUser findByUserName(String name) {
        Example example = new Example(TbUser.class);
        example.createCriteria().andEqualTo("username", name);
        return tbUserMapper.selectOneByExample(example);
    }
}
