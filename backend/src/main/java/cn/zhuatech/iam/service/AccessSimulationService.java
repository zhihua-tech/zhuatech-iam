/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.iam.service;
import jakarta.validation.Valid;import jakarta.validation.constraints.*;import org.springframework.stereotype.Service;import java.time.*;import java.util.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service public class AccessSimulationService{
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public SimulationResult simulate(@Valid SimulationRequest request){Set<String>roles=new HashSet<>(request.roles());List<String>conflicts=request.conflicts().stream().filter(pair->roles.contains(pair.firstRole())&&roles.contains(pair.secondRole())).map(pair->pair.firstRole()+" + "+pair.secondRole()).toList();List<String>expired=new ArrayList<>(),dormant=new ArrayList<>(),privileged=new ArrayList<>();
  for(Entitlement item:request.entitlements()){if(item.expiresAt()!=null&&item.expiresAt().isBefore(request.asOf()))expired.add(item.code());if(item.lastUsedDays()>=90)dormant.add(item.code());if(item.privileged())privileged.add(item.code());}
  List<String>actions=new ArrayList<>();if(!conflicts.isEmpty())actions.add("移除职责分离冲突角色");if(!expired.isEmpty())actions.add("回收已过期权限");if(!dormant.isEmpty())actions.add("复核90天未使用权限");if(!privileged.isEmpty()&&!request.mfaEnabled())actions.add("启用多因素认证后方可授予特权");if(!privileged.isEmpty()&&(request.justification()==null||request.justification().isBlank()))actions.add("补充特权访问业务理由");
  boolean deny=!conflicts.isEmpty()||(!privileged.isEmpty()&&!request.mfaEnabled());String decision=deny?"DENY":!expired.isEmpty()||!dormant.isEmpty()||(!privileged.isEmpty()&&(request.justification()==null||request.justification().isBlank()))?"REVIEW":"ALLOW";return new SimulationResult(decision,conflicts,expired,dormant,privileged,actions);
 }
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public record SimulationRequest(@NotBlank String userNo,@NotNull LocalDate asOf,@NotEmpty Set<@NotBlank String>roles,@NotEmpty List<@Valid Entitlement>entitlements,List<@Valid ConflictPair>conflicts,boolean mfaEnabled,String justification){/**
                                                                                                                                                                                                                                                  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                                                                                                                                                                                                                  */
public SimulationRequest{conflicts=conflicts==null?List.of():List.copyOf(conflicts);}}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public record Entitlement(@NotBlank String code,boolean privileged,@Min(0) int lastUsedDays,LocalDate expiresAt){}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public record ConflictPair(@NotBlank String firstRole,@NotBlank String secondRole){}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public record SimulationResult(String decision,List<String>sodConflicts,List<String>expiredEntitlements,List<String>dormantEntitlements,List<String>privilegedEntitlements,List<String>actions){}
}
