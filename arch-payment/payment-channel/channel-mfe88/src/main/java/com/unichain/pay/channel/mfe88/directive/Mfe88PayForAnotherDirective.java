package com.unichain.pay.channel.mfe88.directive;

import com.alibaba.fastjson.JSONObject;
import com.unichain.pay.channel.mfe88.Mfe88ChannelDirecvite;
import com.unichain.pay.channel.mfe88.Mfe88PayRequestHandler;
import com.unichain.pay.channel.mfe88.domain.PayForAnotherParam;
import com.unichain.pay.channel.mfe88.domain.PayForAnotherResponse;
import com.unichain.pay.channel.mfe88.utils.Base64;
import com.unichain.pay.core.PayDirective;
import com.unichain.pay.core.PayRequest;
import com.unichain.pay.core.PayResponse;
import com.unichain.pay.core.util.AmountUtils;
import com.unichain.pay.entity.ChannelDirectiveRecord;
import com.unichain.pay.service.ChannelDirectiveRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @Author lait.zhang@gmail.com
 * @Tel 15801818092
 * @Date 12/3/2019
 * @Description 代付指令
 */
@Service("Mfe88PayForAnotherDirective")
public class Mfe88PayForAnotherDirective implements PayDirective<Mfe88ChannelDirecvite, PayForAnotherParam> {

    @Autowired
    private ChannelDirectiveRecordService channelDirectiveRecordService;

    @Override
    public PayResponse exec(Mfe88ChannelDirecvite channelDirective, PayForAnotherParam payParam, PayRequest payRequest) {
        payParam.setMerchantNo(channelDirective.getMerchantNo());

        String result = Mfe88PayRequestHandler.build(payParam, channelDirective)
//                .sign(channelDirective.getPrivateKey())
//                .encrypt(channelDirective.getMerchantNo(), channelDirective.getSecretKey())
                .exec(channelDirective.getDirectiveUri());
        return record(payParam, payRequest, result);
    }

    @Override
    public PayResponse record(PayForAnotherParam payForAnotherParam, PayRequest payRequest, String data) {
        // 保存记录
        ChannelDirectiveRecord channelDirectiveRecord = new ChannelDirectiveRecord();
        channelDirectiveRecord.setAccountId(payRequest.getAccountId());
        channelDirectiveRecord.setAppId(payRequest.getAppId());
        channelDirectiveRecord.setPaymentId(payRequest.getPaymentId());
        channelDirectiveRecord.setChannelId(payRequest.getChannelId());
        channelDirectiveRecord.setUserId(payRequest.getUserId());
        channelDirectiveRecord.setCompanyId(payRequest.getCompanyId());
        channelDirectiveRecord.setPaymentId(payRequest.getPaymentId());
        channelDirectiveRecord.setDirectiveCode(getDirectiveCode());
        try {
            channelDirectiveRecord.setBankcard(Base64.decode(payForAnotherParam.getAccountNo()));
            // 设置金额
            channelDirectiveRecord.setAmount(new BigDecimal(AmountUtils.changeF2Y(payForAnotherParam.getOrderAmount())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        PayForAnotherResponse payForAnotherResponse = JSONObject.parseObject(data, PayForAnotherResponse.class);
        String returnCode = payForAnotherResponse.getDealCode();
        channelDirectiveRecord.setReturnCode(returnCode);
        channelDirectiveRecord.setReturnMsg(payForAnotherResponse.getDealMsg());
        //下单结果代码，为 10000 代表受理成功，否则受理失败，最终结果以代付查询为准详细参见 5.3 代码表
        PayResponse result = new PayResponse();
        //下单结果代码，为 10000 代表受理成功，否则受理失败，最终结果以代付查询为准详细参见 5.3 代码表
        if (!returnCode.equals("10000")) {
            // 0默认表示成功，1表示失败
            channelDirectiveRecord.setState(1);
        } else {
            result.data(payRequest.getPaymentId());
        }
        channelDirectiveRecordService.save(channelDirectiveRecord);
        return result;

    }

    @Override
    public PayForAnotherParam buildPayParam(PayRequest payRequest) {
        PayForAnotherParam payForAnotherParam = new PayForAnotherParam();
        Mfe88PayRequestHandler.buildPayParam(payForAnotherParam, payRequest);
        return payForAnotherParam;
    }

    @Override
    public Mfe88ChannelDirecvite buildChannelDirective() {
        return new Mfe88ChannelDirecvite();
    }

    @Override
    public String getDirectiveCode() {
        return "Mfe88PayForAnotherDirective";
    }
}
