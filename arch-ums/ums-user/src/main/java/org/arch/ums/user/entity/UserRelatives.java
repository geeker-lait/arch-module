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
 * 用户亲朋信息(user_relatives)实体类
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user_relatives")
public class UserRelatives extends Model<UserRelatives> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户亲朋信息表ID
     */
    @TableId
	private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 亲朋类型: 1. 家属, 2. 朋友
     */
    private Integer relativesTyp;
    /**
     * 亲朋名称: 哥哥, 妹妹, 父亲, 母亲, 弟弟, 朋友, 同学
     */
    private String relativesName;
    /**
     * 亲朋性别: 0男, 1女
     */
    private Integer relativesSex;
    /**
     * 顺序
     */
    private Integer sorted;
    /**
     * 颁发时间
     */
    private Date awardtime;
    /**
     * 时间戳
     */
    private Date st;

}