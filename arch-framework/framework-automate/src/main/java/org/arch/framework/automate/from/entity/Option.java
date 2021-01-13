package org.arch.framework.automate.from.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Option {
    private Integer OptionId;
    private Integer fieldId;
    private String OptionName;
}
