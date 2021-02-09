package org.arch.framework.automate.from.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;

import java.io.Serializable;

/**
 * 表单定义(FormDefinition) 实体类
 *
 * @author lait
 * @date 2021-02-08 13:25:09
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("form_definition")
public class FormDefinition extends CrudEntity<FormDefinition> {
    private static final long serialVersionUID = 1L;
    @TableId
    protected Long id;
    /**
     * 表单分类
     */
    private String category;

    /**
     * 表单名称
     */
    private String formName;

    /**
     * 表单code,用作页面表单
     */
    private String formCode;

    /**
     * 关联数据表
     */
    private String tableName;

    /**
     * 表单部署字段ID
     */
    private Long fieldId;

    /**
     * 表单部署布局ID
     */
    private Long layoutId;

    /**
     * 版本
     */
    private Integer version;

    /**
     * 表单描述
     */
    private String descr;


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
