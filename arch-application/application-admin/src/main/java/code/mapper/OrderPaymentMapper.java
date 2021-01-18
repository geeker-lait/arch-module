package code.mapper;

import code.entity.OrderPayment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单-支付记录表(order_payment)数据Mapper
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
*/
@Mapper
public interface OrderPaymentMapper extends BaseMapper<OrderPayment> {

}
