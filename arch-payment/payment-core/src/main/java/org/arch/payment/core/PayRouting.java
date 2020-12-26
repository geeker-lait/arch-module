package org.arch.payment.core;

import org.arch.framework.id.IdKey;
import org.arch.framework.id.IdService;
import org.arch.payment.sdk.*;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @Author lait.zhang@gmail.com
 * @Tel 15801818092
 * @Date 12/23/2019
 * @Description ${Description}
 */
@Service
public class PayRouting implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    private IdService idService;

    private Set<DirectiveRouting> directiveRoutings;

    public PayResponse routing(PayRequest payRequest) throws IllegalAccessException, InstantiationException {
        DirectiveName directiveName = payRequest.getDirectiveName();

        DirectiveRequest directiveRequest = directiveName.getDirectiveRequest().newInstance();
        directiveRequest.getRequestId();

        idService.generateId(IdKey.PAY_BINDCARD_ID);

        DirectiveRouting directiveable = directiveRoutings.stream()
                .filter(directiveRouting -> directiveRouting.getDirectiveName().equals(directiveName.name()))
                .findFirst().get();

        //DirectiveRouting directiveable = applicationContext.getBean(directiveName.name(), DirectiveRouting.class);
        if (directiveable != null) {
            PayResponse payResponse = directiveable.routing(payRequest);
            return payResponse;
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
