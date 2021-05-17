package org.arch.alarm.api.pojo.canal;

import lombok.Data;

import java.util.List;

@Data
public class CanalRowData {
    private List<CanalColumn> beforeColumns;
    private List<CanalColumn> afterColumns;
}
