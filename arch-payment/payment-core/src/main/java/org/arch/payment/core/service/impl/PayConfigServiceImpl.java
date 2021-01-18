package org.arch.payment.core.service.impl;

import code.dao.PayConfigDao;
import code.service.PayConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
