package org.arch.payment.sdk;

import org.arch.payment.sdk.bean.PayRequest;
import org.arch.payment.sdk.enums.DirectiveCode;

import java.util.List;

/**
 * @Author lait.zhang@gmail.com
 * @Tel 15801818092
 * @Date 12/24/2019
 * @Description 指令路由
 */
public interface DirectiveRouter {

    /**
     * 路由请求指令
     *
     * @param directives
     * @param payRequest
     */
    void routing(List<Directive> directives, PayRequest payRequest);

    /**
     * 获取指令码
     *
     * @return
     */
    DirectiveCode getDirectiveCode();
}
