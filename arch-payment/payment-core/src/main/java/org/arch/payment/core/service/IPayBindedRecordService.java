package org.arch.payment.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.arch.payment.core.entity.PayBindedRecord;
import org.arch.payment.sdk.PayRequest;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @since 2020-04-08
 */
public interface IPayBindedRecordService extends IService<PayBindedRecord> {

    /**
     * 是否有绑卡
     *
     * @param payRequest
     * @return
     */
    boolean hasBindedCard(PayRequest payRequest);

    /**
     * 获取客户绑卡信息
     *
     * @param payRequest
     * @return
     */
    List<PayBindedRecord> getBindedCardList(PayRequest payRequest);
}
