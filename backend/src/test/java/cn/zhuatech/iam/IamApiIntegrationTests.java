/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.iam;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class IamApiIntegrationTests {
    @Autowired MockMvc mvc;

    @Test void publicAboutIsAccessible() throws Exception {
        mvc.perform(get("/api/public/about")).andExpect(status().isOk())
            .andExpect(jsonPath("$.data.company").value("上海如静知华信息科技有限公司"));
    }

    @Test void adminCanReadDashboardAndAssessRisk() throws Exception {
        mvc.perform(get("/api/admin/dashboard").with(httpBasic("admin", "admin123"))).andExpect(status().isOk())
            .andExpect(jsonPath("$.data.total").value(4));
        mvc.perform(post("/api/admin/risk-assessment").with(httpBasic("admin", "admin123")).contentType(MediaType.APPLICATION_JSON)
            .content("{\"backlog\":18,\"delayedItems\":3,\"criticalItems\":1,\"capacityUtilization\":91,\"dataCompleteness\":86}"))
            .andExpect(status().isOk()).andExpect(jsonPath("$.data.level").exists());
    }

    @Test void operatorCanUseWorkspaceButNotAdmin() throws Exception {
        mvc.perform(get("/api/workspace/tasks").with(httpBasic("operator", "operator123"))).andExpect(status().isOk());
        mvc.perform(get("/api/admin/dashboard").with(httpBasic("operator", "operator123"))).andExpect(status().isForbidden());
    }

    @Test void anonymousRequestIsRejected() throws Exception {
        mvc.perform(get("/api/workspace/tasks")).andExpect(status().isUnauthorized());
    }

    @Test void adminCanReviewAccessRisk() throws Exception {
        mvc.perform(post("/api/admin/access-review").with(httpBasic("admin", "admin123"))
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"orphanedAccounts\":2,\"dormantPrivilegedAccounts\":2,\"excessivePrivilegeAssignments\":3,\"mfaCoverage\":82,\"reviewCompletion\":70}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.riskScore").value(71))
            .andExpect(jsonPath("$.data.decision").value("REMEDIATE"));
    }
}
