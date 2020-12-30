package org.arch.payment.core;

import org.arch.payment.sdk.DirectiveRequest;
import org.arch.payment.sdk.PayDirective;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 12/29/2020 7:30 PM
 */
public interface PayChannel {

    /**
     * 根据指令请求从支付通道中得到一个指令集合
     *
     * @param directiveRequest
     * @return
     */
    List<PayDirective> getPayDirectives(DirectiveRequest directiveRequest);
}
