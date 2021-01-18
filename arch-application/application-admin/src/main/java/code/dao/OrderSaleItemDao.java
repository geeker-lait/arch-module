package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.OrderSaleItem;
import code.mapper.OrderSaleItemMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 订单-销售订单项(order_sale_item)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class OrderSaleItemDao extends ServiceImpl<OrderSaleItemMapper, OrderSaleItem> {

}