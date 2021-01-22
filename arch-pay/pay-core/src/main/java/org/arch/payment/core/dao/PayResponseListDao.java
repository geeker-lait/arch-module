package org.arch.payment.core.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.payment.core.entity.PayResponseList;
import org.arch.payment.core.mapper.PayResponseListMapper;
import org.springframework.stereotype.Repository;

/**
 * 支付-响应记录(pay_response_list)数据DAO
 *
 * @author lait
 * @description PN15855012581
 * @since 2021-01-18 20:28:43
 */
@Slf4j
@Repository
public class PayResponseListDao extends ServiceImpl<PayResponseListMapper, PayResponseList> {

}
