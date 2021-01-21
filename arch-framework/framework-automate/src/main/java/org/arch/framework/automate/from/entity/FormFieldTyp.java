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
 * 表单字段类型(form_field_typ)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("form_field_typ")
public class FormFieldTyp extends Model<FormFieldTyp> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
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
     * 排序
     */
    private Integer sorted;
    /**
     * 时间戳
     */
    private Date st;

}
