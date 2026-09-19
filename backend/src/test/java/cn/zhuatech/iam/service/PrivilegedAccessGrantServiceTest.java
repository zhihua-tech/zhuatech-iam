/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.iam.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class PrivilegedAccessGrantServiceTest {
    private final PrivilegedAccessGrantService service = new PrivilegedAccessGrantService();

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void grantsControlledPrivilegedAccess() {
        var result = service.assess(new PrivilegedAccessGrantService.Request("PAM-100", true, true, true,
                true, false, true, true, true, false, true, true, true));
        assertThat(result.decision()).isEqualTo(PrivilegedAccessGrantService.Decision.GRANT);
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void routesSessionControlsToReview() {
        var result = service.assess(new PrivilegedAccessGrantService.Request("PAM-101", true, true, true,
                true, false, false, true, false, false, true, true, true));
        assertThat(result.actions()).hasSize(2);
        assertThat(result.decision()).isEqualTo(PrivilegedAccessGrantService.Decision.REVIEW);
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void blocksUnsafePrivilegedAccess() {
        var result = service.assess(new PrivilegedAccessGrantService.Request("", false, false, false,
                false, true, false, false, false, true, false, false, false));
        assertThat(result.blockers()).hasSize(10);
        assertThat(result.decision()).isEqualTo(PrivilegedAccessGrantService.Decision.BLOCKED);
    }
}
