package org.arch.alarm.core.computer.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.arch.alarm.api.dto.AlarmParamsDto;
import org.arch.alarm.api.enums.RegComputer;
import org.arch.alarm.api.pojo.biz.AlarmRegData;
import org.arch.alarm.api.pojo.ComputResult;
import org.arch.alarm.core.computer.RegComputable;
import org.arch.alarm.core.engine.ExpressionContext;
import org.arch.alarm.core.engine.ExpressionExecutor;
import org.arch.alarm.mvc.entity.AlarmComputerEntity;
import org.arch.alarm.mvc.entity.AlarmRegEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lait.zhang@gmail.com
 * @description: 通用型计算器，只支持表达式运算
 * @weixin PN15855012581
 * @date 5/1/2021 11:34 PM
 */
@Service("expressionComputer")
@Slf4j
public class ExpressionComputer extends AbstractComputer implements RegComputable {
    // 自动续期，可从配置中心获取 这里10天 10*24*60
    private static final int EXPIRE_TIME = 10 * 24 * 60;

    @Value("${middleware.redis.keyPrefix:ofs-alarm:}")
    private String redisKeyPrefix;

    @Override
    public void compute(AlarmRegEntity alarmRegEntity, List<AlarmParamsDto> alarmParams, List<? extends AlarmRegData> alarmRegDatas, ComputResult computResult) {
        if (null == alarmRegEntity) {
            log.error("预警规则对象(AlarmRegEntity)为空,无法进行计算,请配置预警规则");
            return;
        }
        // todo 解析规则表达式
        String expression = alarmRegEntity.getExpression();
        if (StringUtils.isBlank(expression)) {
            log.error("ExpressionComputer的计算表达式为空!");
            return;
        }
        Set<String> keys = getKeys(expression);
        // 过滤之后的数据
        AlarmRegData alarmRegData = alarmRegDatas.stream().filter(ard -> ard.getDataState().equals("after") && ard.getOrderId() != null).findAny().orElse(null);
        // 确定订单存在
        if (null == alarmRegData) {
            log.error("ExpressionComputer计算的订单号为空!");
            return;
        }
        // 关联订单id key的规则为 订单id+规则id
        final String redisKey = alarmRegData.getOrderId() + "_" + alarmRegEntity.getId();
        final JSONObject emap = new JSONObject();
        JSONObject jsonObject = get(redisKey);
        if (null != jsonObject) {
            emap.putAll(jsonObject);
        }
        // todo 组装规则参数
        // 参数计数,必须要达到指定的数量才能计算结果
        if (emap.size() == alarmRegEntity.getParamCount()) {
            try {
                emap.putAll(BeanUtils.describe(alarmRegData));
                ExpressionContext expressionContext = ExpressionContext.builder().expression(expression).env(emap).build();
                Object obj = ExpressionExecutor.execute(expressionContext);
                computResult.setResult(obj);
                computResult.setSuccess(true);
                computResult.setRegId(alarmRegEntity.getId());
                // 计算完成删除redis 参数
                remove(redisKey);
            } catch (Exception e) {
                computResult.setSuccess(false);
            }
        } else {
            log.info("ExpressionComputer 表达式计算器开始收集：{} 规则计算参数，该规则共需要 {} 个参数，当前有：{} 个参数，无法进行表达式计算，等待其他参数到达中....", alarmRegEntity.getRegName(), alarmParams.size(), emap.size());
            computResult.setMsg("规则对象为:" + JSONObject.toJSONString(alarmRegEntity) + "需要" + alarmRegEntity.getParamCount() + "个参数 ，当前只有" + emap.size() + "无法完成计算！");
            computResult.setSuccess(false);
            return;
        }
    }

    @Override
    public AlarmComputerEntity buildAlarmComputer() {
        AlarmComputerEntity alarmComputerEntity = AlarmComputerEntity.builder()
                .computerName(RegComputer.EXPRESSION_COMPUTER.getComputerName())
                .computerDescr(RegComputer.EXPRESSION_COMPUTER.getComputerDescr())
                .build();
        return alarmComputerEntity;
    }

    @Override
    public String getComputerName() {
        return RegComputer.EXPRESSION_COMPUTER.getComputerName();
    }


    private Set<String> getKeys(String expression) {
        Pattern p = Pattern.compile("[a-zA-Z]\\w*");//定义一个正则表达式的匹配规则
        Matcher m = p.matcher(expression); //进行匹配
        Set<String> list = new HashSet<>();
        while (m.find()) {// 是否寻到匹配字符
            list.add(m.group());
        }
        return list;
    }


    public static void main(String[] args) {
        String ex = "ofs_shop_center_shop_config_info_name == 'delivery' && (ofs_shop_center_shop_config_info_value == 1 || ofs_shop_center_shop_config_info_value == 3 ||ofs_shop_center_shop_config_info_value == 4)  && currentTime - ofs_delivery_center_t_delivery_order_delivery_releasable_time > 10 && ofs_delivery_center_t_delivery_order_delivery_deadline_time - currentTime < 15 && ofs_delivery_center_t_delivery_order_status == 10";
        String expression = "(delivery1_startime - delivery_endtime1_eee)>0 && ddd == 男";
        String s1 = "picklist_db_t_trade_picklist_order_finish_pick_time - picklist_db_t_trade_picklist_order_start_pick_time < 20";
        ExpressionComputer ec = new ExpressionComputer();
        ec.getKeys(ex).forEach(System.out::println);
    }
}
