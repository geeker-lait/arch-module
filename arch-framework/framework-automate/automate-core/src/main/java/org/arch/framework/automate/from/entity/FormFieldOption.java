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
 * 表单字段选项(FormFieldOption) 实体类
 *
 * @author lait
 * @date 2021-02-08 13:25:17
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
     * 主键
     */
    @TableId("id")
    private Long id;

    /**
     * 字段id
     */
    private Long fieldId;

    /**
     * 选项id
     */
    private Long optionId;

    /**
     * 选项名
     */
    private String optionName;

    /**
     * 选项值
     */
    private String optionVal;

    /**
     * 时间戳
     */
    private LocalDateTime st;


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
