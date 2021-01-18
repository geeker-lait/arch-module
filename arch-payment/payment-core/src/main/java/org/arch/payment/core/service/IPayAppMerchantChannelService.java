package org.arch.payment.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.arch.payment.core.entity.PayAppMerchantChannel;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @since 2020-04-08
 */
public interface IPayAppMerchantChannelService extends IService<PayAppMerchantChannel> {

    /**
     * 根据appId 获取商户应用通道集合，并按权重从高到低排序
     *
     * @param appId
     * @return
     */
    List<PayAppMerchantChannel> getSortedAppMerchantChannelsByAppId(String appId);
}
