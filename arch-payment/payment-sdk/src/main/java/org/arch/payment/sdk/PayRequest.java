package org.arch.payment.sdk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * 支付请求
 */
@ToString
@Data
public class PayRequest {
    private DirectiveCode directiveCode;
    private String appId;
    private String accountId;
    private String userId;
    private String merchantId;
    private String channelId;
    private String deviceId;
    private String paymentId;
    // 订单ID
    private String orderId;
    // 银行卡
    private String bankcard;
    // 金额
    private String amount;
    // 支付类型 1支付宝 2微信 3三方支付公司 4四方支付
    private String payType;
    @Getter
    // 具体支付参数
    private JSONObject payParams;


    private PayRequest(){

    }




    public static PayRequest of(DirectiveCode directiveCode){
        PayRequest payRequest = new PayRequest();
        payRequest.directiveCode = directiveCode;
        return payRequest;
    }
    /**
     * 创建map请求参数
     *
     * @param httpServletRequest
     * @return
     */
    public PayRequest init(HttpServletRequest httpServletRequest) {
        payParams = getRequestParam(httpServletRequest);

        appId = payParams.getString("appId");
        // 移除
        Object oAccountId = payParams.remove("accountId");
        Object oCompanyId = payParams.remove("merchantId");
        if(null != oAccountId && StringUtils.isNotBlank(oAccountId.toString())){
            accountId = oAccountId.toString();
        }

        if(null != oCompanyId && StringUtils.isNotBlank(oCompanyId.toString())){
            merchantId = oCompanyId.toString();
        }


        payType = payParams.getString("payType");
        if (PayType.THIRD.toString().equalsIgnoreCase(payType)) {
            bankcard = payParams.getString("bankcard");
        } else if (PayType.ALIPAY.toString().equalsIgnoreCase(payType)) {
            // 获取具体的支付宝账户，这里默认先写死
            bankcard = "alipay";
        } else {
            // 获取具体的微信账户，这里默认先写死
            bankcard = "weixin";
        }
        return this;
    }


    public <T extends PayParam> T getPayParam(Class<T> payParamsClazz){
        T t = null;
        try {
            t = payParamsClazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //t.convert(this, );
        return t;
    }

    /**
     * @param request
     * @return
     * @throws Exception
     * @author tianwyam
     * @description 从POST请求中获取参数
     */
    private JSONObject getRequestParam(HttpServletRequest request) {
        // 返回参数
        JSONObject params = new JSONObject();
        // 获取内容格式
        String contentType = request.getContentType();
        if (contentType != null && !contentType.equals("")) {
            contentType = contentType.split(";")[0];
        }
        // form表单格式  表单形式可以从 ParameterMap中获取
        if ("appliction/x-www-form-urlencoded".equalsIgnoreCase(contentType)) {
            // 获取参数
            Map<String, String[]> parameterMap = request.getParameterMap();
            if (parameterMap != null) {
                for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                    params.put(entry.getKey(), entry.getValue()[0]);
                }
            }
        }
        // json格式 json格式需要从request的输入流中解析获取
        if ("application/json".equalsIgnoreCase(contentType)) {
            // 使用 commons-io中 IOUtils 类快速获取输入流内容
            String paramJson = null;
            try {
                paramJson = IOUtils.toString(request.getInputStream(), "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            params.putAll(JSON.parseObject(paramJson));
        }
        return params;
    }

}
