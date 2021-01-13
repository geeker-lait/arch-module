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
 * 用户工作信息(user_job)实体类
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user_job")
public class UserJob extends Model<UserJob> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户工作信息表ID
     */
    @TableId
	private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 公司行业
     */
    private String groupChannel;
    /**
     * 公司名称
     */
    private String groupName;
    /**
     * 职位名称
     */
    private String postName;
    /**
     * 职级
     */
    private String postLevel;
    /**
     * 顺序
     */
    private Integer sorted;
    /**
     * 入职时间
     */
    private Date hiredateTime;
    /**
     * 离职时间
     */
    private Date dimissionTime;
    /**
     * 时间戳
     */
    private Date st;

}