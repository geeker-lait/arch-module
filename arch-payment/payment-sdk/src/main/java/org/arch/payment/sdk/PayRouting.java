package org.arch.payment.sdk;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @Author lait.zhang@gmail.com
 * @Tel 15801818092
 * @Date 12/23/2019
 * @Description ${Description}
 */
@Service
public class PayRouting implements ApplicationContextAware {
    private ApplicationContext applicationContext;


    public PayResponse routing(PayRequest payRequest) {
        DirectiveName directiveName = payRequest.getDirectiveName();
        DirectiveRouting directiveable = applicationContext.getBean(directiveName.name(), DirectiveRouting.class);
        PayResponse payResponse = directiveable.routing(payRequest);
        return payResponse;

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
