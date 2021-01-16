package org.arch.payment.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.arch.payment.core.entity.PayAppMerchantChannel;
import org.arch.payment.core.mapper.PayAppMerchantChannelMapper;
import org.arch.payment.core.service.IPayAppMerchantChannelService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 */
@Service
public class PayAppMerchantChannelServiceImpl extends ServiceImpl<PayAppMerchantChannelMapper, PayAppMerchantChannel> implements IPayAppMerchantChannelService {
    @Override
    public List<PayAppMerchantChannel> getSortedAppMerchantChannelsByAppId(String appId) {
        return null;
    }
//
//    /**
//     * 根据appId 获取商户应用通道集合，并按权重从高到低排序
//     * @param appId
//     * @return
//     */
//    @Cacheable(value = "uni-payment", key = "'AppMerchantChannelService#getSortedAppMerchantChannelsByAppId#'+#appId")
//    public List<PayAppMerchantChannel> getSortedAppMerchantChannelsByAppId(String appId) {
//        PayAppMerchantChannel appMerchantChannel = new PayAppMerchantChannel();
//        appMerchantChannel.setAppId(appId);
//        QueryWrapper<PayAppMerchantChannel> queryWrapper = new QueryWrapper();
//        queryWrapper.lambda().eq(PayAppMerchantChannel::getAppId, appId).orderByDesc(PayAppMerchantChannel::getWeight);
//        List<PayAppMerchantChannel> merchantAppChannelList = list(queryWrapper);
//        return merchantAppChannelList;
//    }
}
