package org.arch.payment.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.arch.payment.core.entity.PayBindedRecord;
import org.arch.payment.core.mapper.PayBindedRecordMapper;
import org.arch.payment.core.service.IPayBindedRecordService;
import org.arch.payment.sdk.PayRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 */
@Service
public class PayBindedRecordServiceImpl extends ServiceImpl<PayBindedRecordMapper, PayBindedRecord> implements IPayBindedRecordService {
    @Override
    public boolean hasBindedCard(PayRequest payRequest) {
        return false;
    }

    @Override
    public List<PayBindedRecord> getBindedCardList(PayRequest payRequest) {
        return null;
    }

//    /**
//     * 是否有绑卡
//     *
//     * @param payRequest
//     * @return
//     */
//    @Override
//    public boolean hasBindedCard(PayRequest payRequest) {
//        PayBindedRecord bindedRecord = new PayBindedRecord();
//        bindedRecord.setAppId(payRequest.getAppId());
//        bindedRecord.setBankcard(payRequest.getBankcard());
//        bindedRecord.setAccountId(payRequest.getAccountId());
//        QueryWrapper<PayBindedRecord> queryWrapper = new QueryWrapper<>(bindedRecord);
//        PayBindedRecord payBindedRecord = getOne(queryWrapper);
//        if (payBindedRecord != null) {
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 获取客户绑卡信息
//     *
//     * @param payRequest
//     * @return
//     */
//    @Override
//    public List<PayBindedRecord> getBindedCardList(PayRequest payRequest) {
//        PayBindedRecord bindedRecord = new PayBindedRecord();
//        bindedRecord.setAppId(payRequest.getAppId());
//        bindedRecord.setAccountId(payRequest.getAccountId());
//        QueryWrapper<PayBindedRecord> queryWrapper = new QueryWrapper<>(bindedRecord);
//        queryWrapper.lambda().orderByDesc(PayBindedRecord::getId);
//        List<PayBindedRecord> payBindedRecordList = list(queryWrapper);
//        if (payBindedRecordList != null && payBindedRecordList.size() > 0) {
//            return payBindedRecordList;
//        }
//        return null;
//    }

}
