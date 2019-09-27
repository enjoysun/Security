该文章对于spring security OAuth2提供方(认证授权)的WebSecurityConfigurerAdapter配置实现类进行讲解，整个提供方详细流程可查看eddx模型图  
[参考文献](https://juejin.im/post/5d0b1eb35188252f921b1535)

<hr>   

### WebSecurityConfigurerAdapter+@EnableWebSecurity **验证流程、非验证资源、验证用户和权限定义**

#### 实现WebSecurityConfigurerAdapter配置需要先了解三个Builder  

1. HTTPSecurity   **具体验证流程配置** 

> 该配置方法作用范围:  
声明实现具体的权限规则配置。 
指定相应的认证机制(OpenIDLoginConfigurer、AnonymousConfigurer、FormLoginConfigurer、HttpBasicConfigurer)  
使用案例:  
```java
protected void configure(HttpSecurity http) throws Exception {    
     http
     //request 设置
     .authorizeRequests()   //http.authorizeRequests() 方法中的自定义匹配
     .antMatchers("/resources/**", "/signup", "/about").permitAll() // 指定所有用户进行访问指定的url          
     .antMatchers("/admin/**").hasRole("ADMIN")  //指定具有特定权限的用户才能访问特定目录，hasRole()方法指定用户权限，且不需前缀 “ROLE_“  
     .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")//          
     .anyRequest().authenticated()  //任何请求没匹配的都需要进行验证                                           
     .and()        //login设置  自定义登录页面且允许所有用户登录
     .formLogin()      
     .loginPage("/login") //The updated configuration specifies the location of the log in page  指定自定义登录页面
     .permitAll(); // 允许所有用户访问登录页面. The formLogin().permitAll() 方法
     .and 
     .logout()  //logouts 设置                                                              
     .logoutUrl("/my/logout")  // 指定注销路径                                              
     .logoutSuccessUrl("/my/index") //指定成功注销后跳转到指定的页面                                        
     .logoutSuccessHandler(logoutSuccessHandler)  //指定成功注销后处理类 如果使用了logoutSuccessHandler()的话， logoutSuccessUrl()就会失效                                
     .invalidateHttpSession(true)  // httpSession是否有效时间，如果使用了 SecurityContextLogoutHandler，其将被覆盖                                        
     .addLogoutHandler(logoutHandler)  //在最后增加默认的注销处理类LogoutHandler                
     .deleteCookies(cookieNamesToClear);//指定注销成功后remove cookies
     //增加在FilterSecurityInterceptor前添加自定义的myFilterSecurityInterceptor
     http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
   }

```
2. WebSecurity   **配置无需验证资源**

> 该配置方法实现**全局请求忽略规则配置(静态文件、登录页面等)、全局HttpFireWall配置(用于自定义拒绝存在风险的请求)、全局SecurityFilterChain配置(请求传递过滤器)、
SecurityInterceptor配置(决定哪些安全性约束适用于请求)**  

```java
 public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**", "/**/favicon.ico");
    }
```

3. AuthenticationManagerBuilder  **配置需要验证的用户和权限来源**

> 用来配置全局认证相关信息：  
认证服务提供商(AuthenticationProvider):  
用户详情查询服务(UserDetailsService):指定验证用户信息来源，继承实现(数据库、内存等)    

UserDetail实体字段解析  
```java
public interface UserDetails extends Serializable {
    // 权限集合
    Collection<? extends GrantedAuthority> getAuthorities();

    String getPassword();

    String getUsername();
    // 是否账号过期
    boolean isAccountNonExpired();
    // 锁定当前账号
    boolean isAccountNonLocked();
    // 凭证过期字段
    boolean isCredentialsNonExpired();
    // 账号是否可用
    boolean isEnabled();
}
```  


### AuthorizationServerConfigurerAdapter+@EnableAuthorizationServer **授权服务器、授权类型配置**   

#### configure(ClientDetailsServiceConfigurer clients) **实现客户端详情配置(1.在内存中定义该配置 2.JDBC读取数据库中用户配置)**  

>  ClientDetailsServiceConfigurer能够使用内存或者JDBC方式实现获取已注册的客户端详情。客户端详情属性:  
.withClient("app_id"):客户端标识ID    
.secret(bCryptPasswordEncoder.encode("app_secret")):客户端安全码    
.authorizedGrantTypes("authorization_code"):授权类型(默认为空，authorization_code:授权码，refresh_token:token刷新，password:密码授权)      
.scopes("is_auth"):授权范围默认为空用于全部范围  
.authorities:客户端可使用的权限    
.redirectUris("http://localhost:8091/service/welcome?message=login");授权码回调地址   

```java
    @Bean
    public ClientDetailsService clientDetailsService() {
        return new JdbcClientDetailsService(DataSource源);
    }
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
    }
```   

#### **令牌管理**  

> ResourceServerTokenServices 接口定义了令牌加载、读取方法  
  AuthorizationServerTokenServices 接口定义了令牌的创建、获取、刷新方法  
  ConsumerTokenServices 定义了令牌的撤销方法  
  DefaultTokenServices 实现了上述三个接口,它包含了一些令牌业务的实现，如创建令牌、读取令牌、刷新令牌、获取客户端ID。默认的当尝试创建一个令牌时，是使用 UUID 随机值进行填充的，除了持久化令牌是委托一个 TokenStore 接口实现以外，这个类几乎帮你做了所有事情  
  而 TokenStore 接口也有一些实现：  
  InMemoryTokenStore：默认采用该实现，将令牌信息保存在内存中，易于调试  
  JdbcTokenStore：令牌会被保存近关系型数据库，可以在不同服务器之间共享令牌  
  JwtTokenStore：**使用 JWT 方式保存令牌，它不需要进行存储，但是它撤销一个已经授权令牌会非常困难，所以通常用来处理一个生命周期较短的令牌以及撤销刷新令牌**  
  
> 由上述描述得出TokenStore实现了令牌的持久化，而令牌的创建、读取、刷新、等由DefaultTokenServices实现    

###### JWT使用详解  

> 1.使用 JWT 令牌需要在授权服务中使用 JWTTokenStore，资源服务器也需要一个解码 Token 令牌的类 JwtAccessTokenConverter，JwtTokenStore 依赖这个类进行编码以及解码，因此授权服务以及资源服务都需要配置这个转换类  
  2.Token 令牌默认是有签名的，并且资源服务器中需要验证这个签名，因此需要一个对称的 Key 值，用来参与签名计算  
  3.这个 Key 值存在于授权服务和资源服务之中，或者使用非对称加密算法加密 Token 进行签名，Public Key 公布在 /oauth/token_key 这个 URL 中  
  4.默认 /oauth/token_key 的访问安全规则是 "denyAll()" 即关闭的，可以注入一个标准的 SpingEL 表达式到 AuthorizationServerSecurityConfigurer 配置类中将它开启，例如 permitAll()  
  5.需要引入 spring-security-jwt 库    
  
  
#### **配置授权**  

###### **授权类型指定**
> 授权是使用 AuthorizationEndpoint 这个端点来进行控制的，使用 AuthorizationServerEndpointsConfigurer 这个对象实例来进行配置，默认是支持除了密码授权外所有标准授权类型，它可配置以下属性：  
  1.authenticationManager：认证管理器，当你选择了资源所有者密码（password）授权类型的时候，请设置这个属性注入一个 AuthenticationManager 对象  
  2.userDetailsService：可定义自己的 UserDetailsService 接口实现  
  3.authorizationCodeServices：用来设置收取码服务的（即 AuthorizationCodeServices 的实例对象），主要用于 "authorization_code" 授权码类型模式  
  4.implicitGrantService：这个属性用于设置隐式授权模式，用来管理隐式授权模式的状态  
  5.tokenGranter：完全自定义授权服务实现（TokenGranter 接口实现），只有当标准的四种授权模式已无法满足需求时   
  
###### **授权端点URL配置**   

> AuthorizationServerEndpointsConfigurer 配置对象有一个 pathMapping() 方法用来配置端点的 URL，它有两个参数：  
  1.参数一：端点 URL 默认链接  
  2.参数二：替代的 URL 链接  
  下面是一些默认的端点 URL：  
  /oauth/authorize：授权端点  
  /oauth/token：令牌端点  
  /oauth/confirm_access：用户确认授权提交端点  
  /oauth/error：授权服务错误信息端点  
  /oauth/check_token：用于资源服务访问的令牌解析端点  
  /oauth/token_key：提供公有密匙的端点，如果你使用JWT令牌的话  
  授权端点的 URL 应该被 Spring Security 保护起来只供授权用户访问
      


### JWT非对称加密  


### 请求头操作Spring Security – Cache Control Headers [参考文献](https://www.baeldung.com/spring-security-cache-control-headers)  

```java
//WebSecurityHttp

http.and().addHeaderWriter(new HeaderWriter() {
                @Override
                public void writeHeaders(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
                    httpServletResponse.setHeader("max-age", "60");
                    httpServletResponse.setHeader("m", "test");
                }
            });
```  


### 跨域设置 [参考](https://www.jianshu.com/p/87e1ef68794c)






