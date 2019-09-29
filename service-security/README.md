#### spring-security项目介绍  

> 该模块初步实现了oauth协议的认证模块，登录信息编码至jwt中  

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