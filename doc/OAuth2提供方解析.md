该文章对于spring security OAuth2提供方(认证授权)的WebSecurityConfigurerAdapter配置实现类进行讲解，整个提供方详细流程可查看eddx模型图  
[参考文献](https://juejin.im/post/5d0b1eb35188252f921b1535)

<hr>  

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


