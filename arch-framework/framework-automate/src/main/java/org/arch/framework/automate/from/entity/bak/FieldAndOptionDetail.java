package org.arch.framework.automate.from.entity.bak;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 前端用来动态生成表单的类
 */
@Data
@ToString
public class FieldAndOptionDetail extends Field {
    private List<Option> optionList;
}
