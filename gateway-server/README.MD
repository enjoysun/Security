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