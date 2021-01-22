package org.arch.payment.core.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.payment.core.dao.PayConfigDao;
import org.arch.payment.core.service.PayConfigService;
import org.springframework.stereotype.Service;

/**
 * 支付-配置服务接口实现
 *
 * @author lait
 * @description PN15855012581
 * @since 2021-01-18 20:28:43
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PayConfigServiceImpl implements PayConfigService {
    private final PayConfigDao payConfigDao;

}
