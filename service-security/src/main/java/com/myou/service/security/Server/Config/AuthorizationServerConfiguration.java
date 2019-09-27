package com.myou.service.security.Server.Config;

/*
 * @Time    : 2019/9/19 4:26 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : AuthorizationServerConfiguration.java
 * @Software: IntelliJ IDEA
 */

import com.myou.service.security.Common.Config.RedisLettuceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

/**
 * @Author myoueva@gmail.com
 * @Description 该配置类是一个授权服务的服务配置类:
 * 类中保留客户端认证请求信息
 * withClient：客户端ID,即一个客户端的标识
 * secret:授权加密字段
 * authorizedGrantTypes：授权类型(authorization_code:授权码，refresh_token:token刷新，password:密码授权)
 * scopes:授权范围
 * redirectUris:携带验证码回调地址
 * <p>
 * 该认证服务类包含两大信息:
 * 1.客户端详细配置
 * 2.token详细配置
 * 3.认证资源详细配置
 * oauth提供方由认证服务和资源服务组成，
 * @Date 4:26 PM 2019/9/19
 * @Param
 * @return
 **/
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RedisLettuceFactory redisLettuceFactory;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    /**
     * 构建token持久化配置模式
     * jdbc持久化至数据库
     * jwt将token构建为jwt模式
     * redis持久化至缓存
     * in memory内存模式
     */
    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    /**
     * 客户端字段持久化
     * 1.持久化至内存，配置如注释的clients.inMemory()所示
     * 2.持久化至数据库，则需要接入jdbc
     */
    @Bean
    public ClientDetailsService clientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        super.configure(security);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
    }

    /**
     * 认证token操作配置类
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore());
        endpoints.authenticationManager(authenticationManager);

        //TokenService
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(endpoints.getTokenStore());
        tokenServices.setSupportRefreshToken(false);
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30)); // 30天

        endpoints.tokenServices(tokenServices);
    }
}

/**
 * 详细介绍如下(摘自Oauth2协议官网文档描述):
 * Clients in possession of a client password MAY use the HTTP Basic
 * authentication scheme as defined in [RFC2617] to authenticate with
 * the authorization server.  The client identifier is encoded using the
 * "application/x-www-form-urlencoded" encoding algorithm per
 * Appendix B, and the encoded value is used as the username; the client
 * password is encoded using the same algorithm and used as the
 * password.  The authorization server MUST support the HTTP Basic
 * authentication scheme for authenticating clients that were issued a
 * client password.
 * <p>
 * For example (with extra line breaks for display purposes only):
 * <p>
 * Authorization: Basic czZCaGRSa3F0Mzo3RmpmcDBaQnIxS3REUmJuZlZkbUl3
 * <p>
 * Alternatively, the authorization server MAY support including the
 * client credentials in the request-body using the following
 * parameters:
 * <p>
 * client_id
 * REQUIRED.  The client identifier issued to the client during
 * the registration process described by Section 2.2.
 * <p>
 * client_secret
 * REQUIRED.  The client secret.  The client MAY omit the
 * parameter if the client secret is an empty string.
 **/
