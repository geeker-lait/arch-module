package org.arch.project.event.dto;

import org.arch.project.event.api.FulfilOrder;
import org.arch.project.event.api.FulfilRequest;
import org.arch.project.event.api.FulfilTicket;
import lombok.Data;

/**
 * @author lait.zhang@gmail.com
 * @Tel 15801818092
 * @date 11/16/2020 4:56 PM
 * @description: TODO
 */
@Data
public class OmsOrder implements FulfilOrder {
    @Override
    public FulfilTicket convert(FulfilRequest fulfilRequest) {
        return null;
    }
    //

}
