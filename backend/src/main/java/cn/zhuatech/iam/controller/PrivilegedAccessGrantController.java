/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.iam.controller;

import cn.zhuatech.iam.common.ApiResponse;
import cn.zhuatech.iam.service.PrivilegedAccessGrantService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController
@RequestMapping("/api/enterprise/iam")
public class PrivilegedAccessGrantController {
    private final PrivilegedAccessGrantService service;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public PrivilegedAccessGrantController(PrivilegedAccessGrantService service) { this.service = service; }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/privileged-access-grant")
    public ApiResponse<?> assess(@RequestBody PrivilegedAccessGrantService.Request request) {
        return ApiResponse.ok(service.assess(request));
    }
}
