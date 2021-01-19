package code.mapper;

import code.entity.OrderInvoice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单-发票(order_invoice)数据Mapper
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
*/
@Mapper
public interface OrderInvoiceMapper extends BaseMapper<OrderInvoice> {

}
