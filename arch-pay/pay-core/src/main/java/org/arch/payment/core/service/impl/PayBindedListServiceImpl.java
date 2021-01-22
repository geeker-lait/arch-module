package org.arch.payment.core.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.payment.core.dao.PayBindedListDao;
import org.arch.payment.core.dao.PayChannelBankDao;
import org.arch.payment.core.service.PayBindedListService;
import org.springframework.stereotype.Service;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 1/20/2021 8:52 PM
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PayBindedListServiceImpl implements PayBindedListService {

    private final PayBindedListDao payBindedListDao;
}
