package org.arch.framework.automate.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 表单数据(FromBytearray) request
 *
 * @author lait
 * @date 2021-02-08 13:25:31
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FromBytearrayRequest {

    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 二进制内容
     */
    private String contentByte;

    /**
     * 时间戳
     */
    private LocalDateTime st;

}
