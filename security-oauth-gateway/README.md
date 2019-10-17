####  cloud-matcha中service-security和security-oauth-gateway区别  

> 前者为单纯的spring-security框架实现系统权限验证项目(注:mvn包含spring-security和jjwt即可)  
后者是借助spring-security的基础上实现了oauth2.0的spring-security-oauth框架来实现**授权码认证第三方应用模式、账号密码授权模式**(注:mvn包含spring-security-oauth2、spring-security-jwt即可jwt可选不与oauth协议冲突)

<hr />

#### 项目协议  

> 授权码认证模式用于第三方应用接入  
密码认证进行项目系统组成部分客户端认证用于用户登录  

> 所以本项目中授权类型grant_type:password密码模式、authorization_code授权码模式  
/oauth/authorize：授权端点  
/oauth/token：令牌端点  
/oauth/confirm_access：用户确认授权提交端点  
/oauth/error：授权服务错误信息端点  
/oauth/check_token：用于资源服务访问的令牌解析端点  
/oauth/token_key：提供公有密匙的端点，如果你使用 JWT 令牌的话   

###### 密码模式获取过程  
![image](https://github.com/enjoysun/Security/blob/master/security-oauth-gateway/src/main/resources/images/grant_type_password.png)
![image](https://github.com/enjoysun/Security/blob/master/security-oauth-gateway/src/main/resources/images/grant_type_password_paramaters.png)
![image](https://github.com/enjoysun/Security/blob/master/security-oauth-gateway/src/main/resources/images/grant_type_password_success.png)

> 注:oauth密码模式不支持json访问，需要使用form模式端点访问  
basic auth：即http header中:Authorization:Basic (app_id:app_secret).base64

```text
密码模式获取token:
1.访问/oauth/token端点
2.需要参数:
scope:read/write
grant_type:password
username:用户民
password:密码
scope:访问范围
```

###### 授权码模式过程 

. 访问http://localhost:8040/oauth/authorize?client_id=app_id&response_type=code进行登录认证  
![image](https://github.com/enjoysun/Security/blob/master/security-oauth-gateway/src/main/resources/images/grant_type_code_login.png)

. 资源拥有者进行授权确认  
![image](https://github.com/enjoysun/Security/blob/master/security-oauth-gateway/src/main/resources/images/grant_type_code_authorize.png)

. 同意授权后获取授权码  
![image](https://github.com/enjoysun/Security/blob/master/security-oauth-gateway/src/main/resources/images/grant_type_code_authorize_code.png)  

. 拿到授权码进行/oauth/token访问获取token  
![image](https://github.com/enjoysun/Security/blob/master/security-oauth-gateway/src/main/resources/images/grant_type_anthorize_success.png)

```text
1.访问/oauth/authorize进行授权码获取(进行登录)
response_type：表示授权类型，必选项，此处的值固定为"code"
client_id：表示客户端的ID，必选项
redirect_uri：表示重定向URI，可选项
scope：表示申请的权限范围，可选项
state：表示客户端的当前状态，可以指定任意值，认证服务器会原封不动地返回这个值。
2.拿到授权码后进行token获取(/oauth/token),需要参数:
grant_type：表示使用的授权模式，必选项，此处的值固定为"authorization_code"。
code：表示上一步获得的授权码，必选项。
redirect_uri：表示重定向URI，必选项，且必须与A步骤中的该参数值保持一致。
client_id：表示客户端ID，必选项
```  
. 刷新token进行/oauth/token访问获取token 
![image](https://github.com/enjoysun/Security/blob/master/security-oauth-gateway/src/main/resources/images/refresh_auth.png)
![image](https://github.com/enjoysun/Security/blob/master/security-oauth-gateway/src/main/resources/images/refresh_paramater.png)
```text
grant_type:refresh_token
//client_id:客户端ID
//client_secret:secret
refresh_token:token
```

.解析jwtToken信息/oauth/check_token
![image](https://github.com/enjoysun/Security/blob/master/security-oauth-gateway/src/main/resources/images/check_token.png)


<hr />

### 授权服务中心搭建过程  

#### 服务器安全配置  

##### 自定义用户实体  

[参考com.myou.gateway.security.oauth.Grant.Model.UserDetailExtension](#https://github.com/enjoysun/Security/blob/master/security-oauth-gateway/src/main/java/com/myou/gateway/security/oauth/Grant/Model/UserDetailExtension.java)

>用户实体信息继承自spring-security的UserDetails规则接口，实现接口方法，且可深度定制系统相关信息字段方法  

###### 认证加载用户信息  

[参考com.myou.gateway.security.oauth.Grant.Service.Impl.UserDetailImpl](https://github.com/enjoysun/Security/blob/master/security-oauth-gateway/src/main/java/com/myou/gateway/security/oauth/Grant/Service/Impl/UserDetailImpl.java)

>该类也是实现spring-security框架认证部分内容，进行用户信息载入认证。**该模块自定义实现了GrantedAuthorityExtension扩展类进行角色内容类进行扩展**

###### 安全验证配置  

```java
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    // 注入用户角色加载类实现
    @Autowired
    private UserDetailImpl userDetail;
    // 注入密码加密模式实现
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 配置认证用户
        auth.userDetailsService(userDetail).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 过滤路由配置
        web.ignoring()
                .mvcMatchers("/oauth/**")
                .mvcMatchers("/rbac/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 开放路由配置
        http.csrf().disable()
                .cors().and()
                // 关闭session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // http.authorizeRequests() 方法中的自定义匹配 指定所有用户都可以访问antMatchers路由内容，未匹配的路由需要进行身份验证
                .authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll().anyRequest().authenticated();
    }

    // 跨域配置(WebSecurityHttp也需要支持cors().and())
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowCredentials(true);
        cors.addAllowedOrigin("*");
        cors.addAllowedHeader("*");
        cors.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", cors);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
```

#### 认证授权配置  

#todo


#### self-signed certificate configure(本文生产的mykeystore.jks步骤)  

######1. 安装openssl

######2. create rsa private key  
```shell
  # The below command will create a file named 'server.pass.key' and place it in the same folder where the command is executed. 
  $ openssl genrsa -des3 -passout pass:x -out server.pass.key 2048
  # The below command will use the 'server.pass.key' file that just generated and create 'server.key'.
  $ openssl rsa -passin pass:x -in server.pass.key -out server.key
  # We no longer need the 'server.pass.key'
  $ rm server.pass.key
```  
######3. Create the Certificate Signing Request (CSR), utilizing the RSA private key we generated in the last step.
```shell
# The below command will ask you for information that would be included in the certificate. Since this is a self-signed certificate, there is no need to provide the 'challenge password' (to leave it blank, press enter).
$ openssl req -new -key server.key -out server.csr
```  

#######4. Generate a file named v3.ext with the below-listed contents:  
v3.ext内容:
```ext
authorityKeyIdentifier=keyid,issuer
basicConstraints=CA:FALSE
keyUsage = digitalSignature, nonRepudiation, keyEncipherment, dataEncipherment
subjectAltName = @alt_names
[alt_names]
DNS.1 = <specify-the-same-common-name-that-you-used-while-generating-csr-in-the-last-step>
# DNS.1值为3步填充的Common name的值
```

######5. Create the SSL Certificate, utilizing the CSR created in the last step.  

```shell
openssl x509 -req -sha256 -extfile v3.ext -days 365 -in server.csr -signkey server.key -out server.crt
Signature ok
subject=/C=<country>/ST=<state>/L=<locality>/O=<organization-name>/OU=<organization-unit-name>/CN=<common-name-probably-server-fqdn>/emailAddress=<email-address-provided-while-generating-csr>
Getting Private key
# 执行第一行命令，余下皆为输出内容
```

######6.Creating P12  

```shell
openssl pkcs12 -export -name servercert -in server.crt -inkey server.key -out myp12keystore.p12
# 记下p12文件密码，下一个步骤需要使用
```  

######7.Converting P12 to JKS  
```shell
keytool -importkeystore -destkeystore mykeystore.jks -srckeystore myp12keystore.p12 -srcstoretype pkcs12 -alias servercert
输入目标密钥库口令(新jks口令):
再次输入新口令:
输入源密钥库口令:(p12文件口令)
# 注意alias
```