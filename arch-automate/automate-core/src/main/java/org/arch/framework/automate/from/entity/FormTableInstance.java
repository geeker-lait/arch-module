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
 * 表单实例(FormTableInstance) 实体类
 *
 * @author lait
 * @date 2021-02-10 15:40:12
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("form_table_instance")
public class FormTableInstance extends CrudEntity<FormTableInstance> {
    private static final long serialVersionUID = 1L;

    /**
     * schema主键id
     */
    @TableId("id")
    private Long id;

    /**
     * 表单ID
     */
    private Long projectId;
    /**
     * 表单名称
     */
    private Long formDefinitionId;
    /**
     * 表单code,用作页面表单
     */
    private Long interfaceIds;
    /**
     * 表单对应的表ID
     */
    private String formName;
    /**
     * 表单对应数据库中生成的table name
     */
    private String formCode;
    /**
     * 表单描述
     */
    private Long formTableId;
    /**
     * 表单名称
     */
    private String formTableName;
    /**
     * 描述
     */
    private String descr;
    /**
     * 逻辑删除
     */
    private Boolean deleted;
    /**
     * 时间戳
     */
    private LocalDateTime dt;

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
