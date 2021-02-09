package org.arch.framework.automate.from.entity;

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
 * @date 2021-02-08 13:25:26
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("form_layout")
public class FormLayout extends CrudEntity<FormLayout> {
    private static final long serialVersionUID = 1L;
    @TableId
    protected Long id;

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
     * 描述
     */
    private String descr;

    /**
     * 原sourceId
     */
    private Long editorSourceId;



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
