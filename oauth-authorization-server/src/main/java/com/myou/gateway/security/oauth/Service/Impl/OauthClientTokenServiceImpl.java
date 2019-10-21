package com.myou.gateway.security.oauth.Service.Impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.myou.gateway.security.oauth.Mapper.OauthClientTokenMapper;
import com.myou.gateway.security.oauth.Service.OauthClientTokenService;
@Service
public class OauthClientTokenServiceImpl implements OauthClientTokenService{

    @Resource
    private OauthClientTokenMapper oauthClientTokenMapper;

}
