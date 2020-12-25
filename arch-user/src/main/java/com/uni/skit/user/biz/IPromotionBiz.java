package com.uni.skit.user.biz;

import com.uni.skit.user.dto.PromotionDto;
import com.uni.skit.user.vo.ApiBaseResult;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: 推广信息
 *
 * @author kenzhao
 * @date 2020/4/17 14:59
 */
public interface IPromotionBiz {

    /**
     * 发送短信 提供最新版本的地址
     * @return
     */
    ApiBaseResult sendSms(PromotionDto promotionDto, HttpServletRequest request);
    /**
     * 提供最新版本的地址
     * @return
     */
    ApiBaseResult getAppVersion(PromotionDto promotionDto, HttpServletRequest request);

    /**
     * 获取app内的常用信息
     * @return
     */
    ApiBaseResult getAppInfo(PromotionDto promotionDto, HttpServletRequest request);
}