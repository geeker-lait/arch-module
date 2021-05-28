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
 * 表单字段类型(FormFieldTyp) 实体类
 *
 * @author lait
 * @date 2021-02-10 15:40:00
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("form_field_typ")
public class FormFieldTyp extends CrudEntity<FormFieldTyp> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("id")
    private Long id;

    /**
     * 类型code：input/checkbox/radio/select/textarea
     */
    private String typCode;
    /**
     * 类型名称
     */
    private String typName;
    /**
     * 描述
     */
    private String descr;
    /**
     * 逻辑删除
     */
    private Boolean deleted;
    /**
     * 排序
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
