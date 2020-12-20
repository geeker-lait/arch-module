package org.arch.project.event.api;

/**
 * @author lait.zhang@gmail.com
 * @Tel 15801818092
 * @date 11/16/2020 7:25 PM
 * @description: TODO
 */
public interface FulfilOrder {

    FulfilTicket convert(FulfilRequest fulfilRequest);

}
