/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.iam.controller;

import cn.zhuatech.iam.common.ApiResponse;
import cn.zhuatech.iam.service.AccessReviewService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController
@RequestMapping("/api/admin/access-review")
public class AccessReviewController {
    private final AccessReviewService service;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public AccessReviewController(AccessReviewService service) { this.service = service; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping
    ApiResponse<AccessReviewService.ReviewResult> review(
        @Valid @RequestBody AccessReviewService.ReviewRequest request) {
        return ApiResponse.ok(service.review(request));
    }
}
