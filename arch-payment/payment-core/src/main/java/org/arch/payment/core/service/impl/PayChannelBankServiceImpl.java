package org.arch.payment.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.arch.payment.core.entity.PayChannelBank;
import org.arch.payment.core.mapper.PayChannelBankMapper;
import org.arch.payment.core.service.IPayChannelBankService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 */
@Service
public class PayChannelBankServiceImpl extends ServiceImpl<PayChannelBankMapper, PayChannelBank> implements IPayChannelBankService {
    /**
     * 根据银行卡获取支持通道ID集合，并按权重从高到低排序
     *
     * @param bankCode
     * @return
     */
    @Cacheable(value = "uni-payment", key = "'ChannelBankService#getSortedChannelIdListByBankcard#'+#bankCode")
    @Override
    public List<String> getSortedChannelIdListByBankcard(String bankCode) {
        QueryWrapper<PayChannelBank> queryWrapper = new QueryWrapper();
        // 这张卡有哪些通道支持
        queryWrapper.lambda().select(PayChannelBank::getChannelId).eq(PayChannelBank::getBankCode, bankCode).orderByDesc(PayChannelBank::getWeight);

        List<String> channelIdList = list(queryWrapper).stream().map(PayChannelBank::getChannelId).collect(Collectors.toList());
        return channelIdList;

    }
}
