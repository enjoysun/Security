
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

<span>hystrix四种超时配置</span>  

> 1.@HystrixCommand注解配置超时(该注解一般结合ribbon)  

```python
@HystrixCommand(fallbackMethod="fallback",
	commandProperties = {
	     @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000" )
	}
)  

# 可以在配置文件中配置commandKey  
@HystrixCommand(fallbackMethod="fallback",commandKey="userGetKey")  
# porperties 
hystrix.command.userGetKey.execution.isolation.thread.timeoutInMilliseconds = 13000  

# commandKey的value对应command后的key
```  

> 2.单服务全局配置默认超时设置  

```properties
hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=3000
```  

> 3.接口级配置(结合feign的接口调用整合)   

```java
// 1. 声明feign client客户端和熔断处理实例
@FeignClient(value = "SERVICE-CLIENT", fallback = TimeOutHandler.class)
public interface LoginClient {

    // feign提供了与mvc的声明式接口耦合，但是调用时需要声明参数获取方式和rest方法类型，参数名需与服务暴露的名称保持一致
    @RequestMapping(value = "/service/welcome", method = RequestMethod.GET)
    String login(@RequestParam("message") String message);
}  

// 2. 配置文件中加入以下配置:  
/**
* hystrix.command.LoginClient#login(String).execution.isolation.thread.timeoutInMilliseconds = 300  
* 上述接口机配置规则:Client名称#方法名(参数类型)
* */
```   

> 4.服务级配置参考[第四种](https://juejin.im/post/5ced36a0e51d4510835e022d#heading-5)
