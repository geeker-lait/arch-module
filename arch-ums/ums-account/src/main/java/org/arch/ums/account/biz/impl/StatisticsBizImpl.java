package org.arch.ums.account.biz.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.uni.common.constant.RedisKeyCode;
import com.uni.common.entity.TokenInfo;
import com.uni.common.utils.GsonUtils;
import com.uni.common.utils.RedisUtils;
import com.uni.common.utils.SecurityUtils;
import com.uni.common.utils.StringUtils;
import org.arch.ums.account.biz.StatisticsBiz;
import org.arch.ums.account.dto.StatisticsDto;
import com.uni.statistics.entity.mybatis.PromotionConfig;
import com.uni.statistics.entity.mybatis.PromotionStatistics;
import com.uni.statistics.service.IPromotionConfigService;
import com.uni.statistics.service.IPromotionStatisticsService;
import com.ytec.yuap.uacs.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class StatisticsBizImpl implements StatisticsBiz {

    @Autowired
    private IPromotionStatisticsService promotionStatisticsService;
    @Autowired
    private IPromotionConfigService promotionConfigService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private Environment env;

    public void saveStatistics(PromotionStatistics promotionStatistics,HttpServletRequest request) {
        String ipStr = IpUtil.getRemoteIP(request);
        Integer statisticsTyp = promotionStatistics.getStatisticsTyp();
        String isExtisIp = redisUtils.getStr(RedisKeyCode.STATISTICS_IP + ipStr+statisticsTyp);
        if (StringUtils.isNotEmpty(isExtisIp)) {
            //ip已记录跳过
            String ipCount = redisUtils.getStr(RedisKeyCode.STATISTICS_IP_COUNT + ipStr + statisticsTyp);
            Integer maxCount = Integer.valueOf(env.getProperty("statistics.ip.maxcount"));
            if (ipCount != null && Integer.valueOf(ipCount) >= maxCount) {
                return;
            }
        }
        promotionStatistics.setIp(ipStr);
        promotionStatistics.setDeviceId(request.getHeader("deviceId"));
        promotionStatistics.setDeviceTyp(request.getHeader("deviceTyp"));
        promotionStatistics.setMac(request.getHeader("mac"));
        String source = promotionStatistics.getSource();
        if(StringUtils.isEmpty(source)){
            source = request.getHeader("source");
        }
        promotionStatistics.setSource(source);

        PromotionConfig query = new PromotionConfig();
        query.setSource(source);
        String productId = promotionStatistics.getProductId();
        if(StringUtils.isNotEmpty(productId)){
            query.setProductId(productId);
        }

        PromotionConfig promotionConfig = promotionConfigService.getOne(new QueryWrapper<>(query));
        if (promotionConfig != null) {
            /**
             * 扣量情况为：当前注册数（扣量后）大于等于推广配置数量 或者 推广扣量百分比已经达到
             */
            //获取当前注册数
            PromotionStatistics ps = new PromotionStatistics();
            ps.setState(1);
            ps.setSource(source);
            ps.setStatisticsTyp(2);
            List<PromotionStatistics> promotionStatisticsList = promotionStatisticsService.list(new QueryWrapper<>(ps));
            if (!CollectionUtils.isEmpty(promotionStatisticsList)) {
                // 扣量后的数量达到推广配置的数量
                if (promotionConfig.getPromotionCount() <= promotionStatisticsList.size()) {
                    promotionStatistics.setState(1);
                }
            }
            // 推广扣量百分比规则扣量
            BigDecimal percent = promotionConfig.getDeductPercent();
            //标尺
            BigDecimal percentScale = promotionConfig.getDeductScale();

            //总量
            Object obj = redisUtils.get(RedisKeyCode.STATISTICS_TOTAL + source);
            BigDecimal total = obj != null ? new BigDecimal(obj.toString()) : BigDecimal.ONE;
            //实扣量
            Object actualDeductionObj = redisUtils.get(RedisKeyCode.STATISTICS_DEDUCTIBLECOUNT + source);
            BigDecimal actualDeduction = actualDeductionObj != null ? new BigDecimal(actualDeductionObj.toString()) : BigDecimal.ZERO;
            //应扣量
            BigDecimal deductibleCount = percent.multiply(total);
            if (actualDeduction.intValue() < deductibleCount.intValue()) {
                //进行扣量
                double randomDouble = RandomUtil.randomDouble(0, 100);
                if (randomDouble < percentScale.doubleValue()) {
                    // 这里根据扣量规则，确定显示状态0：显示(不扣量)，状态1:不显示（扣量），查询的时候根据state 显示给流量提供方
                    promotionStatistics.setState(1);
                    // 扣量推广+1
                    redisUtils.incr(RedisKeyCode.STATISTICS_DEDUCTIBLECOUNT + source,1);
                }
            }

            promotionStatistics.setPromotionCost(promotionConfig.getPromotionCost());
            promotionStatistics.setPromotionTyp(promotionConfig.getPromotionTyp());
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TokenInfo token = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                token = GsonUtils.fromJson(userDetails.getUsername(), TokenInfo.class);
            }
            if (token != null) {
                promotionStatistics.setAccountId(token.getAccountId());
                promotionStatistics.setAccountPhone(token.getAccountName());
            }
        }
        promotionStatistics.setSource(source);
        // 保存
        promotionStatisticsService.save(promotionStatistics);
        redisUtils.set(RedisKeyCode.STATISTICS_IP + ipStr + statisticsTyp,1,24*60*60);
        redisUtils.incr(RedisKeyCode.STATISTICS_IP_COUNT + ipStr + statisticsTyp,1);
        redisUtils.incr(RedisKeyCode.STATISTICS_TOTAL + source,1);
    }


    /**
     *  uv 统计
     */
    @Override
    public void productUvStatistic(StatisticsDto statisticsDto, HttpServletRequest requestStatisticsBiz) {
        PromotionStatistics promotionStatistics = new PromotionStatistics();
        //统计类型1：点击，2：注册，3：下载，4:登陆 5 打开
        promotionStatistics.setStatisticsTyp(1);
        promotionStatistics.setProductId(statisticsDto.getProductId());
        String source =  statisticsDto.getSource();
        if (StringUtils.isEmpty(source)) {
            source = requestStatisticsBiz.getHeader("source");
        }
        promotionStatistics.setSource(statisticsDto.getSource());
        // 推广源+1
        redisUtils.incr(source,1);
        // 产品+1
        redisUtils.incr(promotionStatistics.getProductId()+"",1);
        // 保存
        saveStatistics(promotionStatistics, requestStatisticsBiz);
    }

    @Override
    public void productCpaStatistic(HttpServletRequest requestStatisticsBiz) {
        //统计类型1：点击，2：注册，3：下载，4:登陆 5 打开
        PromotionStatistics promotionStatistics = new PromotionStatistics();
        promotionStatistics.setStatisticsTyp(2);
        TokenInfo tokenInfo = SecurityUtils.getCurrentUser();
        saveStatistics(promotionStatistics, requestStatisticsBiz);
    }

    @Override
    public void productDownloadStatistic(HttpServletRequest requestStatisticsBiz) {
        PromotionStatistics promotionStatistics = new PromotionStatistics();
        //统计类型1：点击，2：注册，3：下载，4:登陆 5 打开
        promotionStatistics.setStatisticsTyp(3);
        // 保存
        TokenInfo token = SecurityUtils.getCurrentUser();
        saveStatistics(promotionStatistics, requestStatisticsBiz);
    }

    @Override
    public void productLoginStatistic(HttpServletRequest requestStatisticsBiz) {
        PromotionStatistics promotionStatistics = new PromotionStatistics();
        //统计类型1：点击，2：注册，3：下载，4:登陆 5 打开
        promotionStatistics.setStatisticsTyp(4);
        // 保存
        TokenInfo token = SecurityUtils.getCurrentUser();
        saveStatistics(promotionStatistics, requestStatisticsBiz);
    }

    @Override
    public void productOpenStatistic(HttpServletRequest requestStatisticsBiz) {
        PromotionStatistics promotionStatistics = new PromotionStatistics();
        //统计类型1：点击，2：注册，3：下载，4:登陆 5 打开
        promotionStatistics.setStatisticsTyp(5);
        // 保存
        saveStatistics(promotionStatistics, requestStatisticsBiz);
    }

}
