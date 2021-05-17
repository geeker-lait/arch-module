package org.arch.alarm.biz;

import org.arch.alarm.api.pojo.canal.CanalMessageData;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/28/2021 12:36 PM
 */
public interface CanalRowMonitable {
    /**
     * 获取要监控的库
     *
     * @return
     */
    String getMonitorDatabase();

    /**
     * 判断是否是要监控的表
     *
     * @return
     */
    boolean isMonitorTable(String table);


    /**
     * 监控数据
     *
     * @param data
     */
    void monitor(CanalMessageData data);
}
