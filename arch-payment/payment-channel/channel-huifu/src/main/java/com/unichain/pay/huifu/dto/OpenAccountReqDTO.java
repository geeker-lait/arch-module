package com.unichain.pay.huifu.dto;

import lombok.Data;
import lombok.ToString;

/**
 * 开户请求参数
 */
@Data
@ToString
public class OpenAccountReqDTO {

    /**
     * 证件类型
     */
    private String cert_type;
    /**
     * 身份证号码
     **/
    private String cert_id;
    /**
     * 用户真实姓名
     **/
    private String user_name;
    /**
     * 卡类型D-借记，储蓄卡C-贷记，信用卡（本接口暂仅支持借记卡）
     */
    private String card_type;
    /**
     * 银行卡号
     */
    private String card_no;
    /**
     * 用户手机号码
     **/
    private String bank_mobile;
    /**
     * 银行代码，参考附录
     **/
    private String bank_id;
    /**
     * 绑卡请求流水2次调用代扣签约绑卡请求流水必须一致且需为字母+数字
     **/
    private String bind_trans_id;
    /**
     * 阶段标志01-下发短信阶段02-绑卡确认阶段
     **/
    private String step_flag;
    /**
     * 验证码 step_flag为01时不填，step_flag为02时必填
     **/
    private String sms_code;
    /**
     * 职业 可不填
     **/
    private String bank_province_id;
    /**
     * 邮箱	 可不填
     **/
    private String bank_area_id;
    /**
     * 绑卡结果可能为异步，需接受异步应答
     **/
    private String bg_return_url;

}
