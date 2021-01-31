package org.arch.ums.user.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.dto.BaseSearchDto;
import org.arch.framework.ums.enums.RelativesType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 用户亲朋信息(Relatives) search dto
 *
 * @author YongWu zheng
 * @date 2021-01-29 23:02:26
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
public class RelativesSearchDto extends BaseSearchDto {

    /**
     * 用户亲朋信息表ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 亲朋类型: 1. 家属, 2. 朋友
     */
    private RelativesType relativesType;

    /**
     * 亲朋名称: 哥哥, 妹妹, 父亲, 母亲, 弟弟, 朋友, 同学
     */
    private String relativesName;

    /**
     * 亲朋性别: 0男, 1女
     */
    private Integer relativesSex;

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
    protected void buildSearchParams(Map<String, Object> map) {
        // TODO 需要根据实际业务对条件进行增减(对应的字段也需要增减), 包括条件的顺序问题, 需要对应相应的多索引顺序, 使索引生效.
        putNoNull("EQ_user_id", this.getUserId(), map);
        putNoNull("EQ_relatives_type", this.getRelativesType(), map);
        putNoNull("EQ_relatives_name", this.getRelativesName(), map);
        putNoNull("EQ_relatives_sex", this.getRelativesSex(), map);
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