package org.arch.ums.user.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.ums.enums.AddressType;

import java.time.LocalDateTime;

/**
 * 用户地址表(Address) request
 *
 * @author YongWu zheng
 * @date 2021-03-17 22:19:44
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AddressRequest {

    /**
     * 用户地址信息表id
     */
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
     * 详细地址
     */
    private String address;

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
    private LocalDateTime dt;

    /**
     * 是否逻辑删除: 0 未删除(false), 1 已删除(true); 默认: 0
     */
    private Boolean deleted;

}
