### auth-sdk 基本功能:
> 1. JWT 功能
> 2. RBAC 功能

### 资源服务器引用方式:

1. 依赖引用
```xml
<dependency>
    <groupId>org.arch.projects</groupId>
    <artifactId>auth-sdk</artifactId>
    <version>${project.version}</version>
    <exclusions>
        <exclusion>
            <artifactId>nimbus-jose-jwt</artifactId>
            <groupId>com.nimbusds</groupId>
        </exclusion>
    </exclusions>
</dependency>
<!-- 解决兼容性问题 -->
<dependency>
    <artifactId>nimbus-jose-jwt</artifactId>
    <groupId>com.nimbusds</groupId>
    <version>8.19</version>
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
```

3. 添加资源服务器的 appId 与 appCode 属性
```yaml
arch:
  app:
    app-id: 001
    app-code: 001
```

4. 引用 auth-sdk 上的配置文件
```xml
<resources>
    <resource>
        <directory>src/main/resources/</directory>
        <filtering>true</filtering>
        <includes>
            <include>**/*.properties</include>
        </includes>
    </resource>
    <resource>
        <directory>src/main/resources/</directory>
        <filtering>false</filtering>
        <includes>
            <include>**/*.yml</include>
        </includes>
    </resource>
    <resource>
        <directory>src/main/resources/</directory>
        <filtering>false</filtering>
        <includes>
            <include>**/*.xml</include>
        </includes>
    </resource>
</resources>
```