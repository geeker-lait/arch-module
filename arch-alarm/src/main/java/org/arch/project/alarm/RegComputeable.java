package org.arch.project.alarm;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
public interface RegComputeable {
    /**
     * 获取bean的名称
     * @return
     */
    String getRegKey();

    /**
     * 计算表达式
     * @param expression
     * @return
     */
    boolean compute(String expression);

    /**
     * 刷新规则参数，当数据库有改动时，可以监听binlog 也可以显示的调用
     * @param computerId
     */
    void refreshRegParam(Long computerId);
}
