package org.arch.project.event.event;

import org.arch.project.event.api.FulfilTicket;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;


/**
 * @author lait.zhang@gmail.com
 * @Tel 15801818092
 * @date: 11/16/2020 4:11 PM
 * @description:履约事件
 */
@Getter
@Setter
public class FulfilEvent extends ApplicationEvent {

    // 履约单
    FulfilTicket fulfilTicket;


    public FulfilEvent(Object source, FulfilTicket fulfilTicket) {
        super(source);
        this.fulfilTicket = fulfilTicket;
    }
}
