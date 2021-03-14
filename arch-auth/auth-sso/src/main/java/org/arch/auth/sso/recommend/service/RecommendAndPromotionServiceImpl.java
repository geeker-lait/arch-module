package org.arch.auth.sso.recommend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.beans.Response;
import org.arch.framework.beans.exception.BusinessException;
import org.arch.framework.beans.exception.constant.CommonStatusCode;
import org.arch.framework.beans.utils.StringUtils;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.ums.enums.SourceType;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.account.entity.Relationship;
import org.arch.ums.feign.account.client.UmsAccountRelationshipFeignService;
import org.arch.ums.feign.user.client.UmsUserPhoneFeignService;
import org.arch.ums.user.entity.Phone;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static java.util.Objects.isNull;
import static org.arch.auth.sso.utils.RegisterUtils.getTraceId;
import static org.arch.framework.beans.exception.constant.CommonStatusCode.EXTRACT_USER_RECOMMEND_CODE_FAILED;
import static org.arch.framework.beans.exception.constant.CommonStatusCode.QUERY_USER_RECOMMEND_CODE_FAILED;
import static org.arch.framework.ums.enums.SourceType.ARCH;
import static org.arch.framework.ums.enums.SourceType.USER;
import static org.arch.framework.utils.RetryUtils.publishRetryEvent;

/**
 * 推广与用户推荐服务实现
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.8 23:59
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RecommendAndPromotionServiceImpl implements RecommendAndPromotionService, ApplicationContextAware {

    public static final String PSEQ_SEPARATOR = ",";
    public static final int PSEQ_LENGTH = 4;

    private final UmsAccountRelationshipFeignService umsAccountRelationshipFeignService;
    private final UmsUserPhoneFeignService umsUserPhoneFeignService;
    private ApplicationContext applicationContext;

    @NonNull
    @Override
    public String generateUserRecommendCode() {
        TokenInfo currentUser = SecurityUtils.getCurrentUser();
        Relationship relationship = new Relationship();
        relationship.setToUserId(currentUser.getAccountId());
        relationship.setFromUserId(-1L);
        try {
            // 前提: Relationship toUserId fromUserId 必须记录唯一
            Response<Relationship> response = this.umsAccountRelationshipFeignService.findOne(relationship);
            Relationship successData = response.getSuccessData();
            if (isNull(successData)) {
                Phone phone = getPhone(currentUser);
                // 新建推荐关系
                relationship.setPid(-1L);
                // org 通过 sql max(org) + 1 自增.
                relationship.setDeep(0L);
                // seq 通过 sql max(seq) + 1 自增.
                relationship.setToUserName(currentUser.getNickName());
                relationship.setToUserPhone(phone.getPhoneNo());
                relationship.setDeleted(Boolean.FALSE);
                Response<Relationship> saveResponse = this.umsAccountRelationshipFeignService.save(relationship);
                successData = saveResponse.getSuccessData();
                if (isNull(successData)) {
                    throw new BusinessException(CommonStatusCode.GENERATE_USER_RECOMMEND_CODE_FAILED, new Object[0],
                                                "生成用户推荐码失败: identifierId=" + currentUser.getIdentifierId());
                }
            }
            String delimiter = USER.getDelimiter();
            // 002_3_48: 00 为 SourceType 业务前缀, 2_3_48 为 rsOrg_rsDeep_rsSeq(rs=Relationship)
            String recommendCode = USER.getPrefix() +
                    successData.getOrg() +
                    delimiter +
                    successData.getDeep() +
                    delimiter +
                    successData.getSeq();
            return encodeRecommendationOrPromotionCode(recommendCode);

        }
        catch (BusinessException e) {
            throw e;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(CommonStatusCode.GENERATE_USER_RECOMMEND_CODE_FAILED, new Object[0],
                                        "生成用户推荐码失败: identifierId=" + currentUser.getIdentifierId());
        }
    }

    @NonNull
    @Override
    public String generatePromotionCode(@NonNull SourceType sourceType) {
        // 34位字符串 sourceType.getPrefix() + UuidUtils.getUUID()
        // 待实现
        throw new BusinessException("未实现 ARCH 推广逻辑");
    }

    @NonNull
    @Override
    public String encodeRecommendationOrPromotionCode(@NonNull String recommendationOrPromotionCode) {
        if (recommendationOrPromotionCode.startsWith(ARCH.getPrefix())) {
            return recommendationOrPromotionCode;
        }
        String encodeToString =
                Base64.getUrlEncoder().encodeToString(recommendationOrPromotionCode.getBytes(StandardCharsets.UTF_8));

        int codeLength = encodeToString.length();
        int mid = codeLength / 2;
        String beforeStr = encodeToString.substring(0, mid);
        String afterStr = encodeToString.substring(mid);
        int beforeStrLength = beforeStr.length();
        int afterStrLength = afterStr.length();
        int beforeStrQuarter = beforeStrLength / 4;
        int afterStrQuarter = afterStrLength / 4;
        StringBuilder sb = new StringBuilder(codeLength);
        sb.append(afterStr).append(beforeStr);
        sb.replace(afterStrQuarter, afterStrQuarter + 1, String.valueOf(beforeStr.charAt(beforeStrQuarter)));
        sb.replace(afterStrLength + beforeStrQuarter, afterStrLength + beforeStrQuarter + 1,
                   String.valueOf(afterStr.charAt(afterStrQuarter)));
        return sb.toString();
    }

    @NonNull
    @Override
    public String decodeRecommendationOrPromotionCode(@NonNull String recommendationOrPromotionCode) {
        int encodeStrLength = recommendationOrPromotionCode.length();
        int dMid = encodeStrLength / 2;
        //noinspection AlibabaUndefineMagicConstant
        if ((0x01 & encodeStrLength) == 1) {
            dMid++;
        }
        StringBuilder encodeSb = new StringBuilder();
        String beforeEncodeStr = recommendationOrPromotionCode.substring(dMid);
        String afterEncodeStr = recommendationOrPromotionCode.substring(0, dMid);

        int beforeEncodeStrLength = beforeEncodeStr.length();
        int afterEncodeStrLength = afterEncodeStr.length();
        int beforeEncodeStrQuarter = beforeEncodeStrLength / 4;
        int afterEncodeStrQuarter = afterEncodeStrLength / 4;
        encodeSb.append(beforeEncodeStr).append(afterEncodeStr);
        encodeSb.replace(beforeEncodeStrQuarter, beforeEncodeStrQuarter + 1,
                         String.valueOf(afterEncodeStr.charAt(afterEncodeStrQuarter)));
        encodeSb.replace(beforeEncodeStrLength + afterEncodeStrQuarter,
                         beforeEncodeStrLength + afterEncodeStrQuarter + 1,
                         String.valueOf(beforeEncodeStr.charAt(beforeEncodeStrQuarter)));

        String decode = new String(Base64.getUrlDecoder().decode(encodeSb.toString()), StandardCharsets.UTF_8);

        if (!decode.startsWith(USER.getPrefix())) {
            return recommendationOrPromotionCode;
        }
        return decode;
    }

    @NonNull
    @Override
    public Boolean userRecommendOrPromotionHandler(@NonNull String decodeRecommendationOrPromotionCode) {
        /*
            唯一索引: tenantId + org + deep + seq
            唯一索引: tenantId + toUserId + fromUserId
         */

        String userPrefix = decodeRecommendationOrPromotionCode.substring(0, USER.getLength());
        if (USER.getPrefix().equals(userPrefix)) {
            // 002_3_48: 00 为 SourceType 业务前缀, 2_3_48 为 rsOrg_rsDeep_rsSeq(rs=Relationship)
            return userRecommendationHandler(decodeRecommendationOrPromotionCode);
        }
        if (ARCH.getPrefix().equals(userPrefix)) {
            // 182454180b33e7484581f722a8e851f560: 18 为 SourceType 业务前缀, 2454180b33e7484581f722a8e851f560 为 uuid
            return archPromotionHandler(decodeRecommendationOrPromotionCode);
        }

        throw new BusinessException(CommonStatusCode.NOT_USER_OR_ARCH_SOURCE_TYPE);
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private boolean userRecommendationHandler(String decodeRecommendationCode) {
        // 002_3_48: 00 为 SourceType 业务前缀, 2_3_48 为 rsOrg_rsDeep_rsSeq(rs=Relationship)
        String recommendCode = decodeRecommendationCode.substring(USER.getLength());
        String[] splits = recommendCode.split(USER.getDelimiter());

        //noinspection AlibabaUndefineMagicConstant
        if (3 != splits.length) {
            throw new BusinessException(EXTRACT_USER_RECOMMEND_CODE_FAILED,
                                        new Object[]{recommendCode},
                                        "提取 USER 推荐码失败");
        }

        Relationship fromUserRelationship = new Relationship();
        Long org = Long.valueOf(splits[0]);
        Long deep = Long.valueOf(splits[1]);
        Long seq = Long.valueOf(splits[2]);
        fromUserRelationship.setOrg(org);
        fromUserRelationship.setDeep(deep);
        fromUserRelationship.setSeq(seq);
        Response<Relationship> response = this.umsAccountRelationshipFeignService.findOne(fromUserRelationship);
        Relationship successData = response.getSuccessData();
        if (isNull(successData)) {
        	throw new BusinessException(QUERY_USER_RECOMMEND_CODE_FAILED, new Object[]{recommendCode},
                                        "查询 USER 推荐码失败");
        }
        TokenInfo currentUser = SecurityUtils.getCurrentUser();
        Phone phone = getPhone(currentUser);

        Relationship toUserRelationship = new Relationship();
        toUserRelationship.setPid(successData.getId());
        toUserRelationship.setOrg(org);
        // seq 通过 sql max(seq) + 1 自增.
        toUserRelationship.setDeep(deep);
        toUserRelationship.setPseq(getPseq(successData));
        toUserRelationship.setFromUserId(successData.getToUserId());
        toUserRelationship.setFromUserName(successData.getToUserName());
        toUserRelationship.setFromUserPhone(successData.getToUserPhone());
        toUserRelationship.setToUserId(currentUser.getUserId());
        toUserRelationship.setToUserName(currentUser.getNickName());
        toUserRelationship.setToUserPhone(phone.getPhoneNo());
        toUserRelationship.setDeleted(Boolean.FALSE);
        Response<Relationship> saveResponse = this.umsAccountRelationshipFeignService.save(toUserRelationship);
        successData = saveResponse.getSuccessData();
        if (isNull(successData)) {
            String traceId = getTraceId();
            log.warn("保存用户推荐关系失败, 发布重试事件: recommendCode={}, accountId={}, traceId={}",
                     recommendCode, currentUser.getAccountId(), traceId);
            publishRetryEvent(this.applicationContext, getTraceId(),
                              this.umsAccountRelationshipFeignService,
                              UmsAccountRelationshipFeignService.class,
                              "save",
                              new Class[] {Relationship.class},
                              toUserRelationship);
        }

        return true;
    }

    private boolean archPromotionHandler(@NonNull String decodePromotionCode) {
        // 18 + uuid: 18 为 ARCH SourceType 业务前缀
        // 待实现
        throw new BusinessException("未实现 ARCH 推广逻辑");
    }

    /**
     * 获取 pseq
     * @param relationship  上级的 {@link Relationship}
     * @return  pseq(2,4,5,6 | 2 | 2,4)
     */
    @NonNull
    private String getPseq(@NonNull Relationship relationship) {
        String pseq = relationship.getPseq();
        if (isNull(pseq)) {
            return relationship.getSeq().toString();
        }

        String[] pseqs = StringUtils.split(pseq, PSEQ_SEPARATOR);
        if (isNull(pseqs) || pseqs.length == 0) {
            return relationship.getSeq().toString();
        }
        if (pseqs.length == PSEQ_LENGTH) {
            return pseqs[1] +
                    PSEQ_SEPARATOR +
                    pseqs[2] +
                    PSEQ_SEPARATOR +
                    pseqs[3] +
                    PSEQ_SEPARATOR +
                    relationship.getSeq();
        }

        return pseq + PSEQ_SEPARATOR + relationship.getSeq();

    }

    @NonNull
    private Phone getPhone(@NonNull TokenInfo currentUser) {
        Phone phone = new Phone();
        phone.setUserId(currentUser.getAccountId());
        Response<Phone> phoneResponse = this.umsUserPhoneFeignService.findOne(phone);
        Phone phoneSuccessData = phoneResponse.getSuccessData();
        if (isNull(phoneSuccessData)) {
            throw new BusinessException(CommonStatusCode.SERVER_BUSY, new Object[0],
                                        "查询用户电话信息失败: accountId=" + currentUser.getAccountId());
        }
        return phoneSuccessData;
    }

}
