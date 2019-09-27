package com.myou.service.security.Service.Impl;

import com.myou.service.security.Domain.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.myou.service.security.Mapper.TbUserMapper;
import com.myou.service.security.Service.TbUserService;
import tk.mybatis.mapper.entity.Example;

@Service
public class TbUserServiceImpl implements TbUserService {

    @Resource
    private TbUserMapper tbUserMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public TbUser findByUserName(String name) {
        Example example = new Example(TbUser.class);
        example.createCriteria().andEqualTo("username", name);
        return tbUserMapper.selectOneByExample(example);
    }

    @Override
    public TbUser insertTbUser(TbUser user) throws Exception {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        int i = tbUserMapper.insertUser(user);
        return i > 0 ? user : null;
    }
}
