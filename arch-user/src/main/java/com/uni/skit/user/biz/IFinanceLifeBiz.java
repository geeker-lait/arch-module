package com.uni.skit.user.biz;

import com.uni.skit.user.vo.ApiBaseResult;

/**
 * Description: 银行卡业务
 *
 * @author kenzhao
 * @date 2020/4/14 17:38
 */
public interface IFinanceLifeBiz {
    /**
     * 查询金融生活信息
     * @return
     */
    ApiBaseResult getLifes();
    /**
     * 判断用户类型，是否会员
     * @return
     */
    ApiBaseResult getLifeType();
}