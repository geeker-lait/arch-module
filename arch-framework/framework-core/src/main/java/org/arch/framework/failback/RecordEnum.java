package org.arch.framework.failback;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 记录日志类型
 * @author junboXiang
 * @version V1.0
 * 2021-04-07
 */
public enum RecordEnum {

    FAIL("fail", "执行失败"),
    FAIL_BACK("fail_back", "降级"),
    SUCCESS("success", "成功")
        ;
    private final String value;
    private final String description;

    RecordEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public static RecordEnum getEnum(String value) {
        List<RecordEnum> relationEnums = Lists.newArrayList(RecordEnum.values());
        for (RecordEnum relationEnum : relationEnums) {
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
