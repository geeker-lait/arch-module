package org.arch.alarm.biz.converter;

import lombok.extern.slf4j.Slf4j;
import org.arch.alarm.api.pojo.biz.PicklistOrderRegData;
import org.arch.alarm.api.pojo.canal.CanalRowData;
import org.arch.alarm.biz.config.properties.ColumnProperties;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: 如果开启则被使用在PicklistOrderListener#convertRowData中，即外部转换器生效
 * @weixin PN15855012581
 * @date 4/28/2021 12:31 PM
 */
@Slf4j
public class PicklistOrderDataConverter extends AbstractOfsCanalRowConverter<PicklistOrderRegData> {


    @Override
    public List<PicklistOrderRegData> convertRowData(String dbtb, List<ColumnProperties> columnPropertiesList, CanalRowData rowdata) {
        return null;
    }
}
