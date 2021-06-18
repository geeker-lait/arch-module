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
 * 表单字段(FormField) 实体类
 *
 * @author lait
 * @date 2021-02-10 15:39:56
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("form_field")
public class FormField extends CrudEntity<FormField> {
    private static final long serialVersionUID = 1L;

    /**
     * 表单Id
     */
    @TableId("id")
    private Long id;

    /**
     * 字段Id
     */
    private Long formId;
    /**
     * 字段编码，自动生成，对应到数据库中的字段名
     */
    private String fieldCode;
    /**
     * 字段类型，对应到field_type表中的type_code
     */
    private String fieldTyp;
    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 字段描述
     */
    private String descr;
    /**
     * 字段排序
     */
    private Integer sorted;
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
