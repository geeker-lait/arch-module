package org.arch.framework.failback;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author junboXiang
 * @version V1.0
 * 2021-04-07
 */
public enum RelationEnum {

    AND("and", "与操作"),
    OR("or", "或操作")
        ;
    private final String value;
    private final String description;

    RelationEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public static RelationEnum getEnum(String value) {
        List<RelationEnum> relationEnums = Lists.newArrayList(RelationEnum.values());
        for (RelationEnum relationEnum : relationEnums) {
            if (relationEnum.value.equals(value)) {
                return relationEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
