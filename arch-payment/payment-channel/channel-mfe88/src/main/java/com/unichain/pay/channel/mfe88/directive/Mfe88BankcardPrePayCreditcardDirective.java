package com.unichain.pay.channel.mfe88.directive;

import com.unichain.pay.channel.mfe88.Mfe88ChannelDirecvite;
import com.unichain.pay.channel.mfe88.Mfe88PayRequestHandler;
import com.unichain.pay.channel.mfe88.domain.BankcardPrePayParam;
import com.unichain.pay.core.PayDirective;
import com.unichain.pay.core.PayRequest;
import com.unichain.pay.core.PayResponse;
import org.springframework.stereotype.Service;

@Service("Mfe88BankcardPrePayCreditcardDirective")
public class Mfe88BankcardPrePayCreditcardDirective implements PayDirective<Mfe88ChannelDirecvite, BankcardPrePayParam> {


    @Override
    public PayResponse exec(Mfe88ChannelDirecvite channelDirective, BankcardPrePayParam bankcardPrePayParam, PayRequest payRequest) {
        Mfe88PayRequestHandler.build(bankcardPrePayParam, channelDirective)
//                .sign(channelDirective.getPrivateKey())
//                .encrypt(channelDirective.getMerchantNo(),channelDirective.getSecretKey());
                .exec(channelDirective.getDirectiveUri());
        return null;
    }

    @Override
    public BankcardPrePayParam buildPayParam(PayRequest payRequest) {
        BankcardPrePayParam bankcardPrePayParam = new BankcardPrePayParam();
        Mfe88PayRequestHandler.buildPayParam(bankcardPrePayParam, payRequest);
        return bankcardPrePayParam;
    }

    @Override
    public Mfe88ChannelDirecvite buildChannelDirective() {
        return new Mfe88ChannelDirecvite();
    }

    @Override
    public String getDirectiveCode() {
        return "Mfe88BankcardPrePayCreditcardDirective";
    }
}
