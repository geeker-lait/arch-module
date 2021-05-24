package org.arch.alarm.api.pojo.canal;

import lombok.Data;

@Data
public class CanalColumn {
    private String name;
    private String javaName;
    private String value;
    private String mysqlType;

}
