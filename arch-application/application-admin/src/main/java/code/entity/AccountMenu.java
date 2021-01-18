package code.entity;

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
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_menu")
public class AccountMenu extends Model<AccountMenu> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
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
     * 名称
     */
    private String menuName;
    /**
     * 值
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
     * 是否iframe
     */
    private Integer frame;
    /**
     * 图标
     */
    private String icon;

}