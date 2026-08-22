/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.iam.service;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccessReviewService {
    public ReviewResult review(ReviewRequest request) {
        int riskScore = Math.min(30, request.orphanedAccounts() * 10)
            + Math.min(25, request.dormantPrivilegedAccounts() * 8)
            + Math.min(20, request.excessivePrivilegeAssignments() * 4)
            + Math.max(0, 95 - request.mfaCoverage())
            + Math.max(0, 90 - request.reviewCompletion()) / 2;
        riskScore = Math.min(100, riskScore);
        String decision = riskScore >= 65 ? "REMEDIATE" : riskScore >= 30 ? "REVIEW" : "PASS";
        List<String> actions = new ArrayList<>();
        if (request.orphanedAccounts() > 0) actions.add("立即冻结并确认孤立账户归属");
        if (request.dormantPrivilegedAccounts() > 0) actions.add("回收休眠特权账户的高权限角色");
        if (request.mfaCoverage() < 95) actions.add("补齐关键应用多因素认证覆盖");
        if (request.reviewCompletion() < 90) actions.add("催办未完成的访问权限复核任务");
        if (actions.isEmpty()) actions.add("保持季度访问复核与离职回收监测");
        return new ReviewResult(riskScore, decision, actions);
    }

    public record ReviewRequest(@NotNull @Min(0) @Max(1000) Integer orphanedAccounts,
        @NotNull @Min(0) @Max(1000) Integer dormantPrivilegedAccounts,
        @NotNull @Min(0) @Max(10000) Integer excessivePrivilegeAssignments,
        @NotNull @Min(0) @Max(100) Integer mfaCoverage,
        @NotNull @Min(0) @Max(100) Integer reviewCompletion) {}
    public record ReviewResult(int riskScore, String decision, List<String> actions) {}
}
