package org.arch.ums.account.biz;

import org.arch.ums.account.vo.ApiBaseResult;

/**
 * Description: 银行卡业务
 *
 * @author kenzhao
 * @date 2020/4/14 17:38
 */
public interface IIndexBiz {
    /**
     * 查询银行卡列表
     * @return
     */
    ApiBaseResult index();
}