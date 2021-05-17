package org.arch.alarm.core.engine.function.general;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorNumber;
import com.googlecode.aviator.runtime.type.AviatorObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @description: 根据出生年月日计算年龄,出生年月日类型为字符串
 * @Date : 2019/6/6 11:32 AM
 * @Author : lait.zhang@gmail.com
 */
public class Age extends AbstractFunction {

    private static final String YYYYMMDD = "yyyyMMdd";
    private static final Logger logger = LoggerFactory.getLogger(Age.class);

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1) {
        String birth = FunctionUtils.getStringValue(arg1, env);
        SimpleDateFormat dateFormat = new SimpleDateFormat(YYYYMMDD);
        Date targetDate;
        int age = 0;
        try {
            targetDate = dateFormat.parse(birth);
            Calendar cal = Calendar.getInstance();
            //出生日期晚于当前时间，无法计算
            if (cal.before(targetDate)) {
                throw new IllegalArgumentException(
                        "The birthDay is before Tdif.It's unbelievable!");
            }
            //当前年份
            int yearNow = cal.get(Calendar.YEAR);
            //当前月份
            int monthNow = cal.get(Calendar.MONTH);
            //当前日期
            int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
            cal.setTime(targetDate);
            int yearBirth = cal.get(Calendar.YEAR);
            int monthBirth = cal.get(Calendar.MONTH);
            int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
            //计算整岁数
            age = yearNow - yearBirth;
            if (monthNow <= monthBirth) {
                if (monthNow == monthBirth) {
                    if (dayOfMonthNow < dayOfMonthBirth) {
                        //当前日期在生日之前，年龄减一
                        age--;
                    }
                } else {
                    //当前月份在生日之前，年龄减一
                    age--;
                }
            }
        } catch (ParseException e) {
            logger.error("计算年龄异常，birth={}", birth, e);
        }
        logger.info("计算年龄-- birth={}, age={}", birth, age);
        return AviatorNumber.valueOf(age);
    }

    @Override
    public String getName() {
        return "age";
    }
}
