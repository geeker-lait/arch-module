package org.arch.ums.account.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 账号-关系(Relationship) search dto
 *
 * @author YongWu zheng
 * @date 2021-03-14 14:32:59
 * @since 1.0.0
 */
@SuppressWarnings("jol")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class RelationshipSearchDto implements BaseSearchDto {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;

    /**
     * 父节点ID（数据库自增）, 没有父节点则为 -1
     */
    private Long pid;

    /**
     * 组
     */
    private Long org;

    /**
     * 深度
     */
    private Long deep;

    /**
     * 顺序
     */
    private Long seq;

    /**
     * 父节点顺序: 3,4,5,6(对应: deep-4, deep-3, deep-2, deep-1)
     */
    private String pseq;

    /**
     * seq 向量
     */
    private String vector;

    /**
     * 推荐人ID, 没有推荐人则为 -1
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
    private Long toUserId;

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
    @JsonIgnore
    private Integer appId;

    /**
     * 店铺 id
     */
    @JsonIgnore
    private Integer storeId;

    /**
     * 乐观锁, 默认: 0
     */
    @JsonIgnore
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
        putNoNull("EQ_tenant_id", this.getTenantId(), map);
        putNoNull("EQ_id", this.getId(), map);
        putNoNull("EQ_pid", this.getPid(), map);
        putNoNull("EQ_org", this.getOrg(), map);
        putNoNull("EQ_deep", this.getDeep(), map);
        putNoNull("EQ_seq", this.getSeq(), map);
        putNoNull("EQ_to_user_id", this.getToUserId(), map);
        putNoNull("EQ_from_user_id", this.getFromUserId(), map);
        putNoNull("EQ_app_id", this.getAppId(), map);
        putNoNull("EQ_store_id", this.getStoreId(), map);
        putNoNull("EQ_deleted", this.getDeleted(), map);
        putNoNull("EQ_pseq", this.getPseq(), map);
        putNoNull("EQ_vector", this.getVector(), map);
        putNoNull("EQ_from_user_name", this.getFromUserName(), map);
        putNoNull("EQ_from_user_phone", this.getFromUserPhone(), map);
        putNoNull("EQ_to_user_name", this.getToUserName(), map);
        putNoNull("EQ_to_user_phone", this.getToUserPhone(), map);
        putNoNull("EQ_rev", this.getRev(), map);
        putNoNull("EQ_dt", this.getDt(), map);
    }
}
