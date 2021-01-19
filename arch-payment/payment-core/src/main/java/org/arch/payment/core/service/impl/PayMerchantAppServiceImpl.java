package org.arch.payment.core.service.impl;

import code.dao.PayMerchantAppDao;
import code.service.PayMerchantAppService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
