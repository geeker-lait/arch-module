package org.arch.automate.form.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;

import java.io.Serializable;

/**
 * 表单schema(FormSchema) 实体类
 *
 * @author lait
 * @date 2021-02-10 15:40:06
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("form_schema")
public class FormSchema extends CrudEntity<FormSchema> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("id")
    private Long id;

    /**
     * 库名称/项目名称
     */
    private String schemaName;
    /**
     * 库名称/项目名称的code
     */
    private String schemaCode;
    /**
     * 描述
     */
    private String descr;

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
