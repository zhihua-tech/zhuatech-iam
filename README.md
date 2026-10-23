<div align="center">

## 本次升级：账号授权期限与特权MFA预检

支持 UTF-8 CSV 文件/粘贴预检、物理行定位、批次重复业务键检查、模板下载和安全 CSV 导出。

- 管理员授权要求启用MFA。
- 授权失效日期不得早于生效日期。
- 授权到期提醒。

入口：`/preflight.html`（沿用现有后端认证）。预检不写库、不执行审批，也不替代主数据存在性、库存/余额锁定及正式入库事务校验。[规则、接口与边界说明](docs/BATCH-PREFLIGHT.md)。

# ZhuaTech IAM

[简体中文](README.md) | [English](README.en.md)

### 知华身份与访问管理平台 · 社区源码版

[官方网站](https://www.zhuatech.cn/) · [功能范围](#功能范围) · [本地运行](#本地运行) · [使用许可](#使用许可) · [咨询合作](#咨询合作)

</div>

## 本次升级：离职账号残留与闲置权限复审

将离职状态、账号激活状态及最后使用日期合并，区分必须回收的残留账号和需要人工复审的长期闲置权限。

新增已认证的结构化录入工作台（`/operations.html`），支持批量核算、逐条结果依据、分级复核和CSV/完整JSON导出。结果由真实后端算法计算，不是预置演示数字。所有数值精确传递，不调用外部模型。

这是**只读核算**：不写库、不审批、不自动执行财务、签署、销毁或设备控制。输入条件满足不是执行授权。[算法、输入边界与接口说明](docs/OPERATIONS-REVIEW.md)。

## 企业级权限变更模拟

新增授权前模拟接口，联合检查职责分离冲突、已过期权限、90 天未使用权限、特权权限、MFA 和业务理由，返回 `ALLOW / REVIEW / DENY` 决策及整改动作。

## 企业深化能力（2026-08）

新增多租户身份治理、MFA 特权门禁、职责分离（SoD）、访问申请审批、授权回收/到期失效、乐观锁与审计事件。详细状态机与生产边界见 [企业深化说明](docs/ENTERPRISE_DEEPENING.md)。

> 版权所有 © 2026 上海如静知华信息科技有限公司。本工程仅限个人非商业学习、研究和技术交流；企业内部使用、生产部署、SaaS、交付、销售及其他商业行为均须取得书面授权。

## 项目定位

ZhuaTech IAM 面向企业统一身份和权限治理场景，演示从身份同步、应用接入、权限申请到季度访问复核、离职回收和安全审计的协同链路。工程采用 Java 21、Spring Boot、Vue 3 与 MySQL，提供管理端和响应式 H5 工作台。

![IAM 身份治理管理端](docs/images/iam-governance-dashboard.png)

<p align="center"><em>管理端：身份规模、应用接入、审批积压、MFA 覆盖和访问复核事项</em></p>

![IAM 移动权限工作台](docs/images/iam-mobile-access.png)

<p align="center"><em>用户端：权限申请、审批待办、应用入口和安全提醒</em></p>

## 功能范围

| 模块 | 当前能力 |
| --- | --- |
| 身份中心 | 身份总览、同步与离职回收样例 |
| 应用与权限 | 应用接入、权限申请、审批事项和角色治理 |
| 访问复核 | 孤立账户、休眠特权账户、MFA 与复核完成率评估 |
| 安全基线 | ADMIN/OPERATOR 权限隔离、接口鉴权、输入校验 |
| 多端体验 | 桌面管理端与响应式移动工作台 |

核心接口 `POST /api/admin/access-review` 返回风险分数、`PASS / REVIEW / REMEDIATE` 决策和整改建议。

## 工程结构

```text
zhuatech-iam/
├── backend/      Spring Boot 4 / Java 21 / Security / JPA
├── frontend/     Vue 3 / Vite / 管理端与 H5
├── docs/         API、架构与实际页面截图
├── compose.yaml  MySQL、后端和前端编排
└── LICENSE       个人非商业社区源码许可
```

## 本地运行

```bash
cp .env.example .env
docker compose up --build
```

访问 `http://localhost:8090`。演示账号为 `admin / admin123` 和 `operator / operator123`，仅供本地学习；联网部署前必须通过环境变量替换。

详见 [API 文档](docs/API.md) 与 [架构说明](docs/ARCHITECTURE.md)。

## 特权访问授权门禁

新增管理员与高权限角色授权前的企业级门禁，统一检查身份有效性、MFA、角色责任人、业务工单、职责冲突、即时授权、访问有效期、会话记录与紧急访问依据。详见[企业特权访问授权](docs/ENTERPRISE_PRIVILEGED_ACCESS.md)。

## 使用许可

本项目采用 **ZhuaTech Community Source License 1.0（个人非商业版）**，不是 OSI 认可的开源许可证。个人可免费学习、研究和非商业修改；未经上海如静知华信息科技有限公司书面授权，不得商用、企业生产使用、收费下载、外包交付、投标、SaaS 或品牌替换。以 [LICENSE](LICENSE) 为准。

## 咨询合作

知华科技（上海如静知华信息科技有限公司）提供商业授权、私有化部署、系统集成和深度开发定制服务。

- 官网：[https://www.zhuatech.cn/](https://www.zhuatech.cn/)
- 微信咨询：扫描下方任一二维码

<p align="center">
  <img src="docs/images/zhuatech-wechat-consulting.png" alt="知华科技微信咨询二维码一" width="260" />
  &nbsp;&nbsp;&nbsp;&nbsp;
  <img src="docs/images/zhuatech-wechat-consulting-2.png" alt="知华科技微信咨询二维码二" width="260" />
</p>

关键词：知华科技 IAM、身份与访问管理、统一身份认证、权限治理、访问复核、Java IAM、Spring Boot 权限系统、上海软件定制开发。

## 职责分离与冲突权限检查

新增 `POST /api/iam/insights/segregation-of-duties`，识别付款创建与审批、供应商创建与采购审批、用户管理与审计管理等冲突角色，并结合高权限 MFA、休眠身份和服务账号责任人输出 `ALLOW / REVIEW / SUSPEND / REVOKE_CONFLICT`。
