package org.arch.payment.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.payment.core.entity.PayMerchantChannel;

/**
 * 支付-商户通道(pay_merchant_channel)数据Mapper
 *
 * @author lait
 * @description PN15855012581
 * @since 2021-01-18 20:28:43
 */
@Mapper
public interface PayMerchantChannelMapper extends BaseMapper<PayMerchantChannel> {

}
