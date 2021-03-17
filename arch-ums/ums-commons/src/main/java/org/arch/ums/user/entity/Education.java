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
 * 用户学历信息(Education) 实体类
 *
 * @author YongWu zheng
 * @date 2021-03-17 21:30:12
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user_education")
public class Education extends CrudEntity<Education> {
    private static final long serialVersionUID = 1L;

    /**
     * 用户学历信息表ID
     */
    @TableId(value = "`id`")
    private Long id;

    /**
     * 用户ID
     */
    @TableField(value = "`user_id`")
    private Long userId;

    /**
     * 证书编码
     */
    @TableField(value = "`certificate_no`")
    private String certificateNo;

    /**
     * 证书名称
     */
    @TableField(value = "`certificate_name`")
    private String certificateName;

    /**
     * 证书登记机构
     */
    @TableField(value = "`certificate_org`")
    private String certificateOrg;

    /**
     * 学历(如: 大专, 本科, 硕士, 博士)
     */
    @TableField(value = "`certificate_level`")
    private String certificateLevel;

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
