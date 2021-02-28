package org.arch.project.sms;

import javafx.concurrent.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
@Slf4j
@Service
public class SmsSendService {


//    @Resource(name = "smsRabbitTemplate")
//    private RabbitTemplate smsRabbitTemplate;

    /**
     *
     * TODO 发送短信
     *
     * @param smsSendDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void sendMessage(Object smsSendDTO) {

    }

    /**
     * 提交任务到队列
     *
     * @param task 主任务
     * @return 消息ID
     */
    @Transactional(rollbackFor = Exception.class)
    public long joinTask2Queue(Task task, String userId) {
       return 0;
    }
}
