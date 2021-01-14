package org.arch.framework.crud;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 12/11/2020 4:19 PM
 */
public class CrudEntity<T extends Model<?>> extends Model<T> implements Serializable {

    // 主键
    @TableId
    protected Long id;
    // 租户ID saas
    protected Integer tenantId;
    //应用ID
    protected Integer appId;
    //店铺ID
    protected Integer storeId;
    // 乐观锁
    protected Integer rev;
    // 时间戳/创建时间
    protected Timestamp st;
    // 是否逻辑删除，默认不删除
    protected boolean deleted = false;

}