package org.arch.alarm.api.pojo;

import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lait.zhang@gmail.com
 * @description: String msg =
 * "履约告警:\n" +
 * "业态：永辉超市\n" +
 * "省份：福建省\n" +
 * "城市：福州\n" +
 * "门店：[\"9701\"]\n" +
 * "设置内容：\n" +
 * "[增加原因：仓内压力, 增加时长:180分钟]\n" +
 * "操作人：40937(江长挺)\n";
 * @weixin PN15855012581
 * @date 5/11/2021 2:06 PM
 */
@Data
public class AlarmMsgData {
    // 预警类目
    private String alarmCatName;
    // 规则名称
    private String regName;
    // 源数据备注
    private String remark;
    // 描述
    private String descr;
    // 预警业态
    private String bizName;
    // 预警省份
    private String province;
    // 市
    private String city;
    // 仓店号
    private String storeNo;
    // 单号
    private String orderId;
    // 单号类型
    private String orderTyp;
    // 操作人id
    private String operatorId;
    // 操作人名称
    private String operatorName;
    // 操作人手机
    private String operatorMobile;

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        try {
            map = BeanUtils.describe(this);
            return map;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return map;
    }
}
