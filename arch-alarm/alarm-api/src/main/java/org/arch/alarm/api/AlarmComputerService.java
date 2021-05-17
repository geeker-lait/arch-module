package org.arch.alarm.api;

/**
 * 预警规则计算bean 预警规则计算bean，spring启动时会注册 服务类接口
 *
 * @author lait.zhang@gmail.com
 * @date 2021-05-03 9:53 PM
 */
public interface AlarmComputerService extends Convertable {
    /**
     * 注册计算器
     */
    void registComputer();
}
