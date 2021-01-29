package org.arch.ums.user.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.dto.BaseSearchDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 用户身份证表(IdCard) search dto
 *
 * @author YongWu zheng
 * @date 2021-01-29 22:06:04
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
public class IdCardSearchDto extends BaseSearchDto {

    /**
     * 用户身份证表ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 名字
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 民族
     */
    private String nation;

    /**
     * 居住地
     */
    private String domicile;

    /**
     * 颁发机构
     */
    private String signOrg;

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
        putNoNull("EQ_id_card", this.getIdCard(), map);
        putNoNull("EQ_name", this.getName(), map);
        putNoNull("EQ_age", this.getAge(), map);
        putNoNull("EQ_sex", this.getSex(), map);
        putNoNull("EQ_birthday", this.getBirthday(), map);
        putNoNull("EQ_nation", this.getNation(), map);
        putNoNull("EQ_domicile", this.getDomicile(), map);
        putNoNull("EQ_sign_org", this.getSignOrg(), map);
        putNoNull("EQ_tenant_id", this.getTenantId(), map);
        putNoNull("EQ_app_id", this.getAppId(), map);
        putNoNull("EQ_store_id", this.getStoreId(), map);
        putNoNull("EQ_rev", this.getRev(), map);
        putNoNull("EQ_st", this.getSt(), map);
        putNoNull("EQ_deleted", this.getDeleted(), map);
    }
}