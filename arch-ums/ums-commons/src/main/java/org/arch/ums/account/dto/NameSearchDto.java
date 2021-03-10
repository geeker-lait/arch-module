package org.arch.ums.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 账号名(Name) search dto
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:54:16
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class NameSearchDto implements BaseSearchDto {

    /**
     * 账号-标识 ID
     */
    private Long id;

    /**
     * 账号ID/用户ID/会员ID/商户ID
     */
    private Long accountId;

    /**
     * 用户昵称可随机生成
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 来源, 推广统计用
     */
    private String source;

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
    private LocalDateTime dt;

    /**
     * 是否逻辑删除: 0 未删除(false), 1 已删除(true); 默认: 0
     */
    private Boolean deleted;

    @Override
    public void buildSearchParams(Map<String, Object> map) {
        putNoNull("EQ_id", this.getId(), map);
        putNoNull("EQ_tenant_id", this.getTenantId(), map);
        putNoNull("EQ_account_id", this.getAccountId(), map);
        putNoNull("EQ_source", this.getSource(), map);
        putNoNull("EQ_deleted", this.getDeleted(), map);
        putNoNull("EQ_app_id", this.getAppId(), map);
        putNoNull("EQ_store_id", this.getStoreId(), map);
        putNoNull("EQ_nick_name", this.getNickName(), map);
        putNoNull("EQ_avatar", this.getAvatar(), map);
        putNoNull("EQ_rev", this.getRev(), map);
        putNoNull("EQ_dt", this.getDt(), map);
    }
}
