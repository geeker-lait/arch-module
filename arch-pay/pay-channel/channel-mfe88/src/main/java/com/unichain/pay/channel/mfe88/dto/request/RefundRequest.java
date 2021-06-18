//package com.unichain.pay.channel.mfe88.dto.request;
//
//import cn.hutool.core.bean.BeanUtil;
//import cn.hutool.core.date.DateUtil;
//import lombok.Data;
//import org.arch.framework.utils.AmountUtils;
//import org.arch.payment.sdk.DirectiveRequest;
//import org.arch.payment.sdk.PayRequest;
//
//import java.math.BigDecimal;
//import java.util.Map;
//
///**
// * @Author lait.zhang@gmail.com
// * @Tel 15801818092
// * @Date 12/4/2019
// * @Description ${Description}
// */
//@Data
//public class RefundRequest implements DirectiveRequest {
//    //	接口名称	是	20	固定值：refundOrder
//    private String service = "refundOrder";
//    //	商户编号	是	50	商户在的唯一标识
//    private String merchantNo;
//    // 	商户退款交易流水号	是	50	商户系统产生的唯一退款流水号（商户对于本笔退款订单生成的订单记录号）
//    private String refundNo;
//
//
//    // 	商户订单号	是	50	商户系统产生的唯一订单号（需退款的商户订单号）
//    private String orderNo;
//    //  	网关版本	是	10	固定值：V3.0。
//    private String version = "V3.0";
//    //	签名版本	是	10	固定值：V3.0
//    private String signVersion = "V3.0";
//    //	风控版本	是	10	固定值：V3.0
//    private String riskVersion = "V3.0";
//    //	交易币种	是	10	目前只支持人民币 固定值：CNY
//    private String curCode = "CNY";
//    //	退款金额	是	10	以"分"为单位的整型，必须大于零，申请的退款金额
//    private String refundAmount;
//    //	退款申请时间	是	14	退款申请时间，格式：yyyyMMddHHmmss
//    private String refundTime = DateUtil.date().toString("yyyyMMddHHmmss");
//    //	退款说明	否	500	退款说明
//    private String refundDesc;
//    // 	  签名类型	是	1	签名类型：1: MD5； 2： RSA； 3：CFCA证书
//    private String signType = "3";
//    //	商户签名数据		是	1024	具体参考4.1
//    private String sign;
//
//    @Override
//    public DirectiveRequest convert(Map<String, Object> map, PayRequest payRequest) {
//        RefundRequest refundRequest = BeanUtil.mapToBean(map, this.getClass(), true);
//        BeanUtil.copyProperties(refundRequest, this);
//        refundAmount = AmountUtils.changeY2F(new BigDecimal(refundAmount));
//        return this;
//    }
//
//    @Override
//    public DirectiveRequest encrypt(String encryptKey) {
//        return null;
//    }
//}
