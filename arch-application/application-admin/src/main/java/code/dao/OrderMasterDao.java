package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.OrderMaster;
import code.mapper.OrderMasterMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 订单-订单主体(order_master)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class OrderMasterDao extends ServiceImpl<OrderMasterMapper, OrderMaster> {

}