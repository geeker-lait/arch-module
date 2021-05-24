package org.arch.alarm.biz.listener;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.arch.alarm.api.AlarmParamsService;
import org.arch.alarm.api.AlarmRegService;
import org.arch.alarm.api.dto.AlarmParamsDto;
import org.arch.alarm.api.dto.AlarmRegDto;
import org.arch.alarm.api.enums.ComputTable;
import org.arch.alarm.api.pojo.AlarmExpData;
import org.arch.alarm.api.pojo.biz.AlarmRegData;
import org.arch.alarm.api.pojo.canal.CanalColumn;
import org.arch.alarm.api.pojo.canal.CanalMessageData;
import org.arch.alarm.api.pojo.canal.CanalRowData;
import org.arch.alarm.api.utils.ColumnTypeUtils;
import org.arch.alarm.biz.AlarmDataService;
import org.arch.alarm.biz.CanalRowComputable;
import org.arch.alarm.biz.CanalRowConvertable;
import org.arch.alarm.biz.CanalRowMonitable;
import org.arch.alarm.biz.config.AlarmConfig;
import org.arch.alarm.biz.config.properties.ColumnProperties;
import org.arch.alarm.biz.config.properties.DatabaseProperties;
import org.arch.alarm.biz.config.properties.TableProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/28/2021 12:52 PM
 */
@Slf4j
public abstract class AbstractOfsCanalRowListener<T extends AlarmRegData> implements CanalRowMonitable, CanalRowConvertable<T>, ApplicationContextAware {
    @Autowired
    protected AlarmConfig alarmConfig;
    @Autowired
    private AlarmParamsService alarmParamsService;
    @Autowired
    private AlarmRegService alarmRegService;
    @Autowired
    private AlarmDataService alarmDataService;
    @Value("${middleware.redis.keyPrefix:ofs-alarm:}")
    protected String redisKeyPrefix;
    protected Map<ComputTable, CanalRowComputable> COMPUTER_MAP = new HashMap<>();


    protected DatabaseProperties getDatabase(String database) {
        Optional<DatabaseProperties> optional = alarmConfig.getDatabases().stream().filter(d -> database.equals(d.getName())).findAny();
        return optional.orElse(null);
    }

    protected List<TableProperties> getTable(String database) {
        DatabaseProperties d = getDatabase(database);
        if (null != d) {
            return d.getTables();
        }
        return new ArrayList<>();
    }

    protected List<ColumnProperties> getColumns(String database, String table) {
        Optional<TableProperties> tablePropertiesOptional = getTable(database).stream().filter(t -> table.equals(t.getName())).findAny();
        if (tablePropertiesOptional.isPresent()) {
            return tablePropertiesOptional.get().getColumns();
        }
        return new ArrayList<>();
    }

    @Override
    public boolean isMonitorTable(String table) {
        Optional<TableProperties> optional = this.getTable(getMonitorDatabase()).stream().filter(t -> table.equals(t.getName())).findAny();
        if (optional.isPresent()) {
            return true;
        }
        return false;
    }


    @Override
    public void monitor(CanalMessageData data) {
        String table = data.getTableName();
        // todo 如果不在要监听的表中直接跳出
        if (!isMonitorTable(table)) {
            log.warn("库表 {} 不在监听列表配置中", data.getDbName() + "_" + data.getTableName());
            return;
        }
        // todo 获取binlog行数据
        List<CanalRowData> rowdatas = data.getRowdataLst();
        // todo 调用alarm-center的rpc服务，根据库表查询预警计算参数
        String dbtb = data.getDbName() + "_" + table;
        List<AlarmParamsDto> alarmParamsDtos = alarmParamsService.getComputerParams(dbtb);
        if (alarmParamsDtos == null || alarmParamsDtos.size() == 0) {
            log.warn("没有配置预警计算参数");
            return;
        }
        // todo 根据配置列表获取监控binlog数据
        List<ColumnProperties> columnProperties = getColumns(this.getMonitorDatabase(), table);
        final List<AlarmRegData> alarmRegDatas = moniterAndConvert(dbtb, columnProperties, rowdatas);
        if (alarmRegDatas.size() == 0) {
            log.warn("监听数据为空,不作任何处理");
            return;
        }
        /**
         * todo
         * 这里可以抽出一个方法，可以定制不同的业务id
         * 提前计数预警参数，通过redis 完成分布式计算，此处只会有一个单号，需要收集多个计算参数
         */
        AlarmRegData alarmRegData = alarmRegDatas.stream().filter(a -> a.getOrderId() != null).findAny().orElse(null);
        if (alarmRegData.getOrderId() == null) {
            log.warn("没有单号无法收集计算参数");
            return;
        }
        /**
         * todo
         * 规则参数中，监听库表所得到的数据，需要按照规则id（regId）分组合并处理 即regId:List<AlarmParamsDto>
         * 一个规则中可能关联到多个库表，一个库表可能关联多个规则
         */
        Map<Long, List<AlarmParamsDto>> alarmParamsGroupByRegIdMap = alarmParamsDtos.stream()
                .collect(Collectors.groupingBy(AlarmParamsDto::getRegId));
        alarmParamsGroupByRegIdMap.forEach((k, v) -> {
            AlarmRegDto alarmRegDto = alarmRegService.getAlarmRegById(k);
            if (alarmRegDto == null) {
                log.info("规则ID为 {} 没有配置预警规则，无法完成计算", k);
                return;
            }
            // 关联订单id key的规则为：订单ID+规则ID，一个订单可能会关联多个规则，需要分别统计计算
            final String redisKey = alarmRegData.getOrderId() + "_" + k;
            final JSONObject jsonObject = new JSONObject();
            // 获取之前暂存的计算数据
            JSONObject jo = alarmDataService.getAlarmOrderId(redisKey);
            if (null != jo && jo.size() > 0) {
                jsonObject.putAll(jo);
            }
            v.forEach(apd -> {
                final String fieldName = apd.getDbtb() + "_" + apd.getFiledCode();
                // 过滤after的数据
                alarmRegDatas.stream().filter(ard -> "after".equals(ard.getDataState())).forEach(ard -> {
                    // 过滤需要的字段
                    ard.getAlarmExpDatas().stream().filter(aed -> fieldName.equals(aed.getEpn())).forEach(aed -> {
                        aed.setIgnore(false);
                        jsonObject.put(aed.getEpn(), aed.getEpv());
                    });
                });
            });
            // 自动续期，可从配置中心获取 这里10天 10*24*60
            Long expiredTime = alarmConfig.getOfsOrderRedisKeyExpiredTime();
            alarmDataService.updateAlarmOrderId(redisKey, jsonObject, expiredTime == null ? 24 * 10 * 60 : expiredTime);
            // 针对每一个规则，都有具体的参数个数，如果计算参数与实际参数数量不匹配，继续收集，这里没有落库，只是借助redis暂存
            if (jsonObject.size() != alarmRegDto.getParamCount()) {
                log.info("规则：{}，的计算参数为 {} 个，表达式为{} 的计算参数不够,无法触发计算，等待其他库表参数值，当前参数为:{}",
                        alarmRegDto.getRegName(), alarmRegDto.getParamCount(), alarmRegDto.getExpression(), jsonObject.toJSONString());
                return;
            } else {
                /**
                 * todo
                 * 只涉及到关联的表及字段才会触发对应的表计算器
                 * markup-server实现的canal表计器算，
                 * 可以对调用alarm-center服务的结果做记录操作，
                 * 以及对计算结果和监控的binlog数据落库或进一步做业务逻辑处理，如（结果和数据的映射关系）
                 * 注意：这里为RPC同步调用（处于对三高问题的考虑，如TPS过高，可以mq，但如果也业务预警实时性要求较高，增加机器，有钱都不是问题）
                 */
                CanalRowComputable canalRowComputable = COMPUTER_MAP.get(ComputTable.valueOf(dbtb.toUpperCase()));
                if (null != canalRowComputable) {
                    canalRowComputable.comput(alarmRegDto, v, alarmRegDatas);
                }
            }
        });
    }

    /**
     * 过记录转换
     *
     * @param dbtb
     * @param columnPropertiesList
     * @param rowdatas
     * @return
     */
    private List<AlarmRegData> moniterAndConvert(String dbtb, List<ColumnProperties> columnPropertiesList, List<CanalRowData> rowdatas) {
        // todo binlog数据转换
        List<AlarmRegData> args = new ArrayList<>();
        rowdatas.forEach(rowdata -> {
            args.addAll(convertRowData(dbtb, columnPropertiesList, rowdata));
        });
        return args;
    }


    /**
     * 先做自定义转换，再做通用转换
     *
     * @param dbtb
     * @param canalColumns
     * @return
     */
    protected T doConvert(String dbtb, List<CanalColumn> canalColumns) {
        /**
         * 模板方法，由子类实现，创建并自定义转换
         * todo 如果有自定义需要转换的值,进行自定义处理
         */
        T alarmRegData = buildAndConvert(canalColumns);
        List<AlarmExpData> alarmExpDatas = new ArrayList<>();
        if (null == canalColumns || canalColumns.size() == 0) {
            return null;
        }
        for (CanalColumn cc : canalColumns) {
            if (StringUtils.isBlank(cc.getValue())) {
                continue;
            }
            // 拼装表达式key
            String epn = dbtb + "_" + cc.getName();
            // 本地验证类型转换为表达式的值，并验证一下
            Object epv = ColumnTypeUtils.convert(cc.getMysqlType(), cc.getValue());
            if (null == epv) {
                continue;
            }
            AlarmExpData alarmExpData = new AlarmExpData();
            BeanUtils.copyProperties(cc, alarmExpData);
            alarmExpData.setEpn(epn);
            alarmExpData.setEpv(epv.toString());
            alarmExpDatas.add(alarmExpData);
            // 抽取通用参数
            if (cc.getJavaName().equalsIgnoreCase("orderId")) {
                alarmRegData.setOrderId(cc.getValue());
            } else if (cc.getJavaName().equalsIgnoreCase("shopId")) {
                alarmRegData.setStoreNo(cc.getValue());
            } else if (cc.getJavaName().equalsIgnoreCase("riderId") ||
                    cc.getJavaName().equalsIgnoreCase("pickerId") ||
                    cc.getJavaName().equalsIgnoreCase("packerId")) {
                alarmRegData.setOperatorId(cc.getValue());
            } else if (cc.getJavaName().equalsIgnoreCase("riderName") ||
                    cc.getJavaName().equalsIgnoreCase("pickerName") ||
                    cc.getJavaName().equalsIgnoreCase("packerName")) {
                alarmRegData.setOperatorName(cc.getValue());
            } else if (cc.getJavaName().equalsIgnoreCase("riderMobile") ||
                    cc.getJavaName().equalsIgnoreCase("pickerMobile") ||
                    cc.getJavaName().equalsIgnoreCase("packerMobile")) {
                alarmRegData.setOperatorMobile(cc.getValue());
            }
        }
        alarmRegData.setAlarmExpDatas(alarmExpDatas);
        return alarmRegData;
    }


    /**
     * 转换row发生改变之后的数据
     * 也可以放到 CanalRowConvertable 对应的接口转换器中实现，这里高类聚
     *
     * @param rowdata
     * @return
     */
    protected T convertAfterData(String dbtb, List<ColumnProperties> columnPropertiesList, CanalRowData rowdata) {
        if (null != rowdata.getAfterColumns()) {
            return (T) doConvert(dbtb, rowdata.getAfterColumns()).setDataState("after").setVersion(System.currentTimeMillis());
        }
        return null;
    }

    /**
     * 转换row发送改变之前的数据
     * 也可以放到 CanalRowConvertable 对应的接口转换器中实现,这里高类聚
     *
     * @param rowdata
     * @return
     */
    protected T convertBeforeData(String dbtb, List<ColumnProperties> columnPropertiesList, CanalRowData rowdata) {
        if (null != rowdata.getBeforeColumns()) {
            return (T) doConvert(dbtb, rowdata.getBeforeColumns()).setDataState("before").setVersion(System.currentTimeMillis());
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (COMPUTER_MAP.size() == 0) {
            applicationContext.getBeansOfType(CanalRowComputable.class).forEach((k, v) -> {
                COMPUTER_MAP.put(v.getComputTable(), v);
            });
        }
    }

    /**
     * 其他业务自定义转换，模板方法，由子类实现
     *
     * @param canalColumn
     * @return
     */
    protected abstract T buildAndConvert(List<CanalColumn> canalColumn);

}
