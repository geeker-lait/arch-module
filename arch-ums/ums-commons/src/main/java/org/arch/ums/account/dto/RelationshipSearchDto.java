package org.arch.ums.account.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.dto.BaseSearchDto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 账号-关系(Relationship) search dto
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:20:39
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
public class RelationshipSearchDto extends BaseSearchDto {

    /**
     * id
     */
    private Long id;

    /**
     * 父节点ID（数据库自增）
     */
    private Long pid;

    /**
     * 组
     */
    private Integer org;

    /**
     * 深度
     */
    private Integer deep;

    /**
     * 顺序
     */
    private Integer seq;

    /**
     * 推荐人ID
     */
    private Long fromUserId;

    /**
     * 推荐人姓名
     */
    private String fromUserName;

    /**
     * 推荐人手机
     */
    private String fromUserPhone;

    /**
     * 账号ID
     */
    private String toUserId;

    /**
     * 用户名
     */
    private String toUserName;

    /**
     * 用户手机
     */
    private String toUserPhone;

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
        putNoNull("EQ_pid", this.getPid(), map);
        putNoNull("EQ_org", this.getOrg(), map);
        putNoNull("EQ_deep", this.getDeep(), map);
        putNoNull("EQ_seq", this.getSeq(), map);
        putNoNull("EQ_from_user_id", this.getFromUserId(), map);
        putNoNull("EQ_from_user_name", this.getFromUserName(), map);
        putNoNull("EQ_from_user_phone", this.getFromUserPhone(), map);
        putNoNull("EQ_to_user_id", this.getToUserId(), map);
        putNoNull("EQ_to_user_name", this.getToUserName(), map);
        putNoNull("EQ_to_user_phone", this.getToUserPhone(), map);
        putNoNull("EQ_tenant_id", this.getTenantId(), map);
        putNoNull("EQ_app_id", this.getAppId(), map);
        putNoNull("EQ_store_id", this.getStoreId(), map);
        putNoNull("EQ_rev", this.getRev(), map);
        putNoNull("EQ_st", this.getSt(), map);
        putNoNull("EQ_deleted", this.getDeleted(), map);
    }
}