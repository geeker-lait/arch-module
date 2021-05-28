package org.arch.automate.form.entity;

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
 * 表单数据(FromBytearray) 实体类
 *
 * @author lait
 * @date 2021-02-10 15:40:14
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("from_bytearray")
public class FromBytearray extends CrudEntity<FromBytearray> {
    private static final long serialVersionUID = 1L;

    /**
     * id主键
     */
    @TableId("id")
    private Long id;

    /**
     * 表单id
     */
    private Long tableId;
    /**
     * 字段Id
     */
    private Long fieldId;
    /**
     * 名称
     */
    private String name;
    /**
     * 二进制内容
     */
    private String contentByte;
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
