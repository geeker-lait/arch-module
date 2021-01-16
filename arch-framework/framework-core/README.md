### Feign 客户端配置必须继承 FeignGlobalConfig:
- FeignGlobalConfig:
> 1. 添加 tenantId 到 指定请求头;
> 2. 添加 token 到 Authorization 请求头.

### 后端必须添加 org.arch.framework.ums.tenant.context.filter.TenantContextFilter 到过滤器链上.
- 推荐:

```java
protected void configure(HttpSecurity http) throws Exception {
    http.addFilterAfter(new TenantContextFilter(archTenantContextHandler), WebAsyncManagerIntegrationFilter.class);
}
```
    