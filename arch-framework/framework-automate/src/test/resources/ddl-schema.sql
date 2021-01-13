-- 若库不存在创建一个
CREATE DATABASE IF NOT EXISTS `user`;
USE `user`;

CREATE TABLE IF NOT EXISTS `user_idcard`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `uid` bigint(12) COMMENT '用户ID',
    `idcard` varchar(24) COMMENT '身份证号',
    `name` varchar(32) COMMENT '名字',
    `age` int(2) COMMENT '年龄',
    `sex` int(1) COMMENT '性别',
    `birthday` varchar(16) COMMENT '生日',
    `nation` varchar(32) COMMENT '名族',
    `domicile` varchar(32) COMMENT '居住地',
    `sign_org` varchar(64) COMMENT '颁发机构',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户身份证表';

CREATE TABLE IF NOT EXISTS `user_address`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `uid` bigint(12) COMMENT '用户ID',
    `province` varchar(32) COMMENT '省',
    `city` varchar(32) COMMENT '市',
    `district` varchar(32) COMMENT '区',
    `street` varchar(32) COMMENT '街道',
    `typ` int(1) COMMENT '地址类型：工作地址/家庭地址/收货地址...',
    `indx` int(2) COMMENT '顺序',
    `contacts` varchar(32) COMMENT '联系人',
    `phone_num` varchar(11) COMMENT '手机号',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户地址表';

CREATE TABLE IF NOT EXISTS `user_bankcard`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `uid` bigint(12) COMMENT '用户ID',
    `card_no` varchar(32) COMMENT '省',
    `card_bin` varchar(32) COMMENT '市',
    `` varchar(32) COMMENT '区',
    `` varchar(32) COMMENT '街道',
    `` int(1) COMMENT '地址类型：工作地址/家庭地址/收货地址...',
    `` int(2) COMMENT '顺序',
    `` varchar(32) COMMENT '联系人',
    `` varchar(11) COMMENT '手机号',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户银行卡信息';

CREATE TABLE IF NOT EXISTS `user_relationship`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `pid` bigint(12) COMMENT '父节点ID',
    `org` int(11) COMMENT '组',
    `deep` int(11) COMMENT '深度',
    `seq` int(11) COMMENT '顺序',
    `from_uid` bigint(12) COMMENT '推荐人ID',
    `from_user_name` varchar(32) COMMENT '推荐人姓名',
    `from_user_phone` varchar(12) COMMENT '推荐人手机',
    `to_uid` bigint(12) COMMENT '账号ID',
    `to_user_phone` varchar(12) COMMENT '用户手机',
    `to_user_name` varchar(32) COMMENT '用户名',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户关系表';

CREATE TABLE IF NOT EXISTS `user_org`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `uid` bigint(12) COMMENT '用户ID',
    `group_id` bigint(12) COMMENT '组ID',
    `post_id` bigint(12) COMMENT '职位ID',
    `group_name` varchar(64) COMMENT '组名',
    `post_name` varchar(32) COMMENT '职位名',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户组织架构表';

CREATE TABLE IF NOT EXISTS `user_role`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `uid` bigint(12) COMMENT '用户ID',
    `role_id` bigint(12) COMMENT '角色ID',
    `role_name` varchar(32) COMMENT '角色名',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色表';

CREATE TABLE IF NOT EXISTS `user_tag`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `uid` (255) COMMENT '用户id',
    `tag_name` (255) COMMENT '标签名',
    `tag_color` (255) COMMENT '标签色',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户标签';

CREATE TABLE IF NOT EXISTS `user_member`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `uid` (255) COMMENT '用户id',
    `member_level_id` (255) COMMENT '会员级别ID',
    `start_time` (255) COMMENT '开始时间',
    `end_time` (255) COMMENT '结束时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户会员';

CREATE TABLE IF NOT EXISTS `user_ticket`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `uid` (255) COMMENT '用户id',
    `operator_typ` (255) COMMENT '操作类型(会员充值/)',
    `operator_time` (255) COMMENT '操作时间',
    `record_val` (255) COMMENT '记录的值',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户券';

CREATE TABLE IF NOT EXISTS `user_operator_log`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `uid` (255) COMMENT '用户id',
    `operator_typ` (255) COMMENT '操作类型(会员充值/)',
    `operator_time` (255) COMMENT '操作时间',
    `record_val` (255) COMMENT '记录的值',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户操作记录';

-- 若库不存在创建一个
CREATE DATABASE IF NOT EXISTS `rbac`;
USE `rbac`;

CREATE TABLE IF NOT EXISTS `rbac_client`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `` varchar(32) COMMENT '',
    `` varchar(32) COMMENT '',
    `` varchar(32) COMMENT '',
    `` varchar(32) COMMENT '',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

CREATE TABLE IF NOT EXISTS `rbac_oauthcode`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='授权码';

CREATE TABLE IF NOT EXISTS `rbac_username`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username_id` bigint(12) COMMENT '账号ID/用户ID/会员ID/商户ID',
    `nick_name` varchar(64) COMMENT '用户昵称可随机生成',
    `avatar` varchar(64) COMMENT '头像',
    `source` varchar(64) COMMENT '来源，推广统计用',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户名';

CREATE TABLE IF NOT EXISTS `rbac_identifier`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `username_id` bigint(12) COMMENT '用户名ID',
    `identifier` varchar(32) COMMENT '识别标识:身份唯一标识，如：登录账号、邮箱地址、手机号码、QQ号码、微信号、微博号；',
    `credential` varchar(32) COMMENT '授权凭证【CREDENTIAL】：站内账号是密码、第三方登录是Token；',
    `chanel_type` varchar(32) COMMENT '登录类型【IDENTITYTYPE】：登录类别，如：系统用户、邮箱、手机，或者第三方的QQ、微信、微博；',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户标示';

CREATE TABLE IF NOT EXISTS `rbac_category`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `pid` bigint(12) COMMENT '父节点ID',
    `category_name` varchar(64) COMMENT '资源类目名',
    `sorted` int(2) COMMENT '排序',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资源类目表';

CREATE TABLE IF NOT EXISTS `rbac_resource`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `category_id` bigint(12) COMMENT '类目id',
    `resource_name` varchar(64) COMMENT '资源名',
    `resource_code` varchar(64) COMMENT '资源码',
    `resource_typ` varchar(64) COMMENT '类型:1目录、2菜单、3按钮、4链接',
    `resource_val` varchar(64) COMMENT '资源值',
    `resource_path` varchar(64) COMMENT '资源路径',
    `resource_icon` varchar(64) COMMENT '资源图标',
    `resource_descr` varchar(64) COMMENT '资源描述',
    `visible` int(1) COMMENT '是否隐藏',
    `level` int(2) COMMENT '层级',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资源表';

CREATE TABLE IF NOT EXISTS `rbac_menu`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `pid` bigint(12) COMMENT '父节点ID',
    `menu_code` varchar(64) COMMENT '英文码',
    `menu_name` varchar(64) COMMENT '名称',
    `menu_val` varchar(64) COMMENT '值',
    `level` int(12) COMMENT '层级',
    `sorted` int(12) COMMENT '排序',
    `is_frame` int(1) COMMENT '是否iframe',
    `icon` varchar(64) COMMENT '图标',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

CREATE TABLE IF NOT EXISTS `rbac_permission`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `permission_code` varchar(64) COMMENT '权限码query/creat/update/delete',
    `permission_name` varchar(64) COMMENT '权限名称',
    `permission_val` varchar(64) COMMENT '权限值',
    `permission_uri` varchar(64) COMMENT 'url',
    `permission_typ` varchar(64) COMMENT '权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）',
    `sorted` int(3) COMMENT '排序',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

CREATE TABLE IF NOT EXISTS `rbac_group`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `group_pid` bigint(12) COMMENT '父id',
    `group_code` varchar(32) COMMENT '组code',
    `group_name` varchar(32) COMMENT '组织架构名',
    `group_icon` varchar(32) COMMENT '组织架构ICON',
    `sorted` int(2) COMMENT '排序',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组织机构表';

CREATE TABLE IF NOT EXISTS `rbac_post`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `post_pid` bigint(12) COMMENT '父id',
    `post_name` varchar(32) COMMENT '岗位名',
    `post_code` varchar(32) COMMENT '岗位code',
    `post_icon` varchar(32) COMMENT 'icon',
    `salary` varchar(32) COMMENT '薪资',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='岗位表';

CREATE TABLE IF NOT EXISTS `rbac_role_menu`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `role_id` bigint(12) COMMENT '角色ID',
    `menu_id` bigint(12) COMMENT '菜单ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单表';

CREATE TABLE IF NOT EXISTS `rbac_role_resource`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `role_id` bigint(12) COMMENT '角色ID',
    `resource_id` bigint(12) COMMENT '资源ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色资源表';

CREATE TABLE IF NOT EXISTS `rbac_role_permission`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `role_id` bigint(12) COMMENT '角色ID',
    `permission_id` bigint(12) COMMENT '权限ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限表';

CREATE TABLE IF NOT EXISTS `rbac_role_group`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `role_id` bigint(12) COMMENT '角色ID',
    `group_id` bigint(12) COMMENT '组织ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色组织或机构表';

-- 若库不存在创建一个
CREATE DATABASE IF NOT EXISTS `product`;
USE `product`;

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
    `attrbute_count` varchar(11) COMMENT '自定义属性时控制该产品属性的数量',
    `params_count` varchar(11) COMMENT '自定属性时控制该产品分类下属性参数的数量',
    `product_unit` varchar(11) COMMENT '单位',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品分类';

CREATE TABLE IF NOT EXISTS `product_attribute`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `product_category_id` varchar(11) COMMENT '产品类目id',
    `attrbute_name` varchar(11) COMMENT '属性名',
    `attrbute_type` int(11) COMMENT '属性的类型；0->属性，1->规格；',
    `select_type` int(11) COMMENT '属性选择的类型:0->唯一，1->单选，2->多选',
    `input_type` bigint(11) COMMENT '属性录入方式:0->手工录入，1->从列表中选取',
    `filter_type` varchar(11) COMMENT '分类筛选样式',
    `search_type` varchar(11) COMMENT '检索类型；0->不需要进行检索；1->关键字检索；2->范围检索',
    `related_status` varchar(11) COMMENT '相同属性产品是否关联；0->不关联；1->关联',
    `hand_add_status` int(11) COMMENT '是否支持手动新增；0->不支持；1->支持',
    `value_list` int(11) COMMENT '可选值列表(","号分割)',
    `sorted` int(11) COMMENT '排序',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品属性名';

CREATE TABLE IF NOT EXISTS `product_attribute_val`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `product_id` varchar(11) COMMENT '产品id',
    `product_attribute_id` varchar(11) COMMENT '属性名',
    `attrbute__val` varchar(11) COMMENT '属性值',
    `sorted` varchar(11) COMMENT '顺序',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品属性值';

CREATE TABLE IF NOT EXISTS `product_album`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `album_name` varchar(11) COMMENT '相册名称',
    `descr` varchar(11) COMMENT '相册描述',
    `album_pic` int(11) COMMENT '相册封面',
    `sorted` bigint(11) COMMENT '排序',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品相册';

CREATE TABLE IF NOT EXISTS `product_album_pic`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `album_id` varchar(11) COMMENT '相册id',
    `pic_uri` varchar(11) COMMENT '图片url',
    `pic_name` varchar(11) COMMENT '图片名称',
    `sorted` int(11) COMMENT '排序',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品相册图片';

CREATE TABLE IF NOT EXISTS `product_brand`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `category_id` bigint(11) COMMENT '类目id',
    `brand_name` varchar(11) COMMENT '品牌名称',
    `brand_logo` varchar(11) COMMENT '品牌logo',
    `descr` varchar(11) COMMENT '描述',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品品牌';

CREATE TABLE IF NOT EXISTS `product_sku`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `product_id` varchar(11) COMMENT '产品id',
    `sku_code` bigint(11) COMMENT 'sku编码',
    `price` bigint(11) COMMENT '价格',
    `promotion_price` bigint(11) COMMENT '促销价格',
    `pic` bigint(11) COMMENT '图片',
    `sale` bigint(11) COMMENT '销量',
    `stock` bigint(11) COMMENT '库存',
    `low_stock` bigint(11) COMMENT '最低库存',
    `lock_stock` bigint(11) COMMENT '锁定库存',
    `sp_data` varchar(11) COMMENT '规格参数',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品SKU';

CREATE TABLE IF NOT EXISTS `product_marketing`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `product_id` varchar(11) COMMENT '产品id',
    `marketing_id` varchar(11) COMMENT '营销id',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品营销';

CREATE TABLE IF NOT EXISTS `product_master`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `category_id` varchar(11) COMMENT '类目id',
    `category_name` bigint(11) COMMENT '类目名称',
    `attribute_id` varchar(11) COMMENT '属性id',
    `brand_id` int(11) COMMENT '品牌id',
    `brand_name` bigint(11) COMMENT '品牌名称',
    `product_sn` varchar(11) COMMENT '产品序列号',
    `name` varchar(11) COMMENT '名称',
    `pic` varchar(11) COMMENT '图片',
    `sort` varchar(11) COMMENT '排序',
    `sale` varchar(11) COMMENT '售价',
    `price` varchar(11) COMMENT '价格',
    `weight` varchar(11) COMMENT '重量',
    `stock` int(11) COMMENT '库存',
    `unit` int(11) COMMENT '单位',
    `original_price` bigint(11) COMMENT '价格',
    `low_stock` varchar(11) COMMENT '最低库存',
    `sub_title` varchar(11) COMMENT '子标题',
    `descr` varchar(11) COMMENT 'ad',
    `gift_growth` int(11) COMMENT 'ad',
    `gift_point` int(11) COMMENT 'ad',
    `use_point_limit` int(11) COMMENT 'ad',
    `service_ids` varchar(11) COMMENT 'ad',
    `keywords` varchar(11) COMMENT '关键字',
    `note` varchar(11) COMMENT 'ss',
    `album_pics` varchar(11) COMMENT 'ss',
    `detail_title` varchar(11) COMMENT 'ss',
    `detail_desc` varchar(11) COMMENT 'ss',
    `detail_html` int(11) COMMENT 'ss',
    `detail_mobile_html` bigint(11) COMMENT 'ss',
    `promotion_start_time` varchar(11) COMMENT 'ss',
    `promotion_end_time` varchar(11) COMMENT 'ss',
    `promotion_per_limit` varchar(11) COMMENT 'ss',
    `promotion_type` int(11) COMMENT 'ss',
    `promotion_price` bigint(11) COMMENT 'ss',
    `feight_template_id` varchar(11) COMMENT 'ss',
    `delete_status` varchar(11) COMMENT '删除状态',
    `publish_status` varchar(11) COMMENT '发布状态',
    `new_status` varchar(11) COMMENT '最新状态',
    `recommand_status` bigint(11) COMMENT '推荐状态',
    `verify_status` bigint(11) COMMENT 'ss',
    `preview_status` bigint(11) COMMENT 'dd',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品表';

-- 若库不存在创建一个
CREATE DATABASE IF NOT EXISTS `member`;
USE `member`;

CREATE TABLE IF NOT EXISTS `member_level`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `member_name` (255) COMMENT '会员名称',
    `growth_value` (255) COMMENT '成长值',
    `referrer_num` (255) COMMENT '推荐人数量',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员级别';

CREATE TABLE IF NOT EXISTS `member_rights`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `member_level_id` (255) COMMENT '会员级别id',
    `rights_typ` (255) COMMENT '权益类型',
    `rights_name` (255) COMMENT '权益名称',
    `rights_value` (255) COMMENT '权益值',
    `rights_code` (255) COMMENT '权益码',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员权益';

CREATE TABLE IF NOT EXISTS `member_life`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `member_level_id` (255) COMMENT '会员级别id',
    `member_dues` (255) COMMENT '会费',
    `duration` (255) COMMENT '会员时长',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员生命周期';

-- 若库不存在创建一个
CREATE DATABASE IF NOT EXISTS `order`;
USE `order`;

-- 若库不存在创建一个
CREATE DATABASE IF NOT EXISTS `statistic`;
USE `statistic`;

CREATE TABLE IF NOT EXISTS `statistics_master`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `ip` (255) COMMENT 'IP地址',
    `source` (255) COMMENT '推广渠道/渠道来源',
    `user_phone` (255) COMMENT '用户手机',
    `uid` (255) COMMENT '用户ID',
    `resource_id` (255) COMMENT '资源的标识ID',
    `resource_typ` (255) COMMENT '资源类型产品/页面/信息等',
    `behavior_typ` (255) COMMENT '行为类型uv/pv/cpc/cpa/cps',
    `cost` (255) COMMENT '消耗/成本',
    `os_version` (255) COMMENT '系统版本',
    `os_typ` (255) COMMENT '系统类型ios/android/pc',
    `device_name` (255) COMMENT '设备名称',
    `device_id` (255) COMMENT '设备ID',
    `mac` (255) COMMENT '设备MAC',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='行为统计表';

-- 若库不存在创建一个
CREATE DATABASE IF NOT EXISTS `market`;
USE `market`;

CREATE TABLE IF NOT EXISTS `market_category`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `category_name` (255) COMMENT '券分类名称（满减/打折/礼品/红包/拼团/秒杀/抽奖...）',
    `icon` (255) COMMENT '图标',
    `descr` (255) COMMENT '',
    `` (255) COMMENT '',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='营销分类';

CREATE TABLE IF NOT EXISTS `market_list`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `category_id` (255) COMMENT '分类id',
    `market_name` (255) COMMENT '名称（category_name+market_name）',
    `descr` (255) COMMENT '描述',
    `market_val` (255) COMMENT '营销额度值',
    `condition_val` (255) COMMENT '条件值,使用门槛；0表示无门槛',
    `operater` (255) COMMENT '运算符+-x/',
    `publish_count` (255) COMMENT '发放数量',
    `receive_count` (255) COMMENT '领取数量',
    `used_count` (255) COMMENT '已使用数量',
    `use_type` (255) COMMENT '使用类型：0->全场通用；1->指定分类；2->指定商品',
    `per_limit` (255) COMMENT '每人限领数量',
    `platform` (255) COMMENT '使用平台',
    `start_time` (255) COMMENT '有效期开始时间',
    `end_time` (255) COMMENT '有效期结束时间',
    `enable_time` (255) COMMENT '可以领取的日期',
    `code` (255) COMMENT '',
    `member_level` (255) COMMENT '',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='营销清单';

CREATE TABLE IF NOT EXISTS `market_integral`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    `` (255) COMMENT '',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='营销积分表';

-- 若库不存在创建一个
CREATE DATABASE IF NOT EXISTS `pay`;
USE `pay`;

-- 若库不存在创建一个
CREATE DATABASE IF NOT EXISTS `cms`;
USE `cms`;

-- 若库不存在创建一个
CREATE DATABASE IF NOT EXISTS `im`;
USE `im`;

-- 若库不存在创建一个
CREATE DATABASE IF NOT EXISTS `config`;
USE `config`;

-- 若库不存在创建一个
CREATE DATABASE IF NOT EXISTS `wms`;
USE `wms`;


