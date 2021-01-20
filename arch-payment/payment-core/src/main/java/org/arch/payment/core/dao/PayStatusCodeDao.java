package org.arch.payment.core.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.payment.core.entity.PayStatusCode;
import org.arch.payment.core.mapper.PayStatusCodeMapper;
import org.springframework.stereotype.Repository;

/**
 * 支付-状态码映射(pay_status_code)数据DAO
 *
 * @author lait
 * @description PN15855012581
 * @since 2021-01-18 20:28:43
 */
@Slf4j
@Repository
public class PayStatusCodeDao extends ServiceImpl<PayStatusCodeMapper, PayStatusCode> {

}