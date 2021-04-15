### RBAC 基本功能模块
> 1. 资源服务器端使用方式参考: auth-sdk
> 2. 认证服务器端使用方式参考: auth-sso

### 添加 uri 权限时请都添加到 AuthorizeRequestsAutoConfigurer 类的 getAuthorizeRequestMap() 方法中, 以便集中管理.

### 当权限更新时会自动更新本地权限缓存(从 redis 获取相关权限更新信息)
#### 如果是权限管理的 admin 微服务(如: auth-sso), 实现 top.dcenter.ums.security.core.api.permission.service.RolePermissionsServiceAspect
接口并注入 IOC 容器即可