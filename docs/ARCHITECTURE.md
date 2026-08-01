# 架构说明

```text
Vue 3 管理端 / 响应式 H5
          │ HTTP / JSON
Spring Security → Controller → Service → Spring Data JPA → MySQL 8
                                  │
                    身份治理与访问复核规则引擎
```

当前版本以单体分层架构保证易运行与易理解。`DomainCatalog` 管理身份治理样例，`AccessReviewService` 执行权限复核规则，`WorkItem` 承载接入与整改事项。生产化时建议接入企业目录、OIDC/SAML、细粒度 RBAC/ABAC、凭据保险库、不可抵赖审计和异常行为分析。
