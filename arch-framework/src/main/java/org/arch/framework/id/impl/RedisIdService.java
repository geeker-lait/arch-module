package org.arch.framework.id.impl;

import org.arch.framework.id.IdKey;
import org.arch.framework.id.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 描述: id生产
 *
 * @author liat.zhang@gmail.com
 * @tel 15801818092
 * @create 2017-11-24 16:06
 */
@Service
public class RedisIdService implements IdService {
    private Logger logger = LoggerFactory.getLogger(RedisIdService.class);

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @param idBizCode
     * @return 11 17 354 23 000002
     * @Description 支持一个小时100w个订单号的生成
     * 1 2023410332500000001
     */
    @Override
    public String generateId(IdKey idBizCode) {

        String prefix = getTimePrefix(idBizCode);
        String key = idBizCode.getKey().concat(prefix);
        try {
            long index = redisTemplate.opsForValue().increment(key);
            // 设置一秒后超时，清楚key
            redisTemplate.expire(key, idBizCode.getTimeout(), idBizCode.getTimeUnit());

            // 补位操作 保证满足6位  id = 时间 + 000000
            String id = idBizCode.getBizPrefix().concat(prefix.concat(String.format(idBizCode.getFmtSuffix(), index)));
            return id;
        } catch (Exception ex) {
            logger.error("分布式用户ID生成失败异常: " + ex.getMessage());
            //throw new UnichainException(ExceptionType.BusinessException, "分布式用户ID生成异常");
        }
        return prefix;
    }


    /**
     * 20234000001 01 2023410332500000001
     *
     * @param appId
     * @param idBizCode
     * @return
     */
    @Override
    public String generateId(String appId, IdKey idBizCode) {
        if (appId == null) {
            appId = "";
        }
        String prefix = getTimePrefix(idBizCode);
        String key = idBizCode.getKey().concat(appId).concat(prefix);
        try {
            long index = redisTemplate.opsForValue().increment(key);
            // 设置一个分钟后超时，清楚key
            redisTemplate.expire(key, idBizCode.getTimeout(), idBizCode.getTimeUnit());
            // 补位操作 保证满足6位  id = 时间 + 000000
            String id = appId.concat(idBizCode.getBizPrefix().concat(prefix.concat(String.format(idBizCode.getFmtSuffix(), index))));
            return id;
        } catch (Exception ex) {
            logger.error("分布式用户ID生成失败异常: " + ex.getMessage());
            //throw new UnichainException(ExceptionType.BusinessException, "分布式用户ID生成异常");
        }
        return prefix;
    }


    private String getTimePrefix(IdKey idBizCode) {
        // 20 123 13 24 34
        String prefix = getYDHMPrefix(new Date());
        if (idBizCode.getTimeUnit() == TimeUnit.DAYS) {
            prefix = prefix.substring(0, 5);
        } else if (idBizCode.getTimeUnit() == TimeUnit.HOURS) {
            prefix = prefix.substring(0, 7);
        } else if (idBizCode.getTimeUnit() == TimeUnit.MINUTES) {
            prefix = prefix.substring(0, 9);
        } else if (idBizCode.getTimeUnit() == TimeUnit.SECONDS) {
            prefix = prefix.substring(0, 11);
        }
        return prefix;
    }

    @Override
    public Long randomLongId(int length) {
        return null;
    }

    /**
     * @param digit 位数
     * @return 随机生成digit位数的数字
     */
    public Long randomId(int digit) {

        StringBuilder str = new StringBuilder();
        String prefix = getYDHMPrefix(new Date());
        for (int i = 0; i < digit; i++) {
            if (i == 0 && digit > 1) {
                str.append(new Random().nextInt(9) + 1);
            } else {
                str.append(new Random().nextInt(10));
            }
        }
        return Long.valueOf(prefix.concat(str.toString()));
    }


    /**
     * 获取一年中的第多少天的第多少个小时的第多少分 19年310天20时30分
     *
     * @param date
     * @return
     * @Description
     */
    private static String getYDHMPrefix(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        // 今天是第多少天
        int day = c.get(Calendar.DAY_OF_YEAR);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        // 0补位操作 必须满足三位
        String dayFmt = String.format("%1$03d", day);
        // 0补位操作 必须满足2位
        String hourFmt = String.format("%1$02d", hour);
        // 0补位操作 必须满足2位
        String minuteFmt = String.format("%1$02d", minute);
        //
        String secondFmt = String.format("%1$02d", second);
        StringBuffer prefix = new StringBuffer();
        prefix.append((year - 2000)).append(dayFmt).append(hourFmt).append(minuteFmt).append(secondFmt);
        return prefix.toString();
    }


    //    @Override
//    public Long generateId(Long appId, IdBizCode bizIdCode) {
//        if (appId == null || appId == 0) {
//            logger.error("分布式用户ID生成失败异常: 应用ID不存在");
//            throw new RuntimeException("分布式用户ID生成失败异常: 应用ID不存在");
//        }
//        String appid = String.format("%1$08d", appId);
//        String prefix = getYDHMPrefix(new Date());
//        String key = bizIdCode.getKey().concat(appid).concat(prefix);
//        try {
//            long index = redisRemplate.opsForValue().increment(key);
//            // 设置一个分钟后超时，清楚key
//            redisRemplate.expire(key, 60, TimeUnit.SECONDS);
//            // 补位操作 保证满足6位  id = 时间 + 000000
//            String id = prefix.concat(String.format("%1$06d", index));
//            id = bizIdCode.getBizPrefix().toString().concat(id);
//            return Long.valueOf(id);
//        } catch (Exception ex) {
//            logger.error("分布式用户ID生成失败异常: " + ex.getMessage());
//            throw new RuntimeException(ex);
//        }
//    }


    /*    @Override
    public Long randomId(int length) {
        String prefix = getYDHMPrefix(new Date());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(9);
        }
        Long val = Long.valueOf(stringBuilder.toString());
        Random random = new Random(val);
        Long v = random.nextLong();
        return v;
    }*/
    public static void main(String[] args) {

        System.out.println(new RedisIdService().randomId(10));
        String appid = String.format("%1$08d", 100);
        System.out.println(appid);
//
//        StringBuffer stringBuffer = new StringBuffer();
//        for(int i=0;i<9;i++){
//            stringBuffer.append(9);
//        }
//
//        Long val = Long.valueOf(stringBuffer.toString());
//        System.out.println("============" + val);
//        System.out.println("=====" + stringBuffer);
        //String max = String.format("%1$09d", 9);
        //System.out.println("9".concat(max));
        //System.out.println("11".concat(String.format("%1$06d", 111)));
    }


}
