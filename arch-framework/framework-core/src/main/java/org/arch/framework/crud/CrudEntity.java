package org.arch.framework.crud;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 12/11/2020 4:19 PM
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Accessors(chain = true)
public abstract class CrudEntity<T extends Model<?>> extends Model<T> implements Serializable {
    private static final long serialVersionUID = 3627560317746938797L;

    //    /**
//     * 主键
//     */
//    @TableId
//    protected Long id;
    /**
     * 租户ID saas
     */
//    protected Integer tenantId;
//    /**
//     * 应用ID
//     */
//    protected Integer appId;
//    /**
//     * 店铺ID
//     */
//    protected Integer storeId;
//    /**
//     * 乐观锁
//     */
//    protected Integer rev;
//    /**
//     * 时间戳/创建时间
//     */
//    protected LocalDateTime dt;
//    /**
//     * 是否逻辑删除: 0 未删除, 1 已删除; 默认: 0
//     */
//    protected Boolean deleted = false;

}
