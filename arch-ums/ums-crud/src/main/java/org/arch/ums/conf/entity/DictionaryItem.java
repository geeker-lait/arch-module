package org.arch.ums.conf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 数据字典明细(DictionaryItem) 实体类
 *
 * @author YongWu zheng
 * @date 2021-04-17 15:29:48
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("conf_dictionary_item")
public class DictionaryItem extends CrudEntity<DictionaryItem> {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "`id`")
    private Long id;

    /**
     * conf_dictionary id
     */
    @TableField(value = "`dictionary_id`")
    private Long dictionaryId;

    /**
     * 标题
     */
    @TableField(value = "`title`")
    private String title;

    /**
     * 标题对应的值
     */
    @TableField(value = "`val`")
    private String val;

    /**
     * title 顺序
     */
    @TableField(value = "`seq`")
    private Integer seq;

    /**
     * 备注
     */
    @TableField(value = "`mark`")
    private String mark;

    /**
     * 租户 id
     */
    @TableField(value = "`tenant_id`")
    private Integer tenantId;

    /**
     * 应用 id
     */
    @TableField(value = "`app_id`")
    private Integer appId;

    /**
     * 店铺 id
     */
    @TableField(value = "`store_id`")
    private Integer storeId;

    /**
     * 乐观锁, 默认: 0
     */
    @TableField(value = "`rev`")
    private Integer rev;

    /**
     * 时间戳/创建时间
     */
    @TableField(value = "`dt`")
    private LocalDateTime dt;

    /**
     * 是否逻辑删除: 0 未删除(false), 1 已删除(true); 默认: 0
     */
    @TableField(value = "`deleted`")
    private Boolean deleted;


    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return id;
    }
}
