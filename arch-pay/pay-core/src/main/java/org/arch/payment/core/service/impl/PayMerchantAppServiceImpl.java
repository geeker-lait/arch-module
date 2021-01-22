package org.arch.payment.core.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.payment.core.dao.PayMerchantAppDao;
import org.arch.payment.core.service.PayMerchantAppService;
import org.springframework.stereotype.Service;

/**
 * 支付-商户应用服务接口实现
 *
 * @author lait
 * @description PN15855012581
 * @since 2021-01-18 20:28:43
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PayMerchantAppServiceImpl implements PayMerchantAppService {
    private final PayMerchantAppDao payMerchantAppDao;

}
