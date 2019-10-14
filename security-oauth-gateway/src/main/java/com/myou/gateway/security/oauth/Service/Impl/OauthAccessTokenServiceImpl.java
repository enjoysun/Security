package com.myou.gateway.security.oauth.Service.Impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.myou.gateway.security.oauth.Mapper.OauthAccessTokenMapper;
import com.myou.gateway.security.oauth.Service.OauthAccessTokenService;
@Service
public class OauthAccessTokenServiceImpl implements OauthAccessTokenService{

    @Resource
    private OauthAccessTokenMapper oauthAccessTokenMapper;

}
