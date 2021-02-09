package org.arch.ums.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 用户学历信息(Education) search dto
 *
 * @author YongWu zheng
 * @date 2021-01-29 22:04:36
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class EducationSearchDto implements BaseSearchDto {

    /**
     * 用户学历信息表ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 证书编码
     */
    private String certificateNo;

    /**
     * 证书名称
     */
    private String certificateName;

    /**
     * 证书登记机构
     */
    private String certificateOrg;

    /**
     * 学历(如: 大专, 本科, 硕士, 博士)
     */
    private String certificateLevel;

    /**
     * 顺序
     */
    private Integer sorted;

    /**
     * 颁发时间
     */
    private LocalDate awardtime;

    /**
     * 租户 id
     */
    private Integer tenantId;

    /**
     * 应用 id
     */
    private Integer appId;

    /**
     * 店铺 id
     */
    private Integer storeId;

    /**
     * 乐观锁, 默认: 0
     */
    private Integer rev;

    /**
     * 时间戳/创建时间
     */
    private LocalDateTime st;

    /**
     * 是否逻辑删除: 0 未删除(false), 1 已删除(true); 默认: 0
     */
    private Boolean deleted;

    @Override
    public void buildSearchParams(Map<String, Object> map) {
        // TODO 需要根据实际业务对条件进行增减(对应的字段也需要增减), 包括条件的顺序问题, 需要对应相应的多索引顺序, 使索引生效.
        putNoNull("EQ_user_id", this.getUserId(), map);
        putNoNull("EQ_certificate_no", this.getCertificateNo(), map);
        putNoNull("EQ_certificate_name", this.getCertificateName(), map);
        putNoNull("EQ_certificate_org", this.getCertificateOrg(), map);
        putNoNull("EQ_certificate_level", this.getCertificateLevel(), map);
        putNoNull("EQ_sorted", this.getSorted(), map);
        putNoNull("EQ_awardtime", this.getAwardtime(), map);
        putNoNull("EQ_tenant_id", this.getTenantId(), map);
        putNoNull("EQ_app_id", this.getAppId(), map);
        putNoNull("EQ_store_id", this.getStoreId(), map);
        putNoNull("EQ_rev", this.getRev(), map);
        putNoNull("EQ_st", this.getSt(), map);
        putNoNull("EQ_deleted", this.getDeleted(), map);
    }
}
