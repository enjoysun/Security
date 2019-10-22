#### 本模块为网关模块，接入oauth资源认证模块，完成网关服务拦截认证(rbac)  

```
ResourceServerTokenServices 是组成授权服务的另一半。
1）.若是资源服务器和授权服务在同一个应用，可以使用DefaultTokenServices
2）.若是分离的。ResourceServerTokenServices必须知道令牌的如何解码。
ResourceServerTokenServices解析令牌的方法：

使用RemoteTokenServices，资源服务器通过HTTP请求来解码令牌。每次都请求授权服务器的端点-/oauth/check_toke，以此来解码令牌
若是访问量大，则通过http获取之后，换成令牌的结果
若是jwt令牌，需请求授权服务的/oauth/token_key，来获取key进行解码
```  

#### 对接认证授权服务中心  

###### 配置资源验证服务  

```java
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    // 该方法用于自定义解析令牌信息
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.tokenExtractor(new TokenExtractor() {
//            @Override
//            public Authentication extract(HttpServletRequest request) {
//                // 提取请求中的令牌
//                return null;
//            }
//        });
//    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 配置资源需要被保护的路由
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/test").hasAuthority("ROLE_ADMIN");
    }
}
```  

###### 对接认证授权服务  

```properties
# 统一路由路径
server.servlet.context-path=/api


security.oauth2.client.client-id=app_id
security.oauth2.client.client-secret=app_secret
security.oauth2.client.access-token-uri=http://localhost:8040/oauth/token
security.oauth2.client.user-authorization-uri=http://localhost:8040/oauth/authorize

security.oauth2.client.authorized-grant-types[0]=authorization_code
security.oauth2.client.authorized-grant-types[1]=password
security.oauth2.client.authorized-grant-types[2]=refresh_token

security.oauth2.resource.token-info-uri=http://localhost:8040/oauth/check_token
```