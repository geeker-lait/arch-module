package com.uni.skit.user.controller;

import com.uni.skit.user.biz.StatisticsBiz;
import com.uni.skit.user.dto.StatisticsDto;
import com.uni.skit.user.vo.ApiBaseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author lait.zhang@gmail.com
 * @Tel 15801818092
 * @Date 11/23/2019
 * @Description ${Description}
 */
@RestController
@RequestMapping("statistics")
public class StatisticsController {


    @Autowired
    private StatisticsBiz statisticsBiz;


    /**
     * 独立IP点击
     *
     * @param statisticsDto
     */
    @PostMapping("uv")
    public ApiBaseResult productUvStatistic(@RequestBody StatisticsDto statisticsDto, HttpServletRequest requestStatisticsBiz) {
        statisticsBiz.productUvStatistic(statisticsDto, requestStatisticsBiz);
        return ApiBaseResult.success();
    }


    /**
     * 注册统计
     *
     * @param requestStatisticsBiz
     */
    @PostMapping("cpa")
    public ApiBaseResult productCpaStatistic(HttpServletRequest requestStatisticsBiz) {
        statisticsBiz.productCpaStatistic(requestStatisticsBiz);
        return ApiBaseResult.success();
    }

    /**
     * 登陆统计
     *
     * @param requestStatisticsBiz
     */
    @PostMapping("login")
    public ApiBaseResult productLoginStatistic(HttpServletRequest requestStatisticsBiz) {
        statisticsBiz.productLoginStatistic(requestStatisticsBiz);
        return ApiBaseResult.success();
    }


    /**
     * 下载统计
     *
     * @param requestStatisticsBiz
     */
    @PostMapping("download")
    public ApiBaseResult productDownloadStatistic(HttpServletRequest requestStatisticsBiz) {
        statisticsBiz.productDownloadStatistic(requestStatisticsBiz);
        return ApiBaseResult.success();
    }

    /**
     * 打开统计
     *
     * @param requestStatisticsBiz
     */
    @PostMapping("open")
    public ApiBaseResult productOpenStatistic(HttpServletRequest requestStatisticsBiz) {
        statisticsBiz.productOpenStatistic(requestStatisticsBiz);
        return ApiBaseResult.success();
    }


}
