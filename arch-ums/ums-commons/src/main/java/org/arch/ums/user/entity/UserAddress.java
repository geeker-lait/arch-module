package org.arch.ums.user.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户地址表(user_address)实体类
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user_address")
public class UserAddress extends Model<UserAddress> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户地址信息表id
     */
    @TableId
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
    private Integer typ;
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
     * 时间戳
     */
    private Date st;

}