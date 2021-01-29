package org.arch.ums.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;
import org.arch.framework.ums.enums.AddressType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户地址表(Address) 实体类
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:45:17
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
    @TableId("id")
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String district;

    /**
     * 街道
     */
    private String street;

    /**
     * 地址类型:工作地址/家庭地址/收获地址/..
     */
    private AddressType addressType;

    /**
     * 顺序
     */
    private Integer sorted;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 手机号
     */
    private String phoneNum;

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