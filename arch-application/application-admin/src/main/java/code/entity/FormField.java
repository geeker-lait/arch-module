package code.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 表单字段(form_field)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("form_field")
public class FormField extends Model<FormField> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * 表单Id
     */
    private Long formId;
    /**
     * 字段Id
     */
    private Long fieldId;
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
    private Date st;

}