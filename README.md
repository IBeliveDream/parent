# 微服务-Spring Cloud Eureka集群

> 参照文档：https://blog.csdn.net/weixin_40470497/article/details/83625718?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.control&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.control

## 1.系统环境

 高并发微服务添加`hosts`文件添加

```url 
C:\Windows\System32\drivers\etc\hosts
```

```xml
127.0.0.1       eureka1
127.0.0.1       eureka2
```

## 2.pom.xml 文件

```xml
     <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
```

## 3.启动类

```java
//开启对EurekaServer的支持，即：作为Eureka服务端
@EnableEurekaServer
////开启作为Eureka Server的客户端的支持
//@EnableDiscoveryClient
@SpringBootApplication
public class EurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }

}
```

## 4.application.yml 文件

```xml
spring:
  application:
    # 服务名称
    name: spring-cloud-eureka-server
server:
  # 端口号
  port: 8761

eureka:
  instance:
    hostname: eureka1
  client:
    service-url:
      # 默认注册地址
#      defaultZone: http://localhost:${server.port}/eureka/
      # 双服务中心
      defaultZone: http://eureka2:8762/eureka/
    # 当前服务是否注册到eureka
    register-with-eureka: true
    # 表示eurekaServer是否获取服务地址
    fetch-registry: true

```

```xml
spring:
  application:
    # 服务名称
    name: spring-cloud-eureka-server-replica
server:
  # 端口号
  port: 8762

eureka:
  instance:
    hostname: eureka2
  client:
    service-url:
      # 默认注册地址
#      defaultZone: http://localhost:${server.port}/eureka/
      # 双服务中心
      defaultZone: http://eureka1:8761/eureka/
    # 当前服务是否注册到eureka
    register-with-eureka: true
    # 表示eurekaServer是否获取服务地址
    fetch-registry: true

```

### 5.自我保护机制 心跳机制

> 参考文档 https://www.jianshu.com/p/cb7fa0aa47a8

**如果在15分钟内超过85%的客户端节点都没有正常的心跳，那么Eureka就认为客户端与注册中心出现了网络故障，Eureka Server自动进入自我保护机制**

```mathematica
Renews threshold = 服务总数 * 每分钟续约数量(60s/客户端的续约间隔) * 自我保护续约百分比阈值因子 
           5     = 3 *  60/30 * 0.85
```

4处更新`Renews threshold`地方

> * Eureka Server启动的时候
> * registry 服务注册的时候
> * 取消注册
> * 每15分钟更新一次

配置文件.yml

```xml
eureka:
  client:
    server:
      # Eureka自我保护机制
      enable-self-preservation: true
```



## 配置读取本地（git）配置文件

> 参考文档： https://blog.csdn.net/mr_orange_klj/article/details/84669079

URL地址和资源文件映射如下:

> * /{application}/{profile}[/{label}]
> * /{application}-{profile}.yml
> * /{label}/{application}-{profile}.yml
> * /{application}-{profile}.properties
> * /{label}/{application}-{profile}.properties



### 1.pom

```xml
  <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
        </dependency>
```



### 2.启动类

```java
// 开启 配置文件读取
@EnableConfigServer
@SpringBootApplication
public class ConfigserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigserviceApplication.class, args);
    }

}

```

### 3.配置文件

```xml
server:
    # 端口号
    port: 8083
spring:
  application:
    # 项目名称
    name: spring-configservice
  # 配置文件获取位置(本地)
  profiles:
    active: native
  cloud:
    config:
      server:
        native:
          # 配置本地路径
          search-locations: file:D:/1a/config/
#        # 配置文件获取位置（git）
#        git:
#          # 配置项目下的相对路径 （项目下某个文件夹）
#          search-paths: config
#          # git项目路径
#          uri: https://gitee.com/IBeliveDream/spring-cloud-config-server.git
#          # git账号
#          username: 
#          # git密码
#          password: 
#          # 指定读取配置文件的存放路径
#          basedir:
#logging:
#  level:
#    # 显示日志级别
#    com.springboot.provide.consumer.controller.IHelloControllerFeign: debug
#    # 取消注册中心定时回复日志显示（info级别）
#    com.netflix.discovery.shared.resolver.aws.ConfigClusterResolver: WARN

```
> 本地[yml](D:\1a\config)存放地址 D:\1a\config


