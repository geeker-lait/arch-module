package org.arch.ums.user.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.dto.BaseSearchDto;
import org.arch.framework.ums.enums.AddressType;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 用户地址表(Address) search dto
 *
 * @author YongWu zheng
 * @date 2021-01-29 23:07:25
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
public class AddressSearchDto extends BaseSearchDto {

    /**
     * 用户地址信息表id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String district;

    /**
     * 街道
     */
    private String street;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 地址类型:工作地址/家庭地址/收获地址/..
     */
    private AddressType addressType;

    /**
     * 顺序
     */
    private Integer sorted;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 手机号
     */
    private String phoneNum;

    /**
     * 是否默认: 0 否, 1 是
     */
    private Boolean def;

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
        putNoNull("EQ_province", this.getProvince(), map);
        putNoNull("EQ_city", this.getCity(), map);
        putNoNull("EQ_district", this.getDistrict(), map);
        putNoNull("EQ_street", this.getStreet(), map);
        putNoNull("EQ_address", this.getAddress(), map);
        putNoNull("EQ_address_type", this.getAddressType(), map);
        putNoNull("EQ_sorted", this.getSorted(), map);
        putNoNull("EQ_contacts", this.getContacts(), map);
        putNoNull("EQ_phone_num", this.getPhoneNum(), map);
        putNoNull("EQ_default", this.getDef(), map);
        putNoNull("EQ_tenant_id", this.getTenantId(), map);
        putNoNull("EQ_app_id", this.getAppId(), map);
        putNoNull("EQ_store_id", this.getStoreId(), map);
        putNoNull("EQ_rev", this.getRev(), map);
        putNoNull("EQ_st", this.getSt(), map);
        putNoNull("EQ_deleted", this.getDeleted(), map);
    }
}