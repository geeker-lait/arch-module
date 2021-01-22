package com.unichain.pay.sharelink.utils;


import org.apache.commons.lang3.StringUtils;

public final class R {

    /**
     * 一次读取（数据库、文件）最大的内容条数
     */
    public static final int MAX_NUMBER_OF_READ = 500;
    /**
     * 一次写入（数据库、文件）最大的内容条数
     */
    public static final int MAX_NUMBER_OF_WRITE = 500;
    /**
     * 一次进行账务核对的内容条数
     */
    public static final int MAX_NUMBER_OF_CHECK = 500;
    /**
     * 1
     */
    public static final int ONE = 1;
    /**
     * 0
     */
    public static final int ZERO = 0;

    public static final int MAX_BATCH_COLLECT_PAY = 10000;
    public static final String SIGN_TIME_OUT_CODE = "0904";

    /**
     * Function : 证通系统参数 <br/>
     * Date : 2015年6月3日 下午9:26:49 <br/>
     *
     * @author xueaoran
     * @version R
     * @since JDK 1.7
     */
    public enum ECTSystemEnum {
        /**
         * 支付网关
         */
        ECT_SYSTEM_PGW("1011", "PGW"),
        /**
         * 支付转接清算系统
         */
        ECT_SYSTEM_TACS("5001", "TACS"),
        /**
         * 支付转接清算系统
         */
        ECT_SYSTEM_CAS("4001", "CAS"),
        /**
         * 账户前置
         */
        ECT_SYSTEM_PTS("2002", "PTS"),
        /**
         * 商户平台
         */
        ECT_SYSTEM_MSS("1011", "MSS");

        private String value;

        private String label;

        ECTSystemEnum(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }

    /**
     * Function : 用户账号类型定义<br/>
     * Date : 2015年5月20日 下午5:34:59 <br/>
     *
     * @author WANGLONG
     * @version DictConst
     * @since JDK 1.7
     */
    public enum PGWUserAcctTypeEnum {
        /**
         * 用户ID
         */
        ACCT_TYPE_USERID("0", "用户ID"),
        /**
         * 手机号码
         */
        ACCT_TYPE_MOBILE("1", "手机号码"),
        /**
         * 邮箱
         */
        ACCT_TYPE_EMAIL("2", "邮箱");

        private String value;

        private String label;

        PGWUserAcctTypeEnum(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }

    /**
     * Function : 交易账户类型定义<br/>
     * Date : 2015年5月20日 下午5:28:54 <br/>
     *
     * @author WANGLONG
     * @version DictConst
     * @since JDK 1.7
     */
    public enum PGWBindAcctTypeEnum {

        ACCT_TYPE_BORROW("90", "储蓄卡"),
        ACCT_TYPE_CREDIT("91", "信用卡"),
        ACCT_TYPE_PREPAY("92", "预付费卡"),
        ACCT_TYPE_ZTB("94", "宝宝账户"),
        ACCT_TYPE_ACCOUNT("95", "零钱账户"),
        ACCT_TYPE_OTHER("99", "其他"),
        ACCT_TYPE_TRADE("01", "资金账户"),
        ACCT_TYPE_CACCOUNT("02", "信用资金账户"),
        ACCT_TYPE_DERIVE("03", "衍生品账户"),
        ACCT_TYPE_FINANCE("04", "理财账户"),
        ACCT_TYPE_FUND("20", "基金账户"),
        ACCT_TYPE_FUTURES("30", "期货保证金"),
        /**
         * 零钱账户
         */
        ACCT_TYPE_POCKET("A1", "零钱账户"),
        /**
         * 宝宝账户
         */
        ACCT_TYPE_BAOBAO("A2", "宝宝账户"),

        ACCT_TYPE_BANK_LIST("90,91,92", "银行类"),
        ACCT_TYPE_TRADE_LIST("01,02,03,04,20,30", "证券账户类");

        private String value;

        private String label;

        PGWBindAcctTypeEnum(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public static boolean isAccountType(String aType, PGWBindAcctTypeEnum accType) {
            for (String accTypes : accType.getValue().split(",")) {
                if (accTypes.equalsIgnoreCase(aType)) {
                    return true;
                }
            }
            return false;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

    }

    /**
     * Function : PGW错误定义<br/>
     * Date : 2015年5月20日 下午5:34:59 <br/>
     *
     * @author WANGLONG
     * @version DictConst
     * @since JDK 1.7
     */
    public enum PGWErrorEnum {

        /**
         * 操作成功
         */
        SUCCESS("10110000", "操作成功"),
        /**
         * 系统内部异常
         */
        ERROR_SYSTEM("10110910", "其他系统错误"),
        /**
         * 操作数据库发生异常
         */
        ERROR_DATABASE("10110418", "操作数据库发生异常"),
        /**
         * 处理超时
         */
        ERROR_TIMEOUT("10110904", "交易已受理，稍后查询结果。请勿重复提交支付"),
        /**
         * 交易信息错误定义
         */
        ERROR_TRADE_ACCTNO("E00010001", ""),
        /**
         * 没有找到相应支付订单
         */
        ERROR_NO_PAYORDER("10119075", "没有找到相应支付订单"),
        /**
         * 支付订单更新失败
         */
        ERROR_UPDATE_PAYORDER("10119077", "支付订单更新失败"),
        /**
         * 创建支付订单失败
         */
        ERROR_INSERT_PAYORDER("10119076", "创建支付订单失败"),
        /**
         * 调用历史查询系统接口失败
         */
        ERROR_QUERY_TRADE("101110802", "调用历史查询系统接口失败"),

        /**
         * 传入参数不合法
         */
        ERROR_INPUT_PARAM("10110907", "传入参数不合法"),
        /**
         * 商户号为空
         */
        ERROR_MEMR_NO_NULL("10119112", "商户号为空"),
        /**
         * 非本人订单
         */
        ERROR_NOT_SELF_ORDER("10119113", "非本人订单"),
        /**
         * 用户信息查询失败
         */
        ERROR_QUERY_USER_INFO("10119021", "未查询到相关用户信息"),
        /**
         * 支付订单状态不合法
         */
        ERROR_PAY_ORDER_STATUS("10119114", "支付订单状态不合法"),
        /**
         * 绑卡失败
         */
        ERROR_BIND_CARD("10119037", "绑定失败"),
        /**
         * 更改密码出错
         */
        ERROR_CHANGE_PASSWORD("10119005", "更新支付密码操作失败"),
        /**
         * 交易已完成或已经关闭
         */
        ERROR_PAYORDER_CLOSE("10119078", "交易正在进行中或已经关闭"),
        /**
         * 通用借贷记操作异常
         */
        ERROR_DEBITCREDIT_ERROR("10119079", "通用借贷记操作异常"),
        /**
         * 通用借贷标志位异常
         */
        ERROR_DCFLAG_ERROR("10119080", "通用借贷标志位异常"),
        /**
         * 支付订单状态不合法
         */
        ERROR_TRANSFER_STATUS("10119115", "转账状态不合法"),
        /**
         * 未获取到机构权限信息
         */
        ERROR_GET_AUTHRTYSGNCD("10110402", "未获取到机构权限信息"),
        /**
         * 未获取到该机构的权限信息
         */
        ERROR_ORGAN_AUTH("10119116", "当前机构不支持该交易"),
        /**
         * 付款账户与收款账户不能相同
         */
        ERROR_DIFFERENT("10110120", "付款账户与收款账户不能相同"),
        /**
         * 短信验证码插入失败
         */
        ERROR_INSERT_MSGDYNAMIC("10110403", "短信验证码插入失败"),
        /**
         * 短信验证码更新失败
         */
        ERROR_UPDATE_MSGDYNAMIC("10110404", "短信验证码更新失败"),

        /**
         * 登陆手机号为空
         */
        ERROR_LOGINID_ISNULL("10119100", "手机号码为空"),
        /**
         * 用户号为空
         */
        ERROR_USERID_ISNULL("10119117", "用户号为空"),
        /**
         * 手机号格式不正确
         */
        ERROR_MOBILE("10119108", "手机号码格式错误"),

        /**
         * 更新交易转账表失败
         */
        ERROR_UPDATE_TRANSFER("10110404", "更新交易转账表失败"),
        /**
         * 插入转账信息失败
         */
        ERROR_INSERT_TRANSFER("10110403", "插入转账信息失败"),
        /**
         * 没有找到相应的转账信息
         */
        ERROR_NO_TRANSFER("10110402", "没有找到相应的转账信息"),

        /**
         * 该手机号未发送验证码
         */
        ERROR_MSGDYNAMIC_ISNOTSEND("10110402", "该手机号未发送验证码"),
        /**
         * 动态密码不能为空
         */
        ERROR_MSGDYNAMIC_ISNULL("10119118", "动态密码不能为空"),
        /**
         * 动态密码错误,非六位的数字
         */
        ERROR_MSGDYNAMIC_NOTSIXNUM("10119119", "动态密码错误,非四位的数字"),

        /**
         * 支付订单号不能为空
         */
        ERROR_ORDER_PAYID("10119120", "支付订单号不能为空"),

        /**
         * 动态密码超时失效
         */
        ERROR_MSGDYNAMIC_TIMEOUT_INVALID("10119121", "短信验证码超过有效时间"),
        /**
         * 短信验证码没有超出间隔时间
         */
        ERROR_MSGDYNAMIC_VERIFY_PERIOD("10119122", "短信验证码没有超出间隔时间"),
        /**
         * 短信验证码功能类型错误
         */
        ERROR_MSGDYNAMIC_FUNC_TYPE("10119123", "短信验证码的功能类型错误"),
        /**
         * 短信验证码功能类型为空
         */
        ERROR_MSGDYNAMIC_FUNC_TYPE_ISNULL("10119124", "短信验证码的功能类型为空"),
        /**
         * 动态密码验证失效
         */
        ERROR_MSGDYNAMIC_INVALID("10119125", "动态密码验证失效，请重新发送"),
        /**
         * 动态密码验证验证次数超出系统设定
         */
        ERROR_MSGDYNAMIC_MORE_SEND("10119126", "动态密码验证验证次数超出系统设定"),

        /**
         * 交易类型与数据库中交易类型不符
         */
        ERROR_ORDER_TYPE_INPUT("10119127", "传入交易类型与实际交易类型不符"),
        /**
         * 支付密码验证失败
         */
        ERROR_CHECK_PAYPASSWD("10111007", "支付密码验证失败"),
        /**
         * 获取绑卡信息失败
         */
        ERROR_GET_BIND_CARD("10119039", "用户没有绑定卡信息"),
        ERROR_SMS_SEND_FAILED("10119128", "短信发送失败"),

        /**
         * 产品订单状态不合法
         */
        ERROR_PRODUCT_ORDER_STATUS("10119129", "订单已支付或已经关闭"),
        /**
         * 没有找到相应产品订单
         */
        ERROR_NO_PRODUCT_ORDER("10110402", "没有找到相应产品订单"),
        /**
         * 产品订单更新失败
         */
        ERROR_UPDATE_PRODUCT_ORDER("10110404", "产品订单更新失败"),
        /**
         * 产品订单插入失败
         */
        ERROR_INSERT_PRODUCT_ORDER("10110403", "产品订单插入失败"),

        /**
         * 支付转接请求流水失败
         */
        ERROR_NO_TRANS_REQ("10110402", "没有找到相应的请求流水"),
        ERROR_UPDATE_TRANS_REQ("10110404", "请求流水更新失败"),
        ERROR_INSERT_TRANS_REQ("10110403", "请求流水插入失败"),
        ERROR_INSERT_DELAY_PROD_ORDER("10110403", "延时产品订单入库失败"),
        ERROR_INSERT_PROD_ORDER("10110403", "普通产品订单入库失败"),
        //有统一的数据库相关的错误码。

        /**
         * 查询手续费规则失败失败
         */
        ERROR_QYERY_FEE_RULE_FAIL("10110402", "手续费规则查询失败"),
        ERROR_GET_SMS_LIMIT("10110402", "没有查到短信限额记录"),

        /**
         * 生成支付转接订单编码失败
         */
        ERROR_GET_SYSDATE("10110402", "读取系统日期失败"),
        /**
         * 生成支付转接订单编码失败
         */
        ERROR_GET_PAY_ORDER("10110106", "获取支付订单编码失败"),
        ERROR_GEN_REVERT_ORDER("10110403", "生成业务撤销订单失败"),
        /**
         * 用于用户限额查询
         */
        ERROR_GET_AMOUNT_LIMIT("10119081", "没有查到对应的限额规则"),
        ERROR_UPDATE_MERC_AMT_REC("10110404", "商户限额记录更新失败"),
        /**
         * 查询cardBin表失败
         */
        ERROR_QUERY_FAILED("10110402", "卡bin表查询异常"),
        ERROR_QUERY_CARDBIN("10110402", "没有查询到卡bin信息"),

        ERROR_OUT_72_HOURS("10119130", "收款超过时效"),
        ERROR_OUT_24_HOURS("10119131", "订单超出时效"),

        /*******start 运营平台用户、机构同步 异常定义*********/
        /**
         * 用户信息同步： 用户编号为空
         */
        ERROR_SYNCOPERATION_USERCD_NULL("10119132", "用户编号为空"),
        /**
         * 用户信息同步： 操作类型异常
         */
        ERROR_SYNCOPERATION_OPTFLAG_ERROR("10119133", "同步操作类型未识别"),
        /**
         * 用户信息同步： 保存用户基本信息异常
         */
        ERROR_SYNCOPERATION_ADD_BASEMSG_ERROR("10119055", " 保存用户基本信息异常"),
        /**
         * 用户信息同步： 保存用户登录信息异常
         */
        ERROR_SYNCOPERATION_ADD_LOGINMSG_ERROR("10119056", "保存用户登录信息异常"),
        /**
         * 用户信息同步： 修改用户基本信息异常
         */
        ERROR_SYNCOPERATION_MODIFY_BASEMSG_ERROR("10119058", " 修改用户基本信息异常"),
        /**
         * 产品订单更新失败
         */
        ERROR_UPDATE_TRANFER_ORDER("10110404", "更新实时转账产品订单失败"),
        /**
         * 产品订单更新失败
         */
        ERROR_UPDATE_DELAY_ORDER("10110404", "更新延时转账产品订单失败"),
        /**
         * 用户信息同步： 修改用户登录信息异常
         */
        ERROR_SYNCOPERATION_MODIFY_LOGINMSG_ERROR("10119057", "修改用户登录信息异常"),
        /**
         * 用户信息同步： 删除用户基本信息异常
         */
        ERROR_SYNCOPERATION_DELETE_BASEMSG_ERROR("10119059", " 删除用户基本信息异常"),
        /**
         * 用户信息同步： 删除用户登录信息异常
         */
        ERROR_SYNCOPERATION_DELETE_LOGINMSG_ERROR("10119060", "删除用户登录信息异常"),
        /**
         * 用户信息同步： 新增用户角色关联信息异常
         */
        ERROR_SYNCOPERATION_ADD_USERROLE_ERROR("10119061", "新增用户角色关联信息异常"),
        /**
         * 用户信息同步： 删除用户角色关联信息异常
         */
        ERROR_SYNCOPERATION_DELETE_USERROLE_ERROR("10119062", "删除用户角色关联信息异常"),
        /**
         * 商户信息插入失败
         */
        ERROR_INSERT_MERCHANT_INFO("10110403", "商户信息插入失败"),
        /**
         * 商户信息更新失败
         */
        ERROR_UPDATE_MERCHANT_INFO("10110404", "商户信息更新失败"),
        /**
         * 商户附件信息插入失败
         */
        ERROR_INSERT_MERCHANT_ATTACH("10110403", "商户附件信息插入失败"),
        /**
         * 商户附件信息更新失败
         */
        ERROR_UPDATE_MERCHANT_ATTACH("10110404", "商户附件信息更新失败"),
        /**
         * 登录名已经存在
         */
        ERROR_LOGIN_ID_EXISTS("10119134", "登录名已经存在"),

        /*******end 运营平台用户、机构同步 异常定义*********/
        /*******交易金额超过单笔规定金额*********/
        ERROR_TRAN_AMT_LIMIT_SINGLEAL("10111037", "交易金额超过单笔规定金额"),
        /*******当日业务累计金额超过规定金额*********/
        ERROR_TRAN_AMT_LIMIT_DAYAL("10111036", "当日业务累计金额超过规定金额"),
        /*******用户交易金额超过月累计限额*********/
        ERROR_TRAN_AMT_LIMIT_MOUTHAL("10119135", "用户交易金额超过月累计限额"),
        /**
         * 交易金额超过限额
         */
        ERROR_TRAN_AMT_LIMIT("10119191", "交易金额超过限额"),

        /*******交易笔数超过当日上限*********/
        ERROR_TRAN_COUNTER_LIMIT_DAYAL("10119202", "交易笔数超过当日上限"),
        /*******交易笔数超过月累计上限*********/
        ERROR_TRAN_COUNTER_LIMIT_MOUTHAL("10119203", "交易笔数超过月累计上限"),
        /**
         * 交易笔数超过上限
         */
        ERROR_TRAN_COUNTER_LIMIT("10119204", "交易笔数超过上限"),

        ERROR_TRAN_AMT_LIMIT_SINGLEAL_MERC("10111038", "商户交易金额超过单笔规定金额"),
        ERROR_TRAN_AMT_LIMIT_DAYAL_MERC("10111039", "商户当日业务累计金额超过规定金额"),
        ERROR_TRAN_AMT_LIMIT_MOUTHAL_MERC("10119136", "商户交易金额超过月累计限额"),


        ERROR_AMT("10110125", "交易金额格式不正确"),
        ERROR_NOT_SAME_NAME_LIMIT("10119137", "非同名卡不能交易"),
        ERROR_DIFFER_AMT("10110125", "上传的金额与数据库保存的金额不一致"),
        ERROR_TRAN_AMT_MIN("10119138", "交易金额小于最低交易金额{0}"),
        ERROR_FILE_UPLOAD("10120212", "文件上传失败"),
        ERROR_UPDATE_MERC_INFO("10110404", "更新商户信息失败"),
        ERROR_QUERY_MERC_INFO("10110402", "查询商户基本信息失败"),
        ERROR_ACCT_TYPE_LIMIT("10119139", "只允许借记卡支付"),
        ERROR_ACCT_TYPE_LIMIT_ALL("10111016", "账户不支持此类交易"),
        /**
         * 用户风险级别查询失败
         */
        ERROR_QUERY_USER_GRADE("10119140", "用户风险级别查询失败"),
        ERROR_QUERY_USER_INFO_NULL("10119021", "未查询到相关用户信息"),
        ERROR_QUERY_USER_GRADE_NULL("10119141", "没有查到用户风险级别"),

        /**记账订单流水获取失败*/
        //ERROR_INSERT_ACCT_REQ("E00170001", "记账订单流水获取失败"),
        /**
         * 记账订单插入失败
         */
        ERROR_INSERT_ACCT_ORDER("10110403", "记账订单插入失败"),
        /**
         * 没有记账订单失败
         */
        ERROR_NO_ACCT_ORDER("E00170003", "没有记账订单失败"),
        /**
         * 记账明细错误
         */
        ERROR_ACCT_DETAIL("10119142", "记账明细错误"),
        /**
         * 记账订单更新失败
         */
        ERROR_UPDATE_ACCT_ORDER("10110404", "记账订单更新失败"),

        /** 审核任务基本信息录入失败*/
        //ERROR_INSERT_AUDIT_TASK("E00080001","审核任务信息录入失败"),
        /** 审核任务详细信息录入失败*/
        //ERROR_INSERT_AUDIT_DETAIL("E00080002","审核任务详细信息录入失败"),

        /**
         * 处理转接系统变更通知失败
         */
        ERROR_NOTIFY_SYS_STAT_CHANGE("10110404", "处理转接系统变更通知失败"),
        /**
         * 处理转接文件下发通知失败
         */
        ERROR_NOTIFY_DOC_DISTRIBUTION("10119143", "处理转接文件下发通知失败"),
        /**
         * 查询延迟转账产品订单信息失败
         */
        ERROR_QUERY_DELAY_PRODUCT_INFO("10119144", "查询延迟转账产品订单信息失败"),
        /**
         * 查询交易清分信息失败
         */
        ERROR_QUERY_SETTLE_TRANS("10110402", "查询交易清分信息失败"),
        /**
         * 更新延迟转账退款结果失败
         */
        ERROR_UPDATE_REFUND_DELAY_RESULT("10110404", "更新延迟转账退款结果失败"),
        /**
         * 更新延迟转账退款结果失败
         */
        ERROR_DELAY_TRANSFER_REFUND("10110402", "延迟转账退款异常"),
        /**
         * 不存在的结算类型
         */
        ERROR_SETTLE_TYPE_NOT_EXISTS("10119145", "不存在的结算类型"),
        /**
         * 划账流水插入失败
         */
        ERROR_INSERT_SETTLE_TRANSFER_REC("10110403", "划账流水插入失败"),
        /**
         * 备付金操作失败
         */
        ERROR_PROVISION_OPERATION("10119146", "备付金操作失败"),
        /**
         * 创建会计分录失败
         */
        ERROR_CREATE_ACCOUNTING_ENTRY("10110402", "创建会计分录失败"),
        /**
         * 交易记录对账失败
         */
        ERROR_CHECK_TRAN_RECORDS("E000190006", "交易记录对账失败"),
        /**
         * 插入计费信息失败
         */
        ERROR_INSERT_CCS_RECORDS("10110403", "插入计费信息失败"),
        /**
         * 计费对账文件格式错误
         */
        ERROR_CCS_CHECK_FILE_FORMAT("10110207", "计费对账文件格式错误"),
        /**
         * 清分交易失败
         */
        ERROR_CLEAR_TRANS("10119147", "清分交易失败"),
        /**
         * 商户结算失败
         */
        ERROR_MERC_SETTLEMENT("10119148", "商户结算失败"),
        /**
         * 获取结算分录关系流水失败
         */
        ERROR_INSERT_SETT_ACCT_SEQ("10110402", "获取结算划账关系流水失败"),
        /**
         * 划账流水插入失败
         */
        ERROR_INSERT_ACCT_ENTRY_REC("10110403", "分录流水插入失败"),
        /**
         * 获取计费对账文件失败
         */
        ERROR_GET_CCS_CHECK_FILE("10110206", "获取计费对账文件失败"),

        /**
         * 延迟转账收款人信息不匹配
         */
        ERROR_PAYEE_INFO("E00200001", "延迟转账收款人信息不匹配"),
        /**
         * 收款人登记薄查询失败
         */
        ERROR_QUERY_TRANSFER_PAYEE_REC("10110402", "收款人登记薄查询失败"),
        /**
         * 收款人登记薄修改失败
         */
        ERROR_UPDATE_TRANSFER_PAYEE_REC("10119192", "更新收款人登记薄失败"),
        /**
         * 收款人登记薄查询每页条数过大
         */
        ERROR_QUERY_TRANSFER_MAX_PAGE("10119149", "收款人登记薄查询每页条数过大"),
        /**
         * 收款人登记簿序号为空
         */
        ERROR_EMPTY_PAYEE_ORGAN_NO("10119150", "收款人登记簿序号为空"),
        /**
         * 收款人姓名为空
         */
        ERROR_EMPTY_PAYEE_NAME("10119151", "收款人姓名为空"),
        /**
         * 收款人姓名为空
         */
        ERROR_NO_TEMPLATE("10119152", "无此交易类型模版"),
        ERROR_MSGDYNAMIC_INSERT("10110403", "新增短信验证码失败"),

        /**
         * 系统日期错误
         */
        ERROR_SYSDATE("10110901", "系统日期未配置"),

        ERROR_BLACK_LIST("10119069", "支付账户状态异常，详情请咨询证通财富客服"),

        ERROR_NO_TASK("10119193", "没有找到任务记录"),

        TRADE_INFO_WRONG_ENCRYPT("10119190", "系统繁忙，请稍后再试"),

        IDENTIFY_TIME_OUT("10119188", "认证有效性校验超时"),

        IDENTIFY_FAIL("10119189", "认证失败"),

        /**
         * 小微封升级失败
         */
        ERROR_UPDATE_TASK_FAIL("10119194", "小微封任务升级失败"),

        /**
         * 新增外转内用户信息记录失败
         */
        ADD_TRANSFERINFO_FAILED("E00204001", "新增外转内用户信息记录失败"),
        /**
         * 已超过非同名人数限制
         */
        EXCEED_SAME("E00400004", "已超过此交易非同名人数限制"),

        /**
         * 超过每月最大手机号转账次数
         */
        MONTH_DELAY_MAX_TRAN_TIMES_ERROR("10119198", "超过每月最大手机号转账次数"),

        /**
         * 超过每日最大手机号转账次数
         */
        DAY_DELAY_MAX_TRAN_TIMES_ERROR("10119199", "超过每日最大手机号转账次数"),

        /**
         * 超过每月普通转账次数
         */
        MONTH_MAX_TRAN_TIMES_ERROR("10119200", "超过每月最大转账次数"),

        /**
         * 超过每日普通转账次数
         */
        DAY_MAX_TRAN_TIMES_ERROR("10119201", "超过每日最大转账次数"),

        /**
         * 当前交易不在交易时间内
         */
        IS_NOTSURPPOR_TIMEVALUE("10111100", "当前账户的交易不在交易时间内"),
        /**
         * 当前交易不在交易时间内：节假日
         */
        IS_NOTSURPPOR_HOLIDAYS("10111101", "当前账户的交易不在交易时间内"),

        /**
         * 账户验证出现异常
         */
        ERROR_USERBIND_ACCOUNTVAL_EXCEPTION("10119040", "账户验证出现异常"),

        /**
         * 账户验证失败
         */
        ERROR_USERBIND_ACCOUNTVAL_FAIL("10119041", "账户验证失败"),

        /**
         * 获取账户类型失败
         */
        ERROR_USERAUTH_CHANNAL_FIAL("10119084", "获取账户类型失败"),

        /**
         * 快捷支付验签失败
         */
        ERROR_QUICK_VERIFY_FIAL("E230100001", "快捷支付验签失败"),

        /**
         * 快捷支付加签异常
         */
        ERROR_QUICK_SIGN_EXCEPTION("E230109998", "快捷支付请求加签异常"),

        /**
         * 快捷支付验签异常
         */
        ERROR_QUICK_VERIFY_EXCEPTION("E230109999", "快捷支付请求验签异常"),

        /**
         * 查询用户绑定卡信息失败
         */
        ERROR_USERBIND_QUERY_FAIL("10339085", "查询用户绑定卡信息失败"),

        /**
         * 机构号不一致
         */
        ERROR_USERCERT_DIFFERENT_ORGANNO("10339187", "机构号与之前存在的机构号不一致"),

        /**
         * 绑定失败
         */
        ERROR_USERBIND_FAIL("10339037", "绑定失败"),

        /**
         * 账户验证
         */
        ERROR_QUICK_VALAUTE_ACCOUNT_FIAL_1("10339041", "账户验证不通过"),/* cif转译的 */
        ERROR_QUICK_VALAUTE_ACCOUNT_FIAL_2("90020910", "账户验证不通过"),/* 核心返回的  */

        BONUS_DEDUCTION_FAIL("10112001", "积分抵扣失败，请重新选择交易"),

        BONUS_DEDUCTIONMON_GRETHAN_FEEMON("10112002", "积分抵扣金额不能大于交易手续费金额"),

        BONUS_ACCEPT_DATA_EXCEPTION("10112003", "交易失败，接收数据异常"),

        ERROR_VIDEO_IDENTIFY_TIMEOUT("E02134909", "视频认证结果查询超时"),

        ERROR_VIDEO_IDENTIFY_FAIL("E02134909", "视频认证结果查询超时"),

        ERROR_REPLACE_REVOKE("E02134910", "此订单已经撤消或正在处理中，请勿重复提交"),

        ERROR_NOT_SUPPORT("E02134911", "暂不支持该功能"),

        ERROR_STATUS_SUPPORT("E02134912", "退款订单不合法"),

        ERROR_NOT_PERMISS("E02134912", "不是所属商户没有权限"),

        ERROR_BOND_BIND_RELATION("E111111", "账户关系不正确"),

        ERROR_PARA_NO_EXISTS("10119153", "系统管理参数不存在"),

        ERROR_SIGN_DATA_NO_EXISTS("10119154", "签名对象为空"),

        ERROR_PROVISION_NOT_ENOUGH("10119155", "备付金可用余额不足"),

        ERROR_GENERATE_FILE_ERROR("10119156", "生成代付请求文件失败"),

        ERROR_REQUEST_SUM_ZERO("10119157", "代付文件汇总笔数或汇总金额为0，请重新检查"),

        ERROR_GENERATE_RESPONSE_FILE_ERROR("10119158", "生成批量代付回盘文件失败"),

        ERROR_CONTENT_NO_MATCH("10119159", "批量代付文件名中商户号与文件内容中代付订单商户号不一致"),
        ERROR_AMT_NO_MATCH("10119160", "批量代付文件总金额与明细不匹配"),
        ERROR_COUNT_NO_MATCH("10119161", "批量代付文件总笔数与明细不匹配"),

        PAGE_ERROR(R.PGWConstant.PAGE_ERROR_CODE, "");

        private String value;

        private String label;

        PGWErrorEnum(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

    }

    /**
     * Function : 订单相关状态<br/>
     * Date : 2015年6月2日 下午9:04:51 <br/>
     *
     * @author wanglong
     * @version R
     * @since JDK 1.7
     */
    public enum PGWOrderStatus {
        // 订单清算状态
        /**
         * 待清算
         */
        ORDER_SETTLE_STATUS_01("01", "待清算"),
        /**
         * 对账成功
         */
        ORDER_SETTLE_STATUS_02("02", "对账成功"),
        /**
         * 对账失败
         */
        ORDER_SETTLE_STATUS_03("03", "对账失败"),
        /**
         * 已清算
         */
        ORDER_SETTLE_STATUS_04("04", "已清算"),
        /**
         * 对账成功处理中(用于退货、撤销对账成功更新订单表与备付金)
         */
        ORDER_SETTLE_STATUS_05("05", "对账成功处理中"),
        /**
         * 对账失败处理中(用于退货、撤销对账失败更新订单表与备付金)
         */
        ORDER_SETTLE_STATUS_06("06", "对账失败处理中"),
        /**
         * 订单流水相符，但金额不符
         */
        ORDER_SETTLE_STATUS_07("07", "订单流水相符，但金额不符"),
        /**
         * 迅联电子账户，不需要结算，但需要进行出数处理
         */
        ORDER_SETTLE_STATUS_08("08", "已对账并进入电子账户批量结算表"),

        //二次对账核准状态
        /**
         * 二次对账未核准
         */
        SECOND_CHECK_STATUS_01("01", "未核准"),
        /**
         * 二次对账已核准
         */
        SECOND_CHECK_STATUS_02("02", "已核准"),

        // 支付订单状态
        /**
         * 待付款
         */
        PAYORDER_STATUS_01("01", "待付款"),
        /**
         * 交易进行中
         */
        PAYORDER_STATUS_02("02", "交易进行中"),
        /**
         * 交易成功
         */
        PAYORDER_STATUS_03("03", "交易成功"),
        /**
         * 交易失败
         */
        PAYORDER_STATUS_04("04", "交易失败"),
        /**
         * 交易处理超时
         */
        PAYORDER_STATUS_05("05", "交易处理超时"),
        /**
         * 订单超出时效
         */
        PAYORDER_STATUS_06("06", "订单超出时效"),
        /**
         * 订单异常关闭
         */
        PAYORDER_STATUS_07("07", "订单异常关闭"),
        /**
         * 待认证
         */
        PAYORDER_STATUS_11("11", "待认证 "),
        /**
         * 已认证
         */
        PAYORDER_STATUS_12("12", "已认证"),
        /**
         * 认证有效
         */
        PAYORDER_STATUS_13("13", "认证有效"),
        /**
         * 已退回
         */
        PAYORDER_STATUS_14("14", "已退回"),

        // 产品订单状态
        /**
         * 待付款
         */
        PRODORDER_STATUS_01("01", "待付款"),
        /**
         * 待收货
         */
        PRODORDER_STATUS_02("02", "待收货"),
        /**
         * 已完成
         */
        PRODORDER_STATUS_03("03", "已完成"),
        /**
         * 退货中
         */
        PRODORDER_STATUS_04("04", "退货中"),
        /**
         * 退货完成
         */
        PRODORDER_STATUS_05("05", "退货完成"),
        /**
         * 订单失效
         */
        PRODORDER_STATUS_06("06", "订单失效"),
        /**
         * 订单拒收
         */
        PRODORDER_STATUS_07("07", "订单拒收"),

        //记账订单
        /**
         * 待处理
         */
        ACCOUNTING_STATUS_01("01", "待处理"),
        /**
         * 处理中
         */
        ACCOUNTING_STATUS_02("02", "处理中"),
        /**
         * 成功
         */
        ACCOUNTING_STATUS_03("03", "成功"),
        /**
         * 失败
         */
        ACCOUNTING_STATUS_04("04", "失败"),
        /**
         * 待冲正
         */
        ACCOUNTING_STATUS_05("05", "待冲正 "),
        /**
         * 已冲正
         */
        ACCOUNTING_STATUS_06("06", "已冲正 "),
        /**
         * 超时
         */
        ACCOUNTING_STATUS_07("07", "超时 "),

        //订单撤销处理状态
        /**
         * 撤销中
         */
        REVERT_STATUS_01("01", "撤销中"),
        /**
         * 撤销成功
         */
        REVERT_STATUS_02("02", "撤销成功"),
        /**
         * 撤销失败
         */
        REVERT_STATUS_03("03", "撤销失败"),

        //订单退款处理状态
        /**
         * 退款中
         */
        RETURN_STATUS_01("01", "退款中"),
        /**
         * 退款成功
         */
        RETURN_STATUS_02("02", "退款成功"),
        /**
         * 退款失败
         */
        RETURN_STATUS_03("03", "退款失败"),

        //红包收款状态
        /**
         * 待收款
         */
        RETURN_STATUS_0("0", "待收款"),
        /**
         * 超时退款
         */
        RETURN_STATUS_1("1", "超时退款"),
        /**
         * 已收款
         */
        RETURN_STATUS_2("2", "已收款"),
        /**
         * 拒收
         */
        RETURN_STATUS_3("3", "拒收"),

        //订单结算状态
        /**
         * 待结算
         */
        SETTLE_STATUS_00("00", "待结算"),
        /**
         * 结算失败
         */
        SETTLE_STATUS_01("01", "结算失败"),
        /**
         * 结算处理中
         */
        SETTLE_STATUS_02("02", "结算处理中"),
        /**
         * 结算成功
         */
        SETTLE_STATUS_03("03", "结算成功"),

        // 记账订单清算状态
        /**
         * 待清算
         */
        ACCT_SETTLE_STATUS_01("01", "待清算"),
        /**
         * 对账成功
         */
        ACCT_SETTLE_STATUS_02("02", "对账成功"),
        /**
         * 对账失败
         */
        ACCT_SETTLE_STATUS_03("03", "对账失败");

        private String value;

        private String label;

        PGWOrderStatus(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }

    /**
     * Function : 转账状态<br/>
     * Date : 2015-5-27 下午07:32:15 <br/>
     *
     * @author hanjunlu
     * @version R
     * @since JDK 1.7
     */
    public enum PGWTransferStatus {
        // 订单清算状态
        /**
         * 受理中
         */
        TRANSFER_STATUS_01("01", "受理中"),
        /**
         * 受理失败
         */
        TRANSFER_STATUS_02("02", "受理失败"),
        /**
         * 等待收款
         */
        TRANSFER_STATUS_03("03", "等待收款"),
        /**
         * 退款中
         */
        TRANSFER_STATUS_04("04", "退款中"),
        /**
         * 退款成功
         */
        TRANSFER_STATUS_05("05", "退款成功"),
        /**
         * 退款失败
         */
        TRANSFER_STATUS_06("06", "退款失败"),
        /**
         * 收款中
         */
        TRANSFER_STATUS_07("07", "收款中"),
        /**
         * 转账成功
         */
        TRANSFER_STATUS_08("08", "转账成功"),
        /**
         * 转账失败
         */
        TRANSFER_STATUS_09("09", "转账失败"),
        /**
         * 待退款
         */
        TRANSFER_STATUS_10("10", "待退款");

        private String value;

        private String label;

        PGWTransferStatus(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }

    /**
     * 操作类型 ，定义和ESB同步
     * Date : 2015年6月5日 下午5:27:47 <br/>
     *
     * @author xiangkw
     * @version R
     * @since JDK 1.7
     */
    public enum OptFlag {
        ADD("A", "新增"),
        DELETE("D", "删除"),
        MODIFY("M", "修改"),
        QUERY("Q", "查询");

        private String value;

        private String label;

        OptFlag(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }

    /**
     * 用户类型 ，定义和ESB同步 ：0：超级管理员；1：管理员；2：普通用户
     * Date : 2015年6月5日 下午5:27:47 <br/>
     *
     * @author xiangkw
     * @version R
     * @since JDK 1.7
     */
    public enum SyncUserType {

        SUPPERADMIN("0", UserType.MANAGE_SUPPERADMIN.getValue(), "超级管理员"),
        ADMIN("1", UserType.MANAGE_ADMIN.getValue(), "管理员"),
        USER("2", UserType.MANAGE_USER.getValue(), "普通用户");

        private String value;

        private String label;

        private String userType;

        SyncUserType(String value, String userType, String label) {
            this.value = value;
            this.userType = userType;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

    }

    /**
     * 用户类型
     * Date : 2015年7月13日 下午1:27:17 <br/>
     *
     * @author xiangkw
     * @version R
     * @since JDK 1.7
     */
    public enum UserType {

        MANAGE_SUPPERADMIN("00", "运营平台-超级管理员"),
        MANAGE_ADMIN("10", "运营平台-管理员"),
        MANAGE_USER("11", "运营平台-用户"),
        MERC_ADMIN("20", "商户服务平台-管理员"),
        MERC_USER("21", "商户服务平台-用户");

        private String value;

        private String label;

        UserType(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }

    /**
     * 系统默认的角色ID
     * <p>
     * Date : 2015年6月7日 下午5:34:48 <br/>
     *
     * @author xiangkw
     * @version R
     * @since JDK 1.7
     */
    public enum DefaultUserRole {

        ADMIN("01", "管理员角色Id"),
        USER("02", "普通用户角色Id");

        private String roleId;

        private String label;

        DefaultUserRole(String value, String label) {
            this.roleId = value;
            this.label = label;
        }

        public String getRoleId() {
            return roleId;
        }

        public String getLabel() {
            return label;
        }

    }

    /**
     * 定义用户角色类型
     * Date : 2015年6月9日 上午11:23:46 <br/>
     *
     * @author xiangkw
     * @version R
     * @since JDK 1.7
     */
    public enum RoleType {

        ROLE_MNG("1", "运营平台角色"),
        ROLE_MARC_ADMIN("2", "商户管理员角色"),
        ROLE_MARC("3", "普通用户角色Id");

        private String value;

        private String label;

        RoleType(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }

    }

    public enum UserStatus {
        /**
         * 待审核
         */
        USER_STATUS_01("01", "待审核"),
        /**
         * 审核中
         */
        USER_STATUS_02("02", "审核中"),
        /**
         * 审核通过
         */
        USER_STATUS_03("03", "审核通过");

        private String value;

        private String label;

        UserStatus(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }

    /**
     * Function : 商户状态<br/>
     * Date : 2015年6月17日 下午5:30:00 <br/>
     *
     * @author guiye
     * @version R
     * @since JDK 1.7
     */
    public enum PGWMerchantStatus {
        /**
         * 暂存
         */
        MERCHANT_STATUS_01("01", "暂存"),
        /**
         * 待审核
         */
        MERCHANT_STATUS_02("02", "待审核"),
        /**
         * 审核不通过
         */
        MERCHANT_STATUS_03("03", "审核不通过"),
        /**
         * 生效
         */
        MERCHANT_STATUS_04("04", "生效"),
        /**
         * 已冻结
         */
        MERCHANT_STATUS_05("05", "已冻结"),
        /**
         * 已销户
         */
        MERCHANT_STATUS_06("06", "已销户"),
        /**
         * 已删除
         */
        MERCHANT_STATUS_07("07", "已删除");

        private String value;

        private String label;

        PGWMerchantStatus(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }

    public enum WebServiceFunctionId {
        /**
         * 创建延迟转账产品订单
         */
        FUNCTION_TRANSFER_CREATE_DELAY_PRODUCT("TransferService.creDelayTrasfProdOrdService", "创建延迟转账产品订单"),
        /**
         * 创建普通转账产品订单
         */
        FUNCTION_TRANSFER_CREATE_REL_PRODUCT("TransferService.creRelTmTrasfProdOrdService", "创建普通转账产品订单"),
        /**
         * 执行支付订单
         */
        FUNCTION_TRANSFER_EXEC_PAYORDER("TransferService.execOrdService", "执行支付订单"),
        /**
         * 生成支付订单
         */
        FUNCTION_TRANSFER_CREATE_PAYORDER("TransferService.crePayOrdService", "生成支付订单"),
        /**
         * 查询订单列表
         */
        FUNCTION_TRANSFER_QUERY_ORDERLIST("TransferService.qryOrdListService", "查询订单列表"),
        /**
         * 查询单笔订单
         */
        FUNCTION_TRANSFER_QUERY_SIGLORDER("TransferService.qrySiglOrdLService", "查询单笔订单"),
        /**
         * 查询延迟转账收款列表
         */
        FUNCTION_TRANSFER_QUERY_DELAYLIST("TransferService.qryDelayTrasfGathListService", "查询延迟转账收款列表"),
        /**
         * 查询交易明细
         */
        FUNCTION_TRANSFER_QUERY_TRAN_DETAIL("TransactionService.queryTransactionDetail", "查询交易明细"),
        /**
         * 查询账户余额
         */
        FUNCTION_TRANSFER_QUERY_ACCT_BALANCE("AcctService.queryAccountBalance", "查询账户余额"),
        /**
         * 查询账户明细
         */
        FUNCTION_TRANSFER_QUERY_ACCT_DETAIL("AcctService.queryAccountDetails", "查询账户明细"),
        /**
         * 交易支持的账户类型
         */
        FUNCTION_QUERY_SUPPORT_ACCT_LIST("LimitService.qrySptAcctListService", "交易支持的账户类型"),
        /**
         * 交易手续费查询
         */
        FUNCTION_TRANSFER_QUERY_FEE("TransferService.qryTranFeeService", "交易手续费查询"),
        /**
         * 转账交易手续费规则查询
         */
        FUNCTION_TRANSFER_QUERY_FEE_RULE("TransferService.qryTrasfTranFeeRuleService", "转账交易手续费规则查询"),
        /**
         * 限额验证请求
         */
        FUNCTION_LIMIT_CHECK_AMOUNT("LimitService.chkLmtAmtService", "限额验证请求"),
        /**
         * 限额规则查询
         */
        FUNCTION_LIMIT_QUERY_RULE("LimitService.qryLmtRuleService", "限额规则查询"),
        /**
         * 延时转账拒收款
         */
        FUNCTION_DELAY_TRASF_RJCT("TransferService.delayTrasfRjct", "延时转账拒收款"),
        /**
         * 累计交易金额查询
         */
        FUNCTION_QUERY_ACCU_TRAN_AMT("TransferService.qryAccuTranAmtService", "累计交易金额查询"),
        /**
         * 黑名单同步
         */
        FUNCTION_SYNC_BLACKLIST("UserCheckService.syncBlackList", "黑名单同步"),
        /**
         * 最近转账记录查询
         */
        FUNCTION_TRANSFER_QUERY_ORDER("OrderService.qryTrasfService", "最近转账记录查询"),
        /**
         * 更新收款人登记薄
         */
        FUNCTION_PAYEE_CHANGE_SQPNO("TransferService.changePayeeSeqNoService", "更新收款人登记薄"),
        /**
         * 查询收款人登记薄
         */
        FUNCTION_PAYEE_QUERY_PAYEELIST("TransferService.qryPayeeSeqNoService", "查询收款人登记薄"),
        /**
         * 创建小微封任务
         */
        FUNCTION_CREATE_XWF_TASK("TransferService.createXwfTaskService", "创建小微封任务"),
        /**
         * 执行认证支付订单
         */
        FUNCTION_TRANSFER_EXEC_XWF_PAYORDER("TransferService.execXWFOrdService", "执行认证支付订单"),
        /**
         * 小微封任务升级
         */
        FUNCTION_UPDATE_XWF_TASK("TransferService.updXWFTaskService", "小微封任务升级"),
        /**
         * 查询用户认证方式和手续费
         */
        FUNCTION_QUERY_PAY_CND_AND_TRAN_FEE("TransferService.queryPayCndAndTranFee", "查询用户认证方式和手续费"),
        /**
         * 快捷支付签约绑卡
         */
        FUNCTION_QUICK_BIND("BindCardService.bindCard", "快捷支付绑卡签约"),
        /**
         * 快捷支付解约
         */
        FUNCTION_SIGN_OUT("SignOutService.bindCard", "快捷支付解约"),
        /**
         * 快捷支付订单查询
         */
        FUNCTION_QUERY_PAY_ORDER("QueryPayOrderService.payBindCardInfo", "快捷支付订单查询"),
        /**
         * 快捷支付支付
         */
        FUNCTION_QUICK_PAY("PaymentService.doPay", "快捷支付支付"),
        /**
         * 快捷支付退款
         */
        FUNCTION_QUICK_REFUND("PayRefundService.refundDo", "快捷支付退款"),
        /**
         * 快捷支付支持机构查询
         */
        FUNCTION_QUERY_SUPPORT_ORG("QuerySupportOrganService.querySupportOrganDo", "快捷支付查询支持机构信息"),
        /**
         * 红包代收付
         */
        FUNCTION_REDPACK_AGTGATHPAY("OrdFlowService.agtGathPay", "红包代收付"),
        /**
         * 生成零钱支付订单
         */
        FUNCTION_REDPACK_CREPKT_MONEY("OrdFlowService.crepktMoneyPayOrdService", "生成零钱支付订单"),
        /**
         * 门户回调便民执行订单
         */
        FUNCTION_BMPAY_CALLBACKS("bmPay.callBack", "门户回调便民执行订单"),
        /**
         * 快捷支付代付
         */
        FUNCTION_CORPORATE_PAYMENT("CorporatePayment.doPay", "对公账户代付"),
        /**
         * 快捷支付代付
         */
        FUNCTION_PROXY_PAY("", "快捷支付代付"),
        /**
         * 批量送盘文件
         */
        FUNCTION_SEND_DISC("SendDiscService.SendDo", "批量送盘接口"),
        /**
         * 批量回盘文件
         */
        FUNCTION_COUNTER_OFFER("CounterofferService.SendDo", "批量回盘文件"),
        /**
         * 批量查询
         */
        FUNCTION_QUERYBATCH("QueryBatchService.SendDo", "批量查询"),
        /**
         * 验证单笔代收文件格式
         */
        FUNCTION_SINGLE_COLLECT("BatchCollectAndPayService.singleCollect", "校验单笔代收文件"),

        /**
         * 验证单笔代付文件格式
         */
        FUNCTION_SINGLE_PAYEMENT("BatchCollectAndPayService.singlePayment", "校验单笔代付文件");

        private String value;

        private String label;

        WebServiceFunctionId(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }

    }

    /**
     * Function : 备付金交易类型<br/>
     * 类似于摘要码<br/>
     * Date : 2015年6月28日 下午4:52:54 <br/>
     *
     * @author chenwj
     * @version R
     * @since JDK 1.7
     */
    public enum MercAccountTransType {
        /**
         * 充值
         */
        MERCHANT_ACCOUNT_TRANS_TYPE_CHONGZHI("01", "充值"),
        /**
         * 提现
         */
        MERCHANT_ACCOUNT_TRANS_TYPE_TIXIAN("02", "提现"),
        /**
         * 代付
         */
        MERCHANT_ACCOUNT_TRANS_TYPE_03("03", "代付"),
        /**
         * 退货
         */
        MERCHANT_ACCOUNT_TRANS_TYPE_04("04", "退货"),
        /**
         * 手续费支出
         */
        MERCHANT_ACCOUNT_TRANS_TYPE_05("05", "手续费支出"),
        /**
         * 红包支出
         */
        MERCHANT_ACCOUNT_TRANS_TYPE_06("06", "红包支出"),
        /**
         * 红包退回
         */
        MERCHANT_ACCOUNT_TRANS_TYPE_07("07", "红包退回"),
        /**
         * 手续费退回
         */
        MERCHANT_ACCOUNT_TRANS_TYPE_08("08", "手续费退回"),
        /**
         * 服务费结算
         */
        MERCHANT_ACCOUNT_TRANS_TYPE_09("09", "服务费结算"),
        /**
         * 服务费充值（预收款确认收入）
         */
        MERCHANT_ACCOUNT_TRANS_TYPE_10("10", "服务费充值"),
        /**
         * 本金结算
         */
        MERCHANT_ACCOUNT_TRANS_TYPE_11("11", "本金结算"),
        /**
         * 代付失败返还
         */
        MERCHANT_ACCOUNT_TRANS_TYPE_12("12", "代付失败返还"),
        /**
         * 对公代付
         */
        MERCHANT_ACCOUNT_TRANS_TYPE_13("13", "对公代付"),
        /**
         * 对公单笔代付退回
         */
        MERCHANT_ACCOUNT_TRANS_TYPE_14("14", "对公单笔代付退回"),
        /**
         * 宝宝代付
         */
        MERCHANT_ACCOUNT_TRANS_TYPE_15("15", "宝宝代付");
        private String value;

        private String label;

        MercAccountTransType(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function :备付金交易状态<br/>
     * Date : 2015年6月28日 下午4:56:03 <br/>
     *
     * @author chenwj
     * @version R
     * @since JDK 1.7
     */
    public enum MercAccountTransStatus {
        // 备付金交易状态
        /**
         * 待审核
         */
        MERCHANT_ACCOUNT_TRANS_STATUS_DAIQUEREN("01", "待审核"),
        /**
         * 处理中
         */
        MERCHANT_ACCOUNT_TRANS_STATUS_PROCESSING("02", "处理中"),
        /**
         * 成功
         */
        MERCHANT_ACCOUNT_TRANS_STATUS_SUCCESS("03", "成功"),
        /**
         * 失败
         */
        MERCHANT_ACCOUNT_TRANS_STATUS_FAIL("04", "失败"),
        /**
         * 审核中
         */
        MERCHANT_ACCOUNT_TRANS_STATUS_DENAI("05", "审核中");

        private String value;

        private String label;

        MercAccountTransStatus(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * 备付金记账方向
     * Date : 2015年6月29日 下午9:27:11 <br/>
     *
     * @author chenwj
     * @version R
     * @since JDK 1.7
     */
    public enum MercAccountDetailDcflag {
        // 备付金记账方向
        MERCHANT_ACCOUNT_DETAIL_DCFLAG_JIEJI("1", "借记"),
        MERCHANT_ACCOUNT_DETAIL_DCFLAG_DAIJI("2", "贷记");

        private String value;

        private String label;

        MercAccountDetailDcflag(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * 备付金记账是否冲正
     * Date : 2015年6月29日 下午9:27:11 <br/>
     *
     * @author chenwj
     * @version R
     * @since JDK 1.7
     */
    public enum MercAccountDetailReverseflag {

        MERCHANT_ACCOUNT_DETAIL_REVERSEFLAG_Y("1", "是"),
        MERCHANT_ACCOUNT_DETAIL_REVERSEFLAG_N("0", "否");

        private String value;

        private String label;

        MercAccountDetailReverseflag(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * 备付金记账交易类型
     * Date : 2015年6月29日 下午9:31:35 <br/>
     *
     * @author chenwj
     * @version R
     * @since JDK 1.7
     */
    public enum MercAccountDetailTransType {
        MERCHANT_ACCOUNT_DETAIL_TRANS_TYPE_TIXIAN("0101", "提现"),
        MERCHANT_ACCOUNT_DETAIL_TRANS_TYPE_CHONGZHI("0201", "充值"),
        MERCHANT_ACCOUNT_DETAIL_TRANS_TYPE_TIXIANCHONGZHENG("0301", "提现冲正");

        private String value;

        private String label;

        MercAccountDetailTransType(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    public enum PGWMercOrPersonTransType {
        ORDER_TYPE_FLAY_1("1", "个人"),
        ORDER_TYPE_FLAY_2("2", "商户"),
        ORDER_TYPE_FLAY_3("3", "网联业务类型"),
        ;

        private String value;

        private String label;

        PGWMercOrPersonTransType(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : 商户手续费结算方式 <br/>
     * Date : 2017年4月11日 上午11:03:22 <br/>
     *
     * @author xiejf
     * @version 1.0
     * @since JDK 1.7
     */
    public enum PGWMerchantFeeMethod {
        FEE_METHOD_FULL("1", "全额"),
        FEE_METHOD_BALANCE("2", "差额"),
        ;

        private String value;

        private String label;

        PGWMerchantFeeMethod(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : 清算核算摘要码以及摘要<br/>
     * Date : 2015年6月30日 下午3:28:25 <br/>
     *
     * @author caicj
     * @version R
     * @since JDK 1.7
     */
    public enum CASAbstract {
        /**
         * 定时结算-转账业务
         */
        ABS_CODE_001("001", "定时结算-转账业务"),
        /**
         * 定时结算-消费预授权业务
         */
        ABS_CODE_002("002", "定时结算-消费预授权业务"),
        /**
         * 定时结算-资金调拔业务
         */
        ABS_CODE_003("003", "定时结算-资金调拔业务"),
        /**
         * 定时结算-代收付业务
         */
        ABS_CODE_004("004", "定时结算-代收付业务"),
        /**
         * 定时结算-理财支付业务
         */
        ABS_CODE_005("005", "定时结算-理财支付业务"),
        /**
         * 透支归还
         */
        ABS_CODE_006("006", "透支归还"),
        /**
         * 透支划转
         */
        ABS_CODE_007("007", "透支划转"),
        /**
         * 结息
         */
        ABS_CODE_008("008", "结息"),
        /**
         * 利息计提
         */
        ABS_CODE_009("009", "利息计提"),
        /**
         * 资金调入
         */
        ABS_CODE_010("010", "资金调入"),
        /**
         * 资金调出
         */
        ABS_CODE_011("011", "资金调出"),
        /**
         * 抹账
         */
        ABS_CODE_012("012", "抹账"),
        /**
         * 借贷不平单边记账
         */
        ABS_CODE_013("013", "借贷不平单边记账"),
        /**
         * 损益结转 需改为科目结转
         */
        ABS_CODE_014("014", "损益结转 需改为科目结转"),
        /**
         * 长款调账（往来户）
         */
        ABS_CODE_015("015", "长款调账（往来户）"),
        /**
         * 短款调账（往来户）
         */
        ABS_CODE_016("016", "短款调账（往来户）"),
        /**
         * 长款调账（银行存款户）
         */
        ABS_CODE_017("017", "长款调账（银行存款户）"),
        /**
         * 短款调账（银行存款户）
         */
        ABS_CODE_018("018", "短款调账（银行存款户）"),
        /**
         * 圈外资金结算
         */
        ABS_CODE_019("019", "圈外资金结算	"),
        /**
         * 商户备付金充值
         */
        ABS_CODE_020("020", "商户备付金充值"),
        /**
         * 商户备付金提现
         */
        ABS_CODE_021("021", "商户备付金提现"),
        /**
         * 商户清算本金结算
         */
        ABS_CODE_022("022", "商户清算本金结算"),
        /**
         * 商户差额手续费结算
         */
        ABS_CODE_023("023", "商户差额手续费结算"),
        /**
         * 商户全额手续费结算
         */
        ABS_CODE_024("024", "商户全额手续费结算"),
        /**
         * 商户平台计费成本结算
         */
        ABS_CODE_025("025", "商户平台计费成本结算"),
        /**
         * 商户平台收单资金划付
         */
        ABS_CODE_026("026", "商户平台收单资金划付"),
        /**
         * 商户平台分润资金划付
         */
        ABS_CODE_027("027", "商户平台分润资金划付"),
        /**
         * 商户退货本金结算
         */
        ABS_CODE_028("028", "商户退货本金结算"),
        /**
         * 商户退货差额手续费结算
         */
        ABS_CODE_029("029", "商户退货差额手续费结算"),
        /**
         * 商户退货全额手续费结算
         */
        ABS_CODE_030("030", "商户退货全额手续费结算"),
        /**
         * 商户退货退润
         */
        ABS_CODE_031("031", "商户退货退润"),
        /**
         * 商户平台转账手续费收入
         */
        ABS_CODE_032("032", "商户平台转账手续费收入"),
        /**
         * 身份查验预付款入账
         */
        ABS_CODE_033("033", "身份查验预付款入账"),
        /**
         * 征信预付款入账
         */
        ABS_CODE_034("034", "征信预付款入账"),
        /**
         * 机构资金划入
         */
        ABS_CODE_035("035", "机构资金划入"),
        /**
         * 机构资金划出
         */
        ABS_CODE_036("036", "机构资金划出"),
        /**
         * 倒推日记账
         */
        ABS_CODE_037("037", "倒推日记账"),
        /**
         * 定时结算
         */
        ABS_CODE_113("113", "定时结算"),
        /**
         * 结息
         */
        ABS_CODE_116("116", "结息"),
        /**
         * 利息计提
         */
        ABS_CODE_201("201", "利息计提"),
        /**
         * 透支归还
         */
        ABS_CODE_202("202", "透支归还"),
        /**
         * 透支划转
         */
        ABS_CODE_203("203", "透支划转"),
        /**
         * 积分入分
         */
        ABS_CODE_038("038", "积分入分"),
        /**
         * 积分出分
         */
        ABS_CODE_039("039", "积分出分"),
        /**
         * 红包发放
         */
        ABS_CODE_040("040", "红包发放"),
        /**
         * 红包领取
         */
        ABS_CODE_041("041", "红包领取"),
        /**
         * 红包提现
         */
        ABS_CODE_042("042", "红包提现"),
        /**
         * 红包退回
         */
        ABS_CODE_043("043", "红包退回"),
        /**
         * 红包调账
         */
        ABS_CODE_044("044", "红包调账"),
        /**
         * 网上开户业务自动充值
         */
        ABS_CODE_050("050", "网上开户业务自动充值"),
        /**
         * 网上开户业务划付
         */
        ABS_CODE_051("051", "网上开户业务划付"),
        /**
         * 网上开户结算
         */
        ABS_CODE_052("052", "网上开户结算"),
        /**
         * 消费二清垫资
         */
        ABS_CODE_073("073", "消费二清垫资"),
        /**
         * 消费二清垫资返还
         */
        ABS_CODE_074("074", "消费二清垫资返还"),
        /**
         * 批量代付到证通宝
         */
        ABS_CODE_075("075", "批量代付到证通宝"),
        /**
         * 代收二清垫资
         */
        ABS_CODE_076("076", "代收二清垫资"),
        /**
         * 代收二清垫资返还
         */
        ABS_CODE_077("077", "代收二清垫资返还"),
        ;
        private String value;

        private String label;

        CASAbstract(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : 批处理状态<br/>
     * Date : 2017年7月26日 下午17:56:28 <br/>
     *
     * @author WPC
     * @version R
     * @since JDK 1.7
     */
    public enum BatchCollectPaystate {
        /**
         * 送盘成功
         */
        SEND_DISC_SUCCESS("01", "送盘成功"),
        /**
         * 送盘失败
         */
        SEND_DISC_FAIL("02", "送盘失败"),
        /**
         * 处理中
         */
        SEND_IN_PROCESS("03", "处理中"),
        /**
         * 已撤销
         */
        SEND_RESCINDED("04", "已撤销"),
        /**
         * 回盘成功
         */
        SEND_COUNTEROFFER_SUCCESS("05", "回盘成功"),
        /**
         * 回盘失败
         */
        SEND_COUNTEROFFER_FAIL("06", "回盘失败"),
        /**
         * 检测未通过
         */
        SEND_CHECK_FAILES("07", "校验失败");

        private String value;

        private String label;

        BatchCollectPaystate(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }

    }

    /**
     * Function : 批量代收代付异步通知状态<br/>
     * Date : 2017年7月26日 下午17:56:28 <br/>
     *
     * @author WPC
     * @version 1.0
     * @since JDK 1.7
     */
    public enum BatchNotifyState {
        /**
         * 成功
         */
        NOTICE_SUCCESS("01", "成功"),
        /**
         * 失败
         */
        NOTICE_FAIL("02", "失败"),
        /**
         * 处理中
         */
        NOTICE_PROCESS("03", "处理中"),
        /**
         * 初始化状态Initialization
         */
        NOTICE_INIT("04", "未处理");
        private String value;

        private String label;

        BatchNotifyState(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }

    }

    /**
     * Function : 商户文件类型<br/>
     * Date : 2015年7月1日 下午3:21:28 <br/>
     *
     * @author caicj
     * @version R
     * @since JDK 1.7
     */
    public enum PGWMercFileType {
        /**
         * 商户对账文件(自然日)
         */
        MERC_FILE_TYPE_01("01", "商户对账文件(自然日)"),
        /**
         * 商户对账文件(清算日)
         */
        MERC_FILE_TYPE_02("02", "商户对账文件(清算日)"),
        ;

        private String value;

        private String label;

        PGWMercFileType(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : 转接下发文件文件状态<br/>
     * Date : 2015年7月4日 下午5:19:55 <br/>
     *
     * @author caicj
     * @version R
     * @since JDK 1.7
     */
    public enum TACSDistributionFileStatus {
        DOWNLOAD_FILE_STATUS_01("01", "待下载"),
        DOWNLOAD_FILE_STATUS_02("02", "下载中"),
        DOWNLOAD_FILE_STATUS_03("03", "下载成功"),
        DOWNLOAD_FILE_STATUS_04("04", "下载失败"),
        ;

        private String value;

        private String label;

        TACSDistributionFileStatus(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : 会计分录状态<br/>
     * Date : 2015年7月12日 下午2:19:55 <br/>
     *
     * @author caicj
     * @version R
     * @since JDK 1.7
     */
    public enum PGWAccountingEntryStatus {
        /** 划账状态*/
        /**
         * 待划账
         */
        REMIT_STATUS_01("01", "待划账"),
        /**
         * 已确认
         */
        REMIT_STATUS_02("02", "已确认"),
        /**
         * 划账中
         */
        REMIT_STATUS_03("03", "划账中"),
        /**
         * 成功
         */
        REMIT_STATUS_04("04", "成功"),
        /**
         * 失败
         */
        REMIT_STATUS_05("05", "失败"),
        /**
         * 超时
         */
        REMIT_STATUS_06("06", "超时"),

        /** 分录前置条件完成状态*/
        /**
         * 未完成
         */
        PRE_ACCT_ENTRY_STATUS_01("01", "分录前置条件未完成"),
        /**
         * 已完成
         */
        PRE_ACCT_ENTRY_STATUS_02("02", "分录前置条件已完成"),

        /** 分录完成状态*/
        /**
         * 未完成
         */
        ACCT_ENTRY_STATUS_01("01", "分录未完成"),
        /**
         * 已完成
         */
        ACCT_ENTRY_STATUS_02("02", "分录已完成"),
        ;

        private String value;

        private String label;

        PGWAccountingEntryStatus(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : 划账方式<br/>
     * Date : 2015年7月12日 下午2:40:19 <br/>
     *
     * @author Guest-1
     * @version R
     * @since JDK 1.7
     */
    public enum PGWRemitMethod {
        /** 划账方式*/
        /**
         * 联机银企划账
         */
        REMIT_METHOD_02("02", "联机银企划账"),
        /**
         * 手工
         */
        REMIT_METHOD_03("03", "手工"),
        /**
         * 运营划账文件
         */
        REMIT_METHOD_04("04", "运营划账文件"),
        /**
         * 备付金划账
         */
        REMIT_METHOD_05("05", "备付金划账");

        private String value;

        private String label;

        PGWRemitMethod(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : 计费文件类型<br/>
     * Date : 2015年7月20日 下午8:22:51 <br/>
     *
     * @author caicj
     * @version R
     * @since JDK 1.7
     */
    public enum CcsFileType {
        /**
         * 扣费明细文件
         */
        CCS_FILE_TYPE_1("1", "扣费明细文件"),
        /**
         * 分润明细文件
         */
        CCS_FILE_TYPE_2("2", "分润明细文件"),
        ;

        private String value;

        private String label;

        CcsFileType(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : 对账结果<br/>
     * Date : 2015年7月29日 下午2:27:37 <br/>
     *
     * @author caicj
     * @version R
     * @since JDK 1.7
     */
    public enum PGWCheckResult {
        /**
         * 联机成功，对账成功
         */
        CHECK_RESULT_01("01", "联机成功，对账成功"),
        /**
         * 联机成功，对账无记录
         */
        CHECK_RESULT_02("02", "联机成功，对账无记录"),
        /**
         * 联机失败，对账成功
         */
        CHECK_RESULT_03("03", "联机失败，对账成功"),
        /**
         * 联机成功，对账无记录
         */
        CHECK_RESULT_04("04", "联机失败，对账失败"),
        /**
         * 联机未明，对账成功
         */
        CHECK_RESULT_05("05", "联机未明，对账成功"),
        /**
         * 联机未明，对账无记录
         */
        CHECK_RESULT_06("06", "联机未明，对账无记录"),
        ;

        private String value;

        private String label;

        PGWCheckResult(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * 系统标识
     * Date : 2015年8月2日 下午1:10:11 <br/>
     *
     * @author xiangkw
     * @version R
     * @since JDK 1.7
     */
    public enum SysFlag {
        /**
         * 扣费明细文件
         */
        PGW_MANAGE("1", "运营平台"),
        /**
         * 分润明细文件
         */
        PGW_MSS("2", "商户服务管理平台"),
        ;

        private String value;

        private String label;

        SysFlag(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }

    }

    public enum MercCertIdType {
        PGW_CERT_IDTYPE0("0", "02", "身份证"),
        PGW_CERT_IDTYPE1("1", "20", "护照"),
        PGW_CERT_IDTYPE2("2", "07", "军官证"),
        PGW_CERT_IDTYPEC("C", "17", "台胞证"),
        PGW_CERT_IDTYPEZ("Z", "21", "其他 "),
        ;

        private final String new_value;
        private final String old_value;
        private final String label;

        MercCertIdType(String new_value, String old_value, String label) {
            this.new_value = new_value;
            this.old_value = old_value;
            this.label = label;
        }

        public static String getNewVlaueByOldValue(String old) {
            if (StringUtils.isEmpty(old)) return "";
            for (MercCertIdType enumObj : MercCertIdType.values()) {
                if (old.equals(enumObj.getOldValue())) {
                    return enumObj.getNowValue();
                }
            }
            return "";
        }

        public String getNowValue() {
            return new_value;
        }

        public String getOldValue() {
            return old_value;
        }

        public String getLabel() {
            return label;
        }

    }

    /**
     * 商户证书状态
     **/
    public enum MerchantCertStatus {
        /**
         * 未使用
         */
        PGW_CERT_STATUS_01("01", "未使用"),
        /**
         * 使用中
         */
        PGW_CERT_STATUS_02("02", "使用中"),       //已生效
        /**
         * 失效
         */
        PGW_CERT_STATUS_03("03", "失效"),    //十四天之外，但是3年之内 ,可以启用
        /**
         * 申请中
         */
        PGW_CERT_STATUS_04("04", "审核中"),
        /**
         * 过期
         */
        PGW_CERT_STATUS_05("05", "过期"),    //三年之外
        /**
         * 废弃
         */
        PGW_CERT_STATUS_06("06", "停用"),      //三年内，停用不用,不能在启用

        PGW_CERT_STATUS_07("07", "审核拒绝"),
        ;

        private String value;

        private String label;

        MerchantCertStatus(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }

    }

    public enum AuthMode {
        SMS(1),    //短信
        PWD(2),    //支付密码
        CFCA(4),    //CFCA
        BIND_PLATFORM(8),//设备绑定
        SIGN(16),        //签名
        CON_FACE(32);    //视频

        private int f;

        AuthMode(int f) {
            this.f = f;
        }

        public int getF() {
            return f;
        }
    }

    /**
     * Function : 差错预警类型<br/>
     * Date : 2015年10月12日 下午1:57:53 <br/>
     *
     * @author caicj
     * @version R
     * @since JDK 1.7
     */
    public enum PGWMismatchType {
        /**
         * 清算对账金额不一致
         */
        MISMATCH_TYPE_0001("0001", "清算对账金额不一致"),
        /**
         * 备付金余额不足
         */
        MISMATCH_TYPE_0002("0002", "备付金余额不足"),
        /**
         * 小额多笔交易风险
         */
        MISMATCH_TYPE_0003("0003", "小额多笔交易风险"),
        /**
         * 备付金对账差错
         */
        MISMATCH_TYPE_0004("0004", "备付金对账差错"),
        /**
         * 备付金处理差错
         */
        MISMATCH_TYPE_0005("0005", "备付金处理差错"),
        /**
         * 会计分录处理差错
         */
        MISMATCH_TYPE_0006("0006", "会计分录处理差错"),
        /**
         * 其它
         */
        MISMATCH_TYPE_9000("9000", "其它"),
        ;

        private String value;

        private String label;

        PGWMismatchType(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : 积分消费交易状态<br/>
     * Date : 2015年11月16日 上午10:51:58 <br/>
     *
     * @author caicj
     * @version R
     * @since JDK 1.7
     */
    public enum BonusConsumeTranStatus {
        BONUS_CONSUME_PROCESSING("01", "处理中"),
        BONUS_CONSUME_SUCCESS("02", "成功"),
        BONUS_CONSUME_FAIL("03", "失败"),
        ;

        private String value;

        private String label;

        BonusConsumeTranStatus(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : 支付接口返回代码
     * Date : 2015年10月12日 下午1:57:53 <br/>
     *
     * @author zhaojw
     * @version R
     * @since JDK 1.7
     */
    public enum PGWPayResult {

        PGW_PAY_RESULT_ERROR_MERC("0000", "商户不存在或商户异常"),
        PGW_PAY_RESULT_STSYEM_ERROR("0001", "系统内部出错"),
        PGW_PAY_RESULT_BIND_SUCCESS("0002", "签约成功"),
        PGW_PAY_RESULT_PAY_BIND_FAIL("0003", "消费签约失败，请尝试直接签约"),
        PGW_PAY_RESULT_BIND_NO_SUPPORT("0004", "该机构不支持绑卡签约"),
        PGW_PAY_RESULT_LACK_PARAMETER("0005", "交易缺少必传参数或格式非法"),
        PGW_PAY_RESULT_BIND_FAIL("0006", "签约失败，账户验证失败"),
        PGW_PAY_RESULT_ERROR_MERC_STATUS("0007", "商户没有权限进行该交易"),

        PGW_PAY_RESULT_NULL_ORDER("0015", "没有该订单"),
        PGW_PAY_RESULT_QUERY_SUCCESS("0016", "查询订单成功"),
        PGW_PAY_RESULT_ERROR_ACCOUNT_TYPE("0017", "未识别该账户类型"),
        PGW_PAY_RESULT_ACCT_TYPE_UNSUPPORT("0018", "该账户类型不支持此种交易"),
        PGW_PAY_RESULT_ERROR_REFUND("0019", "退款订单不合法"),
        PGW_PAY_RESULT_REFUND_IN_OPERATION("0020", "退款已经正在执行"),
        PGW_PAY_RESULT_REFUND_LACK_AMOUNT("0021", "退款金额不能大于原交易金额"),
        PGW_PAY_RESULT_REFUND_NOT_FULL_AMOUNT("0022", "当日退款必须全额"),
        PGW_PAY_RESULT_REFUND_NO_RESERVE("0023", "交易没有对应的备付金信息"),
        PGW_PAY_RESULT_REFUND_LACK_RESERVE("0024", "商户备付金不足"),
        PGW_PAY_RESULT_REFUND_SUCCESS("0025", "退款成功"),
        PGW_PAY_RESULT_REFUND_TIMEOUT("0026", "退款未明"),
        PGW_PAY_RESULT_NO_SIGN("0027", "快捷支付没有找到绑卡信息,或者卡已经注销"),
        //PGW_PAY_RESULT_BLACK_LIST("0028", "该账号属于黑名单之内"),
        PGW_PAY_RESULT_REPEAT_PAY("0029", "订单号重复"),
        PGW_PAY_RESULT_MER_OVER_LIMIT("0030", "超过商户限额"),
        PGW_PAY_RESULT_PAYORDER_FAIL("0031", "创建支付订单失败"),
        PGW_PAY_RESULT_PAY_SUCCESS("0032", "支付成功"),
        PGW_PAY_RESULT_PAY_TIMEOUT("0033", "交易进行中"),
        PGW_PAY_RESULT_PAY_FAIL("0034", "交易失败"),
        PGW_PAY_RESULT_ALREADY_FULL_REFUND("0035", "该订单已经全额退款"),
        PGW_PAY_RESULT_EX_TIME_LIMIT("0036", "订单已超出退款期限"),
        PGW_PAY_RESULT_NOTSURPPOR_TIMEVALUE("0037", "当前账户的交易不在交易时间内"),
        PGW_PAY_RESULT_BLACK_LIST_1("0038", "该账号属于黑名单之内"),
        PGW_PAY_RESULT_BLACK_LIST_2("0039", "该证件号属于黑名单之内"),
        PGW_PAY_RESULT_BLACK_LIST_3("0040", "该商户属于黑名单之内"),
        PGW_PAY_RESULT_QUERY_ORGAN_SUCCESS("0041", "查询支持机构成功"),

        PGW_PAY_RESULT_VERIFY_FAIL("0050", "验签失败"),
        PGW_PAY_RESULT_SIGN_EXCEPTION("0051", "证通加签异常"),
        PGW_PAY_RESULT_VERIFY_EXCEPTION("0052", "证通验签异常"),
        PGW_PAY_RESULT_REFUND_FAILED("0053", "退款失败"),
        PGW_PAY_RESULT_DUPLICATE_INFO("0054", "查询客户签约要素信息异常"),
        PGW_PAY_RESULT_UNMATCH_ELEMENT("0055", "上送信息与签约信息不匹配"),
        PGW_PAY_RESULT_CORP_TRANSFER_FAIL("0056", "对公划付失败"),
        PGW_PAY_RESULT_CORP_UNFREEZE_FAIL("0057", "对公划付冲正失败"),
        PGW_PAY_RESULT_NO_SIGN_INFO("0058", "没有签约信息"),
        PGW_PAY_RESULT_RISK_NOT_PASS("0060", "风控检查未通过"),
        PGW_PAY_RESULT_PAY_SEND_FAIL("0059", "信息入库失败"),
        PGW_PAY_RESULT_PAY_SEND_DEPOSIT_SUCCESS("0060", "信息已入库，无法进行重复操作"),
        PGW_PAY_RESULT_PAY_SEND_NOTICE("0061", "返回通知"),
        PGW_PAY_RESULT_PAY_SEND_VISION("0062", "版本号不匹配"),
        PGW_PAY_RESULT_PAY_FILE_MISSING("0063", "未检测到送盘文件"),
        PGW_PAY_RESULT_PAY_FILE_SUCCESS("0064", "送盘文件传输成功"),
        PGW_PAY_RESULT_PAY_FILE_FAIL("0065", "该送盘文件信息已存在"),
        PGW_PAY_RESULT_PAY_SEND_SUCCESS("0066", "信息入库成功"),
        PGW_PAY_RESULT_PAY_DOING_TIME("0067", "预处理时间小于请求时间，时间不合理"),
        PGW_PAY_RESULT_PAY_SELECSTAT("0068", "查无该信息"),
        PGW_PAY_RESULT_LACK_EXCEPTION("0069", "解析报文异常"),
        PGW_PAY_RESULT_PAY_FILE_FAILS("0070", "送盘失败"),
        PGW_PAY_RESULT_INCOMPLETE_FILE("0071", "文件上传不完整（没有.end文件）"),
        PGW_PAY_RESULT_BATCH_NUMBER("0072", "文件名批次号不匹配"),
        PGW_PAY_RESULT_MERCHANT_NUMBER("0073", "文件名商户号不匹配"),
        PGW_PAY_RESULT_NO_SUCH_BUSY("0074", "未知的业务类型"),
        PGW_PAY_RESULT_FILE_HEAD_ERROR("0075", "文件头格式不正确"),
        PGW_PAY_RESULT_CONTENT_MERCHANT_ERROR("0076", "文件内容商户号与上送商户号不一致"),
        PGW_PAY_RESULT_CONTENT_BATCHNO_ERROR("0077", "文件内容批次号与上送批次号不一致"),
        PGW_PAY_RESULT_ANALYSIS_CONTENT_ERROR("0078", "解析文件内容格式错误"),
        PGW_PAY_RESULT_TOTALNUM_NOT_SAME("0079", "总笔数与文件头总笔数不一致"),
        PGW_PAY_RESULT_TOTALAMT_NOT_SAME("0080", "总金额与文件头总金额不一致"),
        PGW_PAY_RESULT_TOTALNUM_EXCEED_MAX("0081", "批量文件总笔数超过最大限制"),
        PGW_PAY_RESULT_FILE_NOCONFORMITY("0082", "文件名格式不符合要求"),
        PGW_PAY_RESULT_ANALYSIS_CONTENT_SUCCESS("0083", "校验送盘文件入库成功"),
        PGW_PAY_RESULT_FILE_CONTENT_NULL("0084", "文件内容为空"),
        PGW_PAY_RESULT_ORGANNO_ERROR("0085", "校验上送机构号错误"),
        PGW_PAY_RESULT_CONTENT_AVALIABLE_ERROR("0087", "批量代付入库检验时代付金额大于商户可用备付金余额"),
        PGW_PAY_RESULT_BACK_FILE_SUCCESS("0088", "回盘成功"),
        PGW_PAY_RESULT_BACK_FILE_FAIL("0089", "回盘失败"),
        PGW_PAY_DATE_MAX_AMOUNT_FAIL("0090", "当前累计金额超过累计限额金额"),
        PGW_PAY_DATE_MAX_COUNT_FAIL("0091", "当前累计笔数超过累计笔数"),
        PGW_PAY_SIGNLE_MIN_AMOUNT_FAIL("0092", "当前金额低于最小单笔限额"),
        PGW_PAY_SIGNLE_Max_AMOUNT_FAIL("0093", "当前金额超过最大单笔限额"),
        ;

        private String value;
        private String label;

        PGWPayResult(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : 备付金冻结类型<br/>
     * Date : 2015年12月9日 上午11:41:19 <br/>
     *
     * @author caicj
     * @version R
     * @since JDK 1.7
     */
    public enum MercAccountFrzType {
        /**
         * 金额冻结
         */
        MERCHANT_ACCOUNT_FRZ_TYPE_AMOUNT_FREEZE("01", "金额冻结"),
        ;

        private String value;

        private String label;

        MercAccountFrzType(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : 备付金冻结状态<br/>
     * Date : 2015年12月9日 下午1:37:45 <br/>
     *
     * @author caicj
     * @version R
     * @since JDK 1.7
     */
    public enum MercAccountFrzStatus {
        /**
         * 冻结
         */
        MERCHANT_ACCOUNT_FRZ_STATUS_FREEZE("01", "冻结"),
        /**
         * 解冻
         */
        MERCHANT_ACCOUNT_FRZ_STATUS_UNFREEZE("02", "解冻"),
        /**
         * 冻结扣划
         */
        MERCHANT_ACCOUNT_FRZ_STATUS_FREEZETRANS("03", "冻结扣划"),
        ;

        private String value;

        private String label;

        MercAccountFrzStatus(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : 红包订单状态<br/>
     * Date : 2015年12月18日 下午1:06:45 <br/>
     *
     * @author caicj
     * @version R
     * @since JDK 1.7
     */
    public enum RedPacketStatus {
        // 红包订单状态
        /**
         * 暂存
         */
        RED_PACKET_STATUS_TEMP("01", "暂存"),
        /**
         * 待审核
         */
        RED_PACKET_STATUS_TOAUDIT("02", "待审核"),
        /**
         * 审核成功
         */
        RED_PACKET_STATUS_SUCCESS("03", "审核成功"),
        /**
         * 审核失败
         */
        RED_PACKET_STATUS_FAILURE("04", "审核失败"),
        /**
         * 已结束(已退回)
         */
        RED_PACKET_STATUS_FINISH("05", "已结束"),
        /**
         * 审核撤销(商户在审核通过前发起撤销)
         */
        RED_PACKET_STATUS_CANCEL("06", "审核撤销"),
        /**
         * 审核处理中(需查询红包平台确认)
         */
        RED_PACKET_STATUS_DEALING("07", "审核处理中"),
        ;

        private String value;

        private String label;

        RedPacketStatus(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : 商户属性<br/>
     * Date : 2015年12月31日 下午2:40:56 <br/>
     *
     * @author caicj
     * @version R
     * @since JDK 1.7
     */
    public enum PGWMercProperty {
        /**
         * 普通商户(收单商户)
         */
        MERC_PROPERTY_NORMAL("01", "普通商户"),
        /**
         * 便民运营商
         */
        MERC_PROPERTY_BM("02", "便民运营商"),
        /**
         * 网上开户商户
         */
        MERC_PROPERTY_KH("03", "网上开户商户"),
        /**
         * 网上开户及收单商户
         */
        MERC_PROPERTY_KHANDNORMAL("04", "网上开户及收单商户"),
        ;

        private String value;

        private String label;

        PGWMercProperty(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : OMS转账请求返回状态码<br/>
     * Date : 2016年4月19日 上午10:09:23 <br/>
     *
     * @author qiss
     * @version R
     * @since JDK 1.7
     */
    public enum PGWOmsTranSts {
        /**
         * 成功
         */
        OMS_TRANSTS_SUC("00", "成功"),
        /**
         * 可疑
         */
        OMS_TRANSTS_DOUBT("01", "可疑"),
        /**
         * 待转账
         */
        OMS_WAIT_TO_PAY("02", "等待划账"),
        /**
         * 待确认(批量)
         */
        OMS_TRANSTS_FAIL("03", "失败"),
        ;

        private String value;

        private String label;

        PGWOmsTranSts(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : 网上开户结算记录状态<br/>
     * Date : 2016年4月19日 下午3:32:20 <br/>
     *
     * @author px
     * @version R
     * @since JDK 1.7
     */
    public enum PGWOnlineopenSettleRecStatus {
        /**
         * 未上传文件
         */
        ONLINEOPENSETRECSTAUS_00("00", "未上传文件"),
        /**
         * 已上传，待审批（对账）
         */
        ONLINEOPENSETRECSTAUS_01("01", "已上传，待审批（对账）"),
        /**
         * 已拒绝
         */
        ONLINEOPENSETRECSTAUS_10("10", "已拒绝"),
        /**
         * 审批通过（对账完成）待结算
         */
        ONLINEOPENSETRECSTAUS_02("02", "审批通过（对账完成）待结算"),
        /**
         * 结算中
         */
        ONLINEOPENSETRECSTAUS_80("80", "结算中"),
        /**
         * 结算失败
         */
        ONLINEOPENSETRECSTAUS_88("88", "结算失败"),
        /**
         * 结算成功
         */
        ONLINEOPENSETRECSTAUS_99("99", "结算成功"),
        ;

        private String value;

        private String label;

        PGWOnlineopenSettleRecStatus(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : 网上开户商户角色<br/>
     * Date : 2016年4月21日 下午2:55:44 <br/>
     *
     * @author pengxiao
     * @version R
     * @since JDK 1.7
     */
    public enum PGWOnlineOpenAccountMercRole {
        /**
         * 服务方
         */
        MER_ROLE_S("S", "服务方"),
        /**
         * 分销方
         */
        MER_ROLE_D("D", "分销方"),
        /**
         * 合作方
         */
        MER_ROLE_C("C", "合作方"),
        ;

        private String value;

        private String label;

        PGWOnlineOpenAccountMercRole(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : 网上开户商户划付状态与分润状态<br/>
     * Date : 2016年4月21日 下午2:55:44 <br/>
     *
     * @author pengxiao
     * @version R
     * @since JDK 1.7
     */
    public enum PGWOnlineOpenAccountShareProfitStu {
        /**
         * 未划付
         */
        SHARE_PROFITSTU_00("00", "未划付"),
        /**
         * 划付中
         */
        SHARE_PROFITSTU_01("01", "划付中"),
        /**
         * 分录失败,未划付
         */
        SHARE_PROFITSTU_02("02", "分录失败,未划付"),
        /**
         * 分录成功,划付中
         */
        SHARE_PROFITSTU_80("80", "分录成功,划付中"),
        /**
         * 分录成功,划付失败
         */
        SHARE_PROFITSTU_88("88", "分录成功,划付失败"),
        /**
         * 划付成功
         */
        SHARE_PROFITSTU_99("99", "划付成功"),
        ;
        private String value;

        private String label;

        PGWOnlineOpenAccountShareProfitStu(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : 网上开户记账状态<br/>
     * Date : 2016年4月27日 下午2:55:44 <br/>
     *
     * @author tanlq
     * @version R
     * @since JDK 1.7
     */
    public enum PGWOnlineOpenKeepStatus {
        KEEP_STATUS_00("00", "未上账"),
        KEEP_STATUS_88("88", "上账失败"),
        KEEP_STATUS_90("90", "无需处理"),
        KEEP_STATUS_99("99", "上账成功"),
        ;
        private String value;

        private String label;

        PGWOnlineOpenKeepStatus(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : 运营转账对账状态<br/>
     * Date : 2016年5月2日 下午9:04:51 <br/>
     *
     * @author caicj
     * @version R
     * @since JDK 1.7
     */
    public enum PGWOmsCheckSts {
        /**
         * 未对账
         */
        OMS_CHECK_STATUS_00("00", "未对账"),
        /**
         * 对账失败
         */
        OMS_CHECK_STATUS_88("88", "对账失败"),
        /**
         * 对账成功
         */
        OMS_CHECK_STATUS_99("99", "对账成功");
        private String value;

        private String label;

        PGWOmsCheckSts(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }

    /**
     * Function : 迅联电子账户对账文件处理状态<br/>
     * Date : 2016年10月4日 下午5:19:55 <br/>
     *
     * @author xiejf
     * @since JDK 1.7
     */
    public enum EAccountCheckFileStatus {

        EACCOUNT_CHECK_FILE_STATUS_1("1", "确认对账文件名称并进行记录"),
        EACCOUNT_CHECK_FILE_STATUS_2("2", "读取对账文件并入库完成");

        private String value;
        private String label;

        EAccountCheckFileStatus(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : 迅联电子账户对账文件处理状态<br/>
     * Date : 2016年10月10日 下午5:19:55 <br/>
     *
     * @author xiejf
     * @since JDK 1.7
     */
    public enum EAccountBatcheSettleFileStatus {

        BATCH_SETTLE_FILE_STATUS_1("1", "确定需要进行批量结算的商户请求文件名完成"),
        BATCH_SETTLE_FILE_STATUS_2("2", "该结算文件数据录入完成"),
        BATCH_SETTLE_FILE_STATUS_3("3", "初步处理入库结算数据"),
        BATCH_SETTLE_FILE_STATUS_4("4", "生成电子账户结算请求文件完成"),
        BATCH_SETTLE_FILE_STATUS_5("5", "扫描回盘文件并入库更新结算状态完成"),
        BATCH_SETTLE_FILE_STATUS_6("6", "生成商户回盘文件完成");

        private String value;
        private String label;

        /**
         * 构造函数
         */
        EAccountBatcheSettleFileStatus(String value, String label) {
            this.value = value;
            this.label = label;
        }

        /**
         * 获取枚举值的实例
         */
        public static EAccountBatcheSettleFileStatus getInstance(String paraValue) {
            EAccountBatcheSettleFileStatus[] allEnums = EAccountBatcheSettleFileStatus.values();
            for (EAccountBatcheSettleFileStatus single : allEnums) {
                if (single.value.equals(paraValue)) {
                    return single;
                }
            }
            return null;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }

    }

    public enum PGWMercSettleType {
        /**
         * 00-正常结算类
         */
        MERC_SETTLE_TYPE_00("00", "正常结算类"),
        /**
         * 01-特殊结算类
         */
        MERC_SETTLE_TYPE_01("01", "特殊结算类（普通月结类）"),
        /**
         * 02-特殊结算类
         */
        MERC_SETTLE_TYPE_02("02", "特殊结算类（月结封顶（底）类）"),
        /**
         * 03-特殊结算类
         */
        MERC_SETTLE_TYPE_03("03", "特殊结算类（包月类）"),
        /**
         * 04-备付金结算类
         */
        MERC_SETTLE_TYPE_04("04", "备付金结算类");

        private String value;
        private String label;

        PGWMercSettleType(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : 对账差错处理状态<br/>
     * Date : 2017年4月12日 上午9:58:11 <br/>
     *
     * @author caicj
     * @version R
     * @since JDK 1.7
     */
    public enum PGWCheckMismatchHandleStatus {
        /**
         * 待处理
         */
        HANDLE_STATUS_0("0", "待处理"),
        /**
         * 处理成功
         */
        HANDLE_STATUS_1("1", "处理成功"),
        /**
         * 处理失败
         */
        HANDLE_STATUS_2("2", "处理失败");

        private String value;
        private String label;

        PGWCheckMismatchHandleStatus(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : 对账差错处理状态 <br/>
     * Date : 2017年4月16日 下午9:47:12 <br/>
     *
     * @author xiejf
     * @since JDK 1.7
     */
    public enum OmsPayeeAcctType {
        /**
         * 结算账号
         */
        SETTLE_ACCOUNT("00", "结算账号"),
        /**
         * 记账码
         */
        ACCOUNTING_CODE("01", "记账码"),
        /**
         * 清算账号
         */
        CLEAR_ACCOUNT("02", "清算账号");

        private String value;
        private String label;

        OmsPayeeAcctType(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : 风控前置机支付账号类型 <br/>
     * Date : 2017年7月26日 下午9:47:12 <br/>
     *
     * @author wangxiaohao
     * @since JDK 1.7
     */
    public enum RiskPaccTypeEnum {
        /**
         * 储蓄卡
         */
        DEBIT_CARD("90", "1"),
        /**
         * 信用卡
         */
        CREDIT_CARD("91", "2"),
        /**
         * 余额账户
         */
        POCKET("A1", "3"),
        /**
         * 证通宝账户
         */
        ECT_BAOBAO("A2", "4");

        private String value;
        private String label;

        RiskPaccTypeEnum(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public static RiskPaccTypeEnum getRiskPaccTypeEnumByAcctType(String acctType) {
            for (RiskPaccTypeEnum sexEnum : RiskPaccTypeEnum.values()) {
                if (StringUtils.equals(acctType, sexEnum.getValue())) {
                    return sexEnum;
                }
            }
            return null;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : 风控前置机返回状态码和说明<br/>
     * Date : 2017年7月26日 下午9:47:12 <br/>
     *
     * @author wangxiaohao
     * @since JDK 1.7
     */
    public enum RiskFrontResultEnum {

        SUCCESS("0000", "成功"),
        MSSEAGE_DATA_ERROR("1001", "报文数据异常"),
        UNKNOWN_TRANS("1002", "交易未识别"),
        UNABLE_TRANS("1003", "没有可用交易"),
        UNCONFIRM_TRANS("1004", "交易无需确认"),
        NOT_EXITS_SESSION("1005", "没有此会话"),
        NOT_EXITS_USER("1006", "没有此用户"),
        CALL_RISK_SERVER_TIME_OUT("1007", "调用业务监控系统超时"),
        APT_CLOSED("1008", "API接口开关已关闭"),
        TRANS_DATA_DELETION("1009", "交易数据不全"),
        RISK_EVALUATE_EXCEPTION("1010", "风险评估异常"),
        UNKNOWN_PALTFORM_EXCEPTION("1011", "未知平台异常"),
        UNKNOWN_EXCUTE_EXCEPTION("1012", "未知处理器异常"),
        UNKNOWN_SERVICE_EXCEPTION("1013", "未知服务异常"),
        SERVER_NOT_FOUND_EXCEPTION("1014", "服务没有找到"),
        DECIPHER_DECODE_EXCEPTION("1015", "解码处理器解码异常"),
        ENCRYPT_ENCODE_EXCEPTION("1016", "解码处理器解码异常");

        private String value;
        private String label;

        RiskFrontResultEnum(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : 风控前置机返回处置方式<br/>
     * Date : 2017年7月26日 下午9:47:12 <br/>
     *
     * @author wangxiaohao
     * @since JDK 1.7
     */
    public enum RiskFrontDisposalEnum {

        PASS("PS01", "放行"),
        SMS_TIPS("PS02", "短信提醒"),
        SMS_AUTH("PS03", "短信验证"),
        HANG_UP("PS04", "挂起"),
        BLOCKING_UP("PS05", "阻断"),
        BLOCKING_UP_FREEZE_ACCOUNT("PS06", "阻断并冻结账户"),
        BLOCKING_UP_FREEZE_USER("PS07", "阻断并冻结会员"),
        BLOCKING_UP_ADD_BLACK_LIST("PS08", "阻断并加黑名单"),
        VIDEO_AUTH("PS09", "视频验证"),
        SECURITY_QUESTION("PS10", "安全问题"),
        RISK_EVALUATE_EXCEPTION("PS11", "支付密码验证");

        private String value;
        private String label;

        RiskFrontDisposalEnum(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : 风控检查返回状态<br/>
     * Date : 2017年7月26日 下午9:47:12 <br/>
     *
     * @author wangxiaohao
     * @since JDK 1.7
     */
    public enum RiskFrontResultStatusEnum {

        SUCCESS("1", "通过"),
        FAIL("0", "未通过");

        private String value;
        private String label;

        RiskFrontResultStatusEnum(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }


    /**
     * Function : 商户结算点 <br/>
     * Date : 2017年8月8日 下午9:47:12 <br/>
     *
     * @author edwardChan
     * @since JDK 1.7
     */
    public enum MERCHANTSETTLEPOINT {
        /**
         * 结算账号
         */
        MERCHANT_SETTLE_POINT_01("01", "A类商户"),
        /**
         * 记账码
         */
        MERCHANT_SETTLE_POINT_02("02", "B类商户");

        private String value;
        private String label;

        MERCHANTSETTLEPOINT(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : 网联业务类型 <br/>
     * Date : 2017年11月9日 下午5:23:12 <br/>
     *
     * @author edwardChan
     * @since JDK 1.7
     */
    public enum NUCCBUSINESSTYPE {
        /**
         * 实物商品购买
         */
        MATERIAL_GOODS_BUY("100001", "实物商品购买"),
        /**
         * 虚拟商品购买
         */
        FICTITIOUS_GOODS_BUY("100002", "虚拟商品购买"),
        /**
         * 预付类账号充值
         */
        PREPAID_ACCOUNT_DEPOSIT("100003", "预付类账号充值"),
        /**
         * 航旅服务订购
         */
        AIRLINE_SERVICE_ORDER("100004", "航旅服务订购"),
        /**
         * 活动票务订购
         */
        ACTIVITY_TICKET("100005", "活动票务订购"),
        /**
         * 商业服务消费
         */
        BUSINESS_SERVICE_CONSUMPTION("100006", "商业服务消费"),
        /**
         * 生活服务消费
         */
        LIVE_SERVICE_CONSUMPTION("100007", "生活服务消费"),
        /**
         * 其他商家消费
         */
        ONTHER_MERCHANT_CONSUMPTION("100099", "其他商家消费"),
        /**
         * 水电煤缴费
         */
        HYDROPOWER_COAL_PAYMENT("110001", "水电煤缴费"),
        /**
         * 税费缴纳
         */
        TAX_PAYMENT("110002", "税费缴纳"),
        /**
         * 学校教育缴费
         */
        EDUCATION_PAYMENT("110003", "学校教育缴费"),
        /**
         * 医疗缴费
         */
        MEDICAL_PAYMENT("110004", "医疗缴费"),
        /**
         * 罚款缴纳
         */
        FINE_PAYMENT("110005", "医疗缴费"),
        /**
         * 路桥通行缴费
         */
        TRAFFIC_PAYMENT("110006", "路桥通行缴费"),
        /**
         * 邮政缴费
         */
        POST_PAYMENT("110007", "邮政缴费"),
        /**
         * 电视账单缴费
         */
        TV_ORDER_PAYMENT("110008", "电视账单缴费"),
        /**
         * 话费单缴费
         */
        TELEPHONE_ORDER_PAYMENT("110009", "话费单缴费"),
        /**
         * 宽带账单缴费
         */
        BROADBAND_BILL_PAYMENT("110010", "宽带账单缴费"),
        /**
         * 公益捐款
         */
        PUBLIC_DONATIONS("110011", "公益捐款"),
        /**
         * 其他公共服务
         */
        ONTHER_PUBLIC_SERVICE("110099", "110099"),
        /**
         * 基金申购
         */
        FUND_PURCHASE("120001", "基金申购"),
        /**
         * 基金认购
         */
        FUND_SUBSCRIBING("120002", "基金认购"),
        /**
         * 保险购买
         */
        INSURANCE_PURCHASE("120003", "保险购买"),
        /**
         * 信贷偿还
         */
        CREDIT_REPAYMENT("120004", "信贷偿还"),
        /**
         * 商业众筹
         */
        BUSINESS_CROWD_RAISING("120005", "商业众筹"),
        /**
         * 贵金属投资买入
         */
        Precious_metals_investment_buy("120006", "贵金属投资买入"),
        /**
         * 其他互联网金融
         */
        ONTHER_INTERNET_BANK("120099", "其他互联网金融"),
        /**
         * 支付账户充值
         */
        PAY_ACCOUNT_RECHARGE("130001", "支付账户充值"),
        /**
         * 充值至他人支付账户
         */
        AGENT_PAY_TO_ONTHER_ACCOUNT("130002", "充值至他人支付账户");

        private String value;
        private String label;

        NUCCBUSINESSTYPE(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : PCAC商户类型 <br/>
     * Date : 2017年10月03日 上午10:57:12 <br/>
     *
     * @author wxh
     * @since JDK 1.7
     */
    public enum PCACMERCHANTTYPE {
        /**
         * 个人
         */
        PERSONAL("01", "自然人"),
        /**
         * 商户
         */
        BUSSINESS_MERCHANT("02", "企业商户"),
        /**
         * 个体工商户
         */
        HOUSEHOLDS_MERCHANT("03", "个体工商户");

        private String value;
        private String label;

        PCACMERCHANTTYPE(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : PCAC法人证件类型 <br/>
     * Date : 2017年10月03日 上午10:57:12 <br/>
     *
     * @author wxh
     * @since JDK 1.7
     */
    public enum CORPORATEIDCODE {
        /**
         * 营业执照编码
         */
        BUSINESS_LICENCE_NO("01", "营业执照编码"),
        /**
         * 统一社会信息代码
         */
        UNIFORM_SOCIAL_INFORMATION_CODE("02", "统一社会信息代码"),
        /**
         * 组织机构代码证
         */
        ORGANIZATION_CODE_CERTIFICATE("03", "组织机构代码证"),
        /**
         * 经营许可证
         */
        BUSINESS_CERTIFICATE("04", "经营许可证"),
        /**
         * 税务登记证
         */
        TAX_REGISTRATION_CERTIFICATE("05", "税务登记证"),
        /**
         * 其他
         */
        ONTHER("99", "其他");

        private String value;
        private String label;

        CORPORATEIDCODE(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : PCAC个人证件类型 <br/>
     * Date : 2017年10月03日 上午10:57:12 <br/>
     *
     * @author wxh
     * @since JDK 1.7
     */
    public enum PERSONALIDCODE {
        ID_CARD_CODE("01", "身份证"),
        CERTIFICATE_OF_OFFICERS("02", "军官证"),
        PASSPORT("03", "组织机构代码证"),
        HOUSEHOLD_REGISTER("04", "户口簿"),
        SOLDBUCH("05", "士兵证"),
        HONG_KONG_AND_MAINLAND_TRAVEL_PERMIT("06", "港澳来往内地通行证"),
        TAIWAN_COMPATRIOTS_IN_MAINLAND_TRAVEL_PERMIT("07", "台湾同胞来往内地通行证"),
        TEMPORARY_ID_CARD_CODE("08", "临时身份证"),
        RESIDENCE_PERMIT_FOR_FOREIGNERS("09", "外国人居留证"),
        POLICE_CERTIFICATE("10", "警官证"),
        ONTHER("99", "其他");

        private String value;
        private String label;

        PERSONALIDCODE(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Date : 2017年10月03日 上午10:57:12 <br/>
     *
     * @author edwardChan
     * @since JDK 1.7
     */
    public enum PCACMERCHANTATTRIBUTE {
        /**
         * 实体特约商户
         */
        ENTITY_SPECIAL_MERCHANT("01", "实体特约商户"),
        /**
         * 网络特约商户
         */
        NET_SPECIAL_MERCHANT("02", "网络特约商户"),
        /**
         * 实体兼网络特约商户
         */
        ENTITYANDNET_SPECIAL_MERCHANT("03", "实体兼网络特约商户");

        private String value;
        private String label;

        PCACMERCHANTATTRIBUTE(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : PCAC清算网络 <br/>
     * Date : 2017年10月03日 上午10:57:12 <br/>
     *
     * @author edwardChan
     * @since JDK 1.7
     */
    public enum SETTLENETWORK {
        /**
         * 中国银联
         */
        CHINA_UNIONPAY("01", "中国银联"),
        /**
         * 网络支付清算平台
         */
        NETWORK_PAY_CLEAR_PLATFORM("02", "网络支付清算平台"),
        /**
         * 清算总中心
         */
        CLEAR_CENTER("03", "清算总中心"),
        /**
         * 农信银
         */
        ITS_BANKS("04", "农信银"),
        /**
         * 城商行
         */
        CITY_COMMERCIAL_BANKS("05", "城商行"),
        /**
         * 同城结算中心
         */
        SETTLE_CENTER("06", "同城结算中心"),
        /**
         * 其他
         */
        ONTHER("99", "其他");

        private String value;
        private String label;

        SETTLENETWORK(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : PCAC商户状态 <br/>
     * Date : 2017年10月03日 上午10:57:12 <br/>
     *
     * @author edwardChan
     * @since JDK 1.7
     */
    public enum PCACMERCHANTSTATUS {
        /**
         * 启用
         */
        START("01", "启用"),
        /**
         * 关闭（暂停）
         */
        CLOSE("02", "关闭（暂停）"),
        /**
         * 注销
         */
        LOGOUT("03", "注销");

        private String value;
        private String label;

        PCACMERCHANTSTATUS(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : PCAC开通业务种类 <br/>
     * Date : 2017年10月03日 上午10:57:12 <br/>
     *
     * @author edwardChan
     * @since JDK 1.7
     */
    public enum PCACOPERSERVICETYPE {
        /**
         * POS
         */
        POS("01", "POS"),
        /**
         * 条码
         */
        BAR_CODE("02", "条码"),
        /**
         * 其他
         */
        OTHER("99", "其他");

        private String value;
        private String label;

        PCACOPERSERVICETYPE(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : PCAC计费类型 <br/>
     * Date : 2017年10月03日 上午10:57:12 <br/>
     *
     * @author edwardChan
     * @since JDK 1.7
     */
    public enum PCACBILLTYPE {
        /**
         * 标准
         */
        STANDARD("01", "标准"),
        /**
         * 优惠
         */
        PREFERENTIAL("02", "优惠"),
        /**
         * 减免
         */
        BREAKS("03", "减免");

        private String value;
        private String label;

        PCACBILLTYPE(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : PCAC支持账户类型 <br/>
     * Date : 2017年10月03日 上午10:57:12 <br/>
     *
     * @author edwardChan
     * @since JDK 1.7
     */
    public enum PCACSUPPORTACCOUNTTYPE {
        /**
         * 借记卡
         */
        DEBIT_CARD("01", "借记卡"),
        /**
         * 贷记卡
         */
        CREDIT_CARD("02", "贷记卡"),
        /**
         * 支付账户
         */
        PAY_ACCOUNT("03", "支付账户"),
        /**
         * 银行账户
         */
        BANK_ACCOUNT("04", "银行账户");

        private String value;
        private String label;

        PCACSUPPORTACCOUNTTYPE(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : PCAC上传方式 <br/>
     * Date : 2017年10月03日 上午10:57:12 <br/>
     *
     * @author edwardChan
     * @since JDK 1.7
     */
    public enum PCACUPLOADWAY {
        /**
         * 批量导入
         */
        BATCH_IMPORT("01", "批量导入"),
        /**
         * 人工录入
         */
        MENUAL_ENTRY("02", "人工录入"),
        /**
         * 接口导入
         */
        INTERFACE_IMPORT("03", "接口导入");

        private String value;
        private String label;

        PCACUPLOADWAY(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : PCAC返回响应码<br/>
     * Date : 2017年10月03日 上午10:57:12 <br/>
     *
     * @author edwardChan
     * @since JDK 1.7
     */
    public enum PCACResponseCodeEnum {
        LOGIN_REFUSED("BD1002", "系统拒绝用户登录"),
        USER_LOGINED("BD1003", "用户已登录"),
        TRANS_SUCCESS("S00000", "交易处理成功"),
        QUERY_NOT_EXITS("S00001", "查询无此记录"),
        NOT_LOGGED_IN("H00001", "未登录");

        private String value;
        private String label;

        PCACResponseCodeEnum(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }


    /**
     * Function : FTS任务名称<br/>
     * Date : 2017年10月27日 上午10:57:12 <br/>
     *
     * @author hewt
     * @since JDK 1.7
     */
    public enum FTSTaskName {
        GET_BATCH_COLLECT_PAY_FILE("getBatchCollectPayFile", "主动获取商户批量代收付文件"),
        PUSH_BATCH_COLLECT_PAY_FILE("pushBatchCollectPayFile", "推送商户批量代收代付文件"),
        ;

        private String value;
        private String label;

        FTSTaskName(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    /**
     * Function : PGW常量定义<br/>
     * Date : 2015年5月20日 下午5:33:42 <br/>
     *
     * @author WANGLONG
     * @version DictConst
     * @since JDK 1.7
     */
    public interface PGWConstant {
        /**
         * UTF-8
         */
        String UTF8 = "UTF-8";
        /**
         * MD5
         */
        String MD5 = "MD5";
        /**
         * 返回标识
         */
        String RET_FLAG = "ret_flag";
        /**
         * 返回代码
         */
        String RET_CODE = "ret_code";
        /**
         * 返回消息
         */
        String RET_MSG = "ret_msg";
        /**
         * 返回转接流水号
         */
        String REQ_MSG_ID = "reqMsgId";
        /**
         * 返回转接时间戳
         */
        String TIMESTAMP = "timestamp";
        /**
         * 成功
         */
        String SUCCESS = "Y";
        /**
         * 失败
         */
        String FAILURE = "W";
        /**
         * 异常
         *///JsonChen@20150907 按照《证通技术标准_6_ESB技术规范V0.7》，更正上送异常类型为 W
        String EXCEPTION = "W";
        /**
         * 超时
         */
        String TIMEOUT = "T";
        /**
         * 是
         */
        String YES = "Y";
        /**
         * 否
         */
        String NO = "N";
        /**
         * 2
         */
        String TWO = "2";
        /**
         * 1
         */
        String ONE = "1";
        /**
         * 0
         */
        String ZERO = "0";
        /**
         * 0.00
         */
        String ZERO_2_ACCURACY = "0.00";
        /**
         * 真
         */
        String TRUE = "true";
        /**
         * 假
         */
        String FALSE = "false";
        /**
         * 性别类型之未知
         */
        String SEX_UNKNOW = "0";
        /**
         * 性别类型之男
         */
        String SEX_MALE = "1";
        /**
         * 性别类型之女
         */
        String SEX_FEMALE = "2";
        /**
         * 性别类型之未说明
         */
        String SEX_UNEXPLAN = "9";
        /**
         * 绑定
         */
        String BIND = "0";
        /**
         * 解绑
         */
        String UNBIND = "1";
        /**
         * 单独账户验证
         */
        String VALAUTE_ACCOUNT = "3";
        /**
         * 更新最后使用时间
         */
        String UPDATE_LAST_USED_TIME = "2";
        /**
         * 快捷支付 绑定
         */
        String QUICK_BIND = "4";
        /**
         * 支付密码控件加密随机key
         */
        String RANDOM_KEY = "randomKey";
        /**
         * 报文类型
         */
        String STRUCT_TYPE_XML = "XML";
        /**
         * 默认商户号
         */
        String MERCHANTID = "000000000";
        /**
         * 收单机构号
         */
        String ACCEPT_ORGAN_NO = "0000110013";
        /**
         * 业务种类之其他
         */
        String BIZ_KIND_OTHER = "09001";
        /**
         * 业务种类之账户余额查询
         */
        String BIZ_KIND_QUERY_ACCT_BALANCE = "03703";
        /**
         * 业务种类之账户余额查询
         */
        String BIZ_KIND_QUERY_TRAN_STATUS = "03701";

        String RESULT = "result";
        /**
         * 证件类型之身份证
         */
        String IDENTITY_TYPE_ID = "10";
        /**
         * 证件类型之护照
         */
        String IDENTITY_TYPE_PASSPORT = "11";
        /**
         * 证件类型之军官证
         */
        String IDENTITY_TYPE_OFFICER = "12";
        /**
         * 机构类型之银行
         */
        String ORGAN_TYPE_BANK = "0";
        /**
         * 机构类型之券商
         */
        String ORGAN_TYPE_TRADE = "1";
        /**
         * 机构类型之商户
         */
        String ORGAN_TYPE_MERCHANT = "2";

        /**
         * 累计限额 零值，表示不限额
         */
        Double AOUNT_LMT_ZERO = 0.00;
        Integer INTEGER_ONE = new Integer(1);
        String BLANK = "";

        /**
         * 账户类型 支持
         */
        String SURRPORT = "1";
        /**
         * 账户类型 不支持
         */
        String UNSURRPORT = "0";

        /**
         * 机构支持 有效
         */
        String SURRPORT_ORGAN = "1";
        /**
         * 机构支持 无效
         */
        String UNSURRPORT_ORGAN = "0";

        /**
         * 是否超额    0未超额
         */
        String ISEXCESS_NO = "0";
        /**
         * 是否超额    1超额
         */
        String ISEXCESS_YES = "1";

        /**
         * 发送 短信验证码 / 需要支付密码
         */
        String NEED_SEND = "0";
        /**
         * 不发送 短信验证码/ 不需要支付密码
         */
        String NEED_LESS = "1";

        /**
         * BLACK_TYPE_ID : 黑名单查询类型  身份证
         */
        String BLACK_TYPE_ID = "01";
        /**
         * BLACK_TYPE_ACCOUNT : 黑名单查询类型- 账户
         */
        String BLACK_TYPE_ACCOUNT = "02";

        /**
         * ISCERTFLAG 是否全要素绑卡 0：全要素
         */
        String CERTFLAG = "0";
        /**
         * NOCERTFLAG 是否全要素绑卡 1:非全要素
         */
        String NOCERTFLAG = "1";

        /**
         * 默认节点号
         */
        String DEFAULT_SYS_NODE = "99";

        /**
         * 单笔订单查询操作类型   删除
         */
        String IS_DELETE = "02";
        /**
         * 单笔订单查询操作类型   查询
         */
        String IS_QUERY = "01";
        /**
         * 是否删除单笔支付订单：不删除
         */
        String IS_NOTDELETE_PAYORDERINFO = "0";
        /**
         * 是否删除单笔支付订单：删除
         */
        String IS_DELETE_PAYORDERINFO = "1";

        /**
         * 支持交易日期类型-非节假日
         */
        String NOTHOLIDAYS = "1";
        /**
         * 支持交易时间类型-时间段
         */
        String TIME_QUANTUM = "1";

        /**
         * 是否是节假日-是
         */
        String IS_HOLIDAYS = "1";
        /**
         * 是否是节假日-否
         */
        String IS_NOTHOLIDAYS = "0";

        /**
         * 积分类型     1001普通积分
         */
        String COMMON_BONUS = "1001";
        /**
         * 积分类型     1002专用积分
         */
        String DEDICATED_BONUS = "1002";
        /**
         * 积分消费应答   00失败
         */
        String RETBONUS_CONSUMEFAIL = "00";
        /**
         * 积分消费应答   01成功
         */
        String RETBONUS_CONSUMESUCCESS = "01";

        /**
         * 退款方式   0自动
         */
        String REFOUND_STYLE_AUTO = "0";
        /**
         * 退款方式   1退款
         */
        String REFOUND_STYLE_REFOUND = "1";
        /**
         * 退款方式   2撤销
         */
        String REFOUND_STYLE_REVOKE = "2";

        /**
         * 页面错误码
         */
        String PAGE_ERROR_CODE = "page_error_code";

        /**
         * 网联错误码规范
         */
        String NET_UNION_PAY_CODE = "P";

    }

    /**
     * Function :积分业务类型<br/>
     * Date : 2015年6月2日 下午8:53:47 <br/>
     *
     * @author wanglong
     * @version R
     * @since JDK 1.7
     */
    public interface PGWBonusBpBusiType {
        /**
         * 业务类型    之 积分抵扣手续费
         */
        String BPBUISSTYPE_DUCFEE = "2001";
    }

    /**
     * ClassName : EAccountBalanceType
     * Function : 电子账户余额类型常量
     * Date : 2017年1月18日 下午7:55:02
     *
     * @author xiejf
     * @since JDK 1.7
     */
    public interface EAccountBalanceType {
        /**
         * 可用余额
         */
        String AVAILABLE_BALANCE = "1";
        /**
         * 待结算金额
         */
        String UNSETTLED_BALANCE = "2";
    }

    /**
     * Function : 币种常量<br/>
     * Date : 2015年6月2日 下午8:53:47 <br/>
     *
     * @author wanglong
     * @version R
     * @since JDK 1.7
     */
    public interface PGWCurrency {
        /**
         * 币种类型之人民币
         */
        String CURRENCY_CNY = "CNY";
        /**
         * 币种类型之美元
         */
        String CURRENCY_USD = "USD";
    }

    /**
     * Function : CAS币种常量<br/>
     * Date : 2015年7月21日 下午9:51:27 <br/>
     *
     * @author caicj
     * @version R
     * @since JDK 1.7
     */
    public interface CASCurrency {
        /**
         * 币种类型之人民币
         */
        String CURRENCY_CNY = "01";
    }

    /**
     * Function : 支付网关业务相关元素<br/>
     * Date : 2015年6月2日 下午8:51:11 <br/>
     *
     * @author wanglong
     * @version R
     * @since JDK 1.7
     */
    public interface PGWBusiField {

        String MOBILE = "mobile";
        String EMAIL = "email";
        String NAME = "name";
        String ACCT_TYPE = "acctType";
        String ACCT_NO = "acctNo";
        String PAYER_ACCT_TYPE = "payerAcctType";
        String PAYER_ACCT_NO = "payerAcctNo";
        String PAYEE_ACCT_TYPE = "payeeAcctType";
        String PAYEE_ACCT_NO = "payeeAcctNo";
        String PROD_ORDER_TYPE = "prodOrderType";
        String PROD_ORDER_STATUS = "prodOrderStatus";
        String PAY_ORDER_TYPE = "payOrderType";
        String PAY_ORDER_STATUS = "payOrderStatus";
        String TRANSFER_TYPE = "transferType";
        String TRANSFER_STATUS = "transferStatus";
        String FEE = "fee";
        String REAL_AMOUNT = "realAmount";
        /**
         * 绑定卡账户类型
         */
        String ACCOUNT_TYPE = "accountType";
        String PARAM_REQ_OBJECT = "reqObject";
        String PARAM_REQ_TRANFER_KEY = "tranferKey";
        String ORDER_TYPE = "orderType";
        String STATUS = "status";
        String ACCTNO = "acctNo";
        String CTPYNM = "ctpynm";
        String LOWPAYLMT = "lowPayLmt";
        String HIGHPAYLMT = "highPayLmt";
        String START_TIME = "startTime";
        String END_TIME = "endTime";
        String SUM_AMT = "sumAmt";
        String USER_ID = "userId";
        String CHECK_TYPE = "checkTyp";
        String NEW_PAY_PWD = "newPayPwd";
        String OPT_TYPE = "optTyp";
        String ORIG_PAY_PWD = "origPayPwd";
        String RANDOM_KEY = "randomKey";
        String USER_ACCT_NO = "userAcctNo";
        String CERT_TYPE = "certType";
        String CERT_SEQ = "certSeq";
        String RESET_FLAG = "resetFlag";

        /**
         * 证券手续费链接
         */
        String LINK_URL = "link_url";
        /**
         * SINGLE_AMOUNT_LIMIT : 单笔限额
         *
         * @since JDK 1.7
         */
        String SINGLE_AMOUNT_LIMIT = "singleAmountLimit";
        /**
         * DAY_AMOUNT_LIMIT : 每日累计限额
         *
         * @since JDK 1.7
         */
        String DAY_AMOUNT_LIMIT = "dayAmountLimit";
        /**
         * MONTH_AMOUNT_LIMIT : 每月限额
         *
         * @since JDK 1.7
         */
        String MONTH_AMOUNT_LIMIT = "monthAmountLimit";
        /**
         * 机构类型
         */
        String ORGAN_TYPE = "organType";
        /**
         * 商户通知URL （用于通知商户支付结果)
         */
        String MER_NOTIFY_URL = "merNotifyUrl";
        /**
         * 转账（外转内）最小金额
         */
        String O_TO_I_TRANSFER_AMT_LEAST = "O_TO_I_TRANSFER_AMT_LEAST";
        /**
         * 转账（外转内）非同名限制人数
         */
        String O_TO_I_TRANSFER_NM_LIMIT = "O_TO_I_TRANSFER_NM_LIMIT";
        /**
         * 短信验证码超时时间
         */
        String SM_VERIFY_TIMEOUT = "SM_VERIFY_TIMEOUT";
        /**
         * 短信验证码间隔时间
         */
        String SM_VERIFY_PERIOD = "SM_VERIFY_PERIOD";
        /**
         * 短信验证码可连续验证错误次数
         */
        String SM_ERR_TIMES = "SM_ERR_TIMES";
        /**
         * 支付订单失效时间
         */
        String PAY_ORDER_VALID_TIME = "PAY_ORDER_VALID_TIME";
        /**
         * 延时转账收款失效时间
         */
        String DELAY_TRANSFER_VALID_TIME = "DELAY_TRANSFER_VALID_TIME";
        /**
         * 备注
         */
        String MEMO = "memo";
        /**
         * 支持账户类型标识 0 不支持，1 支持
         */
        String SURPPORT = "surpport";
        /**
         * 支付密码标识
         */
        String PAY_PWD_FLG = "payPwdFlg";
        /**
         * 商户附件路径
         */
        String BSP_MERC_ATTACH_FILE_PATH = "bsp_merc_attach_file_path";
        /**
         * 机构logo文件路径
         */
        String PUB_ORGAN_LOGO_FILE_PATH = "pub_organ_logo_file_path";
        /**
         * 账户绑定卡的余额
         */
        String BALANCE = "balance";
        /**
         * 短信验证码发送频率控制(一小时)
         */
        String SM_UPER_TIMES = "SM_UPER_TIMES ";
        /**
         * 转账最大限额参数  500万
         */
        String TRANSFER_AMONNT_LIMIT = "TRANSFER_AMONNT_LIMIT";
        /**
         * 延迟转账每月最大参数控制
         */
        String MONTH_DELAY_MAX_TRAN_TIMES = "MONTH_DELAY_MAX_TRAN_TIMES";
        /**
         * 普通转账每月最大参数控制
         */
        String MONTH_MAX_TRAN_TIMES = "MONTH_MAX_TRAN_TIMES";
        /**
         * 延迟转账每日最大参数控制
         */
        String DAY_DELAY_MAX_TRAN_TIMES = "DAY_DELAY_MAX_TRAN_TIMES";
        /**
         * 普通转账每日最大参数控制
         */
        String DAY_MAX_TRAN_TIMES = "DAY_MAX_TRAN_TIMES";
        /**
         * 小额支付分钟参数
         */
        String MICRO_PAYMENT_MINUTE = "MICRO_PAYMENT_MINUTE";
        /**
         * 小额支付金额
         */
        String MICRO_PAYMENT_AMOUNT = "MICRO_PAYMENT_AMOUNT";
        /**
         * 小额支付笔数
         */
        String MICRO_PAYMENT_COUNT = "MICRO_PAYMENT_COUNT";
        /**
         * 备付金预警最低金额
         */
        String MISMATCH_PROVISIONS_AMOUNT = "MISMATCH_PROVISIONS_AMOUNT";
        /**
         * 是否启用积分
         */
        String IS_DERAILBONUS = "IS_DERAILBONUS";
        /**
         * 是否启用红包
         */
        String IS_DERAILREDPACKET = "IS_DERAILREDPACKET";
        /**
         * 是否启用邮箱通知
         */
        String IS_DERAILMAIL = "IS_DERAILMAIL";
        /**
         * 是否启用商户对账文件发送
         */
        String IS_MERFILE_DELIVER = "IS_MERFILE_DELIVER";

    }

    /**
     * Function : 证通渠道常量<br/>
     * Date : 2015年6月2日 下午8:47:41 <br/>
     *
     * @author wanglong
     * @version R
     * @since JDK 1.7
     */
    public interface PGWChannelId {
        /**
         * 渠道类型之pc门户
         */
        String CHANNEL_PC_PORTAL = "01";
        /**
         * 渠道类型之移动门户
         */
        String CHANNEL_MOBILE_PORTAL = "02";
        /**
         * 渠道类型之webchat门户
         */
        String CHANNEL_WEBCHAT = "03";
        /**
         * 渠道类型之支付网关
         */
        String CHANNEL_PGW = "04";
        /**
         * 渠道类型之短信平台
         */
        String CHANNEL_SMSP = "05";
        /**
         * 渠道类型之商户平台
         */
        String CHANNEL_MSS = "06";
        /**
         * 渠道类型之产品销售服务平台
         */
        String CHANNEL_PRODUCT = "07";
        /**
         * 渠道类型之开放平台
         */
        String CHANNEL_OPEN = "08";
        /**
         * 渠道类型之PSB总线
         */
        String CHANNEL_PSB = "09";
        /**
         * 渠道类型之账户前置
         */
        String CHANNEL_CORE = "10";
        /**
         * 渠道类型之运营管理系统
         */
        String CHANNEL_OPER = "11";
        /**
         * 渠道类型之差错处理平台
         */
        String CHANNEL_MISTAKE = "12";
        /**
         * 渠道类型之计费平台
         */
        String CHANNEL_FEE = "13";
        /**
         * 渠道类型之客户关系管理系统
         */
        String CHANNEL_CIF = "14";
    }

    /**
     * 证通业务标准_15_交易渠道类型代码V0.5
     * ClassName :PGWChannelType <br/>
     * Date : 2015-7-30下午3:51:33 <br/>
     *
     * @author hanjunlu
     * @see
     * @since JDK 1.7
     */
    public interface PGWChannelType {
        /**
         * 柜面
         */
        String CHANNEL_TYPE_01 = "01"; //柜面
        /**
         * WEB渠道
         */
        String CHANNEL_TYPE_02 = "02"; //WEB渠道
        /**
         * APP渠道
         */
        String CHANNEL_TYPE_03 = "03"; //APP渠道
        /**
         * 微信渠道
         */
        String CHANNEL_TYPE_04 = "04"; //微信渠道
        /**
         * 电话渠道
         */
        String CHANNEL_TYPE_05 = "05"; //电话渠道
        /**
         * 短信渠道
         */
        String CHANNEL_TYPE_06 = "06"; //短信渠道
        /**
         * 自助渠道（ATM、CDM）
         */
        String CHANNEL_TYPE_07 = "07"; //自助渠道（ATM、CDM）
        /**
         * POS
         */
        String CHANNEL_TYPE_08 = "08"; //POS
        /**
         * 电话POS
         */
        String CHANNEL_TYPE_09 = "09"; //电话POS
        /**
         * MPOS（手机刷卡器
         */
        String CHANNEL_TYPE_10 = "10"; //MPOS（手机刷卡器）
        /**
         * 其他
         */
        String CHANNEL_TYPE_99 = "99"; //其他
    }

    /**
     * ClassName :PGWTransferPayeeRecStatus收款人登记簿的状态 <br/>
     * Date : 2015年7月12日下午12:24:03 <br/>
     *
     * @author hanjunlu
     * @see
     * @since JDK 1.7
     */
    public interface PGWTransferPayeeRecStatus {
        /**
         * 收款人登记簿的状态01 有效
         */
        String TRANSFER_PAYEE_REC_STATUS_01 = "01";
        /**
         * 收款人登记簿的状态02 无效
         */
        String TRANSFER_PAYEE_REC_STATUS_02 = "02";
    }

    /**
     * ClassName :PGWTransferPayeeRecSelfFlag <br/>
     * Date : 2015年7月12日下午2:07:20 <br/>
     *
     * @author hanjunlu
     * @see
     * @since JDK 1.7
     */
    public interface PGWTransferPayeeRecSelfFlag {
        /**
         * 收款人登记簿是本人
         */
        String TRANSFER_PAYEE_REC_SELF_FLAG_1 = "1";
        /**
         * 收款人登记簿是他人
         */
        String TRANSFER_PAYEE_REC_SELF_FLAG_2 = "2";
    }

    /**
     * 定义短信发送的常量
     *
     * @author xkw
     */
    public interface SmsConstant {

        /**
         * 应用头本身的版本号，随头部格式变化而递增。为1~9的数字，从1开始。
         */
        String HEADVERSION = "1";

        /**
         * 请求方请求的服务版本号。版本号结构为：X.Y
         */
        String VERSION = "1.01";

        /**
         * 服务请求方的ID。参照《证通技术标准_2_系统编号》中的系统ID英文简称。
         */
        String REQUESTID = "PGW";

        /**
         * 交易处理系统需指定目标系统ID
         */
        String TARGETID = "SMS";

        /**
         * 渠道ID,参照《证通技术标准_3_渠道编号》
         */
        String CHANNELID = "04";

        /**
         * 系统编码，参照《证通技术标准_2_系统编号》中的系统编码
         */
        String SYSCODE_PGW = "1011";

        /**
         * 默认 发送级别
         */
        String SEND_LEVEL = "6";

        /**
         * 发送成功的标示
         */
        String SEND_SUCC = "Y";

        /**
         * 短信业务分类
         * 陈正华@2015-06-23 15:49:05 修改短信交易类型
         */
        String BIZ_TYPE = "10110008";

        /**
         * 短信功能类型 00.登录验证码 01.支付动态密码 02.注册验证码 03.找回密码
         */
        String SMS_FUNC_TYPE_00 = "00";
        String SMS_FUNC_TYPE_01 = "01";
        String SMS_FUNC_TYPE_02 = "02";
        String SMS_FUNC_TYPE_03 = "03";

        /**
         * 登录标记 0未登录 1已登录
         */
        String SMS_LOGIN_FLAG_0 = "0";
        String SMS_LOGIN_FLAG_1 = "1";

    }

    /**
     * 定义CIF系统交互的常量
     *
     * @author JsonChen
     */
    public interface CifConstant {

        /**
         * 应用头本身的版本号，随头部格式变化而递增。为1~9的数字，从1开始。
         */
        String HEAD_VERSION = "1";
        /**
         * 请求方请求的服务版本号。版本号结构为：X.Y
         */
        String VERSION = "1.01";
        /**
         * 服务请求方的ID。参照《证通技术标准_2_系统编号》中的系统ID英文简称。
         */
        String REQUEST_ID = "PGW";
        /**
         * 交易处理系统需指定目标系统ID
         */
        String TARGET_ID = "CIF";
        /**
         * 交易处理系统需指定目标系统ID EPS
         */
        String TARGET_EPS_ID = "EPS";
        /**
         * 交易处理系统需指定目标系统ID EPS
         */
        String TARGET_RES_ID = "RES";
        /**
         * 交易处理系统需指定目标系统ID OMS
         */
        String TARGET_OMS_ID = "OMS";
        /**
         * 渠道ID，参照《证通技术标准_3_渠道编号》
         */
        String CHANNEL_ID = "04";
        /**
         * 密码类型：PC
         */
        String PWD_TYPE_PC = "0";
        /**
         * 密码类型：mobile
         */
        String PWD_TYPE_MOBILE = "1";
        /**
         * 默认 发送级别
         */
        String SEND_LEVEL = "6";
        /**
         * 发送成功的标示
         */
        String SEND_SUCC = "Y";
        /**
         * 支付密码调用失败
         */
        String ERROR_USERAUTH_PAYPASS_FAIL = "10339020";
        /**
         * 支付密码锁定
         */
        String ERROR_USERINFO_PAYPASS_LOCKED = "10339012";
        /**
         * 支付密码待锁定
         */
        String ERROR_USERINFO_PAYPASS_RLOCKED = "10339013";

    }


    /**
     * Function : 支付转接返回业务状态<br/>
     * Date : 2015年5月30日 下午6:39:48 <br/>
     *
     * @author xueaoran
     * @version R
     * @since JDK 1.7
     */
    public interface TACSBusiStatus {
        /**
         * 已请算
         */
        String SETTLE = "PR00";
        /**
         * 已拒绝
         */
        String DECLINE = "PR01";
        /**
         * 已成功
         */
        String SUCCESS = "PR02";
        /**
         * 已接收
         */
        String ACCEPT = "PR03";
        /**
         * 已确认
         */
        String SUBMIT = "PR04";
        /**
         * 已付款
         */
        String PAYMENT = "PR05";
    }

    /**
     * Function : 支付转接返回查询处理状态<br/>
     * Date : 2015年8月2日 下午4:49:42 <br/>
     *
     * @author caicj
     * @version R
     * @since JDK 1.7
     */
    public interface TACSQueryStatus {
        /**
         * 查询失败
         */
        String QUERY_FAIL = "PR01";
        /**
         * 查询成功
         */
        String QUERY_SUCCESS = "PR02";
    }

    /**
     * Function : CAS系统返回业务状态<br/>
     * Date : 2015年6月23日 下午7:21:06 <br/>
     *
     * @author guiye
     * @version R
     * @since JDK 1.7
     */
    public interface CASBusiStatus {
        String SUCCESS = "50010000";
    }

    /**
     * Function : 支付转接业务类型<br/>
     * Date : 2015年5月30日 下午6:39:48 <br/>
     *
     * @author xueaoran
     * @version R
     * @since JDK 1.7
     */
    public interface TACSBusiType {
        /**
         * 实时转账
         */
        String REALTIME_TRANFER = "A100";
        /**
         * 延时转账转出或退款
         */
        String DELAY_TRANFER_OUT = "A200";
        /**
         * 延时转账转入
         */
        String DELAY_TRANFER_IN = "A201";
        /**
         * 消费
         */
        String ONLINE_CONSUME = "B100";
        /**
         * 消费撤销
         */
        String CONSUME_CANCEL = "B101";
        /**
         * 消费退货
         */
        String CONSUME_RETURN = "B200";
        /**
         * 延迟转账退款
         */
        String DELAY_TRANFER_REFUND = "A202";
        /**
         * 账户余额查询
         */
        String QUERY_ACCT_BALANCE = "J200";
        /**
         * 账户明细查询
         */
        String QUERY_ACCT_DETAIL = "J300";
        /**
         * 业务状态查询(与交易明细查询相同)
         */
        String QUERY_TRANS_STATUS = "I100";
        /**
         * 交易明细查询
         */
        String QUERY_TRANS_DETAIL = "I100";
        /**
         * 理财产品购买(预约)
         */
        String FINANCING_PURCHASE = "F100";
        /**
         * 理财产品购买(即时)
         */
        String FINANCING_PURCHASE_REALTIME = "F101";
        /**
         * 理财产品购买(预约)撤销
         */
        String RESCIND_RESERVATION = "F102";
        /**
         * 完成购买
         */
        String PURCHASE_COMPLETION = "F200";
        /**
         * 完成购买撤销
         */
        String RESCIND_PURCHASE_COMPLETION = "F201";
        /**
         * 单笔代付
         */
        String SINGLE_PAYMENT = "E101";
        /**
         * 单笔代收
         */
        String SINGLE_COLLECTION = "E100";
        /**
         * 销售赎回
         */
        String SALES_REDEMPTION = "F300";
        /**
         * 账户验证
         */
        String CHECK_ACCT = "J100";
    }

    public interface TerminalType {
        /**
         * 订单发起终端类型-手机
         */
        String TER_TYPE_MOBILE = "01";
        /**
         * 订单发起终端类型-PAD
         */
        String TER_TYPE_PAD = "02";
        /**
         * 订单发起终端类型-PC
         */
        String TER_TYPE_PC = "03";
    }

    public interface ECTSystemOrgNum {
        /**
         * 支付转接机构号
         */
        String PAY_TRUN_ACCEPT = "0000100000";//1000000000
        /**
         * 支付网关机构号
         */
        String PAY_NET_CHECKPOINT = "0000110013";//206000001
        /**
         * 支付转接机构号CAS
         */
        String PAY_TRUN_ACCEPT_CAS = "0000100000";//1000000000
    }

    /**
     * Function : 支付转接返回业务拒绝码<br/>
     * Date : 2015年5月30日 下午6:40:18 <br/>
     *
     * @author xueaoran
     * @version R
     * @since JDK 1.7
     */
    public interface TACSBusiRejectCode {
        /**
         * 交易超时
         */
        String TIMEOUT = "RJ60";
        /**
         * 原交易不存在
         */
        String QRY_TRANS_NOT_EXIST = "50011041";
    }

    /**
     * Function : 支付转接报文处理状态<br/>
     * Date : 2015年6月14日 上午9:04:19 <br/>
     *
     * @author caicj
     * @version R
     * @since JDK 1.7
     */
    public interface TACSHandleStatus {
        /**
         * 报文接收成功
         */
        String RECEIVE_SUCCESS = "0000";
    }

    /**
     * Function : 订单支付方式<br/>
     * Date : 2015年5月27日 下午4:11:59 <br/>
     *
     * @author guiye
     * @version R
     * @since JDK 1.7
     */
    public interface PGWPayOrderMethod {
        /**
         * 担保支付
         */
        String WARRANTY_PAYMENT = "01";
        /**
         * 立即支付
         */
        String IMMEDIATE_PAYMENT = "02";
    }

    /**
     * Function : 订单类型<br/>
     * Date : 2015年5月27日 下午4:14:58 <br/>
     *
     * @author guiye
     * @version R
     * @since JDK 1.7
     */
    public interface PGWOrderType {
        /**
         * 线上消费（快捷支付）
         */
        String ONLINE_CONSUME = "0100";
        /**
         * 普通转账
         */
        String NORMAL_TRANSFER = "0200";
        /**
         * 延时转账转出
         */
        String DELAY_TRANSFER_OUT = "0300";
        /**
         * 购买理财产品
         */
        String BUY_FINANCING_PRODUCT = "0400";
        /**
         * 发红包
         */
        String REDPACK_SEND = "0500";
        /**
         * 红包提现
         */
        String REDPACK_WITHDRAW = "0600";
        /**
         * 收零钱红包
         */
        String REDPACK_RECEIVE = "0700";
        /**
         * 零钱红包退款
         */
        String REDPACK_RETURN = "0800";
        /**
         * 消费退货
         */
        String CONSUME_RETRUN = "0900";
        /**
         * 延时转账转入
         */
        String DELAY_TRANSFER_IN = "1000";
        /**
         * 延时转账退款
         */
        String DELAY_TRANSFER_REFUND = "1100";
        /**
         * 消费撤销
         */
        String CONSUME_CANCEL = "1200";
        /**
         * 信用卡还款
         */
        String CREDIT_CARD_REPAY = "1300";
        /**
         * 加油卡充值
         */
        String FUEL_CARD_RECHARGE = "1400";
        /**
         * 手机充值
         */
        String PHONE_RECHARGE = "1500";
        /**
         * 单笔代付（快捷支付）
         */
        String SINGLE_PAY = "1600";
        /**
         * 绑定证券账户(此处无字典只作为判断短信类型映射，如若冲突可以修改页面JS和此处的常亮值)
         */
        String BIND_ACCOUNT = "10001";
        /**
         * 绑定证券账户(此处无字典只作为判断短信类型映射，如若冲突可以修改页面JS和此处的常亮值)
         */
        String BIND_CARD = "10002";
        /**
         * 小额支付
         */
        String MICRO_PAY = "1700";
        /**
         * 零钱充值
         */
        String POCKET_RECHARGE = "1800";
        /**
         * 零钱提现
         */
        String POCKET_WITHDRAW = "1900";
        /**
         * 小额支付退款
         */
        String MICRO_RETRUN = "2000";
        /**
         * 电子账户转账
         */
        String EACCOUNT_TRANSFER = "2100";
        /**
         * 订单结算
         */
        String ORDER_SETTLE = "2200";
        /**
         * 批量代收
         */
        String BATCH_COLLECT = "2300";
        /**
         * 批量代付
         */
        String BATCH_AGENTPAY = "2400";
        /**
         * 单笔代收
         */
        String SINGLE_COLLECT = "2500";
    }

    /**
     * Function : 订单细类<br/>
     * Date : 2015年6月9日 下午9:43:01 <br/>
     *
     * @since JDK 1.7
     */
    public interface PGWOrderDetailType {
        /**
         * 普通转账-圈内转圈内
         */
        String NORMAL_TRANSFER_IN_2_IN = "0201";
        /**
         * 普通转账-圈内转圈外
         */
        String NORMAL_TRANSFER_IN_2_OUT = "0202";
        /**
         * 普通转账-圈外转圈内，非同名
         */
        String NORMAL_TRANSFER_OUT_2_IN_NEGTIVE = "0203";
        /**
         * 普通转账-圈外券圈外
         */
        String NORMAL_TRANSFER_OUT_2_OUT = "0204";
        /**
         * 普通转账-圈外转圈内，同名
         */
        String NORMAL_TRANSFER_OUT_2_IN_SAME = "0205";
        /**
         * 普通转账-证通宝转入
         */
        String NORMAL_TRANSFER_ZTB_DEPOSIT = "0206";
        /**
         * 发红包-账户支付（红包支付）
         */
        String REDPACK_SEND_ACCT_PAY = "0501";
        /**
         * 发红包-零钱支付（零钱发红包）
         */
        String REDPACK_SEND_POCKET_PAY = "0502";
        /**
         * 转账交易-前两位
         */
        String NORMAL_TRANFER_PREFIX = "02";
        /**
         * 小额支付-投顾
         */
        String MICRO_PAY_CONSULTANT = "1701";
        /**
         * 小额支付-红包
         */
        String MICRO_PAY_REDPACK = "1702";
        /**
         * 零钱充值-细类
         */
        String POCKET_RECHARGE_DETAIL = "1801";
        /**
         * 零钱提现-细类
         */
        String POCKET_WITHDRAW_DETAIL = "1901";
    }

    /**
     * Function : 核心系统接口功能编号<br/>
     * Date : 2015年6月2日 下午9:21:32 <br/>
     *
     * @author wanglong
     * @version R
     * @since JDK 1.7
     */
    public interface TACSFuncNo {
        /**
         * 转账请求
         */
        String ESB_TRANFER_CODE = "TACS.200.001.01";
        /**
         * 延迟转出请求
         */
        String ESB_DELAY_TRANFER_OUT_CODE = "TACS.202.001.01";
        /**
         * 延迟转入请求
         */
        String ESB_DELAY_TRANFER_IN_CODE = "TACS.204.001.01";
        /**
         * 线上消费
         */
        String ESB_ONLINE_CONSUME_CODE = "TACS.240.001.01";
        /**
         * 消费撤销
         */
        String ESB_RESCIND_CONSUMPTION = "TACS.242.001.01";
        /**
         * 消费退货
         */
        String ESB_RETURN_CONSUMPTION = "TACS.244.001.01";
        /**
         * 延迟转账退款
         */
        String ESB_DELAY_TRANFER_REFUND = "TACS.206.001.01";
        /**
         * 交易状态查询
         */
        String ESB_QUERY_TRAN_STATUS = "TACS.300.001.01";
        /**
         * 通讯级确认
         */
        String ESB_MSG_PROCESS = "TACS.990.001.01";
        /**
         * 消费撤销
         */
        String ESB_CONSUME_CANCEL_CODE = "TACS.242.001.01";
        /**
         * 消费退款
         */
        String ESB_CONSUME_RETURN_CODE = "TACS.244.001.01";
        /**
         * 账户余额查询
         */
        String ESB_QUERY_ACCT_BALANCE_CODE = "TACS.332.001.01";
        /**
         * 账户明细查询
         */
        String ESB_QUERY_ACCT_DETAIL_CODE = "TACS.334.001.01";
        /**
         * 交易明细查询
         */
        String ESB_QUERY_TRANS_DETAIL_CODE = "TACS.306.001.01";
        /**
         * 理财产品购买
         */
        String ESB_FINANCING_PURCHASE_CODE = "TACS.220.001.01";
        /**
         * 理财产品购买撤销
         */
        String ESB_RESCIND_RESERVATION_CODE = "TACS.222.001.01";
        /**
         * 完成购买
         */
        String ESB_PURCHASE_COMPLETION_CODE = "TACS.224.001.01";
        /**
         * 完成购买撤销
         */
        String ESB_RESCIND_PURCHASE_COMPLETION_CODE = "TACS.226.001.01";
        /**
         * 单笔代付
         */
        String ESB_SINGLE_PAYMENT_CODE = "TACS.272.001.01";
        /**
         * 单笔代收
         */
        String ESB_SINGLE_COLLECTION_CODE = "TACS.270.001.01";
        /**
         * 销售赎回
         */
        String ESB_SALES_REDEMPTION_CODE = "TACS.228.001.01";
        /**
         * 账户验证
         */
        String ESB_CHECK_ACCT_CODE = "TACS.330.001.01";
    }

    /**
     * Function : 积分系统接口功能编号<br/>
     * Date : 2015年6月2日 下午9:21:32 <br/>
     *
     * @author wanglong
     * @version R
     * @since JDK 1.7
     */
    public interface EPSFuncNo {
        /**
         * 积分消费请求
         */
        String BONUSCONSUME_CODE = "EPS.102.007.01";
        /**
         * 积分消费冲正请求
         */
        String BONUSCONSUMEREVERSAL_CODE = "EPS.102.008.01";
        /**
         * 积分余额查询请求
         */
        String BONUSQUERY_CODE = "EPS.101.001.01";
    }

    /**
     * Date : 2015年8月16日 下午3:16:13 <br/>
     * 1、自有资金00
     * 2、证券机构资金类型01-19；基金公司客户账户20-29；期货公司客户账户30-39
     */
    public interface FundType {
        /**
         * 自有资金
         */
        String SELF_OWNED_FUND = "00";
        /**
         * 客户资金（证券保证金）
         */
        String SECURITIES_MARGIN_FUND = "01";
        /**
         * 客户资金（信用）
         */
        String CREDIT_FUND = "02";
        /**
         * 客户资金（衍生品）
         */
        String DERIVATIVE_FUND = "03";
        /**
         * 客户资金（期货保证金）
         */
        String COVER_FUND = "30";
    }

    /**
     * Function : 核心系统接口功能编号(CAS)<br/>
     * Date : 2015年6月23日 下午5:58:46 <br/>
     *
     * @author guiye
     * @version R
     * @since JDK 1.7
     */
    public interface CASFuncNo {
        /**
         * 一借一贷
         */
        String ESB_ODOC_CODE = "CAS.260.001.01";
        /**
         * 多借多贷
         */
        String ESB_MDMC_CODE = "CAS.262.001.01";
        /**
         * 记账冲正
         */
        String ESB_REVERSE_CODE = "CAS.264.001.01";
        /**
         * 记账查证
         */
        String ESB_QUERY_CODE = "CAS.266.001.01";
    }

    /**
     * Function : 用户权限相关的常量<br/>
     * Date : 2015年5月27日 下午2:59:52 <br/>
     *
     * @author xkw
     * @version R
     * @since JDK 1.7
     */
    public interface UserEntitlement {
        /**
         * 菜单类型-运营中心管理员
         */
        String DEFAULT_MENU_TYPE = "1";
        /**
         * 菜单类型-商户管理
         */
        String MERCHANT_MENU_TYPE = "2";
    }

    /**
     * Function : 计划任务ID常量<br/>
     * Date : 2015年5月30日 下午7:21:05 <br/>
     *
     * @author wanglong
     * @version R
     * @since JDK 1.7
     */
    public interface ScheduleJobKey {
        /**
         * 测试job1
         */
        String SCHEDULE_JOB_KEY_TEST1 = "201505300001";
        /**
         * 测试job1
         */
        String SCHEDULE_JOB_KEY_TEST2 = "201505300002";
        /**
         * 分润清分job
         */
        String SCHEDULE_JOB_KEY_PARTPROFIT_CLEAR = "PART_PROFIT_CLEAR";
        /**
         * 分润结算job
         */
        String SCHEDULE_JOB_KEY_PARTPROFIT_SETT = "PART_PROFIT_SETT";
    }

    /**
     * Function : 计划任务状态常量<br/>
     * Date : 2015年5月30日 下午7:21:05 <br/>
     *
     * @author wanglong
     * @version R
     * @since JDK 1.7
     */
    public interface ScheduleJobStatus {
        /**
         * 待执行
         */
        String SCHEDULE_JOB_STATUS_WAIT_INVOKE = "0";
        /**
         * 执行中
         */
        String SCHEDULE_JOB_STATUS_INVOKING = "1";
        /**
         * 执行成功
         */
        String SCHEDULE_JOB_STATUS_INVOKE_SUCC = "2";
        /**
         * 执行失败
         */
        String SCHEDULE_JOB_STATUS_INVOKE_FAIL = "3";
    }

    /**
     * Function : 查询类型<br/>
     * Date : 2015年6月2日 下午9:49:50 <br/>
     *
     * @author guiye
     * @version R
     * @since JDK 1.7
     */
    public interface PGWOrderQueryType {
        /**
         * 有循环体
         */
        String WITH_LOOP = "0";
        /**
         * 无循环体
         */
        String WITHOUT_LOOP = "1";

    }

    public interface SortType {
        /**
         * 正序
         */
        String ASC = "0";
        /**
         * 倒序
         */
        String DESC = "1";

    }

    public interface FaceFlag {
        /**
         * 需要刷脸
         */
        String DESIRED = "1";
        /**
         * 不需要刷脸
         */
        String UNDESIRED = "0";
        /**
         * 使用中
         */
        String USED = "1";
        /**
         * 未使用
         */
        String UNUSED = "0";
        /**
         * 通过视频认证
         */
        String PASSED = "1";
        /**
         * 未通过视频认证
         */
        String UNPASSED = "0";
    }

    public interface TACSOrderReqType {
        /* 转账 */
        String TRANFER = "tranfer";
        /* 消费 */
        String CONSUME = "consume";
        /* ESB渠道流水号 */
        String CHANNEL_SERIAL_NO = "channelSerialNo";
        /* 附言 */
        String POSTSCRIPT = "postScript";
        /* 身份证*/
        String IDENTITY_CARD = "identityCard";
        /* 用户ID*/
        String USER_ID = "userId";
        /* 手机号 */
        String MOBILE = "mobile";
        /* 身份鉴权绑卡 */
        String BIND_CARD = "payBindCardInfo";
    }

    /**
     * Function : 限额类型<br/>
     * Date : 2015年6月3日 下午3:35:54 <br/>
     *
     * @author JsonChen
     * @version R
     * @since JDK 1.7
     */
    public interface PGW_AMT_LIMTT_TYPE {
        /**
         * AMT_LIMTI_DAY : 日累计限额
         */
        String AMT_LIMTI_DAY = "02";
        /**
         * AMT_LIMTI_MONTH : 月累计限额
         */
        String AMT_LIMTI_MONTH = "03";
        /**
         * AMT_LIMTI_ONE : 单笔累计限额
         */
        String AMT_LIMTI_SINGLE = "01";
    }

    /**
     * Function : Cif修改支付密码操作类型<br/>
     * Date : 2015年6月3日 下午2:41:13 <br/>
     *
     * @author guiye
     * @version R
     * @since JDK 1.7
     */
    public interface CIFChangePwdOptType {
        /**
         * 设置/重置密码
         */
        String RESET_PWD = "0";
        /**
         * 修改密码
         */
        String CHANGE_PWD = "1";

    }

    /**
     * Function : 证通内部账户状态<br/>
     * Date : 2015年6月4日 下午3:58:19 <br/>
     */
    public interface PGWSurpportOrganValid {
        /**
         * 有效
         */
        String VALID = "1";
        /**
         * 无效
         */
        String INVALID = "0";

    }

    /**
     * 用户状态是否锁定 1： 未锁定，可用，默认可用 0： 锁定，不可用
     *
     * @author yhl update time 2012/05/11 17:03
     */
    public interface MallStaffStatus {
        String MALL_STAFF_IS_IN_USE_TRUE = "1";
        String MALL_STAFF_IS_IN_USE_FALSE = "0";
    }

    public interface PGWOrderFeeType {
        /**
         * 按笔固定金额
         */
        String ORDER_FEE_TYPE_01 = "01";
        /**
         * 按金额阶梯
         */
        String ORDER_FEE_TYPE_02 = "02";
        /**
         * 按支付金额比例
         */
        String ORDER_FEE_TYPE_03 = "03";
    }

    /**
     * Function : 机构分润方式<br/>
     * Date : 2015年6月25日 下午4:22:12 <br/>
     *
     * @author caicj
     * @version R
     * @since JDK 1.7
     */
    public interface PGWOrganProfitType {
        /**
         * 按笔固定金额
         */
        String PROFIT_TYPE_FIX = "01";
        /**
         * 按本金比例
         */
        String PROFIT_TYPE_PRINCIPAL_PERCENT = "02";
        /**
         * 按收单侧比例
         * 收单侧金额=商务平台手续费-发卡转接成本
         */
        String PROFIT_TYPE_CHARGE_PERCENT = "03";
    }

    public interface PGWUserRegisterWay {
        /**
         * 手机号注册
         */
        String REGISTER_BY_MOBILE = "0";
        /**
         * 邮箱注册
         */
        String REGISTER_BY_EMAIL = "1";
    }

    /**
     * 上送CIF的密码强度
     */
    public interface PGWPwdLevel {
        String HIGH = "3";
        String MIDDLE = "2";
        String LOW = "1";
    }

    /**
     * Function : 审核业务类型<br/>
     * Date : 2015年6月28日 下午8:04:22 <br/>
     *
     * @author wanglong
     * @version 1.0
     * @since JDK 1.7
     */
    public interface AuditBusiType {
        String AUDIT_MERCHANT_APPLY = "00";
        String AUDIT_MERCHANT_INFO_CHANGE = "01";
        String AUDIT_MERCHANT_ACCT_LOGOUT = "02";
        String AUDIT_MERCHANT_CHARGE_NOTIFY = "03";
        String AUDIT_SUPPORT_ORGAN_ADD = "07";
        String AUDIT_SUPPORT_ORGAN_UPDATE = "08";
    }

    /**
     * Function : 审核结果类型<br/>
     * Date : 2015年6月28日 下午8:06:54 <br/>
     *
     * @author wanglong
     * @version 1.0
     * @since JDK 1.7
     */
    public interface AuditResult {
        /**
         * 初审拒绝
         */
        String RESULT_AUDIT_REJECT = "0";
        /**
         * 复审拒绝
         */
        String RESULT_REAUDIT_REJECT = "1";
        /**
         * 审核通过
         */
        String RESULT_AUDIT_APPROVE = "2";
    }

    /**
     * Function : 审核状态<br/>
     * Date : 2015年6月28日 下午8:07:26 <br/>
     *
     * @author wanglong
     * @version 1.0
     * @since JDK 1.7
     */
    public interface AuditStatus {
        /**
         * 待初审
         */
        String STAUTS_TODO_AUDIT = "0";
        /**
         * 待复审
         */
        String STAUTS_TODO_REAUDIT = "1";
        /**
         * 审核完成
         */
        String STAUTS_COMPLETED_AUDIT = "2";
    }


    /**
     * Function : 审核相关参数key<br/>
     * Date : 2015年6月28日 下午8:07:26 <br/>
     *
     * @author wanglong
     * @version 1.0
     * @since JDK 1.7
     */
    public interface AuditParamKey {
        /**
         * 审核业务类型
         */
        String KEY_AUDIT_BUSI_TYPE = "KEY_AUDIT_BUSI_TYPE";
        /**
         * 审核业务ID
         */
        String KEY_AUDIT_BUSI_ID = "KEY_AUDIT_BUSI_ID";
        /**
         * 审核结果
         */
        String KEY_AUDIT_RESULT = "KEY_AUDIT_RESULT";
        /**
         * 审核状态
         */
        String KEY_AUDIT_STATUS = "KEY_AUDIT_STATUS";
    }

    /**
     * Function : 日切状态<br/>
     * Date : 2015年7月1日 下午3:21:28 <br/>
     *
     * @author caicj
     * @version R
     * @since JDK 1.7
     */
    public interface PGWCutDayStatus {
        /**
         * 日切完成
         */
        String CUT_DAY_STATUS_DONE = "01";
        /**
         * 日切中
         */
        String CUT_DAY_STATUS_OPERATING = "02";
    }

    /**
     * Function : 商户清算账户类型<br/>
     * Date : 2015年7月1日 下午3:21:28 <br/>
     *
     * @author caicj
     * @version R
     * @since JDK 1.7
     */
    public interface PGWMercSettAcctType {
        /**
         * 结算账户
         */
        String TYPE_SETTLEMENT = "01";
        /**
         * 费用账户
         */
        String TYPE_CHARGE = "02";
    }

    /**
     * Function : 借贷记标记<br/>
     * Date : 2015年7月4日 下午5:19:55 <br/>
     *
     * @author caicj
     * @version R
     * @since JDK 1.7
     */
    public interface DcFlag {
        /**
         * 借记
         */
        String DEBIT_FLAG = "1";
        /**
         * 贷记
         */
        String CREDIT_FLAG = "2";
    }

    /**
     * Function : 是否标记<br/>
     * Date : 2015年7月4日 下午5:19:55 <br/>
     *
     * @author caicj
     * @version R
     * @since JDK 1.7
     */
    public interface IsFlag {
        /**
         * 是
         */
        String TRUE_FLAG = "1";
        /**
         * 否
         */
        String FALSE_FLAG = "0";
    }

    public interface PGWOranTranAuthType {
        /**
         * 允许
         */
        String AUTH_ARGEE = "AS00";
        /**
         * 禁止
         */
        String AUTH_REFUSE = "AS01";
    }

    /**
     * 当前设备是否有证书
     *
     * @author hanjunlu
     */
    public interface PGWDevicePKIFalg {
        /**
         * 没有安装证书
         */
        String DEVICE_PKI_FALG_0 = "0";
        /**
         * 安装证书
         */
        String DEVICE_PKI_FALG_1 = "1";
    }

    public interface PGWXWFResultCode {
        /**
         * 成功
         */
        String SUCCESS = "100";
    }

    /**
     * Function : <br/>
     * Date : 2015年10月16日 下午2:49:22 <br/>
     *
     * @author caicj
     * @version R
     * @since JDK 1.7
     */
    public interface TACSBizKind {
        /**
         * 交易状态查询
         */
        String BIZ_KIND_QUERY_TRANS_STATUS = "03701";
        /**
         * 交易明细查询
         */
        String BIZ_KIND_QUERY_TRANS_DETAIL = "03702";
        /**
         * 零钱支付
         */
        String BIZ_KIND_BALANCE_PAY = "07401";

        /**
         * 零钱充值
         */
        String BIZ_KIND_BALANCE_RECHARGE = "07101";
        /**
         * 零钱提现
         */
        String BIZ_KIND_BALANCE_WITHDRAW = "07201";
    }

    public interface IsRichFriend {
        /**
         * 非财友转账
         */
        String IS_RICH_FRIEND_0 = "0";
        /**
         * 财友转账
         */
        String IS_RICH_FRIEND_1 = "1";
    }

    /**
     * Function : 积分消费应答处理结果代码<br/>
     * Date : 2015年11月16日 上午10:43:07 <br/>
     *
     * @author caicj
     * @version R
     * @since JDK 1.7
     */
    public interface EPSBonusConsumeResCode {
        /**
         * 失败
         */
        String RES_CODE_FAIL = "00";
        /**
         * 成功
         */
        String RES_CODE_SUCCESS = "01";
    }

    /**
     * Function : 快捷支付相关常量 <br/>
     * Date : 2017年4月17日 上午4:51:58 <br/>
     *
     * @author xiejf
     * @version R
     * @since JDK 1.7
     */
    public interface PGW_PAY_Constant {

        /**
         * 成功
         */
        String T = "T";
        /**
         * 失败
         */
        String F = "F";
        /**
         * 超时未知
         */
        String P = "P";

        /**
         * 0-查询支付和退款，1-支付订单，2-退款，3-对公代付
         */
        String QUERY_PAY_ORDER_TYPR_0 = "0";
        String QUERY_PAY_ORDER_TYPR_1 = "1";
        String QUERY_PAY_ORDER_TYPR_2 = "2";
        String QUERY_PAY_ORDER_TYPR_3 = "3";

        /**
         * 是否有退货
         */
        String IS_RETURNED = "1";      // 1代表撤销
        String UN_RETURNED = "0";      // 0代表未撤销

        /**
         * 是否有退货
         */
        String IS_REVERT = "1";      // 1代表退货
        String UN_REVERT = "0";      // 0代表未退货

        /**
         * 撤销退款标记
         */
        String REVERT_FLAG = "00";    //撤销
        String RETURNED_FLAG = "01";    //退款

        /**
         * 快捷支付用户id填充
         */
        String QUERY_PAY_DEFAULT_USERID = "-1";    //CIF 的规则是  [yyyyMMdd]{########}

    }

    /**
     * Function : 红包查询返回状态<br/>
     * Date : 2015年12月18日 下午2:08:53 <br/>
     *
     * @author caicj
     * @version R
     * @since JDK 1.7
     */
    public interface RES_QUERY_RETURN_STATUS {
        String RES_QUERY_RETURN_STATUS_NOTEXIST = "0";
        String RES_QUERY_RETURN_STATUS_DEALING = "1";
        String RES_QUERY_RETURN_STATUS_FAILURE = "2";
        String RES_QUERY_RETURN_STATUS_SUCCESS = "3";
    }

    public interface ESB_RET_TYPE {
        //业务成功
        String ESB_RET_TYPE_SUCESS = "Y";
        //业务失败
        String ESB_RET_TYPE_FAIL = "W";
        //技术失败
        String ESB_RET_TYPE_ERROR = "E";
    }

    /**
     * Function : 数据模型操作标志<br/>
     * Date : 2016年2月24日 下午4:25:36 <br/>
     *
     * @author caicj
     * @version R
     * @since JDK 1.7
     */
    public interface ModelOpFlag {
        /**
         * 不操作
         */
        String NO_OP_FLAG = "0";
        /**
         * 插入操作
         */
        String INSERT_FLAG = "1";
        /**
         * 更新操作
         */
        String UPDATE_FLAG = "2";
    }

    /**
     * Function : 撤销结果
     * Date : 2016年3月10日
     *
     * @author qiss
     * @version R
     * @since JDK1.7
     */
    public interface RevertResult {
        /**
         * 成功
         */
        String REVERT_SUCCESS = "0";
        /**
         * 超时
         */
        String REVERT_TIMEOUT = "1";
        /**
         * 失败
         */
        String REVERT_FAILURE = "2";
    }

    /**
     * 红包触发场景
     *
     * @author yubo
     */
    public interface EventType {
        /**
         * 注册用户
         */
        String REGISTER_USER = "2";
        /*分享用户*/
        String SHARE_USER = "3";
    }

    public interface PGWPayOrderMemoConstant {
        /**
         * 收款方卡号
         */
        String PAYEE_ACCOUNT_NO = "payeeAccountNo";
        /**
         * 收款方账户类型
         */
        String PAYEE_ACCOUNT_TYPE = "payeeAccountType";
        /**
         * 收款方机构类型
         */
        String PAYEE_ORGAN_NO = "payeeOrganNo";
        /**
         * 收款方姓名
         */
        String PAYEE_NAME = "payeeName";
        /**
         * 手机号
         */
        String MOBILE_NO = "mobileNo";
    }

    /**
     * Function : 商户账户类型<br/>
     * Date : 2016年4月28日 下午4:22:59 <br/>
     *
     * @author caicj
     * @version R
     * @since JDK 1.7
     */
    public interface PGWMercAccountType {
        /**
         * 备付金虚拟账户
         */
        String MERC_ACCOUNT_TYPE_PROVISION_VIR = "01";
        /**
         * 佣金虚拟账户
         */
        String MERC_ACCOUNT_TYPE_COMMISSION_VIR = "02";
    }

    /**
     * Function : 运营转账常量<br/>
     * Date : 2016年5月3日 下午3:11:02 <br/>
     *
     * @author caicj
     * @version R
     * @since JDK 1.7
     */
    public interface OMSTransferConstant {
        /**
         * 行内转账
         */
        String OMS_PAYMENT_APPLY_TRANSFER_TYPE_00 = "00";
        /**
         * 跨行转账
         */
        String OMS_PAYMENT_APPLY_TRANSFER_TYPE_01 = "01";
        /**
         * 同城转账
         */
        String OMS_PAYMENT_APPLY_AREA_FLAG_00 = "00";
        /**
         * 异地转账
         */
        String OMS_PAYMENT_APPLY_AREA_FLAG_01 = "01";
        /**
         * 结算账号
         */
        String OMS_PAYMENT_APPLY_ACCT_TYPE_00 = "00";
        /**
         * 记账码
         */
        String OMS_PAYMENT_APPLY_ACCT_TYPE_01 = "01";
        /**
         * 清算账号
         */
        String OMS_PAYMENT_APPLY_ACCT_TYPE_02 = "02";
        /**
         * 交易状态-21：成功
         */
        String OMS_PAYMENT_RESULT_QUERY_TRANSTS_21 = "21";
        /**
         * 交易状态-20：可疑
         */
        String OMS_PAYMENT_RESULT_QUERY_TRANSTS_20 = "20";
        /**
         * 交易状态-22：失败
         */
        String OMS_PAYMENT_RESULT_QUERY_TRANSTS_22 = "22";
    }

    public interface PGWOnlineOpenSettlePeriod {
        /**
         * 月
         */
        String SETTLE_PERIOD_M = "M";
        /**
         * 季
         */
        String SETTLE_PERIOD_S = "S";
        /**
         * 半年
         */
        String SETTLE_PERIOD_H = "H";
        /**
         * 年
         */
        String SETTLE_PERIOD_Y = "Y";
    }

    /**
     * 定义CIF系统交互的常量
     *
     * @author JsonChen
     */
    public interface PayApiConstant {
        /**
         * 应用头本身的版本号，随头部格式变化而递增。为1~9的数字，从1开始。
         */
        String HEAD_VERSION = "1";

        /**
         * 请求方请求的服务版本号。版本号结构为：X.Y
         */
        String VERSION = "1.01";

        /**
         * 服务请求方的ID。参照《证通技术标准_2_系统编号》中的系统ID英文简称。
         */
        String REQUEST_ID = "PGW";

        /**
         * 交易处理系统需指定目标系统ID
         */
        String TARGET_ID = "EAS";

        /**
         * 交易处理系统需指定目标系统ID EPS
         */
        String TARGET_EPS_ID = "EPS";

        /**
         * 交易处理系统需指定目标系统ID EPS
         */
        String TARGET_RES_ID = "RES";

        /**
         * 交易处理系统需指定目标系统ID OMS
         */
        String TARGET_OMS_ID = "OMS";

        /**
         * 渠道ID，参照《证通技术标准_3_渠道编号》
         */
        String CHANNEL_ID = "04";

        /**
         * 密码类型：PC
         */
        String PWD_TYPE_PC = "0";

        /**
         * 密码类型：mobile
         */
        String PWD_TYPE_MOBILE = "1";

        /**
         * 默认发送级别
         */
        String SEND_LEVEL = "6";

        /**
         * 发送成功的标示
         */
        String SEND_SUCC = "Y";
        /**
         * 支付密码调用失败
         */
        String ERROR_USERAUTH_PAYPASS_FAIL = "10339020";
        /**
         * 支付密码锁定
         */
        String ERROR_USERINFO_PAYPASS_LOCKED = "10339012";
        /**
         * 支付密码待锁定
         */
        String ERROR_USERINFO_PAYPASS_RLOCKED = "10339013";

    }

    /**
     * Function : 迅联电子账户对账文件格式相关<br/>
     * Date : 2016年10月4日 下午6:19:55 <br/>
     *
     * @author xiejf
     * @since JDK 1.7
     */
    public interface EAccountFile {
        /**
         * 下划线拼接符号
         */
        String UNDERLINE = "_";
        /**
         * 逗号分隔符
         */
        String COMMA_SPLIT = ",";
        /**
         * CSV文件结束符
         */
        String SUFFIX = ".csv";
        /**
         * 文件结束标识符
         */
        String END = ".end";
        /**
         * 最多往前偏移的天数
         */
        int SETTLE_DATE_OFFSET = 2;
        /**
         * 迅联电子账户对账文件格式
         */
        String PREFIX_RECONCILIATION = "accountreconciliation";
        /**
         * 迅联电子账户批量结算文件
         */
        String PREFIX_BATCH_SETTLE = "accountbatchsettle";
        /**
         * 迅联电子账户文件前缀
         */
        String PREFIX_ZXF = "zxf";
        /**
         * 证通内部文件前缀
         */
        String PREFIX_ECT = "ect";

    }

    /**
     * Function : 证通宝系统参数<br/>
     * Date : 2016年10月10日 下午5:19:55 <br/>
     *
     * @author xiejf
     * @since JDK 1.7
     */
    public interface EctbaoSysParam {
        String CHANNELID = "000000000000";
        String TRADEMODE = "APP";
    }

    /**
     * Function : 支付网关二清清算场次<br/>
     * Date : 2017年3月21日 上午11:09:38 <br/>
     *
     * @author caicj
     * @version R
     * @since JDK 1.7
     */
    public interface PGWBatchNo {
        /**
         * 0点对账批量(自然日T日00:00)
         * 记录范围:清算日T日，自然日T-1日15:30至T-1日24:00
         */
        String BATCH_NO_1 = "1";
        /**
         * 18点对账批量(自然日T日18:00)
         * 记录范围:清算日T日，自然日T日00:00至T日15:30
         */
        String BATCH_NO_2 = "2";
    }

    /**
     * Function : 批量代收代付详情状态<br/>
     * Date : 2017年9月11日 下午5:34:59 <br/>
     *
     * @author hewt
     * @version 1.0
     * @since JDK 1.7
     */
    /**
     * Function : 运营划账时备付金状态 <br/>
     * Date : 2017年4月17日 上午10:23:04 <br/>
     *
     * @author xiejf
     * @version R
     * @since JDK 1.7
     */
    public interface OmsTransferProvisionStatus {
        /**
         * 正常
         */
        String NORMAL = "00";
        /**
         * 冻结成功
         */
        String FREEZE_SUCC = "01";
        /**
         * 冻结失败
         */
        String FREEZE_FAIL = "02";
        /**
         * 解冻成功
         */
        String UNFREEZE_SUCC = "03";
        /**
         * 解冻失败
         */
        String UNFREEZE_FAIL = "04";
        /**
         * 划账成功
         */
        String TRANSFER_SUCC = "05";
        /**
         * 划账失败
         */
        String TRANSFER_FAIL = "06";
        /**
         * 解冻失败已入差错
         */
        String UNFREEZE_IN_ERROR = "07";
        /**
         * 划账失败已入差错
         */
        String TRANSFER_IN_ERROR = "08";
    }

    /**
     * Function : 基金计费手续费收取类型 <br/>
     * Date : 2017年5月20日 上午20:26:24 <br/>
     *
     * @author wangxiaohao
     * @version R
     * @since JDK 1.7
     */
    public interface FundTradeFeeType {
        /**
         * 人工核计
         */
        String ARTIFICIAL_NUCLEAR = "0";
        /**
         * 按笔收取
         */
        String FEE_BY_RECORD = "1";
        /**
         * 按比例收取
         */
        String FEE_BY_SCALE = "2";
    }

    /**
     * Function: tacs收款方类型定义
     * date：2017-06-08
     *
     * @author hewt
     * @version R
     * @since JDK 1.7
     */
    public interface TACSPayFlag {
        String ACCOUNT_PAY_FLAG = "D001";
    }


    /**
     * @author xiejf
     * @version 1.0
     * @ClassName: ZtbConstans
     * @Description: 证通宝代付常量
     * @Date: 2017年6月30日 下午3:20:21
     */
    public interface ZtbConstans {
        String REDEEM = "redeem";
        String APP = "APP";
        /**
         * 默认渠道号
         */
        String DEFAULT_CHANNEL_ID = "000000000000";
        /**
         * 默认基金编号
         */
        String DEFAULT_FUND_CODE = "000709";
    }

    /**
     * Function : 商户鉴权流水来源 <br/>
     * Date : 2017年7月11日 下午20:26:24 <br/>
     *
     * @author wangxiaohao
     * @version R
     * @since JDK 1.7
     */
    public interface MERCHANTSIGNSOURCE {
        /**
         * 后台验证
         */
        String SIGN_BY_INNER = "0";
        /**
         * 商户调用鉴权接口
         */
        String SIGN_BY_INTERFACE = "1";
    }

    /**
     * Function : 商户鉴权状态 <br/>
     * Date : 2017年7月11日 下午20:26:24 <br/>
     *
     * @author wangxiaohao
     * @version R
     * @since JDK 1.7
     */
    public interface MERCHANTSIGNSTATUS {
        /**
         * 成功
         */
        String SUCCESS = "1";
        /**
         * 失败
         */
        String FAIL = "2";
        /**
         * 处理中
         */
        String PROCESSING = "3";
    }

    /**
     * Function : 风控前置交易状态 <br/>
     * Date : 2017年7月26日 下午20:26:24 <br/>
     *
     * @author wangxiaohao
     * @version R
     * @since JDK 1.7
     */
    public interface RiskTransStatus {
        /**
         * 交易失败
         */
        String FAIL = "0";
        /**
         * 交易成功
         */
        String SUCCESS = "1";
        /**
         * 正在处理
         */
        String PROCESSING = "2";
    }

    /**
     * Function : 风控前置交易类型 <br/>
     * Date : 2017年7月26日 下午20:26:24 <br/>
     *
     * @author wangxiaohao
     * @version R
     * @since JDK 1.7
     */
    public interface RiskTransType {
        /**
         * 登陆
         */
        String LOGIN = "01";
        /**
         * 注册
         */
        String REGISTER = "02";
        /**
         * 修改密码
         */
        String MODIFY_PASSWORD = "03";
        /**
         * 修改手机号
         */
        String MODIFY_MOBILE = "04";
        /**
         * 绑卡/实名认证
         */
        String REAL_NAME_AUTH = "05";
        /**
         * 提现
         */
        String WITHDRAW = "06";
        /**
         * 转账
         */
        String TRANSFER = "07";
        /**
         * 消费
         */
        String CONSUMPTION = "08";
        /**
         * 网银充值
         */
        String ONLINE_BANKING_RECHARGE = "09";
        /**
         * 快捷充值
         */
        String QUICK_RECHARGE = "10";
        /**
         * 鉴权
         */
        String SIGN = "11";
        /**
         * 代收
         */
        String COLLECTION = "12";
        /**
         * 代付
         */
        String AGENTPAY = "13";
        /**
         * 退货
         */
        String REFUND = "14";
        /**
         * 风控默认cstno
         */
        String DEFAULT_RISK_CSTNO = "0000000000";
        /**
         * 风控transCode默认前缀
         */
        String DEFAULT_RISK_PREFIX = "T1";
    }

    /**
     * Function : 风控前置渠道类型 <br/>
     * Date : 2017年7月26日 下午20:26:24 <br/>
     *
     * @author wangxiaohao
     * @version R
     * @since JDK 1.7
     */
    public interface RiskChannelType {
        /**
         * 商务平台
         */
        String PGW = "01";
        /**
         * 01-风控评估
         */
        String EVALUATION = "01";
        /**
         * 02-风控确认
         */
        String CONFIRM = "02";
    }

    /**
     * Function : 风控业务类型<br/>
     * Date : 2017年7月26日 下午9:47:12 <br/>
     *
     * @author wangxiaohao
     * @since JDK 1.7
     */
    public interface RiskProdType {
        /**
         * 1-证通支付
         */
        String ECT_PAY = "1";
        /**
         * 2-证通宝
         */
        String ECTBAO = "2";
        /**
         * 3-基金支付
         */
        String FUND_PAY = "3";
        /**
         * 4-二维码支付
         */
        String QR_CODE_PAY = "4";
        /**
         * 5-公交卡
         */
        String TRANSIT_PAY = "5";
        /**
         * 6-云闪付
         */
        String CLOUD_FLASH_PAY = "6";
        /**
         * 7-信用卡还款
         */
        String CREDIT_CARD_REPAYMENT = "7";
        /**
         * 8-境外支付
         */
        String OVERSEAS_PAY = "8";
        /**
         * 9-互金业务
         */
        String MUTUAL_FUND_BUSINESS = "9";
    }

    /**
     * Function : 风控终端类型<br/>
     * Date : 2017年7月26日 下午9:47:12 <br/>
     *
     * @author wangxiaohao
     * @since JDK 1.7
     */
    public interface RiskTerminalType {
        /**
         * 终端类型-0-未知或不区分
         */
        String TER_TYPE_UNKNOWN = "0";
        /**
         * 终端类型-1-PC
         */
        String TER_TYPE_PC = "1";
        /**
         * 终端类型-2-手机
         */
        String TER_TYPE_MOBILE = "2";
        /**
         * 终端类型-3-ipad
         */
        String TER_TYPE_IPAD = "3";
        /**
         * 终端类型-4-手表
         */
        String TER_TYPE_WATCH = "4";
    }

    /**
     * Function : 风控终端类型<br/>
     * Date : 2017年7月26日 下午9:47:12 <br/>
     *
     * @author wangxiaohao
     * @since JDK 1.7
     */
    public interface RiskSoftwareType {
        /**
         * 终端类型-0-未知或不区分
         */
        String TER_TYPE_UNKNOWN = "0";
        /**
         * 终端类型-1-PC
         */
        String TER_TYPE_PC = "1";
        /**
         * 终端类型-2-手机
         */
        String TER_TYPE_MOBILE = "2";
        /**
         * 终端类型-3-ipad
         */
        String TER_TYPE_IPAD = "3";
        /**
         * 终端类型-4-手表
         */
        String TER_TYPE_WATCH = "4";
    }

    /**
     * Function : 批量代收代付业务类型<br/>
     * Date : 2017年9月11日 下午5:34:59 <br/>
     *
     * @author hewt
     * @version 1.0
     * @since JDK 1.7
     */
    public interface BatchCollectAndPayBusyType {
        /**
         * 0--批量代收
         */
        String BATCH_COLLET = "0";
        /**
         * 1--批量代付
         */
        String BATCH_PAYMENT = "1";
    }

    /**
     * Function : 批量详情状态<br/>
     * Date : 2017年8月8日 下午9:47:12 <br/>
     *
     * @author hewt
     * @since JDK 1.7
     */
    public interface SingleCollectTransStatus {
        /**
         * 单笔代收状态  01-待发送
         */
        String INIT = "01";
        /**
         * 单笔代收状态  02-处理中
         */
        String DOING = "02";
        /**
         * 单笔代收状态  03-成功
         */
        String SUCCESS = "03";
        /**
         * 单笔代收状态  04-失败
         */
        String FAILURE = "04";
    }


    /**
     * Function : 验证类型<br/>
     * Date : 2017年9月14日 下午15:14:12 <br/>
     *
     * @author hewt
     * @since JDK 1.7
     */
    public interface PGWCheckType {
        /**
         * 账户验证
         */
        String SIGN_AUTH = "1";
        /**
         * 短信发送
         */
        String SEND_SMS = "2";
        /**
         * 绑卡签约
         */
        String BIND_CARD = "3";
        /**
         * 代收委托
         */
        String ENDO_COLL = "4";
        /**
         * 第三方绑定
         */
        String THIRD_BIND = "5";
        /**
         * 解约
         */
        String SIGN_OUT = "6";

    }

    /**
     * Function : 绑卡签约方式<br/>
     * Date : 2017年9月14日 下午15:14:12 <br/>
     *
     * @author hewt
     * @since JDK 1.7
     */
    public interface PGWSignMethod {
        /**
         * CIF签约
         */
        String CIF = "0";
        /**
         * 网联签约
         */
        String NET_UNION = "1";

    }

    /**
     * Function : 商务平台系统编码<br/>
     *
     * @author wpc
     * @since JDK 1.7
     */
    public interface SystemCode {
        //支付网关1011
        String PAYMENT_GATEWAY = "1011";
        //商务平台1012
        String MERCHANT_SERVICE = "1012";
        //生活服务中心1013
        String LIFE_SERVICE = "1013";

    }

    /**
     * 是否信任商戶
     *
     * @author WPC
     */
    public interface MerchantTrust {
        /**
         * 非信任商戶
         */
        String NON_TRUSTED_MERCHANT = "1";
        /**
         * 信任商戶
         */
        String TRUSTED_MERCHANT = "0";
    }

    /**
     * 批量代收付返回状态
     *
     * @author
     */
    public interface BatchCollectAgentPayStatus {
        /**
         * 非信任商戶
         */
        String SUCCESS = "0";
        /**
         * 信任商戶
         */
        String FAIL = "1";
    }

    /**
     * 验证状态
     *
     * @author WPC
     */
    public interface CheckStatus {
        /**
         * 验证成功
         */
        String SUCCESS = "1";
        /**
         * 验证失败
         */
        String FAIL = "2";
        /**
         * 验证处理中
         */
        String INPROCESS = "3";
    }

    /**
     * 鉴权接口校验状态
     *
     * @author WXH
     */
    public interface SignValiuateStatus {
        /**
         * 首次/重新发起签约
         */
        String SIGN_AGAIN = "0";
        /**
         * 授信商户更新数据
         */
        String TRUST_UPDATE = "1";
        /**
         * 非授信商户拒绝重新签约返回错误
         */
        String UN_TRUST_REFUSE = "2";
        /**
         * 用户已签约
         */
        String SIGN_EXITS = "3";
    }

    /**
     * Function : PCAC系统参数<br/>     *
     *
     * @author
     * @since JDK 1.7
     */
    public interface PCACSystemParams {
        String VERSION = "V1.0.1";
        String MERCHANT_SERVICE = "1012";
        String LIFE_SERVICE = "1013";
        String IS_ABALE = "1";
        String UN_ABALE = "0";
    }

    /**
     * 批量代收金额
     *
     * @author WXH
     */
    public interface MercBatchPayLimit {
        /**
         * 限额开关 - 关闭
         */
        int QUOTA_SWITCH_CLOSE = 0;
        /**
         * 限额开关 - 打开
         */
        int QUPTA_SWICH_OPEN = 1;
        /**
         * compareTo小于
         */
        int LESS_THAN = -1;
        /**
         * compareTo大于
         */
        int GREATER_THAN = 1;
    }

    /**
     * 商户等级
     *
     * @author
     */
    public interface MerchantGrade {
        /**
         * 一级商户
         */
        String ONE = "1";
        /**
         * 二级商户
         */
        String TWO = "2";
    }

    /**
     * Function : PCAC特约商户信息上报交易码<br/>
     *
     * @author wxh
     * @since JDK 1.7
     */
    public interface PCACTransCode {
        /**
         * 登陆
         */
        String LOGIN = "LR0001";
        /**
         * 登出
         */
        String LOGINOUT = "LR0002";
        /**
         * 商户风险信息变更查询
         */
        String MERC_RISK_CHANGE_QUERY = "UP0003";
        /**
         * 商户风险信息补录
         */
        String MERC_RISK_CHANGE_AMENDED_RECORD = "UP0004";
        /**
         * 特约商户信息差错处理
         */
        String MERC_ERROR_HANDING = "EDEL01";

        /**
         * 风险信息补发申请
         */
        String INFO_REPUSH = "TS0004";
        /**
         * 企业商户信息上报
         */
        String MERC_INFO_REPORT = "EER001";
        /**
         * 商户风险信息上报
         */
        String RISK_INFO_REPORT = "ER0001";
        /**
         * 商户风险信息查询
         */
        String RISK_INFO_QUERY = "QR0002";
    }

    /**
     * Function : PCAC系统编号<br/>
     *
     * @author wxh
     * @since JDK 1.7
     */
    public interface PCACSystemCode {
        /**
         * 风险信息共享系统
         */
        String RISK_SHARE_SYSTEM = "R0001";
        /**
         * 特约商户信息管理系统
         */
        String MERCHANT_INFO_MANAGE_SYSTEM = "SECB01";
        /**
         * USERTOKEN参数
         */
        String USER_TOKEN_PARAM = "PCAC_USER_TOKEN";

    }

    /**
     * Function : PCAC商户风险类型<br/>
     *
     * @author wxh
     * @since JDK 1.7
     */
    public interface PCACMercRiskType {
        /**
         * 虚假申请
         */
        String FALSITY_APPLY = "01";
        /**
         * 套现、套积分
         */
        String LIED_CASH_AND_INTEGRAL = "02";
        /**
         * 违法经营
         */
        String UNLAWFUL_BUSINESS = "03";
        /**
         * 销赃
         */
        String SELL_STOLEN_GOODS = "04";
        /**
         * 买卖或租借银行（支付）账户
         */
        String BUY_AND_SELL_BANK_ACCOUNT = "05";
        /**
         * 侧录点
         */
        String LATERAL_RECORDING_POINT = "06";
        /**
         * 伪卡集中使用点
         */
        String PSEUDO_CARD_FOCUS_POINT = "07";
        /**
         * 泄露账户及交易信息
         */
        String DISCLOSE_ACCOUNT_AND_TRANS_INFO = "08";
        /**
         * 09 ：恶意倒闭
         */
        String MALICIOUS_BANKRUPTCY = "09";
        /**
         * 10：恶意分单
         */
        String MALICIOUS_ORDER = "10";
        /**
         * 11 ：移机
         */
        String MOVE_POS = "11";
        /**
         * 12 ：高风险商户
         */
        String HIGH_RISK_MERCHANT = "12";
        /**
         * 13 ：商户合谋欺诈
         */
        String COLLUSION_FRAUD = "13";
        /**
         * 14 ：破产或停业商户
         */
        String BANKRUPT_OR_CLOSED_MERCHANT = "14";
        /**
         * 15 ：强迫签单
         */
        String COMPULSORY_SIGNING = "15";
        /**
         * 17：频繁变更服务机构
         */
        String FREQUENT_CHANGE_SERVICE_ORGAN = "17";
        /**
         * 18：关联商户涉险
         */
        String RELATED_BUSINESSES_AT_RISK = "18";
        /**
         * 19：买卖银行卡信息
         */
        String BUY_AND_SELL_BANK_CARD_INFO = "19";
        /**
         * 20：拒刷信用卡
         */
        String BRUSH_OFF_CREDIT_CARD = "20";
        /**
         * 21：转嫁手续费
         */
        String TRANSFER_COMMISSION = "21";
        /**
         * 其它
         */
        String ONTEHR = "99";

    }

    /**
     * Function : PCAC商户风险变更类型<br/>
     *
     * @author wxh
     * @since JDK 1.7
     */
    public interface PCACMercRiskInfoChangeType {
        /**
         * 01：补录
         */
        String MAKEUP = "01";
        /**
         * 02：失效
         */
        String INVALID = "02";
        /**
         * 03：降级
         */
        String DOWNGRADE = "03";
    }

    /**
     * Function : PCAC商户风险等级<br/>
     *
     * @author wxh
     * @since JDK 1.7
     */
    public interface PCACMercRiskInfoLevel {
        /**
         * 01：一级
         */
        String ONE = "01";
        /**
         * 02：二级
         */
        String TWO = "02";
        /**
         * 03：三级
         */
        String THREE = "03";
    }

    /**
     * Function : PCAC风险事件发生渠道<br/>
     *
     * @author wxh
     * @since JDK 1.7
     */
    public interface PCACRiskComeChannel {
        /**
         * 01：线上
         */
        String ONLINE = "01";
        /**
         * 02：线下
         */
        String OFFLINE = "02";
        /**
         * 03：线上兼线下
         */
        String ONANDOFFLINE = "03";
    }

    /**
     * Function : PCAC拓展方式 <br/>
     * Date : 2017年10月03日 上午10:57:12 <br/>
     *
     * @author edwardChan
     * @since JDK 1.7
     */
    public interface PCACEXPANDWAY {
        /**
         * 01--自主拓展
         */
        String OWN_EXPAND = "01";
        /**
         * 02--外包服务机构推荐
         */
        String OUT_EXPAND = "02";
    }

    /**
     * Function : PCAC合规风险状况 <br/>
     * Date : 2017年10月03日 上午10:57:12 <br/>
     *
     * @author edwardChan
     * @since JDK 1.7
     */
    public interface PCACRISKSTATUS {
        /**
         * 01--合规
         */
        String QUALIFY = "01";
        /**
         * 02--风险
         */
        String RISK = "02";
    }

    /**
     * Function : PCAC客户属性 <br/>
     * Date : 2017年10月03日 上午10:57:12 <br/>
     *
     * @author edwardChan
     * @since JDK 1.7
     */
    public interface PCACCUSTOMERATTRIBUTE {
        /**
         * 01--个人
         */
        String PERSIONAL = "01";
        /**
         * 02--商户
         */
        String MERCHANT = "02";
    }

    public interface RemitFileType {
        /**
         * 划付文件生成类型: 每个商户按照每天一个划付文件生成
         */
        int PERY_DAY = 1;

        /**
         * 划付文件生成类型: 每个商户按照节假日汇总一笔划付文件
         */
        int HOLIDAY_MERGE = 2;
    }

    /**
     * Function : 商户批量代收付限额设置<br/>
     * Date : 2017年10月27日 上午10:57:12 <br/>
     *
     * @author wangxh
     * @since JDK 1.7
     */
    public interface MercBatchPayLimitInfo {
        String YES = "1";
        String NO = "0";
    }

    /**
     * Function : 商户支持机构状态<br/>
     * Date : 2017年10月27日 上午10:57:12 <br/>
     *
     * @author wangxh
     * @since JDK 1.7
     */
    public interface MercSurpportOrganStatus {
        // 生效
        String YES = "1";
        // 无效
        String NO = "0";
    }

    /**
     * Function : 商户模式<br/>
     * Date : 2017年10月27日 上午10:57:12 <br/>
     *
     * @author wangxh
     * @since JDK 1.7
     */
    public interface MercMode {
        // 生效
        String NORMAL = "0";
        // 无效
        String PLATFORM = "1";
    }

    /**
     * Function : 商户结算模式<br/>
     * Date : 2017年10月27日 上午10:57:12 <br/>
     *
     * @author wangxh
     * @since JDK 1.7
     */
    public interface MercSettelMode {
        // 结算到 一级商户
        String ONE = "1";
        // 结算到二级商户
        String TWO = "2";
    }
}
