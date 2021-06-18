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
 * 表单字段选项(FormFieldOption) 实体类
 *
 * @author lait
 * @date 2021-02-10 15:39:58
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("form_field_option")
public class FormFieldOption extends CrudEntity<FormFieldOption> {
    private static final long serialVersionUID = 1L;

    /**
     * 字段id
     */
    @TableId("id")
    private Long id;

    /**
     * 表单Id
     */
    private Long fieldId;
    /**
     * 选项id
     */
    private String optionName;
    /**
     * 选项名
     */
    private String optionCode;
    /**
     * 选项值
     */
    private String optionVal;
    /**
     * 逻辑删除
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
