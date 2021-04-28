package org.arch.framework.automate.xmind.api;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.4.28 22:43
 */
@Data
@Accessors(chain = true)
public class Annotation {
    private String name;
    private String value;
    private String message;
}
