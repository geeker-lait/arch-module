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
 * 表单实例(form_instance)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("form_instance")
public class FormInstance extends Model<FormInstance> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
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
     * 时间戳
     */
    private Date st;

}