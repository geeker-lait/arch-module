package org.arch.ums.conf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 手机号段信息(MobileSegment) 实体类
 *
 * @author YongWu zheng
 * @date 2021-03-17 21:27:02
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("conf_mobile_segment")
public class MobileSegment extends Model<MobileSegment> {
    private static final long serialVersionUID = 1L;

    /**
     * 手机号段信息id
     */
    @TableId(value = "`id`")
    private Long id;

    /**
     * 手机前缀(3/4)
     */
    @TableField(value = "`prefix`")
    private Integer prefix;

    /**
     * 运营商
     */
    @TableField(value = "`mno`")
    private String mno;

    /**
     * 是否虚拟号段: 1 是, 0 否, 默认: 0
     */
    @TableField(value = "`virtual`")
    private Boolean virtual;

    /**
     * 乐观锁, 默认: 0
     */
    @TableField(value = "`rev`")
    private Integer rev;

    /**
     * 时间戳/创建时间
     */
    @TableField(value = "`dt`")
    private LocalDateTime dt;

    /**
     * 是否逻辑删除: 0 未删除(false), 1 已删除(true); 默认: 0
     */
    @TableField(value = "`deleted`")
    private Boolean deleted;


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
