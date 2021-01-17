CREATE DATABASE IF NOT EXISTS `arch`;
USE `arch`;

CREATE TABLE IF NOT EXISTS `order_cart`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `seller_account_id` (255) COMMENT '卖方账号ID',
    `seller_account_name` (255) COMMENT '卖方账号名称',
    `buyer_account_id` (255) COMMENT '买方账号ID',
    `buyer_account_name` (255) COMMENT '买方账号名称',
    `product_category_id` (255) COMMENT '商品分类',
    `product_brand` (255) COMMENT '',
    `product_id` (255) COMMENT '',
    `product_name` (255) COMMENT '商品名称',
    `product_sub_title` (255) COMMENT '商品副标题（卖点）',
    `product_attrs` (255) COMMENT '商品销售属性:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]',
    `product_pic` (255) COMMENT '商品主图',
    `product_sn` (255) COMMENT '',
    `product_sku_no` (255) COMMENT '',
    `product_sku_code` (255) COMMENT '商品sku条码',
    `quantity` (255) COMMENT '购买数量',
    `price` decimal(10,2) COMMENT '添加到购物车的价格',
    `status` (255) COMMENT '状态（下单之后对应商品就不应该显示在购物车了）',
    `app_id` (255) COMMENT '应用ID',
    `store_id` (255) COMMENT '店铺ID',
    `st` (255) COMMENT '',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单-购物车';

CREATE TABLE IF NOT EXISTS `order_master`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `seller_account_id` (255) COMMENT '卖方账号ID',
    `seller_account_name` (255) COMMENT '卖方账号名称',
    `buyer_account_id` (255) COMMENT '买方账号ID',
    `buyer_account_name` (255) COMMENT '买方账号名称',
    `order_no` bigint(255) COMMENT '订单号',
    `order_code` varchar(255) COMMENT '订单码',
    `order_typ` (255) COMMENT '订单类型：销售订单；秒杀订单，采购订单，用表名来表示......',
    `order_source` (255) COMMENT '订单来源：0->PC订单；1->app订单',
    `order_amount` decimal(10,2) COMMENT '订单金额',
    `order_status` (255) COMMENT '订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单',
    `order_time` (255) COMMENT '下单时间',
    `pay_time` (255) COMMENT '支付时间',
    `delivery_time` (255) COMMENT '发货时间',
    `receive_time` (255) COMMENT '确认收货时间',
    `confirm_time` (255) COMMENT '自动确认时间',
    `pay_amount` (10,2) COMMENT '应付金额（实际支付金额）',
    `pay_typ` (255) COMMENT '支付方式：0->未支付；1->支付宝；2->微信',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `app_id` (255) COMMENT '应用ID',
    `store_id` (255) COMMENT '店铺ID',
    `st` (255) COMMENT '',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单-订单主体';

CREATE TABLE IF NOT EXISTS `order_sale_item`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `order_no` (255) COMMENT '订单号',
    `product_id` (255) COMMENT '产品ID',
    `product_pic` (255) COMMENT '',
    `product_name` (255) COMMENT '',
    `product_brand` 1(255) COMMENT '',
    `product_price` (255) COMMENT '销售价格',
    `product_quantity` (255) COMMENT '购买数量',
    `product_sku_no` (255) COMMENT '商品sku编号',
    `product_sku_code` (255) COMMENT '商品sku条码',
    `product_category_id` (255) COMMENT '商品分类id',
    `product_attr` (255) COMMENT '商品销售属性:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]',
    `app_id` (255) COMMENT '应用ID',
    `store_id` (255) COMMENT '店铺ID',
    `st` (255) COMMENT '',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单-销售订单项';

CREATE TABLE IF NOT EXISTS `order_swapper `(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `order_no` (255) COMMENT '订单号',
    `user_name` (255) COMMENT '收/发货人姓名',
    `user_phone` (255) COMMENT '收/发货人手机',
    `user_typ` (255) COMMENT '类型：1:发货人，2:收货人',
    `country` (255) COMMENT '国家',
    `province` (255) COMMENT '省',
    `city` (255) COMMENT '市',
    `discint` (255) COMMENT '区/县',
    `street` (255) COMMENT '街道/办事处',
    `village` (255) COMMENT '小区/村庄',
    `adress` (255) COMMENT '收/发货人地址',
    `app_id` (255) COMMENT '',
    `store_id` (255) COMMENT '店铺ID',
    `st` (255) COMMENT '',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单-收发货方信息';

CREATE TABLE IF NOT EXISTS `物流发货信息`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `order_no` (255) COMMENT '订单号',
    `delivery_name` (255) COMMENT '配送方名称',
    `delivery_type` (255) COMMENT '类型：快递，物流，闪送',
    `delivery_no` (255) COMMENT '运单号',
    `delivery_fee` (255) COMMENT '费用',
    `delivery_time` (255) COMMENT '发货时间',
    `arrival_time` (255) COMMENT '送达时间',
    `app_id` (255) COMMENT '应用ID',
    `store_id` (255) COMMENT '店铺ID',
    `st` (255) COMMENT '',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单-快递';

CREATE TABLE IF NOT EXISTS `order_relish`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `order_no` (255) COMMENT '订单号',
    `order_item_id` (255) COMMENT '订单项ID',
    `integration` (255) COMMENT '可以获得的积分',
    `growth` (255) COMMENT '可以活动的成长值',
    `use_integration` (255) COMMENT '下单时使用的积分',
    `promotion_amount` (255) COMMENT '促销优化金额（促销价、满减、阶梯价）',
    `integration_amount` (255) COMMENT '积分抵扣金额',
    `coupon_amount` (255) COMMENT '优惠券抵扣金额',
    `discount_amount` (255) COMMENT '管理员后台调整订单使用的折扣金额',
    `product_sku_no` (255) COMMENT '商品sku编号',
    `promotion_name` (255) COMMENT '商品促销名称',
    `real_amount` (255) COMMENT '该商品经过优惠后的分解金额',
    `gift_integration` (255) COMMENT '',
    `gift_growth` (255) COMMENT '',
    `app_id` (255) COMMENT '应用ID',
    `store_id` (255) COMMENT '店铺ID',
    `st` (255) COMMENT '',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单-佐料';

CREATE TABLE IF NOT EXISTS `order_invoice`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `order_no` (255) COMMENT '',
    `invoice_type` (255) COMMENT '发票类型：0->不开发票；1->电子发票；2->纸质发票',
    `invoice_title` (255) COMMENT '发票抬头：个人/公司',
    `invoice_no` (255) COMMENT '发表税号',
    `amount` (255) COMMENT '开票金额',
    `remark` (255) COMMENT '说明',
    `invoice_item` (255) COMMENT '开票明细',
    `user_eamil` (255) COMMENT '用户邮箱用来接受字典发票',
    `app_id` (255) COMMENT '应用ID',
    `store_id` (255) COMMENT '店铺ID',
    `st` (255) COMMENT '',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单-发票';

CREATE TABLE IF NOT EXISTS `order_topic`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `order_no` (255) COMMENT '单号',
    `seller_account_id` (255) COMMENT '卖方账号ID',
    `seller_account_name` (255) COMMENT '卖方账号名称',
    `buyer_account_id` (255) COMMENT '买方账号ID',
    `buyer_account_name` (255) COMMENT '买方账号名称',
    `point` (255) COMMENT '点赞数',
    `score` (255) COMMENT '评论分数',
    `pics` (255) COMMENT '图片列表',
    `content` (255) COMMENT '内容',
    `comment_time` (255) COMMENT '评价时间',
    `app_id` (255) COMMENT '应用ID',
    `store_id` (255) COMMENT '店铺ID',
    `st` (255) COMMENT '',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单-评价';

CREATE TABLE IF NOT EXISTS `order_payment`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `order_no` (255) COMMENT '订单单号',
    `pay_typ` (255) COMMENT '',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单-支付记录表';






CREATE TABLE IF NOT EXISTS `product_store`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `store_id` varchar(20) COMMENT '店铺ID,分布式ID生成',
    `store_name` varchar(32) COMMENT '商店名称',
    `avatar` varchar(64) COMMENT '店铺头像',
    `account_id` varchar(32) COMMENT '账号ID',
    `user_id` varchar(20) COMMENT '用户ID',
    `biz_scope` varchar(255) COMMENT '经营范围',
    `approve` boolean(1) COMMENT '是否认证通过',
    `grade` int(2) COMMENT '店铺等级',
    `deleted` boolean(1) COMMENT '是否删除',
    `st` (255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品-店铺';

CREATE TABLE IF NOT EXISTS `product_category`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `pid` varchar(11) COMMENT '父节点',
    `icon` varchar(11) COMMENT '类目图标',
    `category_name` varchar(11) COMMENT '类目名',
    `category_code` varchar(11) COMMENT '类目码',
    `category_typ` bigint(11) COMMENT '类目类型',
    `product_count` varchar(11) COMMENT '产品数量',
    `nav_status` int(11) COMMENT '是否显示在导航栏：0->不显示；1->显示',
    `keywords` bigint(11) COMMENT '关键字',
    `level` varchar(11) COMMENT '分类级别：0->1级；1->2级',
    `descr` varchar(11) COMMENT '描述',
    `attrs_count` varchar(11) COMMENT '自定义属性时控制该产品属性的数量',
    `params_count` varchar(11) COMMENT '自定属性时控制该产品分类下属性参数的数量',
    `product_unit` varchar(11) COMMENT '单位',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品-分类';

CREATE TABLE IF NOT EXISTS `product_attr_group`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `category_id` bigint(19) COMMENT '商品分类id，一个分类下有多个规格组',
    `group_name` varchar(32) COMMENT '属性规格组的名称',
    `sorted` int(11) COMMENT '排序',
    `st` datetime(255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品-规格组，规格参数的分组表，
每个商品分类下有多个规格参数组';

CREATE TABLE IF NOT EXISTS `product_attr_name`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `category_id` bigint(19) COMMENT '商品分类id',
    `spec_group_id` bigint(19) COMMENT '商品属性规格组id',
    `group_name` varchar(32) COMMENT '规格组的名称',
    `attr_name` varchar(32) COMMENT '属性名',
    `attr_typ` int(1) COMMENT '属性的类型；0->属性，1->规格；',
    `select_typ` int(1) COMMENT '属性选择的类型:0->唯一，1->单选，2->多选',
    `input_typ` int(1) COMMENT '属性录入方式:0->手工录入，1->从列表中选取',
    `filter_typ` int(1) COMMENT '分类筛选样式',
    `search_typ` int(1) COMMENT '检索类型；0->不需要进行检索；1->关键字检索；2->范围检索',
    `unit` varchar(32) COMMENT '数字类型参数的单位，非数字类型可以为空',
    `segments` varchar(32) COMMENT '数值类型参数，如果需要搜索，则添加分段间隔值，如CPU频率间隔：0.5-1.0',
    `vals` varchar(32) COMMENT '可选值列表(","号分割)',
    `numeric` boolean(1) COMMENT '是否是数字类型参数，true或false',
    `related` boolean(1) COMMENT '相同属性产品是否关联；0->不关联；1->关联',
    `handed` boolean(1) COMMENT '是否支持手动新增；0->不支持；1->支持',
    `generic` boolean(1) COMMENT '是否是sku通用属性，true或false',
    `searching` boolean(1) COMMENT '是否用于搜索过滤，true或false',
    `sorted` int(11) COMMENT '排序',
    `st` datetime(255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品-规格参数组下的
参数名';

CREATE TABLE IF NOT EXISTS `product_attr_val`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `spu_id` varchar(11) COMMENT '产品id',
    `attr_name_id` varchar(11) COMMENT '属性名ID',
    `attr_val` varchar(11) COMMENT '属性值',
    `st` datetime(255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品-属性值';

CREATE TABLE IF NOT EXISTS `product_brand`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `category_id` bigint(11) COMMENT '类目id',
    `brand_name` varchar(11) COMMENT '品牌名称',
    `brand_logo` varchar(11) COMMENT '品牌logo',
    `descr` varchar(11) COMMENT '描述',
    `sorted` varchar(11) COMMENT '顺序',
    `st` datetime(255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品-品牌';

CREATE TABLE IF NOT EXISTS `product_spu`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `store_id` varchar(20) COMMENT '店铺ID',
    `spu_id` varchar(11) COMMENT '产品id，分布式ID生成，全局唯一',
    `spu_code` varchar(11) COMMENT '产品code',
    `spu_name` varchar(11) COMMENT '名称',
    `spu_album_json` varchar(11) COMMENT '产品主图相册：[{url1:"",sorted:1},{url2:"",sorted:2}]',
    `spu_attr_json` varchar(256) COMMENT '属性组集合(spu共用)：{groupName1:[{"key":"k1","value":"v1",sorted:1},{"key":"k2","value":"v2",sorted:2}],groupName2:[{"key":"k1","value":"v1",sorted:1},{"key":"k2","value":"v2",sorted:2}]}',
    `category_id` varchar(11) COMMENT '产品类目id',
    `category_name` bigint(11) COMMENT '产品类目名称',
    `brand_id` int(11) COMMENT '品牌id',
    `brand_name` bigint(11) COMMENT '品牌名称',
    `sale` decimal(10,2) COMMENT '售价',
    `price` decimal(10,2) COMMENT '价格',
    `stock` int(11) COMMENT '库存，所有sku 可售库存之和',
    `low_stock` varchar(11) COMMENT '最低库存',
    `unit` varchar(10) COMMENT '单位',
    `title` varchar(255) COMMENT '标题',
    `sub_title` varchar(11) COMMENT '子标题',
    `keywords` varchar(11) COMMENT '关键字',
    `detail_title` varchar(11) COMMENT '详情头',
    `detail_html` int(11) COMMENT '详情页',
    `detail_mobile_html` bigint(11) COMMENT '手机详情页',
    `usable` int(1) COMMENT '可用状态',
    `published` int(1) COMMENT '发布状态',
    `new` int(1) COMMENT '最新状态',
    `recommand` int(1) COMMENT '推荐状态；0->不推荐；1->推荐',
    `verify` int(1) COMMENT '审核状态：0->未审核；1->审核通过',
    `presell` boolean(1) COMMENT '是否为预告商品：0->不是；1->是',
    `service_ids` varchar(11) COMMENT '售后服务ID，多个用“，”分割',
    `freight_id` varchar(11) COMMENT '运费模板ID',
    `sorted` int(11) COMMENT '排序',
    `st` datetime(255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品-SPU';

CREATE TABLE IF NOT EXISTS `product_relish`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `spu_id` varchar(11) COMMENT '产品id',
    `gift_growth` int(11) COMMENT '赠送的成长值',
    `gift_point` int(11) COMMENT '赠送的积分',
    `point_limit` int(11) COMMENT '限制使用的积分数',
    `st` datetime(255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品-佐料';

CREATE TABLE IF NOT EXISTS `product_sku`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `spu_id` varchar(11) COMMENT '产品id',
    `sku_code` bigint(11) COMMENT 'sku编码',
    `price` bigint(11) COMMENT '价格',
    `dispose_price` bigint(11) COMMENT '处理或促销价格',
    `pic_json` bigint(11) COMMENT '图片：[{url1:"",sorted:1},{url2:"",sorted:2}]',
    `sale` bigint(11) COMMENT '销量',
    `stock` bigint(11) COMMENT '库存',
    `low_stock` bigint(11) COMMENT '最低库存',
    `lock_stock` bigint(11) COMMENT '锁定库存',
    `spec_json` varchar(11) COMMENT '规格组集合（sku专用）：{groupName1:[{"key":"spec-k1","value":"spec-v1"，sorted:1}]，groupName2:[{"key":"spec-k2","value":"spec-v2"，sorted:2}]}',
    `st` datetime(255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品-SKU';

CREATE TABLE IF NOT EXISTS `product_album`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `store_id` varchar(20) COMMENT '店铺ID',
    `album_name` varchar(11) COMMENT '相册名称',
    `descr` varchar(11) COMMENT '相册描述',
    `album_cover` int(11) COMMENT '相册封面',
    `sorted` bigint(11) COMMENT '排序',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品-相册';

CREATE TABLE IF NOT EXISTS `product_album_pic`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `album_id` varchar(11) COMMENT '相册id',
    `pic_uri` varchar(11) COMMENT '图片url',
    `pic_name` varchar(11) COMMENT '图片名称',
    `sorted` int(11) COMMENT '排序',
    `st` date(255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品-相册图片';

CREATE TABLE IF NOT EXISTS `product_aftersale`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `store_id` varchar(20) COMMENT '店铺ID',
    `service_id` varchar(20) COMMENT '售后或服务ID',
    `service_name` varchar(32) COMMENT '售后或服务名称',
    `fee` decimal(10,2) COMMENT '费用',
    `sorted` int(11) COMMENT '排序',
    `st` datetime(255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品-售后及服务';

CREATE TABLE IF NOT EXISTS `product_freight`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `store_id` varchar(20) COMMENT '店铺ID',
    `category_id` varchar(20) COMMENT '品类ID',
    `freight_id` varchar(20) COMMENT '运费模板ID,分布式ID生成',
    `freight_name` varchar(32) COMMENT '运费模板名称',
    `fee` decimal(10,2) COMMENT '费用',
    `logistics_id` varchar(16) COMMENT '物流Id',
    `logistics_name` varchar(16) COMMENT '物流名称',
    `logistics_code` varchar(16) COMMENT '物流CODE',
    `sorted` int(11) COMMENT '排序',
    `deleted` boolean(1) COMMENT '是否删除',
    `st` datetime(255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品-费用模板';

CREATE TABLE IF NOT EXISTS `product_price`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `spu_id` varchar(20) COMMENT '产品id',
    `count` int(11) COMMENT '数量',
    `discount` decimal(10,2) COMMENT '折扣',
    `price` decimal(10,2) COMMENT '折后价格',
    `st` datetime(255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品-价格(针对批发)';

CREATE TABLE IF NOT EXISTS `product_marketing`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `spu_id` varchar(20) COMMENT '产品id',
    `marketing_id` varchar(20) COMMENT '营销id，关联营销ID',
    `market_name` varchar(64) COMMENT '名称',
    `st` datetime(255) COMMENT '时间戳',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品-营销';
