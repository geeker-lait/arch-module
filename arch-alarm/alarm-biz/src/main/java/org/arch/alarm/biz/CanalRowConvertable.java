package org.arch.alarm.biz;

import org.arch.alarm.api.pojo.biz.AlarmRegData;
import org.arch.alarm.api.pojo.canal.CanalRowData;
import org.arch.alarm.biz.config.properties.ColumnProperties;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: canal binlog数据转换接口
 * @weixin PN15855012581
 * @date 4/27/2021 10:35 PM
 */
public interface CanalRowConvertable<T extends AlarmRegData> {
    /**
     * binlog数据转换
     *
     * @param dbtb
     * @param columnPropertiesList 列转换配置
     * @param rowdata              binlog 行数据
     * @return
     */
    List<T> convertRowData(String dbtb, List<ColumnProperties> columnPropertiesList, CanalRowData rowdata);
}
