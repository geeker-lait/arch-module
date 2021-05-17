package org.arch.alarm.biz.listener;

import lombok.extern.slf4j.Slf4j;
import org.arch.alarm.api.pojo.biz.PicklistOrderRegData;
import org.arch.alarm.api.pojo.canal.CanalColumn;
import org.arch.alarm.api.pojo.canal.CanalRowData;
import org.arch.alarm.biz.CanalRowMonitable;
import org.arch.alarm.biz.config.properties.ColumnProperties;
import org.arch.alarm.biz.converter.PicklistOrderDataConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/28/2021 12:50 PM
 */
@Slf4j
@Component
public class PicklistOrderListener extends AbstractOfsCanalRowListener<PicklistOrderRegData> implements CanalRowMonitable {
    @Autowired(required = false)
    private PicklistOrderDataConverter picklistOrderDataConverter;

    @Override
    public String getMonitorDatabase() {
        // 可以从配置中心数据库根据key获取 ，这里走配置文件
        return alarmConfig.getOfsPickCenterDb();
    }

    @Override
    protected PicklistOrderRegData buildAndConvert(List<CanalColumn> canalColumn) {
        return new PicklistOrderRegData();
    }


    @Override
    public List<PicklistOrderRegData> convertRowData(String dbtb, List<ColumnProperties> columnPropertiesList, CanalRowData rowdata) {
        // 这里可以调用外置的的专用转换器,如果有需要其他特殊处理，配置该组件调用自定义的解析器解析，否则使用内部默认的转换规则
        if (picklistOrderDataConverter != null) {
            return picklistOrderDataConverter.convertRowData(dbtb, columnPropertiesList, rowdata);
        }
        List<PicklistOrderRegData> picklistOrderDatas = new ArrayList<>();
        PicklistOrderRegData a = convertAfterData(dbtb, columnPropertiesList, rowdata);
        PicklistOrderRegData b = convertBeforeData(dbtb, columnPropertiesList, rowdata);
        if (null != a) {
            picklistOrderDatas.add(a);
        }
        if (null != b) {
            picklistOrderDatas.add(b);
        }
        return picklistOrderDatas;
    }


}
