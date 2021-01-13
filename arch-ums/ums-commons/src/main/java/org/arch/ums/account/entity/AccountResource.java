package org.arch.ums.account.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 账号-资源(account_resource)实体类
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_resource")
public class AccountResource extends Model<AccountResource> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 账号-资源表ID
     */
    @TableId
	private Long id;
    /**
     * 账号-资源类目ID
     */
    private Long categoryId;
    /**
     * 资源名
     */
    private String resourceName;
    /**
     * 资源码
     */
    private String resourceCode;
    /**
     * 类型: 1目录, 2菜单, 3按钮, 4链接
     */
    private Integer resourceTyp;
    /**
     * 资源值
     */
    private String resourceVal;
    /**
     * 资源路径
     */
    private String resourcePath;
    /**
     * 资源图标
     */
    private String resourceIcon;
    /**
     * 资源描述
     */
    private String resourceDesc;
    /**
     * 是否隐藏: 0不隐藏, 1隐藏. 默认: 0
     */
    private Integer visible;
    /**
     * 层级
     */
    private Integer level;

}