//package com.unichain.pay.huifu;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.TypeReference;
//import com.unichain.framework.mvc.exception.BusinessException;
//import com.unichain.pay.base.payapi.huifupay.properties.HuifuProperties;
//import com.unichain.pay.base.payapi.huifupay.dto.CardBinQueryDto;
//import com.unichain.pay.base.payapi.huifupay.utils.SignUtils;
//import com.unichain.pay.base.payapi.huifupay.vo.CardBinResVo;
//import com.unichain.pay.mvc.constant.PayChannelUrl;
//import com.unichain.pay.mvc.enums.PayErrorCode;
//import com.unichain.pay.mvc.util.DateUtils;
//import com.unichain.pay.mvc.util.HttpsUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * @author: PC
// * @version: v1.0
// * @description: com.unichain.pay.base.payapi.huifupay
// * @date:2019/4/12
// */
//@Slf4j
//@Component
//public class HuifuPayService {
//    @Autowired
//    private HuifuProperties huifuProperties;
//    //查询卡bin
//    public CardBinResVo cardBinQuery(CardBinQueryDto cardBinQueryDto){
//        if(cardBinQueryDto==null || StringUtils.isBlank(cardBinQueryDto.getBankNo())){
//            log.error("cardBinQuery 查询卡bin参数异常");
//            throw new BusinessException(PayErrorCode.PAY_PARAM_ERROR);
//        }
//        String url =huifuProperties.getLoanReqBaseUrl()+ PayChannelUrl.huifuQueryCardBinUrl;
//        List<NameValuePair> parameters = new ArrayList<>();
//        String dateStr = DateUtils.getDay(new Date());
//        String companyName = huifuProperties.getCompanyName();
//        // 签名
//        String plain = "GET" + url + "app_token=" + huifuProperties.getAppToken() + "&card_no=" + cardBinQueryDto.getBankNo() + "&client_name=" + huifuProperties.getClientName()
//                + "&company_name=" + companyName + "&request_date=" + dateStr + "&request_seq=" + cardBinQueryDto.getRequest_seq();
//        parameters.add(new BasicNameValuePair("card_no",cardBinQueryDto.getBankNo()));
//        parameters = baseParamsTransfer(parameters,cardBinQueryDto.getRequest_seq(),dateStr,plain);
//        log.info("查询卡bin 报文:{}",plain);
//        String json = HttpsUtils.sendGet(url,parameters);
//        log.info("查询卡bin 返回:{}",json);
//        if(StringUtils.isBlank(json)){
//            return null;
//        }
//        CardBinResVo cardBinResVo= JSON.parseObject(json,new TypeReference<CardBinResVo>(){});
//        return cardBinResVo;
//    }
//
//    public List<NameValuePair> baseParamsTransfer(List<NameValuePair> parameters, String requestNo, String dateStr, String plain){
//        String signature = SignUtils.hmacsha256(plain, huifuProperties.getAppKey());
//        parameters.add(new BasicNameValuePair("signature",signature));
//        parameters.add(new BasicNameValuePair("app_token",huifuProperties.getAppToken()));
//        parameters.add(new BasicNameValuePair("client_name",huifuProperties.getClientName()));
//        parameters.add(new BasicNameValuePair("company_name",huifuProperties.getCompanyName()));
//        parameters.add(new BasicNameValuePair("request_date",dateStr));
//        parameters.add(new BasicNameValuePair("request_seq",requestNo));//该笔请求流水号
//        return parameters;
//    }
//}
