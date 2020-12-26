CREATE TABLE IF NOT EXISTS `stock_sku`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `id` bigint(19) COMMENT '主键,[receiptId,orderId引用],根据类型区分',
    `stock_type` int(2) COMMENT '库存类型(包含：出库[销售出库/仓配出库/...] 入库[采购入库/仓配入库/...]等类型)',
    `stock_biz_type` int(2) COMMENT '库存业务类型',
    `stock_no` varchar(32) COMMENT '出入库单号[入/出库共用该单号]',
    `stock_time` date(255) COMMENT '出入库单时间',
    `stock_post_date` date(255) COMMENT '过账日期',
    `stock_work_date` date(255) COMMENT '作业日期',
    `stock_close_date` date(255) COMMENT '作业日期（关单日期）',
    `sku_code` varchar(32) COMMENT '商品编码',
    `qty` decimal(32) COMMENT '数量',
    `source_no` varchar(32) COMMENT '来源单号',
    `source_code` varchar(32) COMMENT '来源单号编码',
    `ref_no` varchar(32) COMMENT '关联单号',
    `ref_code` varchar(32) COMMENT '关联单号编码',
    `workshop_code` varchar(32) COMMENT '小店编码',
    `workshop_name` varchar(32) COMMENT '小店名称',
    `user_name` varchar(32) COMMENT '用户名称',
    `distribution_address` varchar(32) COMMENT '发货地点',
    `receipt_address` varchar(32) COMMENT '收货地点',
    `is_bt` int(1) COMMENT '是否账面直通 0否 1是',
    `is_process` int(1) COMMENT '是否加工商品 0否 1是',
    `settle_location_is_hc` int(1) COMMENT '结算地点是否上线DC',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存';

CREATE TABLE IF NOT EXISTS `stock_sku_item`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `id` bigint(19) COMMENT '主键,[receiptId,orderId引用],根据类型区分',
    `stock_id` bigint(19) COMMENT '库存单表主键',
    `stock_no` varchar(32) COMMENT '库存单号单号',
    `sku_id` bigint(19) COMMENT '商品表主键',
    `sku_no` varchar(32) COMMENT '行号 原orderDetailNo',
    `sku_code` varchar(33) COMMENT '商品编码',
    `sku_name` varchar(34) COMMENT '商品名称',
    `sku_bar_code` varchar(35) COMMENT '商品69码',
    `sku_date` varchar(36) COMMENT '商品生产日期',
    `sku_spec` varchar(37) COMMENT '规格',
    `sku_price` decimal(18,4) COMMENT '单价',
    `total_price` decimal(18,4) COMMENT '明细单总金额',


    `lot_no` varchar(32) COMMENT '批号',
    `unit_name` varchar(32) COMMENT '单位',
    `tax_rate` varchar(32) COMMENT '税率',
    `rate` varchar(32) COMMENT '商品件装数',


    `is_free` int(1) COMMENT '是否赠品 0->否 1->是',
    `is_clear` int(1) COMMENT '是否清仓(0 否, 1是)',
    `is_process` int(1) COMMENT '是否有加工单明细(0 否, 1是)',
    `is_discount` int(1) COMMENT '是否打折商品,OMS下传标识',
    `is_oos` int(1) COMMENT '是否缺货（0:否,1:是）',
    `is_joint` int(1) COMMENT '是否联运',
    `is_spec_alloc` int(1) COMMENT '是否规格分配',
    `order_detail_status` int(1) COMMENT '0:过账成功,1:过账失败',
    `posting_time` date(255) COMMENT '过账时间',


    `current_qty_before` decimal(18,4) COMMENT '现货库存修改前的值',
    `current_qty_after` decimal(18,4) COMMENT '现货库存改变值 增加为正，减少为负',
    `current_qty_change` decimal(18,4) COMMENT '现货库存修改后的值',
    `usable_qty_before` decimal(18,4) COMMENT '可用库存修改前的值',
    `usable_qty_after` decimal(18,4) COMMENT '可用库存修改后的值',
    `usable_qty_change` decimal(18,4) COMMENT '可用库存改变值 增加为正，减少为负',
    `order_qty_before` decimal(18,4) COMMENT '预占库存修改前的值',
    `order_qty_after` decimal(18,4) COMMENT '预占库存修改后的值',
    `order_qty_change` decimal(18,4) COMMENT '预占库存改变值 增加为正，减少为负',
    `lock_qty_before` decimal(18,4) COMMENT '锁定库存修改前的值',
    `lock_qty_after` decimal(18,4) COMMENT '锁定库存修改后的值',
    `lock_qty_change` decimal(18,4) COMMENT '锁定库存改变值 增加为正，减少为负',
    `transit_qty_before` decimal(18,4) COMMENT '在途库存修改前的值',
    `transit_qty_after` decimal(18,4) COMMENT '在途库存修改后的值',
    `transit_qty_change` decimal(18,4) COMMENT '在途库存改变值 增加为正，减少为负',


    `warehouse_id` bigint(255) COMMENT '仓库表主键',
    `workshop_code` varchar(32) COMMENT '小店编码',
    `workshop_name` varchar(32) COMMENT '小店名称',
    `comments` varchar(32) COMMENT '备注',


    `expected_qty` decimal(18,4) COMMENT '计划数量',
    `received_qty` decimal(18,4) COMMENT '实收数量',
    `qty_ordered` decimal(18,4) COMMENT '数量',
    `real_qty_ordered` decimal(18,4) COMMENT '实际计划数量',
    `qty_outbound` decimal(18,4) COMMENT '出库量',
    `qty_allocated` decimal(18,4) COMMENT '已分配量',
    `qty_remain_ordered` decimal(18,4) COMMENT '剩余需求数量, 即: 数量-已分配数量',
    `qty_picked` decimal(18,4) COMMENT '已拣货量',
    `qty_remain_picked` decimal(18,4) COMMENT '剩余拣货数量, 即: 已分配数量-已拣货数量',
    `qty_checked` decimal(18,4) COMMENT '已复核量',
    `qty_shipped` decimal(18,4) COMMENT '已发货量',
    `now_qty_allocated` decimal(18,4) COMMENT '当前分配数量 - 波次分配使用 - 未持久化',
    `now_qty_picked` decimal(18,4) COMMENT '当前已拣货数量 - 容器拣货使用 - 未持久化',
    `surplus_qty` decimal(18,4) COMMENT '剩余需要分配数量',
    `rep_num` decimal(18,4) COMMENT '需要补货的数量',
    `qty_ordered_total` decimal(18,4) COMMENT '总数量',
    `qty_allocated_total` decimal(18,4) COMMENT '总已分配量',
    `qty_picked_total` decimal(18,4) COMMENT '总已拣货量',


    `purchasing_group` varchar(32) COMMENT '课组',
    `purchasing_group_name` varchar(32) COMMENT '课组名',


    `picking_seq` int(19) COMMENT '拣货动线顺序',
    `alloc_location_code` varchar(32) COMMENT '商品默认拣选面',
    `qty_force` decimal(255) COMMENT '强制完成拣货任务数量',


    `department_category_code` varchar(32) COMMENT '部类',
    `big_category_code` varchar(32) COMMENT '大类',
    `middle_category_code` varchar(32) COMMENT '中类',
    `small_category_code` varchar(32) COMMENT '小类',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存明细';

-- CREATE TABLE IF NOT EXISTS `stock_master`(
--     `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
--     `id` bigint(19) COMMENT '主键自增',
--     `sku_id` bigint(19) COMMENT '商品编号',
--     `store_no` varchar(32) COMMENT '仓店编号(规则id，店仓类型：1-实体店；2-前置仓；3-前店后仓；4-线上店如：100000001，200000001，......',
--     `store_typ` int(2) COMMENT '店仓类型：1-普通店；2-前置仓',
--     `vender_no` varchar(32) COMMENT '商家编号',
--     `current_qty` decimal(18,4) COMMENT '现货库存（仓库/门店存放的实际库存按照SKU、库房、库存状态汇总的数量；）',
--     `usable_qty` decimal(18,4) COMMENT '可用库存（可用库存根据实物库存、预占库存和锁定库存计算而来，是分店仓和销售库存计算的依据。可用库存=实物库存-预占库存-锁定库存（注：若库存明细被锁定，则此条明细不用作可用库存计算））',
--     `order_qty` decimal(18,4) COMMENT '预占库存（发货之前对库存预占，防止超卖。分为销售出库预占、退供预占等；）',
--     `lock_qty` decimal(18,4) COMMENT '锁定库存（商品尚在库房，需临时锁定的库存。如：盘点临时锁定/临期锁定/盘点差异/其它原因）',
--     `transit_qty` decimal(18,4) COMMENT '在途库存（即将入库的库存，含调拨未入库库存、采购未到货库存；待实物上架以后，清除在途库存）',
--     `stock_status` int(255) COMMENT '库存状态（合格/不合格/停售/待验）',
--     `ts` date COMMENT '时间戳',
--     PRIMARY KEY (`id`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存';
--
-- CREATE TABLE IF NOT EXISTS `stock_item`(
--     `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
--     `id` bigint(19) COMMENT '主键自增',
--     `sku_id` bigint(19) COMMENT '商品编号',
--     `store_no` varchar(32) COMMENT '门店编号',
--     `vender_no` varchar(32) COMMENT '商家编号',
--     `current_qty_before` decimal(18,4) COMMENT '现货库存修改前的值',
--     `current_qty_after` decimal(18,4) COMMENT '现货库存改变值 增加为正，减少为负',
--     `current_qty_change` decimal(18,4) COMMENT '现货库存修改后的值',
--     `usable_qty_before` decimal(18,4) COMMENT '可用库存修改前的值',
--     `usable_qty_after` decimal(18,4) COMMENT '可用库存修改后的值',
--     `usable_qty_change` decimal(18,4) COMMENT '可用库存改变值 增加为正，减少为负',
--     `order_qty_before` decimal(18,4) COMMENT '预占库存修改前的值',
--     `order_qty_after` decimal(18,4) COMMENT '预占库存修改后的值',
--     `order_qty_change` decimal(18,4) COMMENT '预占库存改变值 增加为正，减少为负',
--     `lock_qty_before` decimal(18,4) COMMENT '锁定库存修改前的值',
--     `lock_qty_after` decimal(18,4) COMMENT '锁定库存修改后的值',
--     `lock_qty_change` decimal(18,4) COMMENT '锁定库存改变值 增加为正，减少为负',
--     `transit_qty_before` decimal(18,4) COMMENT '在途库存修改前的值',
--     `transit_qty_after` decimal(18,4) COMMENT '在途库存修改后的值',
--     `transit_qty_change` decimal(18,4) COMMENT '在途库存改变值 增加为正，减少为负',
--     `stock_typ` int(2) COMMENT '仓存类型，1：入库，2：修改现货库存，3：修改锁定库存，4：下单',
--     `stock_source` int(2) COMMENT '库存来源，1：库存系统，2：订单中心，3：其他...',
--     `order_id` bigint(19) COMMENT '订单号（2C/2B，订单号可以区分）',
--     `ts` date COMMENT '时间戳',
--     PRIMARY KEY (`id`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存明细';
