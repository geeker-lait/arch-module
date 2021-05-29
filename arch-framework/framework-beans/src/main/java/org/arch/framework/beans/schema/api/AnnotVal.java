package org.arch.framework.beans.schema.api;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 注解中的键值对
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.4.28 22:43
 */
@Data
@Accessors(chain = true)
public class AnnotVal {
    private String key;
    private String value;
}
