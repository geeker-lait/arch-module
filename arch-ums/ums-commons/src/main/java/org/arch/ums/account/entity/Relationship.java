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
 * 账号-关系(Relationship) 实体类
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:20:26
 * @since 1.0.0
 */
@SuppressWarnings("jol")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_relationship")
public class Relationship extends CrudEntity<Relationship> {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父节点ID（数据库自增）, 没有父节点则为 0
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
     * 父节点顺序, 没有父节点则为 -1
     */
    private Integer pseq;

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