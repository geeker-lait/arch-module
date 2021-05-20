package org.arch.framework.automate.generater.service.xmind.api;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * 注解: 实体或字段注解
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.4.28 22:43
 */
@Data
@Accessors(chain = true)
public class Annot {
    private String name;
    private final List<AnnotVal> annotVals = new ArrayList<>();
}
