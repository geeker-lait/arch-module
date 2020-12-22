package org.arch.payment.sdk;


/**
 * @Author lait.zhang@gmail.com
 * @Tel 15801818092
 * @Date 12/24/2019
 * @Description ${Description}
 */
public interface DirectiveRouting {
    PayResponse routing(PayRequest payRequest);

    default PayConfigurable buildChannelConfig(PayMerchantChannel merchantChannel, PayDirective directive) {
        ChannelDirectiveConfiguration merchantChannelConfig = new ChannelDirectiveConfiguration();

        merchantChannelConfig.setDirectiveUri(directive.getUri());
        merchantChannelConfig.setCallbackUrl(directive.getCallbackUrl());
        merchantChannelConfig.setRedirectUrl(directive.getRedirectUrl());

        merchantChannelConfig.setMerchantNo(merchantChannel.getMerchantNo());
        merchantChannelConfig.setPrivateKey(merchantChannel.getPrivateKey());
        merchantChannelConfig.setSecretKey(merchantChannel.getSecretKey());
        merchantChannelConfig.setPublicKey(merchantChannel.getPublicKey());
        return merchantChannelConfig;
    }
}
