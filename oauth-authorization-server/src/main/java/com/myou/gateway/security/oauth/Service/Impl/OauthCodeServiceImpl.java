package com.myou.gateway.security.oauth.Service.Impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.myou.gateway.security.oauth.Mapper.OauthCodeMapper;
import com.myou.gateway.security.oauth.Service.OauthCodeService;
@Service
public class OauthCodeServiceImpl implements OauthCodeService{

    @Resource
    private OauthCodeMapper oauthCodeMapper;

}
