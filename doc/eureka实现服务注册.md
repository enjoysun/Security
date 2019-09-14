[eureka官方文档](https://cloud.spring.io/spring-cloud-netflix/reference/html/)

#### eureka实现服务注册与eureka集群   
**具体案例参照spring-cloud-eureka模块**  

> eureka提供了客户端和服务端，在eureka模块实现服务端，其他服务模块实现客户端并注册到服务端，即可实现服务注册  

###### eureka客户端可服务端相关配置  

<span>服务模块作为客户端操作和配置</span>
```properties
# 客户端配置(多个服务模块以命名和端口区分，注册至eureka集群) 
 
server.port=8082
# 注意配置，每个模块必须命名，因为服务消费时根据模块服务的命名进行发现和均衡策略
spring.application.name=service-test
# 服务端注册地址
eureka.client.service-url.defaultZone=http://localhost:8081/eureka/,http://localhost:8080/eureka/
```    
> 上述配置文件后需要启动类添加@EnableEurekaClient注解，服务启动即注册至服务端

<span>注册中心配置和操作</span>

```properties
# 服务端配置  

# 假设集群环境(peer1:8081 peer2:8082 peer3:8083)
#指定服务名称
spring.application.name=spring-cloud-eureka

server.port=8081

#指定eureka地址
eureka.instance.hostname=peer1

# 下两项设置为false关闭避免将自己注册到注册中心(单机建议设置为false)
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false

eureka.client.service-url.defaultZone=http://peer3:8083/eureka/,http://peer2:8082/eureka/
```  

> 服务启动之时需要添加@EnableEurekaServer注解至启动类  
