/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.iam.service;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SegregationOfDutiesService {
    public Result evaluate(Request request) {
        Set<String> roles = new HashSet<>(request.roles());
        List<String> conflicts = new ArrayList<>();
        detect(roles, conflicts, "PAYMENT_CREATE", "PAYMENT_APPROVE", "付款创建与审批");
        detect(roles, conflicts, "VENDOR_CREATE", "PURCHASE_APPROVE", "供应商创建与采购审批");
        detect(roles, conflicts, "USER_ADMIN", "AUDIT_ADMIN", "用户管理与审计管理");

        int riskScore = conflicts.size() * 40;
        if (request.privilegedAccess() && !request.mfaEnabled()) riskScore += 30;
        if (request.dormantDays() >= 90) riskScore += 25;
        if (request.serviceAccount() && !request.ownerAssigned()) riskScore += 35;
        riskScore = Math.min(100, riskScore);
        String decision = !conflicts.isEmpty() ? "REVOKE_CONFLICT"
            : request.dormantDays() >= 90 || request.serviceAccount() && !request.ownerAssigned() ? "SUSPEND"
            : request.privilegedAccess() && !request.mfaEnabled() ? "REVIEW" : "ALLOW";

        List<String> actions = new ArrayList<>();
        if (!conflicts.isEmpty()) actions.add("拆分冲突角色并重新指定审批责任人");
        if (request.privilegedAccess() && !request.mfaEnabled()) actions.add("启用多因素认证后再授予高权限");
        if (request.dormantDays() >= 90) actions.add("暂停长期未使用身份并通知直属负责人复核");
        if (request.serviceAccount() && !request.ownerAssigned()) actions.add("为服务账号指定业务与技术责任人");
        if (actions.isEmpty()) actions.add("允许保留权限并记录本次复核证据");
        return new Result(request.identityCode(), riskScore, decision, conflicts, actions);
    }

    private void detect(Set<String> roles, List<String> conflicts,
                        String left, String right, String label) {
        if (roles.contains(left) && roles.contains(right)) conflicts.add(label);
    }

    public record Request(@NotBlank String identityCode,
                          @NotEmpty List<@NotBlank String> roles,
                          boolean privilegedAccess, boolean mfaEnabled,
                          @Min(0) int dormantDays, boolean serviceAccount,
                          boolean ownerAssigned) {}

    public record Result(String identityCode, int riskScore, String decision,
                         List<String> conflicts, List<String> actions) {}
}
