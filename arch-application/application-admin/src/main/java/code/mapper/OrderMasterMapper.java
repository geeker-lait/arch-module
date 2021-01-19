package code.mapper;

import code.entity.OrderMaster;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单-订单主体(order_master)数据Mapper
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
*/
@Mapper
public interface OrderMasterMapper extends BaseMapper<OrderMaster> {

}
