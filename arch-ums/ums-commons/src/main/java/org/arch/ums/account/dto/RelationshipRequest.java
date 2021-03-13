package org.arch.ums.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 账号-关系(Relationship) request
 *
 * @author YongWu zheng
 * @date 2021-03-13 13:03:05
 * @since 1.0.0
 */
@SuppressWarnings("jol")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class RelationshipRequest {

    /**
     * id
     */
    private Long id;

    /**
     * 父节点ID（数据库自增）, 没有父节点则为 0
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
     * 父节点顺序, 没有父节点则为 0
     */
    private Long pseq;

    /**
     * 顺序
     */
    private Long seq;

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

}
