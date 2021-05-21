package org.arch.ums.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;
import org.arch.framework.encrypt.EncryptField;
import org.arch.framework.ums.enums.Mno;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户电话信息(Phone) 实体类
 *
 * @author YongWu zheng
 * @date 2021-03-17 22:16:12
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user_phone")
public class Phone extends CrudEntity<Phone> {
    private static final long serialVersionUID = 1L;

    /**
     * 用户电话信息表ID
     */
    @TableId(value = "`id`")
    private Long id;

    /**
     * 用户ID
     */
    @TableField(value = "`user_id`")
    private Long userId;

    /**
     * 手机号
     */
    @TableField(value = "`phone_no`")
    @EncryptField(encryptType = "FPE")
    private String phoneNo;

    /**
     * 省份
     */
    @TableField(value = "`province`")
    private String province;

    /**
     * 城市
     */
    @TableField(value = "`city`")
    private String city;

    /**
     * 运营商: 移动/电信/联通/电话..
     */
    @TableField(value = "`mno`")
    private Mno mno;

    /**
     * 顺序
     */
    @TableField(value = "`sorted`")
    private Integer sorted;

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
