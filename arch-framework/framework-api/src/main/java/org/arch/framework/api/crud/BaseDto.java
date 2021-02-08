package org.arch.framework.api.crud;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础Dto信息<br>
 * 所有的Dto都继承该对象（每个Dto都有相同的字段）<br>
 */
@Data
public abstract class BaseDto implements Serializable {

    /**
     * 实体ID, 主键
     */
    protected Long id;

    /**
     * 租户ID, 主键
     */
    protected Integer tenantId;

    /**
     * 应用ID, 主键
     */
    protected Integer appId;

    /**
     * 实体删除标记，为false表示删除
     */
    protected Boolean deleted;

}
