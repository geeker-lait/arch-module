package org.arch.form.crud.entity;

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
 * 表单布局(FormLayout) 实体类
 *
 * @author lait
 * @date 2021-02-10 15:40:04
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("form_layout")
public class FormLayout extends CrudEntity<FormLayout> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 表ID
     */
    private Long tableId;
    /**
     * 布局名称
     */
    private String layoutName;
    /**
     * 布局码
     */
    private String layoutCode;
    /**
     * 布局css样式
     */
    private String layoutStyle;
    /**
     * 描述
     */
    private String descr;
    /**
     * 版本
     */
    private Integer ver;
    /**
     * 是否逻辑删除
     */
    private Boolean deleted;
    /**
     * 时间戳
     */
    private LocalDateTime dt;


    /**
     * 租户ID saas
     */
    private Integer tenantId;

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
