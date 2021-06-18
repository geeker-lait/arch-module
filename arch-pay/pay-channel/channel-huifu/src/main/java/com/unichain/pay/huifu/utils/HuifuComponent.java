//package com.unichain.pay.huifu.utils;
//
//import com.alibaba.fastjson.JSON;
//import com.google.gson.Gson;
//
//import com.unichain.pay.huifu.properties.BaseResult;
//import com.unichain.pay.huifu.properties.HuifuProperties;
//import com.unichain.pay.huifu.properties.HuifuResponCode;
//import com.unichain.pay.huifu.dto.HuifuBaseReqDTO;
//import com.unichain.pay.huifu.exception.PayErrException;
//import com.unichain.pay.huifu.exception.PayErrorCode;
//import com.unichain.pay.huifu.vo.HuifuBaseResDTO;
//import jodd.http.HttpRequest;
//import jodd.http.HttpResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.annotation.PostConstruct;
//import java.util.Map;
//
///**
// * 汇付的统一处理接口
// *
// * @author shaoqing.liu
// * @date 2018/12/7 12:05
// */
//
//public class HuifuComponent {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(HuifuComponent.class);
//    @Autowired
//    HuifuProperties huifuConfig;
//
//    /**
//     * 初始化加载公私钥
//     */
//    @PostConstruct
//    public void init() {
////        try {
////            if (huifuConfig != null) {
////                HuifuCFCASignature.putPrivateKey(huifuConfig.getMerchantNo(), huifuConfig.getPathPfx(), huifuConfig.getPathPassWord());
////                HuifuCFCAVerify.putPublicKey(huifuConfig.getMerchantNo(), huifuConfig.getPathCer());
////            }
////        } catch (Exception ex) {
////            LOGGER.error("putSignature error:{}", ex);
////        }
//    }
//
//
//    /**
//     * 汇付通用请求接口
//     *
//     * @param params
//     * @param huifuConfig
//     * @param cmdId
//     * @return
//     */
//    public BaseResult huifuRquest(Map<String, Object> params, HuifuProperties huifuConfig, String cmdId) {
//        if (huifuConfig != null) {
//            LOGGER.error("渠道对应支付记录不存在.");
//        }
//        try {
//            //公共参数组装
//            HuifuBaseReqDTO huifuBaseReqDTO = new HuifuBaseReqDTO();
//            huifuBaseReqDTO.setVersion(huifuConfig.getVersion());
//            huifuBaseReqDTO.setCmd_id(cmdId);
//            huifuBaseReqDTO.setMer_cust_id(huifuConfig.getMerCustId());
//            Map<String, Object> reqMap = BeanUtil.transforObjectToMap(huifuBaseReqDTO);
//            params.putAll(reqMap);
//
//            String paramsStr = JSON.toJSONString(params);
//
//            String sign = HuifuSignatureUtils.sign(paramsStr, huifuConfig);
//            HttpRequest httpRequest = (HttpRequest) HttpRequest.post(huifuConfig.getRequestUrl()).charset("UTF-8");
//            String postStr = "cmd_id=" + cmdId + "&version=" + huifuConfig.getVersion() + "&mer_cust_id=" + huifuConfig.getMerCustId() + "&check_value=" + sign;
//
//            HttpResponse httpResponse = ((HttpRequest) ((HttpRequest) httpRequest.contentType("application/x-www-form-urlencoded")).body(postStr)).send();
//
//            String body = httpResponse.bodyText();
//            String resultStr = HuifuSignatureUtils.parseCVResult(body, huifuConfig);
//            if (BeanUtil.isJson(resultStr)) {
//                Gson gson = new Gson();
//                HuifuBaseResDTO huifuBaseResDTO = gson.fromJson(resultStr, HuifuBaseResDTO.class);
//                if (huifuBaseResDTO != null) {
//                    String successCode = cmdId + HuifuResponCode.SUCCESS;
//                    String proccessingCode = cmdId + HuifuResponCode.PROCCESSING;
//                    //成功和处理中都正确返回，其他情况 错误返回
//                    if (successCode.equals(huifuBaseResDTO.getResp_code()) || proccessingCode.equals(huifuBaseResDTO.getResp_code())) {
//                        return BaseResult.responseSuccessData(huifuBaseResDTO);
//                    } else {
//                        return BaseResult.responseFaillMsg(huifuBaseResDTO.getResp_code(), huifuBaseResDTO.getResp_desc());
//                    }
//                }
//               throw new PayErrException(PayErrorCode.PAY_REQUEST_ERROR_ERROR);
//            } else {
//                throw new PayErrException(PayErrorCode.PAY_REQUEST_ERROR_ERROR);
//            }
//
//        } catch (Exception var12) {
//            LOGGER.error("汇付天下接口异常", var12);
//            throw new PayErrException(PayErrorCode.PAY_REQUEST_ERROR);
//        }
//    }
//
//
//}
