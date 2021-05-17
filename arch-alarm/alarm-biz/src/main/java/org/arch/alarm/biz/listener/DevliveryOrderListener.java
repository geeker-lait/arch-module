package org.arch.alarm.biz.listener;

import lombok.extern.slf4j.Slf4j;
import org.arch.alarm.api.pojo.biz.DeliveryOrderRegData;
import org.arch.alarm.api.pojo.canal.CanalColumn;
import org.arch.alarm.api.pojo.canal.CanalRowData;
import org.arch.alarm.biz.CanalRowMonitable;
import org.arch.alarm.biz.config.properties.ColumnProperties;
import org.arch.alarm.biz.converter.DeliveryOrderDataConverter;
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
public class DevliveryOrderListener extends AbstractOfsCanalRowListener<DeliveryOrderRegData> implements CanalRowMonitable {
    @Autowired(required = false)
    private DeliveryOrderDataConverter deliveryOrderDataConverter;

    @Override
    public String getMonitorDatabase() {
        // 可以从配置中心数据库根据key获取 ，这里走配置文件
        return alarmConfig.getOfsDeliveryCenterDb();
    }


    @Override
    protected DeliveryOrderRegData buildAndConvert(List<CanalColumn> canalColumn) {
        return new DeliveryOrderRegData();
    }


    @Override
    public List<DeliveryOrderRegData> convertRowData(String dbtb, List<ColumnProperties> columnPropertiesList, CanalRowData rowdata) {
        // 这里可以调用外置的的专用转换器,如果有配置改组件，则调用，负责使用内部默认的转换规则
        if (deliveryOrderDataConverter != null) {
            return deliveryOrderDataConverter.convertRowData(dbtb, columnPropertiesList, rowdata);
        }
        DeliveryOrderRegData a = convertAfterData(dbtb, columnPropertiesList, rowdata);
        DeliveryOrderRegData b = convertBeforeData(dbtb, columnPropertiesList, rowdata);
        List<DeliveryOrderRegData> deliveryOrderRegDataList = new ArrayList<>();
        if (null != a) {
            deliveryOrderRegDataList.add(a);
        }
        if (null != b) {
            deliveryOrderRegDataList.add(b);
        }
        return deliveryOrderRegDataList;
    }
}
