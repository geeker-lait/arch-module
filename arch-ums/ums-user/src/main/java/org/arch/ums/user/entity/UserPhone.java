package org.arch.ums.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户电话信息(user_phone)实体类
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user_phone")
public class UserPhone extends Model<UserPhone> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户电话信息表ID
     */
    @TableId
	private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 手机号
     */
    private String phoneNo;
    /**
     * 号码归属地
     */
    private String location;
    /**
     * 运营商: 移动/电信/联通/电话..
     */
    private String mno;
    /**
     * 顺序
     */
    private Integer sorted;
    /**
     * 时间戳
     */
    private Date st;

}