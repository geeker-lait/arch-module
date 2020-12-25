package com.uni.skit.user.biz;

import com.uni.skit.user.vo.ApiBaseResult;

/**
 * Description: 银行卡业务
 *
 * @author kenzhao
 * @date 2020/4/14 17:38
 */
public interface IBankCardBiz {
    /**
     * 查询银行卡列表
     * @return
     */
    ApiBaseResult getBankCards();
    /**
     * 查询银行卡列表
     * @return
     */
    ApiBaseResult delBankCard(String bankcard);
}