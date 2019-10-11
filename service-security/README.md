#### spring-security项目介绍  

> 该模块参照oauth协议的认证模块,登录信息编码至jwt中,实现动态资源验证  

> 控制路由模块类:WebSecurityConfigurerAdapter实现类中   
UsernamePasswordAuthenticationFilter->AbstractUserDetailsAuthenticationProvider->SecurityContextHolder.getContext().setAuthentication链路 


> JwtUtil.class:token包装工具类  
JwtAuthentication.class:截获token、认证获取权限过滤器(由httpSecurity添加该过滤器至spring security链路中并生效)  
JwtUserDetail:增加userDetails定制user信息实体

> Token：   
登录:/rbac/auth/login  
刷新:/rbac/auth/refresh  
注册:/rbac/auth/register  
余下路由皆需要接入认证  

#### 认证和授权模型设计  
![image](https://github.com/enjoysun/Security/blob/master/service-security/src/main/resources/images/doc/tb-er.png)  

###### 认证模块设计  


#### token和jwt接入

#### 动态权限验证设计  

#### 授权设计