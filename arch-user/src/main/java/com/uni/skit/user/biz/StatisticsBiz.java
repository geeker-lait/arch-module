package com.uni.skit.user.biz;

import com.uni.skit.user.dto.StatisticsDto;

import javax.servlet.http.HttpServletRequest;

public interface StatisticsBiz {

    /**
     * UV统计
     */
    void productUvStatistic(StatisticsDto statisticsDto, HttpServletRequest requestStatisticsBiz);


    /**
     * CPA统计
     */
    void productCpaStatistic(HttpServletRequest requestStatisticsBiz);


    /**
     * DOWNLOAD统计
     */
    void productDownloadStatistic(HttpServletRequest requestStatisticsBiz);


    /**
     * LOGIN统计
     */
    void productLoginStatistic(HttpServletRequest requestStatisticsBiz);

    /**
     * OPEN统计
     */
    void productOpenStatistic(HttpServletRequest requestStatisticsBiz);


}
