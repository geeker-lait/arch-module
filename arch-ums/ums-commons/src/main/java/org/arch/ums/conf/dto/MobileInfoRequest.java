package org.arch.ums.conf.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 手机号归属地信息(MobileInfo) request
 *
 * @author YongWu zheng
 * @date 2021-03-15 21:47:56
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MobileInfoRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 手机号归属地信息id
     */
    private Long id;

    /**
     * 手机前缀(7)
     */
    private Integer prefix;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 运营商
     */
    private String mno;

    /**
     * 是否虚拟号段: 1 是, 0 否, 默认: 0
     */
    private Boolean virtual;

    /**
     * 乐观锁, 默认: 0
     */
    private Integer rev;

    /**
     * 时间戳/创建时间
     */
    private LocalDateTime dt;

    /**
     * 是否逻辑删除: 0 未删除(false), 1 已删除(true); 默认: 0
     */
    private Boolean deleted;

}
