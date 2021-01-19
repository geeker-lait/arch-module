package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.OrderSwapper;
import code.mapper.OrderSwapperMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 订单-收发货方信息(order_swapper)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class OrderSwapperDao extends ServiceImpl<OrderSwapperMapper, OrderSwapper> {

}