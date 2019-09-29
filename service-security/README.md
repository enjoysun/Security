#### spring-security项目介绍  

> 该模块初步实现了oauth协议的认证模块，登录信息编码至jwt中  

> 控制路由模块类:WebSecurityConfigurerAdapter实现类中  

> Token：   
登录:/rbac/auth/login  
刷新:/rbac/auth/refresh  
注册:/rbac/auth/register  
余下路由皆需要接入认证