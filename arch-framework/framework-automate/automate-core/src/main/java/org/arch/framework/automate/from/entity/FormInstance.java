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
 * 表单实例(FormInstance) 实体类
 *
 * @author lait
 * @date 2021-02-08 13:25:23
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("form_instance")
public class FormInstance extends CrudEntity<FormInstance> {
    private static final long serialVersionUID = 1L;
    @TableId
    protected Long id;

    /**
     * 表单ID
     */
    private Long formId;

    /**
     * 表单名称
     */
    private String formName;

    /**
     * 表单code,用作页面表单
     */
    private String formCode;

    /**
     * 表单对应的表ID
     */
    private Long formTableId;

    /**
     * 表单对应数据库中生成的table name
     */
    private String formTableName;

    /**
     * 表单描述
     */
    private String formDescr;



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
