package org.arch.payment.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.payment.core.entity.PayMerchantApp;

/**
 * 支付-商户应用(pay_merchant_app)数据Mapper
 *
 * @author lait
 * @description PN15855012581
 * @since 2021-01-18 20:28:43
 */
@Mapper
public interface PayMerchantAppMapper extends BaseMapper<PayMerchantApp> {

}
