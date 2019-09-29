package com.myou.service.security.Service.Impl;

import com.myou.service.security.Domain.OauthClientDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.myou.service.security.Mapper.OauthClientDetailsMapper;
import com.myou.service.security.Service.OauthClientDetailsService;

@Service
public class OauthClientDetailsServiceImpl implements OauthClientDetailsService {

    @Resource
    private OauthClientDetailsMapper oauthClientDetailsMapper;

    @Override
    public OauthClientDetails selectOauthByAppId(String aid) {
        return oauthClientDetailsMapper.selectOauthClientByAppId(aid);
    }
}
