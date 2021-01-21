package org.arch.framework.automate.from.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 表单字段选项(form_field_option)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("form_field_option")
public class FormFieldOption extends Model<FormFieldOption> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
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
    private Date st;

}
