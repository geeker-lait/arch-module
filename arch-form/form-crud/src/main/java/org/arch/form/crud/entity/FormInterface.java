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
 * 表单接口(FormInterface) 实体类
 *
 * @author lait
 * @date 2021-02-10 15:40:02
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("form_interface")
public class FormInterface extends CrudEntity<FormInterface> {
    private static final long serialVersionUID = 1L;

    /**
     * id主键
     */
    @TableId("id")
    private Long id;

    /**
     * 接口名称
     */
    private String formCategory;
    /**
     * 接口
     */
    private String interfaceName;
    /**
     * 接口参数
     */
    private String interfaceCode;
    /**
     * 接口uri
     */
    private String interfaceUri;
    /**
     * json 参数
     */
    private String paramJson;
    /**
     * 接口描述
     */
    private String descr;
    /**
     * 是否逻辑删除
     */
    private Boolean deleted;
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
