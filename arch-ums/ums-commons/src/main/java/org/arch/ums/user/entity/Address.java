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
import org.arch.framework.ums.enums.AddressType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户地址表(Address) 实体类
 *
 * @author YongWu zheng
 * @date 2021-03-17 22:16:37
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user_address")
public class Address extends CrudEntity<Address> {
    private static final long serialVersionUID = 1L;

    /**
     * 用户地址信息表id
     */
    @TableId(value = "`id`")
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "`user_id`")
    private Long userId;

    /**
     * 省
     */
    @TableField(value = "`province`")
    private String province;

    /**
     * 市
     */
    @TableField(value = "`city`")
    private String city;

    /**
     * 区
     */
    @TableField(value = "`district`")
    private String district;

    /**
     * 街道
     */
    @TableField(value = "`street`")
    private String street;

    /**
     * 详细地址
     */
    @TableField(value = "`address`")
    private String address;

    /**
     * 地址类型:工作地址/家庭地址/收获地址/..
     */
    @TableField(value = "`address_type`")
    private AddressType addressType;

    /**
     * 顺序
     */
    @TableField(value = "`sorted`")
    private Integer sorted;

    /**
     * 联系人
     */
    @TableField(value = "`contacts`")
    private String contacts;

    /**
     * 手机号
     */
    @TableField(value = "`phone_num`")
    @EncryptField(encryptType = "FPE")
    private String phoneNum;

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
