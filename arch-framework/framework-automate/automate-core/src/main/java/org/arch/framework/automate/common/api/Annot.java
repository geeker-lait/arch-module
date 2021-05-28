package org.arch.framework.automate.common.api;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

/**
 * 注解: 实体或字段注解
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.4.28 22:43
 */
@Data
@Accessors(chain = true)
public class Annot {
    private String name;
    private final Set<AnnotVal> annotateVals = new HashSet<>();
}
