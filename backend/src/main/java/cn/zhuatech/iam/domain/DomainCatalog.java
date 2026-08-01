/* Copyright 2026 上海如静知华信息科技有限公司 */
package cn.zhuatech.iam.domain;
import org.springframework.stereotype.Component;
import java.util.List;
@Component public class DomainCatalog {
    public String systemName(){return "知华 IAM 身份与访问管理平台";}
    public String sceneName(){return "统一身份、应用接入、权限申请、访问复核与安全审计";}
    public List<SeedItem> seedItems(){return List.of(
        new SeedItem("IAM-20260801-001","财务共享系统季度权限复核","处理中","内控与安全组","高"),
        new SeedItem("IAM-20260801-002","供应商门户单点登录接入","待处理","身份平台组","中"),
        new SeedItem("IAM-20260801-003","离职账户自动回收核验","已完成","账号治理组","高"),
        new SeedItem("IAM-20260801-004","研发管理员 MFA 覆盖整改","处理中","终端安全组","紧急"));}
    public List<String> recommendedActions(){return List.of("优先回收孤立与休眠特权账户","对高权限角色发起负责人复核","提升关键应用 MFA 与单点登录覆盖率");}
    public record SeedItem(String recordNo,String title,String status,String owner,String priority){}
}
