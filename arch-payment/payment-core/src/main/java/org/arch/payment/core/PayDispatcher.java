package org.arch.payment.core;

import org.arch.framework.id.IdService;
import org.arch.payment.sdk.Directive;
import org.arch.payment.sdk.DirectiveCode;
import org.arch.payment.sdk.DirectiveRouter;
import org.arch.payment.sdk.PayRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 12/30/2020 12:46 PM
 */
public class PayDispatcher {
    @Autowired
    private PayChannel payChannel;
    @Autowired
    private IdService idService;


    public void dispatch(PayRequest payRequest) {
        // 获取指令码
        DirectiveCode directiveCode = payRequest.getDirectiveCode();
        // 全局分布式ID
        String id = idService.generateId(directiveCode.getIdKey());
        // 获取当前指令码对应的指令集
        List<Directive> directives = payChannel.getPayDirectives(directiveCode);
        // 获取当前指令码对应的指令路由
        DirectiveRouter directiveRouter = payChannel.getDirectiveRouter(directiveCode);
        // 路由指令
        directiveRouter.routing(directives);
    }
}
