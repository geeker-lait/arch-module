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
 * 项目业务(FormBiz) 实体类
 *
 * @author lait
 * @date 2021-02-10 15:39:53
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("form_biz")
public class FormBiz extends CrudEntity<FormBiz> {
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
     * 业务名称
     */
    private String bizName;
    /**
     * 业务码
     */
    private String bizCode;
    /**
     * 业务说明
     */
    private String descr;
    /**
     * 排序
     */
    private Integer sorted;
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
