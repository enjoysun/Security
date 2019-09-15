
### 如何进行eureka发现和服务消费  

<span>ribbon服务消费</span>  
> 对应当前项目结构  
spring-cloud-eureka:服务注册中心  
spring-cloud-service:服务提供者(需要注册至eureka)  
spring-cloud-consumer-*:服务对外提供者(该层级服务对外暴露，使用rest方式进行访问，restTemplate
进行服务地址均衡和权重进行rest访问并将结果返回到客户端)（注:该案例测试使用docker模拟，其中使用容器名进行地址通信
在rest地址中转时可能会导致unknownhosterror错误需要对hosts文件进行将ip映射到容器名的操作）  

<span>消费流程图</span>  
```text  

 ------             -------------                                     ----------  
|pc    |           |              |   restTemplate(loadblanace)      |          |
|mobile|------>    | consumer集群  |---------------------------->    | eureka集群 | 
|web   |           |              |   返回service注册地址              |          |
 ------             -------------   <-----------------------           ----------  
                       |
                       |
                       |restTemplate
                       |
            service01  service02  service03
 
```

<span>ribbon服务消费</span>  

> feign集成了ribbon模式和mvn耦合的声明式接口，生产模式使用feign替代ribbon，详细调用查看spring-cloud-consumer-feign工程service/interface/login接口声明  


##### feign超时重试机制  

> 超时重试设置  
配置查看feign工程实例中application配置文件 

> feign超时与hystrix的超时机制是两个概念，feign超时是内部服务调用超时机制，  
hystrix超时是提供外部服务rest调用熔断超时机制，一般hystrix超时时间要大于
feign超时时间，因为feign超时重试要在熔断之前才有效果  

###### 重试对于post和put、patch如何保证幂等？  

> 服务端根据前端提交信息业务生成一个唯一的token，该token夹带于下次修改请求或者存在于服务端中，
后端根据该token进行是否进行操作，借用分布式锁setnx，或者session，若服务端已存在一次带token操作则
一定时间内相同token操作不允许修改数据。这个一定时间是token清除过期时间，该时间在feign重试机制中一定大于当前服务实例设置
ribbon超时时间。在请求成功执行修改后需要对token做过期操作  

###### hystrix熔断