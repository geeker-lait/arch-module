### auth-sdk 基本功能:
> 1. JWT 功能
> 2. RBAC 功能
> 3. springMVC TokenInfo 参数解析功能, 通过 spring-boot-starter 自动配置.

### 资源服务器引用方式:

1. 依赖引用
```xml
<dependency>
    <groupId>org.arch.projects</groupId>
    <artifactId>auth-sdk</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-stream</artifactId>
        </exclusion>
    </exclusions>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-stream</artifactId>
    <version>3.0.3.RELEASE</version>
    <scope>compile</scope>
</dependency>
```

2. 添加 WebSecurityConfigurer 核心配置.

```java
@Configuration
public class SdkWebSecurityAutoConfigurer  {
    @Bean
    public SecurityCoreAutoConfigurer securityCoreAutoConfigurer() {
        return new SecurityCoreAutoConfigurer();
    }
}
// 注意: 需要自定义配置 WebSecurityConfigurer 时可通过实现 HttpSecurityAware 接口添加.
```

3. 添加资源服务器的 appId 与 appCode 属性, MQ 配置(权限相关)
```yaml
arch:
  app:
    client-id: "003"
    client-secret: "003"
  csrf:
    # 是否支持 csrf, 默认: true
    enable: true
    # 需要忽略 csrf 校验的 POST 请求.
    ignoring-ant-matcher-urls:
      - '/account/oauthClient/scopes'
logging:
  level:
    org.arch.auth: warn
    top.dcenter: warn
    com.alibaba.cloud.stream.binder.rocketmq: warn

# 更新用户权限相关
spring:
  cloud:
    # Spring Cloud Stream 配置项，对应 BindingServiceProperties 类
    stream:
      # Binding 配置项，对应 BindingProperties Map
      bindings:
        rbacPermissionUpdateConsume:
          destination: rbacPermissionUpdate-topic # 目的地。这里使用 RocketMQ Topic
          content-type: application/json # 内容格式。这里使用 JSON
          group: rbac-update-cache-group # 此 group 要唯一值
          concurrency: 1
          # batchMode: tree

      # Spring Cloud Stream RocketMQ 配置项
      rocketmq:
        # RocketMQ Binder 配置项，对应 RocketMQBinderConfigurationProperties 类
        binder:
          name-server: 192.168.50.2:9876 # RocketMQ Namesrv 地址
          enable-msg-trace: true
          customized-trace-topic: rbac_permission_update_trace
        # RocketMQ 自定义 Binding 配置项，对应 RocketMQBindingProperties Map
        bindings:
          rbacPermissionUpdateConsume:
            # RocketMQ Consumer 配置项，对应 RocketMQConsumerProperties 类
            consumer:
              enabled: true # 是否开启消费，默认为 true
              broadcasting: true # 是否使用广播消费，默认为 false 使用集群消费，如果要使用广播消费值设成true。
              tags: RPUC
              orderly: false
              delayLeveLWhenNextConsume: 3
              #              suspendCurrentQueueTimeMillis: 1000
```

4. 开发时: account_auth_client 添加记录
```sql
insert into `account_auth_client` (`client_id`, `client_secret`, `scopes`, `client_type`) values('006', '006', 'AUTH,
OAUTH2,
OSS,UMS,ACCOUNT,USER,MEMBER,MERCHANT,PMS,OMS,JWK,PAY,MARKETING,WATCHDOG', 'ums-api');
```