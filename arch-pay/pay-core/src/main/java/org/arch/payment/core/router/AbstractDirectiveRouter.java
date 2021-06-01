package org.arch.payment.core.router;

import org.arch.framework.id.IdService;
import org.arch.sms.api.SmsApi;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 12/30/2020 5:56 PM
 */
public abstract class AbstractDirectiveRouter {

    //    @Autowired
//    protected IPayBindedRecordService bindedRecordervice;
//    @Autowired
//    protected IPayAppMerchantChannelService appMerchantChannelService;
//    @Autowired
//    protected IPayMerchantChannelService merchantChannelService;
//    @Autowired
//    protected IPayChannelBankService channelBankService;
//    @Autowired
//    protected IPayDirectiveService directiveService;
//    @Autowired
//    protected IPayChannelDirectiveRecordService channelDirectiveRecordService;
//    @Autowired
//    protected ApplicationContext applicationContext;
//

//    @Autowired
//    PayStrategyService payStrategyService;
    @Autowired
    protected IdService idService;
    //@Autowired
    //protected RedisService redisService;
    @Autowired
    protected SmsApi smsApi;


}


