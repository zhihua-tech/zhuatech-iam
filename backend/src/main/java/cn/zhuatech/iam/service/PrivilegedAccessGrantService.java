/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.iam.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PrivilegedAccessGrantService {
    public Result assess(Request request) {
        var blockers = new ArrayList<String>();
        var actions = new ArrayList<String>();
        if (request.requestId() == null || request.requestId().isBlank()) blockers.add("特权申请编号不能为空");
        if (!request.identityActive()) blockers.add("申请身份无效或已停用");
        if (!request.mfaEnforced()) blockers.add("未强制多因素认证");
        if (!request.roleOwnerApproved()) blockers.add("特权角色责任人未批准");
        if (!request.ticketLinked()) blockers.add("未关联业务工单");
        if (request.sodConflict()) blockers.add("存在职责分离冲突");
        if (!request.expiryConfigured()) blockers.add("特权访问未设置到期时间");
        if (request.breakGlass() && !request.breakGlassReasonPresent()) blockers.add("紧急访问缺少原因与授权依据");
        if (!request.reviewerSeparated()) blockers.add("申请人与审批人未职责分离");
        if (!request.auditReady()) blockers.add("特权授权审计证据不完整");
        if (!request.justInTime()) actions.add("改为按需即时授权");
        if (!request.sessionRecordingEnabled()) actions.add("启用特权会话记录");
        var decision = !blockers.isEmpty() ? Decision.BLOCKED : actions.isEmpty() ? Decision.GRANT : Decision.REVIEW;
        return new Result(decision, List.copyOf(blockers), List.copyOf(actions));
    }

    public enum Decision { GRANT, REVIEW, BLOCKED }
    public record Request(String requestId, boolean identityActive, boolean mfaEnforced,
                          boolean roleOwnerApproved, boolean ticketLinked, boolean sodConflict,
                          boolean justInTime, boolean expiryConfigured, boolean sessionRecordingEnabled,
                          boolean breakGlass, boolean breakGlassReasonPresent,
                          boolean reviewerSeparated, boolean auditReady) {}
    public record Result(Decision decision, List<String> blockers, List<String> actions) {}
}
