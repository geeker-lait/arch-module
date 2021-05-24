package org.arch.alarm.core.computer;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
public interface ComputerRegistable {
    /**
     * 获取bean的名称
     *
     * @return
     */
    String getComputerName();


    /**
     * 刷新规则参数，当数据库有改动时，可以监听binlog 也可以显示的调用
     *
     * @param computerId
     */
    void refreshRegParam(Long computerId);
}
