### Feign 客户端配置必须继承 FeignGlobalConfig:
- FeignGlobalConfig:
> 1. 添加 tenantId 到 指定请求头;
> 2. 添加 token 到 Authorization 请求头.

### 后端必须添加 org.arch.framework.ums.tenant.context.filter.TenantContextFilter 到过滤器链上.
```java
protected void configure(HttpSecurity http) throws Exception {
    http.addFilterAfter(new TenantContextFilter(archTenantContextHolder), WebAsyncManagerIntegrationFilter.class);
}
```
- 推荐:
> 引入 auth-sdk 模块: auth-sdk 已经通过 spring-boot-starter 模式自动配置 TenantContextFilter.
    