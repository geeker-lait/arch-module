package org.arch.payment.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.payment.core.entity.PayBindedList;

/**
 * 支付-绑卡记录(pay_binded_list)数据Mapper
 *
 * @author lait
 * @description PN15855012581
 * @since 2021-01-18 20:28:43
 */
@Mapper
public interface PayBindedListMapper extends BaseMapper<PayBindedList> {

}
