package com.myou.gateway.security.oauth.Service.Impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.myou.gateway.security.oauth.Mapper.ClientDetailsMapper;
import com.myou.gateway.security.oauth.Service.ClientDetailsService;
@Service
public class ClientDetailsServiceImpl implements ClientDetailsService{

    @Resource
    private ClientDetailsMapper clientDetailsMapper;

}
