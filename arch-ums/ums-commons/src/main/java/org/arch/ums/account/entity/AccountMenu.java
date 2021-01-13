package org.arch.ums.account.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 账号-菜单(account_menu)实体类
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_menu")
public class AccountMenu extends Model<AccountMenu> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 账号-菜单ID
     */
    @TableId
	private Long id;
    /**
     * 父节点ID
     */
    private Long pid;
    /**
     * 英文码
     */
    private String menuCode;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 菜单值
     */
    private String menuVal;
    /**
     * 层级
     */
    private Integer level;
    /**
     * 排序
     */
    private Integer sorted;
    /**
     * 是否iframe: 1是, 0不是, 默认: 1
     */
    private Integer isFrame;
    /**
     * 图标
     */
    private String icon;

}