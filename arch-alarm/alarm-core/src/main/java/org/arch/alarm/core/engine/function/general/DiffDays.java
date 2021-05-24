package org.arch.alarm.core.engine.function.general;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.type.AviatorLong;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @description: 两时间相差天数,参数类型为date
 * @Date : 2019/6/5 4:07 PM
 * @Author : lait.zhang@gmail.com
 */
public class DiffDays extends AbstractFunction {

    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
        Date targetDay1 = (Date)arg1.getValue(env);
        Date targetDay2 = (Date)arg2.getValue(env);
        Calendar cal = Calendar.getInstance();
        cal.setTime(targetDay1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(targetDay2);
        long time2 = cal.getTimeInMillis();
        long diffDays = (time1-time2)/(1000*3600*24);
        return AviatorLong.valueOf(diffDays);
    }

    @Override
    public String getName() {
        return "days.diff";
    }
}
