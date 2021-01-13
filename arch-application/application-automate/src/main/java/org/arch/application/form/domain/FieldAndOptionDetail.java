package org.arch.application.form.domain;

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
