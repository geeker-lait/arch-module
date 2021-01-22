package org.arch.payment.core.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.payment.core.dao.PayStatusCodeDao;
import org.arch.payment.core.service.PayStatusCodeService;
import org.springframework.stereotype.Service;

/**
 * 支付-状态码映射服务接口实现
 *
 * @author lait
 * @description PN15855012581
 * @since 2021-01-18 20:28:43
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PayStatusCodeServiceImpl implements PayStatusCodeService {
    private final PayStatusCodeDao payStatusCodeDao;

}
