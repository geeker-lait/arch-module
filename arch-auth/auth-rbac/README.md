### RBAC 基本功能模块
> 1. 资源服务器端使用方式参考: auth-sdk
> 2. 认证服务器端使用方式参考: auth-sso

### 添加 uri 权限时请都添加到 AuthorizeRequestsAutoConfigurer 类的 getAuthorizeRequestMap() 方法中, 以便集中管理.

### 暂时还未实现权限功能, 暂时权限都是 permitAll()