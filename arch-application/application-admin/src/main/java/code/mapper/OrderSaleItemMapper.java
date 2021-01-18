package code.mapper;

import code.entity.OrderSaleItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单-销售订单项(order_sale_item)数据Mapper
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
*/
@Mapper
public interface OrderSaleItemMapper extends BaseMapper<OrderSaleItem> {

}
