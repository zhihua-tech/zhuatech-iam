/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.iam.controller;

import cn.zhuatech.iam.common.ApiResponse;
import cn.zhuatech.iam.service.AccessReviewService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/access-review")
public class AccessReviewController {
    private final AccessReviewService service;
    public AccessReviewController(AccessReviewService service) { this.service = service; }
    @PostMapping
    ApiResponse<AccessReviewService.ReviewResult> review(
        @Valid @RequestBody AccessReviewService.ReviewRequest request) {
        return ApiResponse.ok(service.review(request));
    }
}
