package org.arch.payment.core.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.payment.core.entity.PayChannelBank;
import org.arch.payment.core.mapper.PayChannelBankMapper;
import org.springframework.stereotype.Repository;

/**
 * 支付-应用通道支持银行表(pay_channel_bank)数据DAO
 *
 * @author lait
 * @description PN15855012581
 * @since 2021-01-18 20:28:43
 */
@Slf4j
@Repository
public class PayChannelBankDao extends ServiceImpl<PayChannelBankMapper, PayChannelBank> {

}
