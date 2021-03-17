package org.arch.ums.user.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;

import java.io.Serializable;

/**
 * 用户身份证表(IdCard) 实体类
 *
 * @author YongWu zheng
 * @date 2021-03-17 21:30:12
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user_id_card")
public class IdCard extends CrudEntity<IdCard> {
    private static final long serialVersionUID = 1L;

    /**
     * 用户身份证表ID
     */
    @TableId(value = "`id`")
    private Long id;

    /**
     * 用户ID
     */
    @TableField(value = "`user_id`")
    private Long userId;

    /**
     * 身份证号
     */
    @TableField(value = "`id_card`")
    private String idCard;

    /**
     * 名字
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 年龄
     */
    @TableField(value = "`age`")
    private Integer age;

    /**
     * 性别
     */
    @TableField(value = "`sex`")
    private Integer sex;

    /**
     * 生日
     */
    @TableField(value = "`birthday`")
    private LocalDate birthday;

    /**
     * 民族
     */
    @TableField(value = "`nation`")
    private String nation;

    /**
     * 居住地
     */
    @TableField(value = "`domicile`")
    private String domicile;

    /**
     * 颁发机构
     */
    @TableField(value = "`sign_org`")
    private String signOrg;

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
