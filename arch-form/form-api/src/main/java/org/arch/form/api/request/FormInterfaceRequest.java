package org.arch.form.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 表单接口(FormInterface) request
 *
 * @author lait
 * @date 2021-02-10 15:55:47
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormInterfaceRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id主键
     */
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

}
