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
 * 业务表单(FormTable) 实体类
 *
 * @author lait
 * @date 2021-02-10 15:40:10
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("form_table")
public class FormTable extends CrudEntity<FormTable> {
    private static final long serialVersionUID = 1L;

    /**
     * id主键
     */
    @TableId("id")
    private Long id;

    /**
     * 项目id
     */
    private Long projectId;
    /**
     * 业务id
     */
    private Long bizId;
    /**
     * 表单实力id
     */
    private Long tableInstanceId;
    /**
     * 是否逻辑删除
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
