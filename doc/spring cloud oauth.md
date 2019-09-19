#### Roles  
[OAth2.0官网参考指导](https://oauth.net/2/)
1. 资源所有者(resource owner)  
2. 资源服务者(resource server)  
3. 客户端(client)  
4. 授权服务(authorization server)  

[Spring Security OAuth2.0实现官方案例](https://github.com/spring-cloud-samples/authserver)

#### 为什么需要OAuth协议  

> 在微服务架构中，对原先的soa架构进行按模块拆分成多个boot单体服务进行解耦，按模块纵向拆分和集群横向部署使得微服务
架构具有较高的可用性和跟专业的服务提供，**而对于集群的微服务难点就在于客户端如何访问，spring框架接入一套网关机制(gateway)**进而产线下所示结构  

```text  

访问请求(客户端、浏览器、第三方接入)
        |
        |
        |           未认证请求
服务提供品平台(转向)------------->认证、授权服务器(转向该服务进行授权凭证获取) 
        |
        |
        |
服务1   服务2  服务3    
```

> 上述过程中描述了客户端和服务提供之间的工作流程。**内部服务之间进行通信使用rpc方式，对外部提供rest服务进行资源访问**  


#### 认证授权的解决方案(spring security OAuth2)  
 
#### 客户端授权方式  

- 简单模式(该模式不需要自备服务器，较为简单不推荐,直接对接企业第三方，访问其资源数据)  

- 授权码模式(如下推荐)  

```
Client1      Client2         Client3 (客户端访问请求服务资源，需要先进入网关进行认证和访问资源服务器路由分配)
    |           |               |       客户端访问可携带自定义参数(clientid&username&等)
    |           |               |
    API 网关服务器(spring cloud gateway)
         |       |   根据截获客户端携带参数访问授权服务器，授权服务器返回授权码
         |       |   根据授权码在此进行访问授权服务器，获取授权码
         |       |   网关访问资源服务并根据客户度携带id返回对应客户端资源数据
         |   认证、授权服务器
    服务1  服务2
```  

- 密码模式

- 客户端模式  

#### 默认请求地址  

**进行授权验证时请求头authorization不设置类型**
> http://localhost:8050/oauth/authorize?client_id=app_id&response_type=code  
/oauth/authorize：授权端点  
/oauth/token：令牌端点  
/oauth/confirm_access：用户确认授权提交端点  
/oauth/error：授权服务错误信息端点  
/oauth/check_token：用于资源服务访问的令牌解析端点  
/oauth/token_key：提供公有密匙的端点，如果你使用 JWT 令牌的话  
