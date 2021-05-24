package org.arch.alarm.core;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.arch.alarm.api.dto.AlarmParamsDto;
import org.arch.alarm.api.pojo.ComputResult;
import org.arch.alarm.api.pojo.biz.AlarmRegData;
import org.arch.alarm.core.computer.RegComputable;
import org.arch.alarm.core.computer.impl.ExpressionComputer;
import org.arch.alarm.mvc.dao.AlarmRegDao;
import org.arch.alarm.mvc.entity.AlarmRegEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/29/2021 1:08 AM
 */
@Service("regComputerService")
@Slf4j
@AllArgsConstructor
public class ComputService {
    private final ApplicationContext applicationContext;
    private final AlarmRegDao alarmRegDao;

    public ComputResult compute(List<AlarmParamsDto> alarmParamsDtos, List<? extends AlarmRegData> alarmDatas) {
        ComputResult computResult = new ComputResult();
        /**
         * todo 规则参数中，监听库表所得到的数据，可能参与或关联多个计算器，需要合并处理 即regId:List<AlarmParamsDto>
         */
        Map<Long, List<AlarmParamsDto>> alarmParamGroupByRegIdMap = alarmParamsDtos.stream().collect(Collectors.groupingBy(AlarmParamsDto::getRegId));
        /**
         * regid
         * 计算器所需要的参数
         */
        alarmParamGroupByRegIdMap.forEach((regId, alarmParams) -> {
            // 根据计算器regId 查询 预警规则表alarm_reg,获取表达式或计算器id以及规则值
            AlarmRegEntity alarmRegEntity = alarmRegDao.getById(regId);
            String expression = alarmRegEntity.getExpression();
            /**
             * 动态技术处理，如果表达式不为空，调用引擎计算，不支持复杂的逻辑，并且alarmDatas数据都应准备好（参与表达式计算的数据）
             */
            if (StringUtils.isNoneEmpty(expression)) {
                // todo 调用引擎计算
                ExpressionComputer expressionComputer = applicationContext.getBean(ExpressionComputer.class);
                expressionComputer.compute(alarmRegEntity, alarmParams, alarmDatas, computResult);
            } else if (alarmRegEntity.getComputerId() != null) {
                /**
                 * 防止用户错配
                 */
                if (alarmRegEntity.getComputerName().equalsIgnoreCase(ExpressionComputer.class.getSimpleName())) {
                    log.warn("配置错误,当前计算不支持表达式计算器");
                    return;
                }
                /**
                 * 静态计算处理，并且alarmDatas数据可以不准备好，计算的时候直接从数据库查，支持复杂的逻辑处理
                 */
                RegComputable computer = applicationContext.getBean(alarmRegEntity.getComputerName(), RegComputable.class);
                if (null == computer) {
                    log.warn("没有找到配置的计算器,{}", alarmRegEntity.getComputerName());
                    return;
                }
                computer.compute(alarmRegEntity, alarmParams, alarmDatas, computResult);
            } else {
                log.error("没有为规则 {} 配置表达式或计算器", alarmRegEntity.getRegName());
            }
        });
        return computResult;
    }

}


