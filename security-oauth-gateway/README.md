####  cloud-matcha中service-security和security-oauth-gateway区别  

> 前者为单纯的spring-security框架实现系统权限验证项目(注:mvn包含spring-security和jjwt即可)  
后者是借助spring-security的基础上实现了oauth2.0的spring-security-oauth框架来实现**授权码认证第三方应用模式、账号密码授权模式**(注:mvn包含spring-security-oauth2、spring-security-jwt即可jwt可选不与oauth协议冲突)