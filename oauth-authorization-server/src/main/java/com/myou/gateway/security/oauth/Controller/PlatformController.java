package com.myou.gateway.security.oauth.Controller;

import com.myou.gateway.security.oauth.Common.Utils.Result;
import com.myou.gateway.security.oauth.Model.OauthClientDetails;
import com.myou.gateway.security.oauth.Service.OauthClientDetailsService;
import com.myou.gateway.security.oauth.Service.OauthClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/*
 * @Time    : 2019/10/23 11:26 AM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : PlatformController.java
 * @Software: IntelliJ IDEA
 */
@RestController
public class PlatformController {

    @Autowired
    private OauthClientUtil oauthClientUtil;

    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/platform/register")
    public Result registerPlatform(@RequestBody Map<String, String> map) {
        String name = map.get("name");
        String phone = map.get("phone");
        String uri = map.get("uri");
        String clientID = oauthClientUtil.createClientID(name);
        String secret = oauthClientUtil.createSecret(name, clientID);
        OauthClientDetails clientDetails = OauthClientDetails.builder().clientId(clientID)
                .clientSecret(bCryptPasswordEncoder.encode(secret))
                .name(name)
                .phone(phone)
                .scope("all")
                .authorizedGrantTypes("password,authorization_code,refresh_code")
                .webServerRedirectUri(uri)
                .platformId(secret)
                .build();
        oauthClientDetailsService.OauthClientDetailsInsert(clientDetails);
        return Result.success(clientDetails);
    }
}
