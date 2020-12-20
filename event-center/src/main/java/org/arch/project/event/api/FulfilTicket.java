package org.arch.project.event.api;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * @author lait.zhang@gmail.com
 * @Tel 15801818092
 * @date: 11/16/2020 4:05 PM
 * @description:履约票据
 */
@Data
public class FulfilTicket {
    // 发货人信息
    private ShipperInfo shipperInfo;

    // 商品信息
    private GoodsInfo goodsInfo;

    // 付款信息
    private PaymentInfo paymentInfo;

    // 运送人信息
    private TransporterInfo transporterInfo;

    // 收货人信息
    private ConsigneeInfo consigneeInfo;


    // 是否购买保障服务
    private Boolean protection;
    // 保金
    private BigDecimal cost;
    // 创建履约票据的时间
    private LocalDateTime createTime = LocalDateTime.now();
    // 类型(自营/非自营)
    private Integer fulfilType;
    // 订单的来源（不懂渠道的仓库/门店）
    private String source;

    // 履约id号,回传给业务方，分布式id生成器生成
    private Long fulfilId;
    // 履约阶段
    private FulfilPhase fulfilPhase;
    // 履约批次，如果有填写批次（比如拆成几单，分几次完成），如果没有为0
    private Integer fulfilBatch;




}
