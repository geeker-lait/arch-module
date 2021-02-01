package org.arch.ums.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.ums.enums.SourceType;

import java.time.LocalDateTime;

/**
 * 账号名(Name) request
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:54:15
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class NameRequest {

    /**
     * 账号-名称ID
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
    private SourceType source;

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

}