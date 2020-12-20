package org.arch.project.event.service;

import org.arch.project.event.api.FulfilRequest;
import org.arch.project.event.api.FulfilTicket;
import org.arch.project.event.core.FulfilOrderConvert;
import org.arch.project.event.event.FulfilEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author lait.zhang@gmail.com
 * @Tel 15801818092
 * @date 11/16/2020 4:54 PM
 * @description: 我们在传统项目中往往各个业务逻辑之间耦合性较强，因为我们在service都是直接引用的关联service或者jpa来作为协作处理逻辑，
 * 然而这种方式在后期更新、维护性难度都是大大提高了。然而我们采用事件通知、事件监听形式来处理逻辑时耦合性则是可以降到最小。
 */
@Service
public class FulfilService {

    /**
     * 事件发布是由ApplicationContext对象管控的，
     * 我们发布事件前需要注入ApplicationContext对象调用publishEvent方法完成事件发布。
     */
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    FulfilOrderConvert fulfilOrderConver;

    /**
     * 根据履约单，监听各自的事件：核心作业、仓内作业、仓外作业、末端作业等...
     * @param fulfilTicket
     */
    private void fulfil(FulfilTicket fulfilTicket) {
        //发布履约事件
        applicationContext.publishEvent(new FulfilEvent(this, fulfilTicket));
    }

    /**
     * 生成履约作业并建立作业间的关联关系
     * @param fulfilRequest
     */
    public void fulfil(FulfilRequest fulfilRequest) {
        FulfilTicket fulfilTicket = fulfilOrderConver.convert(fulfilRequest);

        // 1.生成并创建作业(业务逻辑)

        // 2.落库并记录(关系型数据库#mysql/其他?)


        this.fulfil(fulfilTicket);
    }
}
