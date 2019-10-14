package com.myou.gateway.security.oauth.Service.Impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.myou.gateway.security.oauth.Mapper.OauthRefreshTokenMapper;
import com.myou.gateway.security.oauth.Service.OauthRefreshTokenService;
@Service
public class OauthRefreshTokenServiceImpl implements OauthRefreshTokenService{

    @Resource
    private OauthRefreshTokenMapper oauthRefreshTokenMapper;

}
