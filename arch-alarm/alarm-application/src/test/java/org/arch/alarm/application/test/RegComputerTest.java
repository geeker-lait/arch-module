package org.arch.alarm.application.test;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import lombok.extern.slf4j.Slf4j;
import org.arch.alarm.api.utils.ColumnTypeUtils;
import org.arch.alarm.core.engine.ExpressionContext;
import org.arch.alarm.core.engine.ExpressionExecutor;
import org.arch.alarm.core.engine.function.config.PullAndCompare;
import org.arch.alarm.core.engine.function.datetime.Tdif;
import org.arch.alarm.mvc.entity.AlarmParamsEntity;
import org.arch.alarm.mvc.entity.AlarmRegEntity;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/29/2021 11:29 AM
 */
@Slf4j
public class RegComputerTest {

    @Test
    public void expression() {
        AlarmRegEntity alarmRegEntity = AlarmRegEntity.builder().expression("").build();
        // datetime/picklist_db.t_trade_picklist_order.finish_pick_time/结束时间
        AlarmParamsEntity finish_pick_time = AlarmParamsEntity.builder().filedCode("finish_pick_time").build();
        //datetime/picklist_db.t_trade_picklist_order.start_pick_time/开始时间
        AlarmParamsEntity start_pick_time = AlarmParamsEntity.builder().filedCode("start_pick_time").build();

        Map<String, Object> env = new HashMap<String, Object>() {{
            put("pickFinishTime", finish_pick_time);
            put("pickStartTime", start_pick_time);
        }};

        Object value = ExpressionExecutor.execute(ExpressionContext.builder().env(env).expression("pickStartTime.filedCode").build());
        System.out.println(value);


    }

    @Test
    public void compute() {

        AlarmRegEntity alarmRegEntity = AlarmRegEntity.builder().regVal("10").build();
        // datetime/picklist_db.t_trade_picklist_order.finish_pick_time/结束时间
        AlarmParamsEntity finish_pick_time = AlarmParamsEntity.builder().filedCode("finish_pick_time").build();
        //datetime/picklist_db.t_trade_picklist_order.start_pick_time/开始时间
        AlarmParamsEntity start_pick_time = AlarmParamsEntity.builder().filedCode("start_pick_time").build();

        // AlarmReg表中读取
        int defaultVal = Integer.valueOf(alarmRegEntity.getRegVal());

        Map<String, Object> env = new HashMap<String, Object>() {{
            put(finish_pick_time.getFiledCode(), 100);
            put(start_pick_time.getFiledCode(), 40);
            put("defaultVal", defaultVal);
        }};

        String expression = finish_pick_time.getFiledCode() + "-" + start_pick_time.getFiledCode() + " > 10";
        log.info("=========expression: {}", expression);
        Expression exp = AviatorEvaluator.compile(expression);
        Boolean bool = (Boolean) exp.execute(env);


//        ExpressionContext aviatorContext = ExpressionContext.builder().env(env).expression(expression).build();
//        Object value = ExpressionExecutor.execute(aviatorContext);
        log.info("=========result: {}", bool);

    }


    /**
     * 测试门店作弊
     */
    @Test
    public void computMendianzuobi() {
        Map<String, Object> env = new HashMap<>();
        String exps = "ofs_delivery_center_t_delivery_order_status == 90 " +
                "&& " +
                "(ofs_delivery_center_t_delivery_order_end_time - ofs_delivery_center_t_delivery_order_dispatch_time < 2 " +
                "|| ofs_delivery_center_t_delivery_order_end_time - ofs_delivery_center_t_delivery_order_pickup_time < 2) " +
                "&& " +
                "((ofs_delivery_center_t_delivery_exception_order_delivery_order_id ==  ofs_delivery_center_t_delivery_order_id) " +
                "&& " +
                "(ofs_delivery_center_t_delivery_order_dispatch_time < 2 || ofs_delivery_center_t_delivery_order_end_time - ofs_delivery_center_t_delivery_order_pickup_time < 2)) " +
                "&& " +
                "ofs_delivery_center_t_delivery_exception_order_delivery == 1;";
        getKeys(exps).forEach(e -> {
            System.out.println(e);
            if (e.equals("ofs_delivery_center_t_delivery_order_status")) {
                env.put(e, 90);
            } else if (e.equals("ofs_delivery_center_t_delivery_order_end_time")) {
                env.put(e, ColumnTypeUtils.convert(ColumnTypeUtils.DATETIME.getType(),
                        LocalDateTime.of(2021, 5, 10, 10, 24, 10, 1000).toString()));
            } else if (e.equals("ofs_delivery_center_t_delivery_order_pickup_time")) {
                env.put(e, ColumnTypeUtils.convert(ColumnTypeUtils.DATETIME.getType(),
                        LocalDateTime.of(2021, 5, 10, 10, 24, 10, 1000).toString()));
            } else if (e.equals("ofs_delivery_center_t_delivery_order_dispatch_time")) {
                env.put(e, ColumnTypeUtils.convert(ColumnTypeUtils.DATETIME.getType(),
                        LocalDateTime.of(2021, 5, 10, 10, 24, 10, 1000).toString()));
            } else if (e.equals("ofs_delivery_center_t_delivery_exception_order_delivery_order_id")) {
                env.put(e, 100);
            } else if (e.equals("ofs_delivery_center_t_delivery_order_id")) {
                env.put(e, 100);
            } else if (e.equals("ofs_delivery_center_t_delivery_exception_order_delivery")) {
                env.put(e, 1);
            }
        });

        log.info("=========expression: {}", exps);
        Expression exp = AviatorEvaluator.compile(exps);
        System.out.println("comput result " + exp.execute(env));
    }


    /**
     * 派单超时
     */
    @Test
    public void computPaidanchaoshi() {
        Map<String, Object> env = new HashMap<>();
        String exps = "ofs_shop_center_shop_config_info_name == 'delivery' " +
                "&& " +
                "(ofs_shop_center_shop_config_info_value == 1 || ofs_shop_center_shop_config_info_value == 3 ||ofs_shop_center_shop_config_info_value == 4)  " +
                "&& " +
                "TDIF('now',ofs_delivery_center_t_delivery_order_delivery_releasable_time,'m') > 10 " +
                "&& " +
                "TDIF(ofs_delivery_center_t_delivery_order_delivery_deadline_time,'now','m') < 15 " +
                "&& " +
                "ofs_delivery_center_t_delivery_order_status == 10";
        log.info("=========expression: {}", exps);
        getKeys(exps).forEach(e -> {
            System.out.println(e);
            if (e.equals("ofs_shop_center_shop_config_info_name")) {
                env.put(e, "delivery");
            } else if (e.equals("ofs_shop_center_shop_config_info_value")) {
                env.put(e, 1);
            } else if (e.equals("ofs_delivery_center_t_delivery_order_delivery_releasable_time")) {
                env.put(e, ColumnTypeUtils.convert(ColumnTypeUtils.DATETIME.getType(),
                        LocalDateTime.of(2021, 5, 9, 10, 24).toString()));
            } else if (e.equals("ofs_delivery_center_t_delivery_order_delivery_deadline_time")) {
                env.put(e, ColumnTypeUtils.convert(ColumnTypeUtils.DATETIME.getType(),
                        LocalDateTime.of(2021, 5, 10, 10, 23).toString()));
            } else if (e.equals("ofs_delivery_center_t_delivery_order_status")) {
                env.put(e, 10);
            }

        });
        env.put("a", 10);
        AviatorEvaluator.addFunction(new Tdif());

        log.info("ofs_shop_center_shop_config_info_name ========= " + AviatorEvaluator.execute("ofs_shop_center_shop_config_info_name == 'delivery'", env));
        log.info("ofs_shop_center_shop_config_info_value ========= " + AviatorEvaluator.execute("ofs_shop_center_shop_config_info_value == 1 || ofs_shop_center_shop_config_info_value == 3 ||ofs_shop_center_shop_config_info_value == 4", env));
        log.info("ofs_delivery_center_t_delivery_order_delivery_releasable_time ========= " + AviatorEvaluator.execute("ofs_delivery_center_t_delivery_order_delivery_releasable_time", env));
        log.info("ofs_delivery_center_t_delivery_order_delivery_deadline_time ========= " + AviatorEvaluator.execute("ofs_delivery_center_t_delivery_order_delivery_deadline_time", env));
        log.info("now time =========" + AviatorEvaluator.execute("now()"));
        log.info("now gt release time =========" + AviatorEvaluator.execute("TDIF('now',ofs_delivery_center_t_delivery_order_delivery_releasable_time,'m')", env));
        log.info("now lt deadline time =========" + AviatorEvaluator.execute("TDIF(ofs_delivery_center_t_delivery_order_delivery_deadline_time,'now','m')", env));

        Expression exp = AviatorEvaluator.compile(exps);
        log.info("=========comput result " + exp.execute(env));
    }


    @Test
    public void computTiqianbaodan() {
        Map<String, Object> env = new HashMap<>();
        String exps = "TDIF(picklist_db_t_trade_picklist_order_finish_pick_time,picklist_db_t_trade_picklist_order_start_pick_time,'m') < 20";
        String exps1 = "picklist_db_t_trade_picklist_order_finish_pick_time - picklist_db_t_trade_picklist_order_start_pick_time < 20";
        getKeys(exps).forEach(e -> {
            System.out.println(e);
            if (e.equals("picklist_db_t_trade_picklist_order_finish_pick_time")) {
                env.put(e, 1620394698000L);
            } else if (e.equals("picklist_db_t_trade_picklist_order_start_pick_time")) {
                env.put(e, 1620393914000L);
            }
        });
        AviatorEvaluator.addFunction(new Tdif());
        Expression exp = AviatorEvaluator.compile(exps);
        log.info("=========comput result " + exp.execute(env));


        //System.out.println(1620617118000L - 1620393914000L);
        //System.out.println(1620393914000L - 1620617118000L);
        //picklist_db_t_trade_picklist_order_finish_pick_time -> {Long@13175} 1620617118000
        //picklist_db_t_trade_picklist_order_start_pick_time -> {Long@13173} 1620393914000
    }


    @Test
    public void testPaidanchaoshi() {

        AviatorEvaluator.addFunction(new PullAndCompare());
        Map<String, Object> env = new HashMap<>();
        env.put("shopId", "9D13");
        env.put("storeNo", "1234");

        String exps = "PAC('http://172.16.15.45:11740/shopconf/listByShopId','shopId=${shopId}','delivery=1|2|3') &&" +
                "TDIF('now',ofs_delivery_center_t_delivery_order_delivery_releasable_time,'m') < 10 &&" +
                "TDIF(ofs_delivery_center_t_delivery_order_delivery_deadline_time,'now','m') < 15 &&" +
                "ofs_delivery_center_t_delivery_order_status == 10";
        //StrSubstitutor strSubstitutor = new StrSubstitutor(env);
        //exps = strSubstitutor.replace(exps);
        log.info("=========expression: {}", exps);

        Expression exp = AviatorEvaluator.compile(exps);
        log.info("=========comput result " + exp.execute(env));
    }

    @Test
    public void testConfigCenter() {
        RestTemplate restTemplate = new RestTemplate();
        //创建HttpHeaders对象。
        HttpHeaders httpHeadersObj = new HttpHeaders();
        //设置内容类型
        httpHeadersObj.setContentType(MediaType.APPLICATION_JSON);
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("shopId", "9L03");
        //HttpEntity<MultiValueMap> param = new HttpEntity(parameters, httpHeadersObj);
        Object result = restTemplate.postForObject("http://172.16.15.45:11740/shopconf/listByShopId", parameters, Object.class);

        log.info("========= result " + result);
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


}
