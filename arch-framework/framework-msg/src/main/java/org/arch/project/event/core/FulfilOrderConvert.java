package org.arch.project.event.core;

import org.arch.project.event.api.FulfilOrder;
import org.arch.project.event.api.FulfilRequest;
import org.arch.project.event.api.FulfilTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author lait.zhang@gmail.com
 * @Tel 15801818092
 * @date 11/23/2020 11:05 AM
 * @description: TODO
 */
@Component
public class FulfilOrderConvert {
    @Autowired
    private ApplicationContext applicationContext;

    public FulfilTicket convert(FulfilRequest fulfilRequest) {
        FulfilOrder fulfilOrder = applicationContext.getBean(fulfilRequest.getOrderChannel(),FulfilOrder.class);
        return fulfilOrder.convert(fulfilRequest);
    }
}
