/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.iam;

import cn.zhuatech.iam.service.SegregationOfDutiesService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class SegregationOfDutiesServiceTests {
    private final SegregationOfDutiesService service = new SegregationOfDutiesService();

    @Test
    void revokesConflictingPaymentRoles() {
        var result = service.evaluate(new SegregationOfDutiesService.Request(
            "USER-1001", List.of("PAYMENT_CREATE", "PAYMENT_APPROVE"),
            true, true, 3, false, true));

        assertEquals("REVOKE_CONFLICT", result.decision());
        assertFalse(result.conflicts().isEmpty());
    }

    @Test
    void allowsCleanReviewedIdentity() {
        var result = service.evaluate(new SegregationOfDutiesService.Request(
            "USER-1002", List.of("REPORT_VIEW", "ORDER_VIEW"),
            false, true, 10, false, true));

        assertEquals("ALLOW", result.decision());
        assertEquals(0, result.riskScore());
    }
}
