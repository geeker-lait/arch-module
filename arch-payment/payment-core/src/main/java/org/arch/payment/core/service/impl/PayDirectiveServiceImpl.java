package org.arch.payment.core.service.impl;

import code.dao.PayDirectiveDao;
import code.service.PayDirectiveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 支付-指令集服务接口实现
 *
 * @author lait
 * @description PN15855012581
 * @since 2021-01-18 20:28:43
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PayDirectiveServiceImpl implements PayDirectiveService {
    private final PayDirectiveDao payDirectiveDao;

}
