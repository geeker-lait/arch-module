package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.OrderPayment;
import code.mapper.OrderPaymentMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 订单-支付记录表(order_payment)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class OrderPaymentDao extends ServiceImpl<OrderPaymentMapper, OrderPayment> {

}