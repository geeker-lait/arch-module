package org.arch.alarm.api.pojo.canal;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
public class CanalMessageData implements Serializable {
    private String dbName;
    private String tableName;
    private String destination;
    private List<CanalRowData> rowdataLst;
    private CanalEventType eventType;
    private String relaData;

}
