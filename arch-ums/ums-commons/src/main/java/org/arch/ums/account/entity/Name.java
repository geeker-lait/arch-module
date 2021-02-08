package org.arch.ums.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 账号名(Name) 实体类
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:53:24
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_name")
public class Name extends CrudEntity<Name> {
    private static final long serialVersionUID = 1L;

    /**
     * 账号-标识 ID
     */
    @TableId(value = "id", type = IdType.INPUT)
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
    private LocalDateTime st;

    /**
     * 是否逻辑删除: 0 未删除(false), 1 已删除(true); 默认: 0
     */
    private Boolean deleted;


    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return id;
    }
}