package org.arch.alarm.biz.converter;

import lombok.extern.slf4j.Slf4j;
import org.arch.alarm.api.pojo.biz.DeliveryOrderRegData;
import org.arch.alarm.api.pojo.canal.CanalRowData;
import org.arch.alarm.biz.config.properties.ColumnProperties;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/28/2021 12:31 PM
 */
@Slf4j
//@Component
public class DeliveryOrderDataConverter extends AbstractOfsCanalRowConverter<DeliveryOrderRegData> {

    @Override
    public List<DeliveryOrderRegData> convertRowData(String dbtb, List<ColumnProperties> columnPropertiesList, CanalRowData rowdata) {
        return null;
    }
}
