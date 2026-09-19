/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.iam.controller;import cn.zhuatech.iam.common.ApiResponse;import cn.zhuatech.iam.service.AccessSimulationService;import jakarta.validation.Valid;import org.springframework.web.bind.annotation.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/advanced/iam") public class AccessSimulationController{private final AccessSimulationService service;/**
                                                                                                                                            * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                            */
public AccessSimulationController(AccessSimulationService service){this.service=service;}/**
                                                                                                                                                                                                                                     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                     */
@PostMapping("/access-simulation") public ApiResponse<AccessSimulationService.SimulationResult> simulate(@Valid @RequestBody AccessSimulationService.SimulationRequest request){return ApiResponse.ok(service.simulate(request));}}
