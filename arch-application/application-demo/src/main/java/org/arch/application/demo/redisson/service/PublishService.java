package org.arch.application.demo.redisson.service;

import org.arch.application.demo.redisson.Consts;
import org.arch.application.demo.redisson.MyObjectDTO;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublishService {
    @Autowired
    private RedissonClient redissonClient;

    //发布
    public long publish(MyObjectDTO myObjectDTO) {
        RTopic rTopic = redissonClient.getTopic(Consts.TopicName);
        return rTopic.publish(myObjectDTO);
    }

}
