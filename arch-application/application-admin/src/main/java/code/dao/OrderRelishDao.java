package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.OrderRelish;
import code.mapper.OrderRelishMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 订单-佐料(order_relish)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class OrderRelishDao extends ServiceImpl<OrderRelishMapper, OrderRelish> {

}