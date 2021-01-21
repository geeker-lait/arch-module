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
 * 表单定义(form_definition)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("form_definition")
public class FormDefinition extends Model<FormDefinition> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * 表单分类
     */
    private String category;
    /**
     * 表单名称
     */
    private String formName;
    /**
     * 表单code,用作页面表单
     */
    private String formCode;
    /**
     * 关联数据表
     */
    private String tableName;
    /**
     * 表单部署字段ID
     */
    private Long fieldId;
    /**
     * 表单部署布局ID
     */
    private Long layoutId;
    /**
     * 版本
     */
    private Integer ver;
    /**
     * 表单描述
     */
    private String descr;
    /**
     * 时间戳
     */
    private Date st;

}
