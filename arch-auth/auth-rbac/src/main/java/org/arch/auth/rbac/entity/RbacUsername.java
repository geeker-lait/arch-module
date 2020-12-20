package org.arch.auth.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 12/11/2020 4:11 PM
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("rbac_username")
public class RbacUsername extends CrudEntity<RbacUsername> {

    /**
     * ID
     */
    @TableId
    private Long id;

    private Long uid;
    private String username;

    private String nick;
    private String icon;
    private String source;

}
