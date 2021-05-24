package org.arch.ums.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户亲朋信息(Relatives) 实体类
 *
 * @author YongWu zheng
 * @date 2021-03-17 21:30:11
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user_relatives")
public class Relatives extends CrudEntity<Relatives> {
    private static final long serialVersionUID = 1L;

    /**
     * 用户亲朋信息表ID
     */
    @TableId(value = "`id`")
    private Long id;

    /**
     * 用户ID
     */
    @TableField(value = "`user_id`")
    private Long userId;

    /**
     * 亲朋类型: 1. 家属, 2. 朋友
     */
    @TableField(value = "`relatives_type`")
    private Integer relativesType;

    /**
     * 亲朋名称: 哥哥, 妹妹, 父亲, 母亲, 弟弟, 朋友, 同学
     */
    @TableField(value = "`relatives_name`")
    private String relativesName;

    /**
     * 亲朋性别: 0男, 1女
     */
    @TableField(value = "`relatives_sex`")
    private Integer relativesSex;

    /**
     * 顺序
     */
    @TableField(value = "`sorted`")
    private Integer sorted;

    /**
     * 颁发时间
     */
    @TableField(value = "`awardtime`")
    private LocalDate awardtime;

    /**
     * 租户 id
     */
    @TableField(value = "`tenant_id`")
    private Integer tenantId;

    /**
     * 应用 id
     */
    @TableField(value = "`app_id`")
    private Integer appId;

    /**
     * 店铺 id
     */
    @TableField(value = "`store_id`")
    private Integer storeId;

    /**
     * 乐观锁, 默认: 0
     */
    @TableField(value = "`rev`")
    private Integer rev;

    /**
     * 时间戳/创建时间
     */
    @TableField(value = "`dt`")
    private LocalDateTime dt;

    /**
     * 是否逻辑删除: 0 未删除(false), 1 已删除(true); 默认: 0
     */
    @TableField(value = "`deleted`")
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
