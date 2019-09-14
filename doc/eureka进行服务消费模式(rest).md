
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