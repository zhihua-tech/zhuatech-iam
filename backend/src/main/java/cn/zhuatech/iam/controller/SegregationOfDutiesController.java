/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.iam.controller;

import cn.zhuatech.iam.common.ApiResponse;
import cn.zhuatech.iam.service.SegregationOfDutiesService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/iam/insights")
public class SegregationOfDutiesController {
    private final SegregationOfDutiesService service;

    public SegregationOfDutiesController(SegregationOfDutiesService service) {
        this.service = service;
    }

    @PostMapping("/segregation-of-duties")
    public ApiResponse<SegregationOfDutiesService.Result> evaluate(
        @Valid @RequestBody SegregationOfDutiesService.Request request) {
        return ApiResponse.ok(service.evaluate(request));
    }
}
