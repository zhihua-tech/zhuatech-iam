# 企业特权访问授权

`POST /api/enterprise/iam/privileged-access-grant` 在授予管理员或高权限角色前检查身份、MFA、角色责任人、业务工单、职责分离、有效期、紧急访问依据和审计证据。

- `GRANT`：身份与控制条件完整，可以授权。
- `REVIEW`：不存在硬性阻断，但建议启用即时授权或会话记录。
- `BLOCKED`：身份、MFA、审批、工单、职责冲突、有效期或审计控制失败。

决策结果可供 IAM、PAM、ITSM 和目录服务适配器执行后续授权。
