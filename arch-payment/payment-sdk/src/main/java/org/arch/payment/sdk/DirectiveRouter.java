package org.arch.payment.sdk;


import java.util.List;

/**
 * @Author lait.zhang@gmail.com
 * @Tel 15801818092
 * @Date 12/24/2019
 * @Description ${Description}
 */
public interface DirectiveRouter {
    void routing(List<Directive> directives);

    DirectiveCode getDirectiveCode();
//    PayResponse routing(PayRequest payRequest);

//    default PayConfigurable buildChannelConfig(PayMerchantChannel merchantChannel, PayDirective directive) {
//        ChannelConfiguration merchantChannelConfig = new ChannelConfiguration();
//
//        merchantChannelConfig.setDirectiveUri(directive.getUri());
//        merchantChannelConfig.setCallbackUrl(directive.getCallbackUrl());
//        merchantChannelConfig.setRedirectUrl(directive.getRedirectUrl());
//
//        merchantChannelConfig.setMerchantNo(merchantChannel.getMerchantNo());
//        merchantChannelConfig.setPrivateKey(merchantChannel.getPrivateKey());
//        merchantChannelConfig.setSecretKey(merchantChannel.getSecretKey());
//        merchantChannelConfig.setPublicKey(merchantChannel.getPublicKey());
//        return merchantChannelConfig;
//    }
//
//    Object getDirectiveName();
}
