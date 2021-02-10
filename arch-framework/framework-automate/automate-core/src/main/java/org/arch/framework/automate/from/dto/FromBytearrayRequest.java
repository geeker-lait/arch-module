package org.arch.framework.automate.from.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 表单数据(FromBytearray) request
 *
 * @author lait
 * @date 2021-02-10 15:45:43
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FromBytearrayRequest {

    /**
     * id主键
     */
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

}
