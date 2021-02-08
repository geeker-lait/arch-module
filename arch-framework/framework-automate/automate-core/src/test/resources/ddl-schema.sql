CREATE DATABASE IF NOT EXISTS `arch_module`;
USE `arch_module`;
-- 若库不存在创建一个

CREATE TABLE IF NOT EXISTS `user_idcard`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`  bigint(19) COMMENT '用户ID',
    `idcard`  varchar(24) COMMENT '身份证号',
    `name`  varchar(32) COMMENT '名字',
    `age`  int(2) COMMENT '年龄',
    `sex`  int(1) COMMENT '性别',
    `birthday` datetime COMMENT '生日',
    `nation`  varchar(32) COMMENT '名族',
    `domicile`  varchar(32) COMMENT '居住地',
    `sign_org`  varchar(64) COMMENT '颁发机构',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-身份信息';

CREATE TABLE IF NOT EXISTS `user_address`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`  bigint(19) COMMENT '用户ID',
    `province`  varchar(32) COMMENT '省',
    `city`  varchar(32) COMMENT '市',
    `district`  varchar(32) COMMENT '区',
    `street`  varchar(32) COMMENT '街道',
    `typ`  int(1) COMMENT '地址类型：工作地址/家庭地址/收货地址...',
    `sorted`  int(2) COMMENT '顺序',
    `contacts`  varchar(32) COMMENT '联系人',
    `phone_num`  varchar(11) COMMENT '手机号',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-地址信息';

CREATE TABLE IF NOT EXISTS `user_bankcard`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`  bigint(19) COMMENT '用户ID',
    `card_no`  varchar(32) COMMENT '卡号',
    `card_bin`  varchar(16) COMMENT '卡bin 如：6221，6222',
    `card_code`  varchar(32) COMMENT '卡code 如：ICBC,ABC',
    `cvv`  varchar(32) COMMENT '卡cvv',
    `card_typ`  int(1) COMMENT '卡类型：0:储蓄卡，1：借记卡',
    `sorted`  int(2) COMMENT '顺序',
    `validity` datetime COMMENT '有效期',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-银行卡信息';

CREATE TABLE IF NOT EXISTS `user_relatives`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`  bigint(19) COMMENT '用户ID',
    `relatives_typ`  int(1) COMMENT '亲朋类型：1：家属，2:朋友',
    `relatives_name`  varchar(12) COMMENT '亲朋名称：哥哥，姐姐，妹妹，父亲，母亲，弟弟，朋友，同学',
    `relatives_sex`  int(1) COMMENT '亲朋性别：0男，1女',
    `sorted`  int(2) COMMENT '顺序',
    `awardtime` datetime COMMENT '颁发时间',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-亲朋信息';

CREATE TABLE IF NOT EXISTS `user_education`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`  bigint(19) COMMENT '用户ID',
    `certificate_no`  varchar(24) COMMENT '证书编码',
    `certificate_name`  varchar(32) COMMENT '证书名称',
    `certificate_org`  varchar(32) COMMENT '证书登记机构',
    `certificate_level`  varchar(32) COMMENT '学历(如: 大专, 本科, 硕士, 博士)',
    `sorted`  int(2) COMMENT '顺序',
    `awardtime` datetime COMMENT '颁发时间',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-学历信息';

CREATE TABLE IF NOT EXISTS `user_job`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`  bigint(19) COMMENT '用户ID',
    `group_channel`  varchar(32) COMMENT '公司行业',
    `group_name`  varchar(32) COMMENT '公司名称',
    `post_name`  varchar(12) COMMENT '职位名称',
    `post_level`  varchar(64) COMMENT '职级',
    `sorted`  int(2) COMMENT '顺序',
    `hiredatetime_time` datetime COMMENT '入职时间',
    `dimission_time` datetime COMMENT '离职时间',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-工作信息';

CREATE TABLE IF NOT EXISTS `user_phone`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`  bigint(19) COMMENT '用户ID',
    `phone_no`  varchar(11) COMMENT '手机号',
    `location`  varchar(12) COMMENT '号码归属地',
    `mno`  varchar(12) COMMENT '运营商：移动/电信/联通/电话......',
    `sorted`  int(255) COMMENT '顺序',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-电话信息';

CREATE TABLE IF NOT EXISTS `user_account`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`  bigint(19) COMMENT '用户ID',
    `account_id`  varchar(19) COMMENT '账号ID',
    `nick_name`  varchar(32) COMMENT '昵称',
    `phone_num`  bigint(11) COMMENT '账号关联的手机号',
    `sorted`  int(255) COMMENT '顺序',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-账号信息';

-- 若库不存在创建一个

CREATE TABLE IF NOT EXISTS `account_oauth_client`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `app_id`  varchar(32) COMMENT '客户端 ID',
    `app_code`  varchar(32) COMMENT '客户端secret',
    `scopes`  varchar(255) COMMENT 'openid/userinfo/token/code/资源服务器标识等',
    `app_type`  int(4) COMMENT '客户端类型: web, 安卓, ios, 小程序…',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-授权客户端';

CREATE TABLE IF NOT EXISTS `account_oauth_token`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `account_identifier_id`  bigint(19) COMMENT 'accountIdentifierId',
    `enable_refresh`  int(1) COMMENT '是否支持 refreshToken, 默认: 1. 1 表示支持, 0 表示不支持',
    `provider_id`  int(20) COMMENT '第三方服务商,如: qq,github',
    `access_token`  varchar(64) COMMENT 'accessToken',
    `expire_in`  bigint(20) COMMENT 'accessToken过期时间, 无过期时间默认为 -1',
    `refresh_token_expire_in`  bigint(20) COMMENT 'refreshToken过期时间, 无过期时间默认为 -1',
    `refresh_token`  varchar(64) COMMENT 'refreshToken',
    `uid`  varchar(20) COMMENT 'alipay userId',
    `open_id`  varchar(64) COMMENT 'qq/mi/toutiao/wechatMp/wechatOpen/weibo/jd/kujiale/dingTalk/douyin/feishu',
    `access_code`  varchar(64) COMMENT 'dingTalk, taobao 附带属性',
    `union_id`  varchar(64) COMMENT 'QQ附带属性',
    `scope`  varchar(64) COMMENT 'Google附带属性',
    `token_type`  varchar(20) COMMENT 'Google附带属性',
    `id_token`  varchar(64) COMMENT 'Google附带属性',
    `mac_algorithm`  varchar(20) COMMENT '小米附带属性',
    `mac_key`  varchar(64) COMMENT '小米附带属性',
    `code`  varchar(64) COMMENT '企业微信附带属性',
    `oauth_token`  varchar(64) COMMENT 'Twitter附带属性',
    `oauth_token_secret`  varchar(64) COMMENT 'Twitter附带属性',
    `user_id`  varchar(64) COMMENT 'Twitter附带属性',
    `screen_name`  varchar(64) COMMENT 'Twitter附带属性',
    `oauth_callback_confirmed`  varchar(64) COMMENT 'Twitter附带属性',
    `expire_time`  bigint(20) COMMENT '过期时间, 基于 1970-01-01T00:00:00Z, 无过期时间默认为 -1',
    `st` datetime COMMENT '',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-第三方账号授权';

CREATE TABLE IF NOT EXISTS `accountname`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `account_id`  bigint(19) COMMENT '账号ID/用户ID/会员ID/商户ID',
    `nick_name`  varchar(64) COMMENT '用户昵称可随机生成',
    `avatar`  varchar(64) COMMENT '头像',
    `source`  varchar(64) COMMENT '来源，推广统计用',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-名称';

CREATE TABLE IF NOT EXISTS `account_identifier`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `account_id`  bigint(19) COMMENT '用户名ID',
    `identifier`  varchar(32) COMMENT '识别标识:身份唯一标识，如：登录账号、邮箱地址、手机号码、QQ号码、微信号、微博号；',
    `credential`  varchar(32) COMMENT '授权凭证【CREDENTIAL】：站内账号是密码、第三方登录是Token；',
    `channel_type`  varchar(32) COMMENT '登录类型【IDENTITYTYPE】：登录类别，如：系统用户、邮箱、手机，或者第三方的QQ、微信、微博；',
    `user_id`  varchar(20) COMMENT '关联的用户Id',
    `locked` boolean COMMENT '是否被锁定，否认false',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-标识';

CREATE TABLE IF NOT EXISTS `account_relationship`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `pid`  bigint(19) COMMENT '父节点ID（数据库自增）',
    `org`  int(11) COMMENT '组',
    `deep`  int(11) COMMENT '深度',
    `seq`  int(11) COMMENT '顺序',
    `from_user_id`  bigint(19) COMMENT '推荐人ID',
    `from_user_name`  varchar(32) COMMENT '推荐人姓名',
    `from_user_phone`  varchar(12) COMMENT '推荐人手机',
    `to_user_id`  bigint(19) COMMENT '账号ID',
    `to_user_phone`  varchar(12) COMMENT '用户手机',
    `to_user_name`  varchar(32) COMMENT '用户名',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-关系';

CREATE TABLE IF NOT EXISTS `account_role`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `role_name`  varchar(32) COMMENT '角色名',
    `icon`  varchar(32) COMMENT '角色icon',
    `sorted`  varchar(32) COMMENT '顺序',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-角色';

CREATE TABLE IF NOT EXISTS `account_category`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `pid`  bigint(19) COMMENT '父节点ID',
    `category_name`  varchar(64) COMMENT '资源类目名',
    `sorted`  int(2) COMMENT '排序',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-资源类目';

CREATE TABLE IF NOT EXISTS `account_resource`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `category_id`  bigint(19) COMMENT '类目id',
    `resource_name`  varchar(64) COMMENT '资源名',
    `resource_code`  varchar(64) COMMENT '资源码',
    `resource_typ`  int(4) COMMENT '类型:1目录、2菜单、3按钮、4链接',
    `resource_val`  varchar(64) COMMENT '资源值',
    `resource_path`  varchar(64) COMMENT '资源路径',
    `resource_icon`  varchar(64) COMMENT '资源图标',
    `resource_desc`  varchar(64) COMMENT '资源描述',
    `visible`  int(1) COMMENT '是否隐藏',
    `level`  int(2) COMMENT '层级',
    `sorted`  varchar(32) COMMENT '顺序',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-资源';

CREATE TABLE IF NOT EXISTS `account_menu`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `pid`  bigint(19) COMMENT '父节点ID',
    `menu_code`  varchar(64) COMMENT '英文码',
    `menu_name`  varchar(64) COMMENT '名称',
    `menu_val`  varchar(64) COMMENT '值',
    `level`  int(2) COMMENT '层级',
    `sorted`  int(2) COMMENT '排序',
    `frame`  int(1) COMMENT '是否iframe',
    `icon`  varchar(64) COMMENT '图标',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-菜单';

CREATE TABLE IF NOT EXISTS `account_permission`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `permission_code`  varchar(64) COMMENT '权限码(与RequestMethod对应)list(GET)/add(POST)/edit(PUT)/delete(DELETE)/..',
    `permission_name`  varchar(64) COMMENT '权限名称',
    `permission_val`  varchar(64) COMMENT '权限值',
    `permission_uri`  varchar(64) COMMENT 'url',
    `permission_typ`  int(4) COMMENT '权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）',
    `sorted`  int(3) COMMENT '排序',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-权限';

CREATE TABLE IF NOT EXISTS `account_group`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `group_pid`  bigint(19) COMMENT '父id',
    `group_code`  varchar(32) COMMENT '组code',
    `group_name`  varchar(32) COMMENT '组织架构名',
    `group_icon`  varchar(32) COMMENT '组织架构ICON',
    `sorted`  int(2) COMMENT '排序',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-组织机构';

CREATE TABLE IF NOT EXISTS `account_post`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `post_pid`  bigint(19) COMMENT '父id',
    `post_name`  varchar(32) COMMENT '岗位名',
    `post_code`  varchar(32) COMMENT '岗位code',
    `post_icon`  varchar(32) COMMENT 'icon',
    `salary`  decimal(19,4) COMMENT '薪资',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-岗位';

CREATE TABLE IF NOT EXISTS `account_role_menu`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `role_id`  bigint(20) COMMENT '角色ID',
    `menu_id`  bigint(20) COMMENT '菜单ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-角色菜单';

CREATE TABLE IF NOT EXISTS `account_role_resource`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `role_id`  bigint(20) COMMENT '角色ID',
    `resource_id`  bigint(20) COMMENT '资源ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-角色资源表';

CREATE TABLE IF NOT EXISTS `account_role_permission`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `role_id`  bigint(20) COMMENT '角色ID',
    `permission_id`  bigint(20) COMMENT '权限ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-角色权限表';

CREATE TABLE IF NOT EXISTS `account_role_group`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `role_id`  bigint(20) COMMENT '角色ID',
    `group_id`  bigint(20) COMMENT '组织ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-角色组织或机构';

CREATE TABLE IF NOT EXISTS `account_ticket`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `account_id`  bigint(19) COMMENT '用户id',
    `ticket_typ`  int(2) COMMENT '券类型：打折，优惠，抵用....',
    `ticket_no`  varchar(32) COMMENT '券号',
    `ticket_val`  int(6) COMMENT '券值',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-券';

CREATE TABLE IF NOT EXISTS `account_tag`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `account_id`  bigint(19) COMMENT '用户id',
    `tag_category`  varchar(32) COMMENT '标签类目',
    `tag_name`  varchar(64) COMMENT '标签名',
    `tag_color`  varchar(32) COMMENT '标签色',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-标签';

CREATE TABLE IF NOT EXISTS `account_member`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `account_id`  bigint(19) COMMENT '用户id',
    `member_level_id`  int(3) COMMENT '会员级别ID',
    `start_time` datetime COMMENT '开始时间',
    `end_time` datetime COMMENT '结束时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号-会员账号';

CREATE TABLE IF NOT EXISTS `account_operate_log`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `account_id`  bigint(19) COMMENT '用户id',
    `operate_service`  varchar(32) COMMENT '操作的服务',
    `operate_table`  int(4) COMMENT '操作类型()',
    `operate_time` datetime COMMENT '操作时间',
    `record_val`  varchar(512) COMMENT '记录的值json',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账号操作记录';

-- 若库不存在创建一个

CREATE TABLE IF NOT EXISTS `member_level`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `member_name`  varchar(32) COMMENT '会员名称',
    `growth_value`  int(2) COMMENT '成长值',
    `referrer_num`  int(8) COMMENT '推荐人数量',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员级别';

CREATE TABLE IF NOT EXISTS `member_rights`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `member_level_id`  bigint(19) COMMENT '会员级别id',
    `rights_typ`  int(4) COMMENT '权益类型',
    `rights_name`  varchar(32) COMMENT '权益名称',
    `rights_value`  varchar(32) COMMENT '权益值',
    `rights_code`  varchar(32) COMMENT '权益码',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员权益';

CREATE TABLE IF NOT EXISTS `member_life`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `member_level_id`  bigint(19) COMMENT '会员级别id',
    `member_dues`  decimal(10,2) COMMENT '会费',
    `duration`  int(4) COMMENT '会员时长',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员生命周期';

-- 若库不存在创建一个

CREATE TABLE IF NOT EXISTS `product_store`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `store_id`  varchar(20) COMMENT '店铺ID,分布式ID生成',
    `store_name`  varchar(32) COMMENT '商店名称',
    `avatar`  varchar(64) COMMENT '店铺头像',
    `account_id`  varchar(32) COMMENT '账号ID',
    `user_id`  varchar(20) COMMENT '用户ID',
    `biz_scope`  varchar(255) COMMENT '经营范围',
    `approve` boolean COMMENT '是否认证通过',
    `grade`  int(2) COMMENT '店铺等级',
    `deleted` boolean COMMENT '是否删除',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品-店铺';

CREATE TABLE IF NOT EXISTS `product_category`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `pid`  varchar(11) COMMENT '父节点',
    `icon`  varchar(11) COMMENT '类目图标',
    `category_name`  varchar(11) COMMENT '类目名',
    `category_code`  varchar(11) COMMENT '类目码',
    `category_typ`  bigint(11) COMMENT '类目类型',
    `product_count`  varchar(11) COMMENT '产品数量',
    `nav_status`  int(11) COMMENT '是否显示在导航栏：0->不显示；1->显示',
    `keywords`  bigint(11) COMMENT '关键字',
    `level`  varchar(11) COMMENT '分类级别：0->1级；1->2级',
    `descr`  varchar(11) COMMENT '描述',
    `attrs_count`  varchar(11) COMMENT '自定义属性时控制该产品属性的数量',
    `params_count`  varchar(11) COMMENT '自定属性时控制该产品分类下属性参数的数量',
    `product_unit`  varchar(11) COMMENT '单位',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品-分类';

CREATE TABLE IF NOT EXISTS `product_attr_group`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `category_id`  bigint(19) COMMENT '商品分类id，一个分类下有多个规格组',
    `group_name`  varchar(32) COMMENT '属性规格组的名称',
    `sorted`  int(11) COMMENT '排序',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品-规格组，规格参数的分组表，
每个商品分类下有多个规格参数组';

CREATE TABLE IF NOT EXISTS `product_attr_name`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `category_id`  bigint(19) COMMENT '商品分类id',
    `spec_group_id`  bigint(19) COMMENT '商品属性规格组id',
    `group_name`  varchar(32) COMMENT '规格组的名称',
    `attr_name`  varchar(32) COMMENT '属性名',
    `attr_typ`  int(1) COMMENT '属性的类型；0->属性，1->规格；',
    `select_typ`  int(1) COMMENT '属性选择的类型:0->唯一，1->单选，2->多选',
    `input_typ`  int(1) COMMENT '属性录入方式:0->手工录入，1->从列表中选取',
    `filter_typ`  int(1) COMMENT '分类筛选样式',
    `search_typ`  int(1) COMMENT '检索类型；0->不需要进行检索；1->关键字检索；2->范围检索',
    `unit`  varchar(32) COMMENT '数字类型参数的单位，非数字类型可以为空',
    `segments`  varchar(32) COMMENT '数值类型参数，如果需要搜索，则添加分段间隔值，如CPU频率间隔：0.5-1.0',
    `vals`  varchar(32) COMMENT '可选值列表(","号分割)',
    `numeric` boolean COMMENT '是否是数字类型参数，true或false',
    `related` boolean COMMENT '相同属性产品是否关联；0->不关联；1->关联',
    `handed` boolean COMMENT '是否支持手动新增；0->不支持；1->支持',
    `generic` boolean COMMENT '是否是sku通用属性，true或false',
    `searching` boolean COMMENT '是否用于搜索过滤，true或false',
    `sorted`  int(11) COMMENT '排序',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品-规格参数组下的
参数名';

CREATE TABLE IF NOT EXISTS `product_attr_val`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `spu_id`  varchar(11) COMMENT '产品id',
    `attr_name_id`  varchar(11) COMMENT '属性名ID',
    `attr_val`  varchar(11) COMMENT '属性值',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品-属性值';

CREATE TABLE IF NOT EXISTS `product_brand`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `category_id`  bigint(11) COMMENT '类目id',
    `brand_name`  varchar(11) COMMENT '品牌名称',
    `brand_logo`  varchar(11) COMMENT '品牌logo',
    `descr`  varchar(11) COMMENT '描述',
    `sorted`  varchar(11) COMMENT '顺序',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品-品牌';

CREATE TABLE IF NOT EXISTS `product_spu`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `store_id`  varchar(20) COMMENT '店铺ID',
    `spu_id`  varchar(11) COMMENT '产品id，分布式ID生成，全局唯一',
    `spu_code`  varchar(11) COMMENT '产品code',
    `spu_name`  varchar(11) COMMENT '名称',
    `spu_album_json`  varchar(11) COMMENT '产品主图相册：[{url1:"",sorted:1},{url2:"",sorted:2}]',
    `spu_attr_json`  varchar(256) COMMENT '属性组集合(spu共用)：{groupName1:[{"key":"k1","value":"v1",sorted:1},{"key":"k2","value":"v2",sorted:2}],groupName2:[{"key":"k1","value":"v1",sorted:1},{"key":"k2","value":"v2",sorted:2}]}',
    `category_id`  varchar(11) COMMENT '产品类目id',
    `category_name`  bigint(11) COMMENT '产品类目名称',
    `brand_id`  int(11) COMMENT '品牌id',
    `brand_name`  bigint(11) COMMENT '品牌名称',
    `sale`  decimal(10,2) COMMENT '售价',
    `price`  decimal(10,2) COMMENT '价格',
    `stock`  int(11) COMMENT '库存，所有sku 可售库存之和',
    `low_stock`  varchar(11) COMMENT '最低库存',
    `unit`  varchar(10) COMMENT '单位',
    `title`  varchar(255) COMMENT '标题',
    `sub_title`  varchar(11) COMMENT '子标题',
    `keywords`  varchar(11) COMMENT '关键字',
    `detail_title`  varchar(11) COMMENT '详情头',
    `detail_html`  int(11) COMMENT '详情页',
    `detail_mobile_html`  bigint(11) COMMENT '手机详情页',
    `usable`  int(1) COMMENT '可用状态',
    `published`  int(1) COMMENT '发布状态',
    `new`  int(1) COMMENT '最新状态',
    `recommand`  int(1) COMMENT '推荐状态；0->不推荐；1->推荐',
    `verify`  int(1) COMMENT '审核状态：0->未审核；1->审核通过',
    `presell` boolean COMMENT '是否为预告商品：0->不是；1->是',
    `service_ids`  varchar(11) COMMENT '售后服务ID，多个用“，”分割',
    `freight_id`  varchar(11) COMMENT '运费模板ID',
    `sorted`  int(11) COMMENT '排序',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品-SPU';

CREATE TABLE IF NOT EXISTS `product_relish`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `spu_id`  varchar(11) COMMENT '产品id',
    `gift_growth`  int(11) COMMENT '赠送的成长值',
    `gift_point`  int(11) COMMENT '赠送的积分',
    `point_limit`  int(11) COMMENT '限制使用的积分数',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品-佐料';

CREATE TABLE IF NOT EXISTS `product_sku`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `spu_id`  varchar(11) COMMENT '产品id',
    `sku_code`  bigint(11) COMMENT 'sku编码',
    `price`  bigint(11) COMMENT '价格',
    `dispose_price`  bigint(11) COMMENT '处理或促销价格',
    `pic_json`  bigint(11) COMMENT '图片：[{url1:"",sorted:1},{url2:"",sorted:2}]',
    `sale`  bigint(11) COMMENT '销量',
    `stock`  bigint(11) COMMENT '库存',
    `low_stock`  bigint(11) COMMENT '最低库存',
    `lock_stock`  bigint(11) COMMENT '锁定库存',
    `spec_json`  varchar(11) COMMENT '规格组集合（sku专用）：{groupName1:[{"key":"spec-k1","value":"spec-v1"，sorted:1}]，groupName2:[{"key":"spec-k2","value":"spec-v2"，sorted:2}]}',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品-SKU';

CREATE TABLE IF NOT EXISTS `product_album`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `store_id`  varchar(20) COMMENT '店铺ID',
    `album_name`  varchar(11) COMMENT '相册名称',
    `descr`  varchar(11) COMMENT '相册描述',
    `album_cover`  int(11) COMMENT '相册封面',
    `sorted`  bigint(11) COMMENT '排序',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品-相册';

CREATE TABLE IF NOT EXISTS `product_album_pic`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `album_id`  varchar(11) COMMENT '相册id',
    `pic_uri`  varchar(11) COMMENT '图片url',
    `pic_name`  varchar(11) COMMENT '图片名称',
    `sorted`  int(11) COMMENT '排序',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品-相册图片';

CREATE TABLE IF NOT EXISTS `product_aftersale`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `store_id`  varchar(20) COMMENT '店铺ID',
    `service_id`  varchar(20) COMMENT '售后或服务ID',
    `service_name`  varchar(32) COMMENT '售后或服务名称',
    `fee`  decimal(10,2) COMMENT '费用',
    `sorted`  int(11) COMMENT '排序',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品-售后及服务';

CREATE TABLE IF NOT EXISTS `product_freight`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `store_id`  varchar(20) COMMENT '店铺ID',
    `category_id`  varchar(20) COMMENT '品类ID',
    `freight_id`  varchar(20) COMMENT '运费模板ID,分布式ID生成',
    `freight_name`  varchar(32) COMMENT '运费模板名称',
    `fee`  decimal(10,2) COMMENT '费用',
    `logistics_id`  varchar(16) COMMENT '物流Id',
    `logistics_name`  varchar(16) COMMENT '物流名称',
    `logistics_code`  varchar(16) COMMENT '物流CODE',
    `sorted`  int(11) COMMENT '排序',
    `deleted` boolean COMMENT '是否删除',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品-费用模板';

CREATE TABLE IF NOT EXISTS `product_price`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `spu_id`  varchar(20) COMMENT '产品id',
    `count`  int(11) COMMENT '数量',
    `discount`  decimal(10,2) COMMENT '折扣',
    `price`  decimal(10,2) COMMENT '折后价格',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品-价格(针对批发)';

CREATE TABLE IF NOT EXISTS `product_marketing`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `spu_id`  varchar(20) COMMENT '产品id',
    `marketing_id`  varchar(20) COMMENT '营销id，关联营销ID',
    `market_name`  varchar(64) COMMENT '名称',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='产品-营销';

-- 若库不存在创建一个

CREATE TABLE IF NOT EXISTS `market_name`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `pid`  bigint(19) COMMENT '父ID',
    `market_typ`  int(4) COMMENT '分类如：1:获客拉新、2:下单转化、3:提高客单、4:复购留存、5:....(数据字典获取)',
    `market_name`  varchar(32) COMMENT '名称',
    `market_code`  varchar(32) COMMENT '营销英文名',
    `icon`  varchar(64) COMMENT '图标',
    `tier`  int(8) COMMENT '层级',
    `sorted`  int(4) COMMENT '排序',
    `descr`  varchar(32) COMMENT '描述',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='营销-名称';

CREATE TABLE IF NOT EXISTS `market_attrname`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name_id`  bigint(19) COMMENT '营销ID',
    `attr_name`  varchar(32) COMMENT '属性名',
    `filed_name`  varchar(32) COMMENT '字段名',
    `filed_typ`  varchar(32) COMMENT '字段类型：string，int，long，decimal，double',
    `select_typ`  int(4) COMMENT '属性选择的类型:0->唯一，1->单选，2->多选',
    `input_typ`  int(4) COMMENT '属性录入方式:0->手工录入，1->从列表中选取',
    `unit`  varchar(32) COMMENT '数字类型参数的单位，非数字类型可以为空',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='营销-自定义动态属性';

CREATE TABLE IF NOT EXISTS `market_attrval`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `attr_name_id`  bigint(19) COMMENT '属性ID',
    `attr_val`  varchar(32) COMMENT '属性值',
    `defined_val`  varchar(32) COMMENT '手动输入属性值（attrVal）的自定义值',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='营销-自定义动态属性值';

CREATE TABLE IF NOT EXISTS `market_master`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `market_name_id`  bigint(19) COMMENT '营销名称ID',
    `market_name`  varchar(32) COMMENT '营销名称',
    `market_table`  varchar(32) COMMENT '动态创建营销对应的表',
    `tenant_id`  bigint(32) COMMENT '租户ID',
    `store_id`  bigint(32) COMMENT '店铺ID',
    `app_id`  bigint(32) COMMENT '应用ID',
    `account_id`  bigint(32) COMMENT '账号ID',
    `descr`  varchar(32) COMMENT '使用说明',
    `h5`  varchar(64) COMMENT 'h5链接',
    `start_time` datetime COMMENT '有效期/开始时间',
    `end_time` datetime COMMENT '失效期/结束时间',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='营销主题';

CREATE TABLE IF NOT EXISTS `market_actor`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `market_id`  bigint(19) COMMENT '营销ID',
    `market_typ`  int(4) COMMENT '营销类型',
    `account_id`  bigint(19) COMMENT '账号id',
    `rule_json`  varchar(128) COMMENT '营销规则',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='营销参与者';

CREATE TABLE IF NOT EXISTS `market_product`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `market_id`  bigint(19) COMMENT '营销ID',
    `product_id`  varchar(32) COMMENT '产品ID',
    `rule_json`  varchar(32) COMMENT '营销规则',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='营销关联产品';

-- 若库不存在创建一个

CREATE TABLE IF NOT EXISTS `statistics_master`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `ip`  varchar(32) COMMENT 'IP地址',
    `source`  varchar(32) COMMENT '源（推广渠道标识源）推广渠道/渠道来源',
    `phone_no`  varchar(11) COMMENT '手机号',
    `resource_id`  bigint(19) COMMENT '资源的标识ID（产品，信息等）',
    `resource_typ`  int(2) COMMENT '资源类型产品/页面/信息等',
    `behavior_typ`  int(2) COMMENT '行为类型uv/pv/cpc/cpa/cps',
    `device_id`  varchar(32) COMMENT '设备ID',
    `device_name`  varchar(32) COMMENT '设备名称',
    `os_typ`  varchar(32) COMMENT '系统类型ios/android/pc',
    `os_version`  varchar(32) COMMENT '系统版本',
    `mac`  varchar(32) COMMENT '设备MAC',
    `app_id`  varchar(32) COMMENT '应用Id',
    `account_id`  bigint(19) COMMENT '账号ID',
    `cost`  decimal(10,2) COMMENT '消耗/成本',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='统计-行为统计';

CREATE TABLE IF NOT EXISTS `statictics_promotion_config`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `source`  varchar(32) COMMENT '源（推广渠道标识源）推广渠道/渠道来源',
    `resource_id`  bigint(19) COMMENT '资源的标识ID（产品，信息等）',
    `resource_name`  varchar(32) COMMENT '资源名称',
    `promotion_price`  decimal(10,2) COMMENT '推广价格',
    `promotion_count`  decimal(10,2) COMMENT '推广数量',
    `deduct_percent`  decimal(10,2) COMMENT '扣量百分比',
    `promotion_name`  varchar(32) COMMENT '推广商名称',
    `promotion_typ`  int(2) COMMENT '推广方式:uv,cpa,cps',
    `promotion_url`  varchar(64) COMMENT '引流推广页地址',
    `user_name`  varchar(32) COMMENT '开放统计看板的用户名',
    `pwd`  varchar(32) COMMENT '开放统计看板的密码',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='统计-统计配置';

CREATE TABLE IF NOT EXISTS `statictics_promotion_link`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `indx`  int(2) COMMENT '排序',
    `resource_id`  bigint(19) COMMENT '资源ID',
    `resource_uri`  varchar(128) COMMENT '资源URI链接',
    `source`  varchar(32) COMMENT '源（推广渠道标识源）推广渠道/渠道来源',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='统计-推广链接';

CREATE TABLE IF NOT EXISTS `statictics_promotion_rate`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `price`  decimal(10,2) COMMENT '价格',
    `resource_id`  bigint(19) COMMENT '资源ID',
    `source`  varchar(32) COMMENT '源（推广渠道标识源）推广渠道/渠道来源',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='统计-推广费率';

-- 若库不存在创建一个

CREATE TABLE IF NOT EXISTS `order_cart`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `seller_account_id`  bigint(19) COMMENT '卖方账号ID',
    `seller_account_name`  varchar(32) COMMENT '卖方账号名称',
    `buyer_account_id`  bigint(19) COMMENT '买方账号ID',
    `buyer_account_name`  varchar(32) COMMENT '买方账号名称',
    `product_category_id`  bigint(19) COMMENT '商品分类',
    `product_brand`  varchar(32) COMMENT '产品品牌',
    `product_id`  bigint(19) COMMENT '产品id',
    `product_name`  varchar(32) COMMENT '商品名称',
    `product_sub_title`  varchar(256) COMMENT '商品副标题（卖点）',
    `product_attrs`  varchar(512) COMMENT '商品销售属性:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]',
    `product_pic`  varchar(128) COMMENT '商品主图',
    `product_sn`  varchar(32) COMMENT '产品sn',
    `product_sku_no`  varchar(32) COMMENT '产品skuNo',
    `product_sku_code`  varchar(32) COMMENT '商品sku条码',
    `quantity`  decimal(10,2) COMMENT '购买数量',
    `price`  decimal(10,2) COMMENT '添加到购物车的价格',
    `status`  int(1) COMMENT '状态（下单之后对应商品就不应该显示在购物车了）',
    `app_id`  bigint(19) COMMENT '应用ID',
    `store_id`  bigint(19) COMMENT '店铺ID',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单-购物车';

CREATE TABLE IF NOT EXISTS `order_master`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `seller_account_id`  bigint(19) COMMENT '卖方账号ID',
    `seller_account_name`  varchar(32) COMMENT '卖方账号名称',
    `buyer_account_id`  bigint(19) COMMENT '买方账号ID',
    `buyer_account_name`  varchar(32) COMMENT '买方账号名称',
    `order_no`  bigint(19) COMMENT '订单号',
    `order_code`  varchar(32) COMMENT '订单码',
    `order_typ`  int(2) COMMENT '订单类型：销售订单；秒杀订单，采购订单，用表名来表示......',
    `order_source`  int(2) COMMENT '订单来源：0->PC订单；1->app订单',
    `order_amount`  decimal(10,2) COMMENT '订单金额',
    `order_status`  int(2) COMMENT '订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单',
    `order_time` datetime COMMENT '下单时间',
    `pay_time` datetime COMMENT '支付时间',
    `delivery_time` datetime COMMENT '发货时间',
    `receive_time` datetime COMMENT '确认收货时间',
    `confirm_time` datetime COMMENT '自动确认时间',
    `pay_amount`  decimal(10,2) COMMENT '应付金额（实际支付金额）',
    `pay_typ`  int(2) COMMENT '支付方式：0->未支付；1->支付宝；2->微信',
    `app_id`  bigint(19) COMMENT '应用ID',
    `store_id`  bigint(19) COMMENT '店铺ID',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单-订单主体';

CREATE TABLE IF NOT EXISTS `order_sale_item`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `order_no`  bigint(19) COMMENT '订单号',
    `product_id`  bigint(19) COMMENT '产品ID',
    `product_pic`  varchar(128) COMMENT '产品图片',
    `product_name`  varchar(64) COMMENT '产品名称',
    `product_brand`  varchar(32) COMMENT '产品品牌',
    `product_price`  decimal(10,2) COMMENT '销售价格',
    `product_quantity`  decimal(10,2) COMMENT '购买数量',
    `product_sku_no`  varchar(32) COMMENT '商品sku编号',
    `product_sku_code`  varchar(32) COMMENT '商品sku条码',
    `product_category_id`  bigint(19) COMMENT '商品分类id',
    `product_attr`  varchar(128) COMMENT '商品销售属性:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]',
    `app_id`  bigint(19) COMMENT '应用ID',
    `store_id`  bigint(19) COMMENT '店铺ID',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单-销售订单项';

CREATE TABLE IF NOT EXISTS `order_swapper`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `order_no`  bigint(19) COMMENT '订单号',
    `user_name`  varchar(32) COMMENT '收/发货人姓名',
    `user_phone`  varchar(11) COMMENT '收/发货人手机',
    `user_typ`  int(2) COMMENT '类型：1:发货人，2:收货人',
    `country`  varchar(16) COMMENT '国家',
    `province`  varchar(16) COMMENT '省',
    `city`  varchar(16) COMMENT '市',
    `discint`  varchar(16) COMMENT '区/县',
    `street`  varchar(16) COMMENT '街道/办事处',
    `village`  varchar(16) COMMENT '小区/村庄',
    `adress`  varchar(16) COMMENT '收/发货人地址',
    `app_id`  bigint(19) COMMENT '应用id',
    `store_id`  bigint(19) COMMENT '店铺ID',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单-收发货方信息';

CREATE TABLE IF NOT EXISTS `物流发货信息`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `order_no`  bigint(19) COMMENT '订单号',
    `delivery_name`  varchar(16) COMMENT '配送方名称',
    `delivery_type`  int(2) COMMENT '类型：快递，物流，闪送',
    `delivery_no`  varchar(32) COMMENT '运单号',
    `delivery_fee`  decimal(10,2) COMMENT '费用',
    `delivery_time` datetime COMMENT '发货时间',
    `arrival_time` datetime COMMENT '送达时间',
    `app_id`  bigint(19) COMMENT '应用ID',
    `store_id`  bigint(19) COMMENT '店铺ID',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单-快递';

CREATE TABLE IF NOT EXISTS `order_relish`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `order_no`  bigint(19) COMMENT '订单号',
    `order_item_id`  bigint(19) COMMENT '订单项ID',
    `integration`  int(2) COMMENT '可以获得的积分',
    `growth`  int(2) COMMENT '可以活动的成长值',
    `use_integration`  int(2) COMMENT '下单时使用的积分',
    `promotion_amount`  decimal(10,2) COMMENT '促销优化金额（促销价、满减、阶梯价）',
    `integration_amount`  decimal(10,2) COMMENT '积分抵扣金额',
    `coupon_amount`  decimal(10,2) COMMENT '优惠券抵扣金额',
    `discount_amount`  decimal(10,2) COMMENT '管理员后台调整订单使用的折扣金额',
    `product_sku_no`  varchar(32) COMMENT '商品sku编号',
    `promotion_name`  varchar(32) COMMENT '商品促销名称',
    `real_amount`  decimal(10,2) COMMENT '该商品经过优惠后的分解金额',
    `gift_integration`  int(8) COMMENT '积分',
    `gift_growth`  int(8) COMMENT '成长值',
    `app_id`  bigint(19) COMMENT '应用ID',
    `store_id`  bigint(19) COMMENT '店铺ID',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单-佐料';

CREATE TABLE IF NOT EXISTS `order_invoice`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `order_no`  bigint(19) COMMENT '订单号',
    `invoice_type`  int(2) COMMENT '发票类型：0->不开发票；1->电子发票；2->纸质发票',
    `invoice_title`  varchar(4) COMMENT '发票抬头：个人/公司',
    `invoice_no`  varchar(16) COMMENT '发表税号',
    `amount`  decimal(10,2) COMMENT '开票金额',
    `remark`  varchar(32) COMMENT '说明',
    `invoice_item`  varchar(128) COMMENT '开票明细',
    `user_eamil`  varchar(32) COMMENT '用户邮箱用来接受字典发票',
    `app_id`  bigint(19) COMMENT '应用ID',
    `store_id`  bigint(19) COMMENT '店铺ID',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单-发票';

CREATE TABLE IF NOT EXISTS `order_topic`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `order_no`  bigint(19) COMMENT '单号',
    `seller_account_id`  bigint(19) COMMENT '卖方账号ID',
    `seller_account_name`  varchar(16) COMMENT '卖方账号名称',
    `buyer_account_id`  bigint(19) COMMENT '买方账号ID',
    `buyer_account_name`  varchar(16) COMMENT '买方账号名称',
    `point`  bigint(16) COMMENT '点赞数',
    `score`  int(3) COMMENT '评论分数',
    `pics`  varchar(64) COMMENT '图片列表',
    `content`  varchar(128) COMMENT '内容',
    `comment_time` datetime COMMENT '评价时间',
    `app_id`  bigint(19) COMMENT '应用ID',
    `store_id`  bigint(19) COMMENT '店铺ID',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单-评价';

CREATE TABLE IF NOT EXISTS `order_payment`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `order_no`  bigint(19) COMMENT '订单单号',
    `pay_typ`  int(2) COMMENT '支付类型',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单-支付记录表';

-- 若库不存在创建一个

CREATE TABLE IF NOT EXISTS `pay_merchant`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `account_id`  bigint(19) COMMENT '系统账号ID',
    `merchant_no`  bigint(19) COMMENT '系统为其生成商户号',
    `merchant_name`  varchar(32) COMMENT '公司名称',
    `merchant_code`  varchar(32) COMMENT '公司营业执照编码',
    `concat_name`  varchar(32) COMMENT '联系人',
    `concat_phone`  varchar(11) COMMENT '联系人手机',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付-商户';

CREATE TABLE IF NOT EXISTS `pay_merchant_app`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `account_id`  bigint(19) COMMENT '系统账号ID',
    `merchant_no`  bigint(19) COMMENT '系统为其生成商户号',
    `app_id`  bigint(19) COMMENT '应用ID',
    `app_code`  varchar(32) COMMENT '应用码',
    `app_name`  varchar(255) COMMENT '应用名',
    `app_typ`  int(2) COMMENT '应用业务类型，适配不同支付通道，可从字典获取',
    `app_url`  varchar(64) COMMENT '应用下载链接',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付-商户应用';

CREATE TABLE IF NOT EXISTS `pay_channel`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `channel_name`  varchar(64) COMMENT '通道名称',
    `channel_code`  varchar(64) COMMENT '通道码',
    `channel_url`  varchar(64) COMMENT '通道连接',
    `descr`  varchar(64) COMMENT '描述说明',
    `sotrted`  int(8) COMMENT '排序',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付-通道';

CREATE TABLE IF NOT EXISTS `pay_status_code`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `channel_id`  bigint(19) COMMENT '通道ID',
    `channel_code`  varchar(32) COMMENT '通道码',
    `channel_state_code`  varchar(32) COMMENT '通道状态码',
    `channel_state_descr`  varchar(32) COMMENT '通过状态码描述',
    `state_code`  varchar(32) COMMENT '统一状态码',
    `state_descr`  varchar(32) COMMENT '统一状态码描述',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付-状态码映射';

CREATE TABLE IF NOT EXISTS `pay_channel_bank`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `bank_code`  varchar(32) COMMENT '银行码',
    `bank_name`  varchar(32) COMMENT '银行名称',
    `channel_code`  varchar(32) COMMENT '通道码',
    `channel_id`  bigint(19) COMMENT '通道ID',
    `weight`  int(4) COMMENT '权重',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付-应用通道支持银行表';

CREATE TABLE IF NOT EXISTS `pay_directive`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `channel_id`  bigint(19) COMMENT '通道ID',
    `channel_code`  varchar(32) COMMENT '通道码',
    `directive_name`  varchar(32) COMMENT '指令名称',
    `directive_code`  varchar(32) COMMENT '指令码',
    `directive_uri`  varchar(32) COMMENT '指令URI',
    `redirect_url`  varchar(32) COMMENT '定向URL',
    `callback_url`  varchar(32) COMMENT '指令回调URL',
    `weight`  int(4) COMMENT '权重',
    `descr`  varchar(32) COMMENT '描述',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付-指令集';

CREATE TABLE IF NOT EXISTS `pay_config`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `app_id`  bigint(19) COMMENT '应用ID',
    `config_key`  varchar(32) COMMENT '配置key',
    `config_json`  varchar(128) COMMENT '配置key对应的json串',
    `descr`  varchar(32) COMMENT '描述',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付-配置';

CREATE TABLE IF NOT EXISTS `pay_merchant_channel`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `channel_id`  bigint(19) COMMENT '通道ID',
    `merchant_id`  bigint(19) COMMENT '商户ID',
    `merchant_no`  bigint(19) COMMENT '商户号',
    `private_key`  varchar(128) COMMENT '私钥',
    `public_key`  varchar(128) COMMENT '公钥',
    `secret_key`  varchar(128) COMMENT '密钥',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付-商户通道';

CREATE TABLE IF NOT EXISTS `pay_strategy`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `app_id`  bigint(19) COMMENT '应用ID',
    `app_code`  varchar(32) COMMENT '应用码',
    `channel_id`  bigint(19) COMMENT '通道ID',
    `merchant_id`  bigint(19) COMMENT '商户ID',
    `priority`  int(4) COMMENT '权重或优先级',
    `rule_id`  bigint(19) COMMENT '规则ID,这里后期借助规则引擎来实现，预留规则id,前期先简单静态，根据权重或优先级',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付-应用商户通道策略';

CREATE TABLE IF NOT EXISTS `pay_request_list`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `app_id`  bigint(19) COMMENT '应用ID',
    `user_id`  bigint(19) COMMENT '用户id',
    `amount`  decimal(10,2) COMMENT '金额',
    `bankcard`  varchar(32) COMMENT '银行卡',
    `bank_code`  varchar(32) COMMENT '银行码',
    `biz_code`  varchar(32) COMMENT '业务码',
    `biz_id`  bigint(19) COMMENT '业务id',
    `channel_code`  varchar(32) COMMENT '通道码',
    `channel_id`  bigint(19) COMMENT '通道id',
    `merchant_id`  bigint(19) COMMENT '商户id',
    `device_id`  varchar(32) COMMENT '设备ID',
    `directive_code`  varchar(32) COMMENT '指令码',
    `payment_id`  bigint(19) COMMENT '三方需要的支付流水号',
    `phone_num`  varchar(32) COMMENT '手机号',
    `protocol_id`  varchar(32) COMMENT '协议号',
    `return_code`  varchar(32) COMMENT '返回码',
    `return_msg`  varchar(32) COMMENT '返回信息',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付-请求记录';

CREATE TABLE IF NOT EXISTS `pay_binded_list`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `app_id`  bigint(19) COMMENT '应用ID',
    `channel_id`  bigint(19) COMMENT '通道ID',
    `user_id`  bigint(19) COMMENT '用户ID',
    `idcard`  varchar(32) COMMENT '身份证号',
    `user_name`  varchar(32) COMMENT '用户名',
    `bankcard`  varchar(32) COMMENT '银行卡',
    `bank_code`  varchar(32) COMMENT '卡码',
    `phone`  varchar(12) COMMENT '预留手机号',
    `protocol_id`  varchar(12) COMMENT '协议id',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付-绑卡记录';

CREATE TABLE IF NOT EXISTS `pay_response_list`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `app_id`  bigint(19) COMMENT '应用ID',
    `user_id`  bigint(19) COMMENT '用户id',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付-响应记录';

-- 若库不存在创建一个

-- 若库不存在创建一个

CREATE TABLE IF NOT EXISTS `form_definition`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `category`  varchar(32) COMMENT '表单分类',
    `form_name`  varchar(32) COMMENT '表单名称',
    `form_code`  varchar(32) COMMENT '表单code,用作页面表单',
    `table_name`  varchar(32) COMMENT '关联数据表',
    `field_id`  bigint(19) COMMENT '表单部署字段ID',
    `layout_id`  bigint(19) COMMENT '表单部署布局ID',
    `version`  int(2) COMMENT '版本',
    `descr`  varchar(64) COMMENT '表单描述',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表单定义';

CREATE TABLE IF NOT EXISTS `form_instance`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `form_id`  bigint(19) COMMENT '表单ID',
    `form_name`  varchar(32) COMMENT '表单名称',
    `form_code`  varchar(32) COMMENT '表单code,用作页面表单',
    `form_table_id`  bigint(19) COMMENT '表单对应的表ID',
    `form_table_name`  varchar(32) COMMENT '表单对应数据库中生成的table name',
    `form_descr`  varchar(32) COMMENT '表单描述',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表单实例';

CREATE TABLE IF NOT EXISTS `form_field`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `form_id`  bigint(19) COMMENT '表单Id',
    `field_id`  bigint(19) COMMENT '字段Id',
    `field_code`  varchar(32) COMMENT '字段编码，自动生成，对应到数据库中的字段名',
    `field_typ`  varchar(32) COMMENT '字段类型，对应到field_type表中的type_code',
    `field_name`  varchar(32) COMMENT '字段名称',
    `descr`  varchar(32) COMMENT '字段描述',
    `sorted`  int(2) COMMENT '字段排序',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表单字段';

CREATE TABLE IF NOT EXISTS `form_field_typ`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `typ_code`  varchar(32) COMMENT '类型code：input/checkbox/radio/select/textarea',
    `typ_name`  varchar(32) COMMENT '类型名称',
    `descr`  varchar(32) COMMENT '描述',
    `sorted`  int(2) COMMENT '排序',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表单字段类型';

CREATE TABLE IF NOT EXISTS `form_field_option`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `field_id`  bigint(19) COMMENT '字段id',
    `option_id`  bigint(19) COMMENT '选项id',
    `option_name`  varchar(32) COMMENT '选项名',
    `option_val`  varchar(32) COMMENT '选项值',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表单字段选项';

CREATE TABLE IF NOT EXISTS `form_layout`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `table_id`  bigint(19) COMMENT '表ID',
    `layout_name`  varchar(32) COMMENT '布局名称',
    `layout_code`  varchar(32) COMMENT '布局码',
    `descr`  varchar(32) COMMENT '描述',
    `editor_source_id`  bigint(19) COMMENT '原sourceId',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表单布局';

CREATE TABLE IF NOT EXISTS `from_bytearray`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`  varchar(32) COMMENT '名称',
    `content_byte`  varchar(256) COMMENT '二进制内容',
    `st` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='表单数据';

-- 若库不存在创建一个

CREATE TABLE IF NOT EXISTS `stock_sku`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `stock_type`  int(2) COMMENT '库存类型(包含：出库[销售出库/仓配出库/...] 入库[采购入库/仓配入库/...]等类型)',
    `stock_biz_type`  int(2) COMMENT '库存业务类型',
    `stock_no`  varchar(32) COMMENT '出入库单号[入/出库共用该单号]',
    `stock_time` datetime COMMENT '出入库单时间',
    `stock_postdatetime` datetime COMMENT '过账日期',
    `stock_workdatetime` datetime COMMENT '作业日期',
    `stock_closedatetime` datetime COMMENT '作业日期（关单日期）',
    `sku_code`  varchar(32) COMMENT '商品编码',
    `qty`  decimal(32) COMMENT '数量',
    `source_no`  varchar(32) COMMENT '来源单号',
    `source_code`  varchar(32) COMMENT '来源单号编码',
    `ref_no`  varchar(32) COMMENT '关联单号',
    `ref_code`  varchar(32) COMMENT '关联单号编码',
    `workshop_code`  varchar(32) COMMENT '小店编码',
    `workshop_name`  varchar(32) COMMENT '小店名称',
    `user_name`  varchar(32) COMMENT '用户名称',
    `distribution_address`  varchar(32) COMMENT '发货地点',
    `receipt_address`  varchar(32) COMMENT '收货地点',
    `is_bt`  int(1) COMMENT '是否账面直通 0否 1是',
    `is_process`  int(1) COMMENT '是否加工商品 0否 1是',
    `settle_location_is_hc`  int(1) COMMENT '结算地点是否上线DC',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存';

CREATE TABLE IF NOT EXISTS `stock_sku_item`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `id`  bigint(19) COMMENT '主键,[receiptId,orderId引用],根据类型区分',
    `stock_id`  bigint(19) COMMENT '库存单表主键',
    `stock_no`  varchar(32) COMMENT '库存单号单号',
    `sku_id`  bigint(19) COMMENT '商品表主键',
    `sku_no`  varchar(32) COMMENT '行号 原orderDetailNo',
    `sku_code`  varchar(33) COMMENT '商品编码',
    `sku_name`  varchar(34) COMMENT '商品名称',
    `sku_bar_code`  varchar(35) COMMENT '商品69码',
    `skudatetime`  varchar(36) COMMENT '商品生产日期',
    `sku_spec`  varchar(37) COMMENT '规格',
    `sku_price`  decimal(18,4) COMMENT '单价',
    `total_price`  decimal(18,4) COMMENT '明细单总金额',
    `lot_no`  varchar(32) COMMENT '批号',
    `unit_name`  varchar(32) COMMENT '单位',
    `tax_rate`  varchar(32) COMMENT '税率',
    `rate`  varchar(32) COMMENT '商品件装数',
    `is_free`  int(1) COMMENT '是否赠品 0->否 1->是',
    `is_clear`  int(1) COMMENT '是否清仓(0 否, 1是)',
    `is_process`  int(1) COMMENT '是否有加工单明细(0 否, 1是)',
    `is_discount`  int(1) COMMENT '是否打折商品,OMS下传标识',
    `is_oos`  int(1) COMMENT '是否缺货（0:否,1:是）',
    `is_joint`  int(1) COMMENT '是否联运',
    `is_spec_alloc`  int(1) COMMENT '是否规格分配',
    `order_detail_status`  int(1) COMMENT '0:过账成功,1:过账失败',
    `posting_time` datetime COMMENT '过账时间',
    `current_qty_before`  decimal(18,4) COMMENT '现货库存修改前的值',
    `current_qty_after`  decimal(18,4) COMMENT '现货库存改变值 增加为正，减少为负',
    `current_qty_change`  decimal(18,4) COMMENT '现货库存修改后的值',
    `usable_qty_before`  decimal(18,4) COMMENT '可用库存修改前的值',
    `usable_qty_after`  decimal(18,4) COMMENT '可用库存修改后的值',
    `usable_qty_change`  decimal(18,4) COMMENT '可用库存改变值 增加为正，减少为负',
    `order_qty_before`  decimal(18,4) COMMENT '预占库存修改前的值',
    `order_qty_after`  decimal(18,4) COMMENT '预占库存修改后的值',
    `order_qty_change`  decimal(18,4) COMMENT '预占库存改变值 增加为正，减少为负',
    `lock_qty_before`  decimal(18,4) COMMENT '锁定库存修改前的值',
    `lock_qty_after`  decimal(18,4) COMMENT '锁定库存修改后的值',
    `lock_qty_change`  decimal(18,4) COMMENT '锁定库存改变值 增加为正，减少为负',
    `transit_qty_before`  decimal(18,4) COMMENT '在途库存修改前的值',
    `transit_qty_after`  decimal(18,4) COMMENT '在途库存修改后的值',
    `transit_qty_change`  decimal(18,4) COMMENT '在途库存改变值 增加为正，减少为负',
    `warehouse_id`  bigint(255) COMMENT '仓库表主键',
    `workshop_code`  varchar(32) COMMENT '小店编码',
    `workshop_name`  varchar(32) COMMENT '小店名称',
    `comments`  varchar(32) COMMENT '备注',
    `expected_qty`  decimal(18,4) COMMENT '计划数量',
    `received_qty`  decimal(18,4) COMMENT '实收数量',
    `qty_ordered`  decimal(18,4) COMMENT '数量',
    `real_qty_ordered`  decimal(18,4) COMMENT '实际计划数量',
    `qty_outbound`  decimal(18,4) COMMENT '出库量',
    `qty_allocated`  decimal(18,4) COMMENT '已分配量',
    `qty_remain_ordered`  decimal(18,4) COMMENT '剩余需求数量, 即: 数量-已分配数量',
    `qty_picked`  decimal(18,4) COMMENT '已拣货量',
    `qty_remain_picked`  decimal(18,4) COMMENT '剩余拣货数量, 即: 已分配数量-已拣货数量',
    `qty_checked`  decimal(18,4) COMMENT '已复核量',
    `qty_shipped`  decimal(18,4) COMMENT '已发货量',
    `now_qty_allocated`  decimal(18,4) COMMENT '当前分配数量 - 波次分配使用 - 未持久化',
    `now_qty_picked`  decimal(18,4) COMMENT '当前已拣货数量 - 容器拣货使用 - 未持久化',
    `surplus_qty`  decimal(18,4) COMMENT '剩余需要分配数量',
    `rep_num`  decimal(18,4) COMMENT '需要补货的数量',
    `qty_ordered_total`  decimal(18,4) COMMENT '总数量',
    `qty_allocated_total`  decimal(18,4) COMMENT '总已分配量',
    `qty_picked_total`  decimal(18,4) COMMENT '总已拣货量',
    `purchasing_group`  varchar(32) COMMENT '课组',
    `purchasing_group_name`  varchar(32) COMMENT '课组名',
    `picking_seq`  int(19) COMMENT '拣货动线顺序',
    `alloc_location_code`  varchar(32) COMMENT '商品默认拣选面',
    `qty_force`  decimal(255) COMMENT '强制完成拣货任务数量',
    `department_category_code`  varchar(32) COMMENT '部类',
    `big_category_code`  varchar(32) COMMENT '大类',
    `middle_category_code`  varchar(32) COMMENT '中类',
    `small_category_code`  varchar(32) COMMENT '小类',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存明细';

CREATE TABLE IF NOT EXISTS `stock_master`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `id`  bigint(19) COMMENT '主键自增',
    `sku_id`  bigint(19) COMMENT '商品编号',
    `store_no`  varchar(32) COMMENT '仓店编号(规则id，店仓类型：1-实体店；2-前置仓；3-前店后仓；4-线上店
如：100000001，200000001，......',
    `store_typ`  int(2) COMMENT '店仓类型：1-普通店；2-前置仓',
    `vender_no`  varchar(32) COMMENT '商家编号',
    `current_qty`  decimal(18,4) COMMENT '现货库存（仓库/门店存放的实际库存按照SKU、库房、库存状态汇总的数量；）',
    `usable_qty`  decimal(18,4) COMMENT '可用库存（可用库存根据实物库存、预占库存和锁定库存计算而来，是分店仓和销售库存计算的依据。可用库存=实物库存-预占库存-锁定库存（注：若库存明细被锁定，则此条明细不用作可用库存计算））',
    `order_qty`  decimal(18,4) COMMENT '预占库存（发货之前对库存预占，防止超卖。分为销售出库预占、退供预占等；）',
    `lock_qty`  decimal(18,4) COMMENT '锁定库存（商品尚在库房，需临时锁定的库存。如：盘点临时锁定/临期锁定/盘点差异/其它原因）',
    `transit_qty`  decimal(18,4) COMMENT '在途库存（即将入库的库存，含调拨未入库库存、采购未到货库存；待实物上架以后，清除在途库存）',
    `stock_status`  int(255) COMMENT '库存状态（合格/不合格/停售/待验）',
    `ts` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存';

CREATE TABLE IF NOT EXISTS `stock_item`(
    `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `id`  bigint(19) COMMENT '主键自增',
    `sku_id`  bigint(19) COMMENT '商品编号',
    `store_no`  varchar(32) COMMENT '门店编号',
    `vender_no`  varchar(32) COMMENT '商家编号',
    `current_qty_before`  decimal(18,4) COMMENT '现货库存修改前的值',
    `current_qty_after`  decimal(18,4) COMMENT '现货库存改变值 增加为正，减少为负',
    `current_qty_change`  decimal(18,4) COMMENT '现货库存修改后的值',
    `usable_qty_before`  decimal(18,4) COMMENT '可用库存修改前的值',
    `usable_qty_after`  decimal(18,4) COMMENT '可用库存修改后的值',
    `usable_qty_change`  decimal(18,4) COMMENT '可用库存改变值 增加为正，减少为负',
    `order_qty_before`  decimal(18,4) COMMENT '预占库存修改前的值',
    `order_qty_after`  decimal(18,4) COMMENT '预占库存修改后的值',
    `order_qty_change`  decimal(18,4) COMMENT '预占库存改变值 增加为正，减少为负',
    `lock_qty_before`  decimal(18,4) COMMENT '锁定库存修改前的值',
    `lock_qty_after`  decimal(18,4) COMMENT '锁定库存修改后的值',
    `lock_qty_change`  decimal(18,4) COMMENT '锁定库存改变值 增加为正，减少为负',
    `transit_qty_before`  decimal(18,4) COMMENT '在途库存修改前的值',
    `transit_qty_after`  decimal(18,4) COMMENT '在途库存修改后的值',
    `transit_qty_change`  decimal(18,4) COMMENT '在途库存改变值 增加为正，减少为负',
    `stock_typ`  int(2) COMMENT '仓存类型，1：入库，2：修改现货库存，3：修改锁定库存，4：下单',
    `stock_source`  int(2) COMMENT '库存来源，1：库存系统，2：订单中心，3：其他...',
    `order_id`  bigint(19) COMMENT '订单号（2C/2B，订单号可以区分）',
    `ts` datetime COMMENT '时间戳',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存明细';

-- 若库不存在创建一个

-- 若库不存在创建一个

