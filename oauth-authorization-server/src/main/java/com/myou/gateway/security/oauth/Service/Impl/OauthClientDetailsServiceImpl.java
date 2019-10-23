package com.myou.gateway.security.oauth.Service.Impl;

import com.myou.gateway.security.oauth.Model.OauthClientDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.myou.gateway.security.oauth.Mapper.OauthClientDetailsMapper;
import com.myou.gateway.security.oauth.Service.OauthClientDetailsService;

@Service
public class OauthClientDetailsServiceImpl implements OauthClientDetailsService {

    @Resource
    private OauthClientDetailsMapper oauthClientDetailsMapper;

    @Override
    public boolean OauthClientDetailsInsert(OauthClientDetails oauthClientDetails) {
        int insert = oauthClientDetailsMapper.insert(oauthClientDetails);
        return insert > 0 ? true : false;
    }
}
