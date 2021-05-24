package org.arch.project.event.dto;

import org.arch.project.event.api.FulfilOrder;
import org.arch.project.event.api.FulfilRequest;
import org.arch.project.event.api.FulfilTicket;

/**
 * @author lait.zhang@gmail.com
 * @Tel 15801818092
 * @date 11/16/2020 6:05 PM
 * @description: TODO
 */
public class ThirdpartyOrder implements FulfilOrder {
    @Override
    public FulfilTicket convert(FulfilRequest fulfilRequest) {

        FulfilTicket fulfilTicket = new FulfilTicket();
        fulfilRequest.getOrderParams().getString("");

        return null;
    }
}
