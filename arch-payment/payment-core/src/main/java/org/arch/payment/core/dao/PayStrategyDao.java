package org.arch.payment.core.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.payment.core.entity.PayStrategy;
import org.arch.payment.core.mapper.PayStrategyMapper;
import org.springframework.stereotype.Repository;

/**
 * 支付-应用商户通道策略(pay_strategy)数据DAO
 *
 * @author lait
 * @description PN15855012581
 * @since 2021-01-18 20:28:43
 */
@Slf4j
@Repository
public class PayStrategyDao extends ServiceImpl<PayStrategyMapper, PayStrategy> {

}
