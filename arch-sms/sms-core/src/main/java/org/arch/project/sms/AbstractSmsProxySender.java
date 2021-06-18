//package org.arch.project.sms;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.annotation.Resource;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Slf4j
//public abstract class AbstractSmsProxySender implements SmsProxySendable {
//
//    @Autowired
//    protected SmsProxyService smsProxyService;
//
//    @Resource
//    protected RabbitTemplate rabbitTemplate;
//
//    /**
//     * 通道ID锁
//     */
//    private final Map<String, Object> passageLockMonitor = new ConcurrentHashMap<>();
//
//    private void addPassageLockMonitor(String passageId) {
//        passageLockMonitor.putIfAbsent(passageId, new Object());
//    }
//
//    /**
//     * TODO 获取直连对象的代理信息（不同通道间初始化互不影响）
//     *
//     * @param parameter
//     * @return
//     */
//    protected Object getSmManageProxy(SmsPassageParameter parameter) {
//        addPassageLockMonitor(parameter.getPassageId());
//
//        synchronized (passageLockMonitor.get(parameter.getPassageId())) {
//            if (smsProxyManageService.isProxyAvaiable(parameter.getPassageId())) {
//                return SmsProxyManagerTemplate.getManageProxy(parameter.getPassageId());
//            }
//
//            boolean isOk = smsProxyManageService.startProxy(parameter);
//            if (!isOk) {
//                return null;
//            }
//
//            // 重新初始化后将错误计数器归零
//            smsProxyManageService.clearSendErrorTimes(parameter.getPassageId());
//
//            return SmsProxyManagerTemplate.GLOBAL_PROXIES.get(parameter.getPassageId());
//        }
//    }
//
//    /**
//     * TODO 断开直连协议连接
//     *
//     * @param passageId
//     */
//    public void onTerminate(String passageId) {
//        smsProxyManageService.stopProxy(passageId);
//    }
//}
