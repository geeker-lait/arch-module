package org.arch.ums.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户身份证表(user_id_card)实体类
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user_id_card")
public class UserIdCard extends Model<UserIdCard> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId
	private Long userId;
    /**
     * 身份证号
     */
    private String idcard;
    /**
     * 名字
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 民族
     */
    private String nation;
    /**
     * 居住地
     */
    private String domicile;
    /**
     * 颁发机构
     */
    private String signOrg;
    /**
     * 时间戳
     */
    private Date st;

}