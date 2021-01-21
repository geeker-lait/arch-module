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
 * 表单布局(form_layout)实体类
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("form_layout")
public class FormLayout extends Model<FormLayout> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
	private Long id;
    /**
     * 表ID
     */
    private Long tableId;
    /**
     * 布局名称
     */
    private String layoutName;
    /**
     * 布局码
     */
    private String layoutCode;
    /**
     * 描述
     */
    private String descr;
    /**
     * 原sourceId
     */
    private Long editorSourceId;
    /**
     * 时间戳
     */
    private Date st;

}
