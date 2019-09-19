package com.myou.service.security.Server.Config;

/*
 * @Time    : 2019/9/19 4:26 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : AuthorizationServerConfiguration.java
 * @Software: IntelliJ IDEA
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * @Author myoueva@gmail.com
 * @Description
 * 该配置类就是一个授权服务的服务配置类:
 *  类中保留客户端认证请求信息
 *  withClient：客户端ID,即一个客户端的标识
 *  secret:授权加密字段
 *  authorizedGrantTypes：授权类型(authorization_code:授权码，refresh_token:token刷新，password:密码授权)
 *  scopes:授权范围
 *  redirectUris:携带验证码回调低至
 * @Date 4:26 PM 2019/9/19
 * @Param
 * @return
 **/
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                .withClient("app_id")
                .secret(bCryptPasswordEncoder.encode("app_secret"))
                .authorizedGrantTypes("authorization_code")
                .scopes("is_auth")
                .redirectUris("http://localhost:8091/service/welcome?message=login");
    }
}

/** 详细介绍如下(摘自Oauth2协议官网文档描述):
 Clients in possession of a client password MAY use the HTTP Basic
 authentication scheme as defined in [RFC2617] to authenticate with
 the authorization server.  The client identifier is encoded using the
 "application/x-www-form-urlencoded" encoding algorithm per
 Appendix B, and the encoded value is used as the username; the client
 password is encoded using the same algorithm and used as the
 password.  The authorization server MUST support the HTTP Basic
 authentication scheme for authenticating clients that were issued a
 client password.

 For example (with extra line breaks for display purposes only):

 Authorization: Basic czZCaGRSa3F0Mzo3RmpmcDBaQnIxS3REUmJuZlZkbUl3

 Alternatively, the authorization server MAY support including the
 client credentials in the request-body using the following
 parameters:

 client_id
 REQUIRED.  The client identifier issued to the client during
 the registration process described by Section 2.2.

 client_secret
 REQUIRED.  The client secret.  The client MAY omit the
 parameter if the client secret is an empty string.
 **/
