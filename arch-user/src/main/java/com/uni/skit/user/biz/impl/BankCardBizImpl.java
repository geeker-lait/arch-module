package com.uni.skit.user.biz.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.uni.common.entity.TokenInfo;
import com.uni.common.utils.SecurityUtils;
import com.uni.pay.entity.mybatis.PayBindedRecord;
import com.uni.pay.service.IPayBindedRecordService;
import com.uni.skit.user.biz.IBankCardBiz;
import com.uni.skit.user.vo.ApiBaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description: 订单相关业务
 *
 * @author kenzhao
 * @date 2020/4/14 11:30
 */
@Service
@Slf4j
public class BankCardBizImpl implements IBankCardBiz {
    @Autowired
    private IPayBindedRecordService payBindedRecordService;

    /**
     * 查询银行卡列表
     *
     * @return
     */
    @Override
    public ApiBaseResult getBankCards() {
        TokenInfo tokenInfo = SecurityUtils.getCurrentUser();
        PayBindedRecord payBindedRecord = new PayBindedRecord();
        payBindedRecord.setAppId(tokenInfo.getAppId());
        payBindedRecord.setAccountId(tokenInfo.getAccountId());
        List<PayBindedRecord> payBindedRecordList = payBindedRecordService.list(new QueryWrapper<>(payBindedRecord));
        return ApiBaseResult.success(payBindedRecordList);
    }

    /**
     * 查询银行卡列表
     *
     * @param bankcard
     * @return
     */
    @Override
    public ApiBaseResult delBankCard(String bankcard) {

        TokenInfo tokenInfo = SecurityUtils.getCurrentUser();
        PayBindedRecord payBindedRecord = new PayBindedRecord();
        payBindedRecord.setAppId(tokenInfo.getAppId());
        payBindedRecord.setAccountId(tokenInfo.getAccountId());
        payBindedRecord.setBankcard(bankcard);
        payBindedRecordService.remove(new UpdateWrapper<>(payBindedRecord));
        return ApiBaseResult.success("删除成功");
    }

}