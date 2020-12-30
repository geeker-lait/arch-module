package org.arch.payment.core;

import org.arch.payment.sdk.Directive;
import org.arch.payment.sdk.DirectiveCode;
import org.arch.payment.sdk.DirectiveRouter;
import org.arch.payment.sdk.PayRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author lait.zhang@gmail.com
 * @description: 支付路由
 * @weixin PN15855012581
 * @date 12/30/2020 12:46 PM
 */
@Service
public class PayDispatcher {
    @Autowired
    private ApplicationContext applicationContext;
    private static final Map<DirectiveCode, List<Directive>> directiveListMap = new HashMap<>();
    private static final Map<DirectiveCode, DirectiveRouter> directiveRouterMap = new HashMap<>();

    /**
     * 初始化指令码对应的指令集合
     */
    @PostConstruct
    public void init() {
        Arrays.stream(DirectiveCode.values()).forEach(directiveCode -> {
            directiveListMap.put(directiveCode, applicationContext.getBeansOfType(directiveCode.getDirectiveTyp()).values().stream().collect(Collectors.toList()));
            directiveRouterMap.putAll(applicationContext.getBeansOfType(DirectiveRouter.class).values().stream().collect(Collectors.toMap(DirectiveRouter::getDirectiveCode, Function.identity())));
        });
    }


    /**
     * 根据指令码从支付通道中得到一个指令路由
     *
     * @param directiveCode
     * @return
     */
    public DirectiveRouter getDirectiveRouter(DirectiveCode directiveCode) {
        return directiveRouterMap.get(directiveCode);
    }

    /**
     * 根据指令码从支付通道中得到一个指令集合
     *
     * @param directiveCode
     * @return
     */
    public List<Directive> getPayDirectives(DirectiveCode directiveCode) {
        List<Directive> directives = directiveListMap.get(directiveCode);
        return directives;
    }

    public void dispatch(PayRequest payRequest) {
        // 获取指令码
        DirectiveCode directiveCode = payRequest.getDirectiveCode();
        // 获取当前指令码对应的指令路由
        DirectiveRouter directiveRouter = this.getDirectiveRouter(directiveCode);
        // 获取当前指令码对应的指令集
        List<Directive> directives = this.getPayDirectives(directiveCode);
        // 路由指令
        directiveRouter.routing(directives);
    }
}
