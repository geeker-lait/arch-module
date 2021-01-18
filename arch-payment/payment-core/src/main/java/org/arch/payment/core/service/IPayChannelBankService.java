package org.arch.payment.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.arch.payment.core.entity.PayChannelBank;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 */
public interface IPayChannelBankService extends IService<PayChannelBank> {

    /**
     * 根据银行卡获取支持通道ID集合，并按权重从高到低排序
     *
     * @param bankCode
     * @return
     */
    List<String> getSortedChannelIdListByBankcard(String bankCode);
}
