package org.arch.auth.sso.recommend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.beans.exception.AuthenticationException;
import org.arch.framework.beans.exception.BusinessException;
import org.arch.framework.beans.exception.constant.AuthStatusCode;
import org.arch.framework.beans.exception.constant.CommonStatusCode;
import org.arch.framework.ums.userdetails.ArchUser;
import org.arch.ums.account.entity.Relationship;
import org.arch.ums.feign.account.client.UmsAccountRelationshipFeignService;
import org.arch.ums.feign.user.client.UmsUserPhoneFeignService;
import org.arch.ums.user.entity.Phone;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;
import static org.arch.framework.ums.enums.SourceType.USER;

/**
 * 推广与用户推荐服务实现
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.8 23:59
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RecommendAndPromotionServiceImpl implements RecommendAndPromotionService {

    private final UmsAccountRelationshipFeignService umsAccountRelationshipFeignService;
    private final UmsUserPhoneFeignService umsUserPhoneFeignService;

    @NonNull
    @Override
    public String generateUserRecommendCode() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof ArchUser)
            {
                ArchUser archUser = ((ArchUser) principal);
                Relationship relationship = new Relationship();
                relationship.setToUserId(archUser.getAccountId());
                try {
                    // 前提: Relationship toUserId 必须记录唯一
                    Response<Relationship> response = this.umsAccountRelationshipFeignService.findOne(relationship);
                    Relationship successData = response.getSuccessData();
                    if (isNull(successData)) {
                        Phone phone = new Phone();
                        phone.setUserId(archUser.getAccountId());
                        Response<Phone> phoneResponse = this.umsUserPhoneFeignService.findOne(phone);
                        Phone phoneSuccessData = phoneResponse.getSuccessData();
                        if (isNull(phoneSuccessData)) {
                            throw new BusinessException(CommonStatusCode.SERVER_BUSY, new Object[]{archUser},
                                                        "生成用户推荐关系失败");
                        }
                        // 新建推荐关系
                        relationship.setPid(0L);
                        // TODO: 2021.3.10 org 自增策略.
                        relationship.setOrg(0);
                        relationship.setDeep(0);
                        relationship.setSeq(0);
                        relationship.setPseq(-1);
                        relationship.setToUserName(archUser.getNickName());
                        relationship.setToUserPhone(phoneSuccessData.getPhoneNo());
                        relationship.setDeleted(Boolean.FALSE);
                        Response<Relationship> saveResponse = this.umsAccountRelationshipFeignService.save(relationship);
                        successData = saveResponse.getSuccessData();
                        if (isNull(successData)) {
                            throw new BusinessException(CommonStatusCode.SERVER_BUSY, new Object[]{archUser},
                                                        "生成用户推荐关系失败");
                        }
                    }
                    String delimiter = USER.getDelimiter();
                    return USER.getPrefix() +
                            delimiter +
                            successData.getOrg() +
                            delimiter +
                            successData.getDeep() +
                            delimiter +
                            successData.getSeq() +
                            delimiter +
                            successData.getPseq();

                }
                catch (Exception e) {
                    log.error(e.getMessage(),e);
                    throw new BusinessException(CommonStatusCode.SERVER_BUSY, new Object[]{archUser}, "生成用户推荐关系失败");
                }
            }
        }
        throw new AuthenticationException(AuthStatusCode.UNAUTHORIZED.getCode(), "未登录或 token 失效");
    }

    @NonNull
    @Override
    public String generatePromotionCode(@NonNull String sourceType) {
        // TODO
        return "null";
    }

    @NonNull
    @Override
    public String encodeRecommendationOrPromotionCode(@NonNull String recommendationOrPromotionCode) {
        // TODO
        return "null";
    }

    @NonNull
    @Override
    public String decodeRecommendationOrPromotionCode(@NonNull String recommendationOrPromotionCode) {
        // TODO
        return "null";
    }

    @NonNull
    @Override
    public Boolean userRecommendOrPromotionHandler(@NonNull String decodeRecommendationOrPromotionCode) {
        // TODO
        /*
            org: 是针对全局的组计数, 还是针对个人的组计数
            deep: 深度针对全局深度.
            pseq: 上节点的 seq, (或者可以在增加一个 ppseq(上上级seq)字段, 这样可以减少查询频率.)
         */
        /*
            唯一索引: tenantId + org + deep + seq
            当前位置: A20[DEEP, SEQ] pseq=2, id=10, pid=2, org=1
            查找上级: A12[DEEP-1, PSEQ], 条件: tenantId, id=pid=2
            查找上上级: A0?[DEEP-2, ?], 条件: tenantId, org=1, deep=2-2=0, seq=?
            查找下级: 条件: tenantId,org=1, 2+2=4>deep>2
         */
        /*
            增加一个 ppseq(上上级顺序)字段, 这样可以减少查询频率.

            唯一索引: tenantId + org + deep + seq
            当前位置: A20[DEEP, SEQ] pseq=2, id=10, pid=2, org=1, ppseq=4
            查找上级: A12[DEEP-1, PSEQ], 条件: tenantId, id=pid=2 或 tenantId, org=1, deep=2-1=1, seq=pseq=2
            查找上上级: A04[DEEP-2, PPSEQ], 条件: tenantId, org=1, deep=2-2=0, seq=ppseq=4
            查找下级: 条件: tenantId, org=1, 2+2=4>deep>2

            结论: 添加一个 ppseq, 去掉 pid
         */
        /*
            seq 与 org 递增问题: 用 max(seq/org) 获取最大值, 插入时还需要并发校验, 锁表或乐观锁.
            Relationship 表本身读写压力大,
            是否可以通过 IdService 那样, 通过 redis 获取自增的 seq 与 org. 来提高 Relationship 表的读写性能.
         */
        /*
            用户推广时: 怎么获取自己的推广记录:
                    前提: Relationship toUserId fromUserId 必须记录唯一
                    唯一索引: UNQ_TENANTID_TOUSERID_FROMUSERID
         */
        return false;
    }

}
