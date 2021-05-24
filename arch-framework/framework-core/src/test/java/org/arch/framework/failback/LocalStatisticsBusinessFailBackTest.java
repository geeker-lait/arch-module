package org.arch.framework.failback;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author junboXiang
 * @version V1.0
 * 2021-04-09
 */
@Slf4j
public class LocalStatisticsBusinessFailBackTest {
    private static Map<String, Function<SmsRecord, String>> businessExecuteFunctionList = new HashMap<>();

    static {
        businessExecuteFunctionList.put("ali_sms", smsRecord -> new AliSmsSend().send(smsRecord));
        businessExecuteFunctionList.put("tencent_sms", smsRecord -> new TencentSmsSend().send(smsRecord));
    }

    public static void main(String[] args) throws InterruptedException {
        BusinessFailBack localStatisticsBusinessFailBack = new LocalStatisticsBusinessFailBackImpl();
        BusinessFailBackConfig businessFailBackConfig = new BusinessFailBackConfig();

        BusinessFailBackConfig.Rule rule = new BusinessFailBackConfig.Rule();
        rule.setDuration(10).setFailBackNum(10).setRuleCode(RecordEnum.FAIL.getValue()).setWriteRecordEnum(RecordEnum.FAIL);

        BusinessFailBackConfig.Channel smsAli = new BusinessFailBackConfig.Channel();
        smsAli.setCircuitBreakerTimeSpan(1L)
                .setChannelCode("ali_sms")
                .setRelation(RelationEnum.OR).setRules(Lists.newArrayList(rule));



        BusinessFailBackConfig.Rule tencentRule = new BusinessFailBackConfig.Rule()
                .setDuration(10).setFailBackNum(5).setRuleCode(RecordEnum.FAIL.getValue()).setWriteRecordEnum(RecordEnum.FAIL);

        BusinessFailBackConfig.Channel smsTencent = new BusinessFailBackConfig.Channel();
        smsTencent.setCircuitBreakerTimeSpan(1L)
                .setChannelCode("tencent_sms")
                .setRelation(RelationEnum.OR).setRules(Lists.newArrayList(tencentRule));

        businessFailBackConfig.setAppCode("sms")
        .setTenantId(1L).setChannels(Lists.newArrayList(smsAli, smsTencent));

        for (int i = 0; i < 1000; i++) {
            SmsRecord smsRecord = new SmsRecord();
            smsRecord.setPhone(DateTime.now().toString(DateTimeFormat.forPattern("HH:mm:ss")));

            localStatisticsBusinessFailBack.execute(businessFailBackConfig, "test", smsRecord, businessExecuteFunctionList);
            Thread.sleep(1000);
        }
    }


    @Data
    public static class SmsRecord {
        private String phone;
        private String template;
    }


    public interface SmsSend {
        String send(SmsRecord phone);
    }

    public static class AliSmsSend implements SmsSend {

        @Override
        public String send(SmsRecord phone) {
            if (true) {
                throw new RuntimeException("ali sms执行失败");
            }
            log.info("阿里短信发送成功：{}", phone.getPhone());
            return "ali sms";
        }
    }

    public static class TencentSmsSend implements SmsSend {

        @Override
        public String send(SmsRecord phone) {
            log.info("腾讯短信发送成功：{}", phone.getPhone());
            return "tencent sms";
        }
    }

}
