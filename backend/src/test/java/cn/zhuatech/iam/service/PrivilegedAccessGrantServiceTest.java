/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.iam.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class PrivilegedAccessGrantServiceTest {
    private final PrivilegedAccessGrantService service = new PrivilegedAccessGrantService();

    @Test void grantsControlledPrivilegedAccess() {
        var result = service.assess(new PrivilegedAccessGrantService.Request("PAM-100", true, true, true,
                true, false, true, true, true, false, true, true, true));
        assertThat(result.decision()).isEqualTo(PrivilegedAccessGrantService.Decision.GRANT);
    }

    @Test void routesSessionControlsToReview() {
        var result = service.assess(new PrivilegedAccessGrantService.Request("PAM-101", true, true, true,
                true, false, false, true, false, false, true, true, true));
        assertThat(result.actions()).hasSize(2);
        assertThat(result.decision()).isEqualTo(PrivilegedAccessGrantService.Decision.REVIEW);
    }

    @Test void blocksUnsafePrivilegedAccess() {
        var result = service.assess(new PrivilegedAccessGrantService.Request("", false, false, false,
                false, true, false, false, false, true, false, false, false));
        assertThat(result.blockers()).hasSize(10);
        assertThat(result.decision()).isEqualTo(PrivilegedAccessGrantService.Decision.BLOCKED);
    }
}
