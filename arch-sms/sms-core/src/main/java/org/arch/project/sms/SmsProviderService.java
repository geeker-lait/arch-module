//package org.arch.project.sms;
//
//import cn.hutool.core.collection.CollUtil;
//import com.alibaba.fastjson.JSON;
//import com.google.common.util.concurrent.RateLimiter;
//import com.sun.deploy.net.protocol.ProtocolType;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.locks.ReentrantLock;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
///**
// * @author lait.zhang@gmail.com
// * @description: TODO
// * @weixin PN15855012581
// * @date :
// */
//@Slf4j
//@Service
//public class SmsProviderService {
//    @Autowired
//    private List<SmsProxySendable> smsProxySendables;
//    private final static Map<String,SmsProxySendable> SENDER_MAP= new HashMap();
//    @PostConstruct
//    public void init(){
//        SENDER_MAP.putAll(smsProxySendables.stream().collect(Collectors.toMap(s->s.getName(),Function.identity())));
//    }
//
//
//    /**
//     * 默认限流计流数
//     */
//    private static final Integer DEFAULT_RATE_LIMITER_TIMES = 1;
//
//
//    public List<ProviderSendResponse> sendSms(SmsPassageParameter parameter, String mobile, String content, Integer fee,
//                                              String extNumber) throws ExchangeProcessException {
//
//        validate(parameter);
//
//        // 判断是否需要限流
//        if (!isNeedLimitSpeed(parameter.getProtocol())) {
//            return submitData2Gateway(parameter, mobile, content, extNumber);
//        }
//
//        // 获取通道对应的流速设置
//        RateLimiter rateLimiter = getRateLimiter(parameter.getPassageId(), parameter.getPacketsSize());
//        String[] mobiles = mobile.split(MobileNumberCatagoryUtil.DATA_SPLIT_CHARCATOR);
//
//        // 目前HTTP用途并不是很大（因为取决于HTTP自身的瓶颈）
//        List<String[]> packets = recombineMobilesByLimitSpeedInSecond(mobiles,
//                getRateLimiterAmount(parameter.getProtocol(), fee), parameter.getPacketsSize());
//
//        // 如果手机号码同时传递多个，需要对流速进行控制，多余流速的需要分批提交
//        List<ProviderSendResponse> responsesInMultiGroup = new ArrayList<ProviderSendResponse>(packets.size());
//        for (String[] m : packets) {
//            // 设置acquire 当前分包后的数量
//            rateLimiter.acquire(Integer.parseInt(m[0]));
//            List<ProviderSendResponse> reponsePerGroup = submitData2Gateway(parameter, m[1], content, extNumber);
//            if (CollUtil.isEmpty(reponsePerGroup)) {
//                continue;
//            }
//
//            responsesInMultiGroup.addAll(reponsePerGroup);
//        }
//
//        return responsesInMultiGroup;
//    }
//
//    /**
//     * TODO 计算限流总次数 根据协议类型判断多条短信是否以多次限流计数（直连按照计费条数计数，如4条（长短信）计4次，其他协议，如HTTP则按照一次计数）
//     *
//     * @param protocol
//     * @param fee
//     * @return
//     */
//    private static Integer getRateLimiterAmount(String protocol, Integer fee) {
//        // 如果协议类型无法识别或者为空则按照 1次计数
//        if (StringUtils.isEmpty(protocol)) {
//            return DEFAULT_RATE_LIMITER_TIMES;
//        }
//
//        ProtocolType protocolType = ProtocolType.parse(protocol);
//        if (protocolType != null && (protocolType == ProtocolType.CMPP2 || protocolType == ProtocolType.SGIP
//                || protocolType == ProtocolType.SMGP)) {
//            return fee;
//        }
//
//        return DEFAULT_RATE_LIMITER_TIMES;
//    }
//
//    /**
//     * TODO 是否需要限流
//     *
//     * @param protocol
//     * @return
//     */
//    private boolean isNeedLimitSpeed(String protocol) {
//        if (StringUtils.isEmpty(protocol)) {
//            return false;
//        }
//
//        ProtocolType protocolType = ProtocolType.parse(protocol);
//
//        return !(protocolType == null || protocolType == ProtocolType.HTTP || protocolType == ProtocolType.WEBSERVICE);
//    }
//
//    /**
//     * TODO 提交数据到网关（此网关可能是真正网关也可能是上家渠道）
//     *
//     * @param parameter
//     * @param mobile
//     * @param content
//     * @param extNumber
//     * @return
//     */
//    private List<ProviderSendResponse> submitData2Gateway(SmsPassageParameter parameter, String mobile, String content,
//                                                          String extNumber) {
//
//        ProtocolType pt = ProtocolType.parse(parameter.getProtocol());
//        if (pt == null) {
//            log.warn("协议类型不匹配，跳过");
//            return null;
//        }
//
//        List<ProviderSendResponse> list = null;
//        switch (pt) {
//            case HTTP: {
//                list = httpResolver.post(parameter, mobile, content, extNumber);
//                break;
//            }
//            case WEBSERVICE: {
//                break;
//            }
//            case CMPP2: {
//                list = cmppProxySender.send(parameter, extNumber, mobile, content);
//                break;
//            }
//            case CMPP3: {
//                list = cmpp3ProxySender.send(parameter, extNumber, mobile, content);
//                break;
//            }
//            case SGIP: {
//                list = sgipProxySender.send(parameter, extNumber, mobile, content);
//                break;
//            }
//            case SMGP: {
//                list = smgpProxySender.send(parameter, extNumber, mobile, content);
//                break;
//            }
//            default:
//                log.warn("Ignored by protocol is not matched");
//                break;
//        }
//
//        return list;
//    }
//
//    /**
//     * TODO 对提交网关的数据进行1秒内限流
//     *
//     * @param mobiles     手机号码
//     * @param amount      单个手机号码分流计数器数量
//     * @param packetsSize 限流次数
//     * @return 数组：[0]手机号码个数，[1]逗号拼接后的手机号码
//     */
//    public static List<String[]> recombineMobilesByLimitSpeedInSecond(String[] mobiles, Integer amount,
//                                                                      Integer packetsSize) {
//        // 每组手机号码个数 ，分包数/短信条数，如 50/3,则每个包手机号码数
//        int groupMobileSize = packetsSize / amount;
//        // 总手机号码数
//        int totalMobileSize = mobiles.length;
//        // 分组数
//        int groupSize = (totalMobileSize % groupMobileSize == 0) ? (totalMobileSize / groupMobileSize)
//                : (totalMobileSize / groupMobileSize + 1);
//
//        List<String[]> mobileData = new ArrayList<>(groupSize);
//        StringBuilder builder;
//        String[] report;
//        int index;
//        for (int i = 0; i < groupSize; i++) {
//            int roundSize = 0;
//            report = new String[2];
//            builder = new StringBuilder();
//
//            index = i * groupMobileSize;
//            for (int j = 0; j < groupMobileSize && index < totalMobileSize; j++) {
//                builder.append(mobiles[index++]).append(MobileNumberCatagoryUtil.DATA_SPLIT_CHARCATOR);
//                roundSize++;
//            }
//
//            report[0] = (roundSize * amount) + "";
//            report[1] = builder.substring(0, builder.length() - 1);
//
//            // 第0位为本次处理手机号码总个数，第1位为手机号码信息
//            mobileData.add(report);
//        }
//
//        return mobileData;
//    }
//
//    /**
//     * TODO 传递参数前校验
//     *
//     * @param parameter
//     */
//    private void validate(SmsPassageParameter parameter) {
//        if (StringUtils.isEmpty(parameter.getUrl())) {
//            throw new IllegalArgumentException("SmsPassageParameter's url is empty");
//        }
//
//        if (StringUtils.isEmpty(parameter.getParams())) {
//            throw new IllegalArgumentException("SmsPassageParameter's params are empty");
//        }
//
//    }
//
//
//    public List<SmsMtMessageDeliver> receiveMtReport(SmsPassageAccess access, JSONObject report) {
//        try {
//            return httpResolver.deliver(access, report);
//        } catch (Exception e) {
//            log.error("Failed by args - SmsPassageAccess[" + JSON.toJSONString(access) + "], JSONObject["
//                    + report.toJSONString() + "] ", e);
//            throw new ExchangeProcessException(e);
//        }
//    }
//
//
//    public List<SmsMtMessageDeliver> pullMtReport(SmsPassageAccess access) {
//        try {
//            return httpResolver.deliver(access);
//        } catch (Exception e) {
//            log.error("Failed by args - SmsPassageAccess[" + JSON.toJSONString(access) + "]", e);
//            throw new ExchangeProcessException(e);
//        }
//    }
//
//
//    public List<SmsMoMessageReceive> receiveMoReport(SmsPassageAccess access, JSONObject report) {
//        try {
//            return httpResolver.mo(access, report);
//        } catch (Exception e) {
//            log.error("Failed by args - SmsPassageAccess[" + JSON.toJSONString(access) + "], JSONObject["
//                    + report.toJSONString() + "] ", e);
//            throw new ExchangeProcessException(e);
//        }
//    }
//
//    public List<SmsMoMessageReceive> pullMoReport(SmsPassageAccess access) {
//        try {
//            return httpResolver.mo(access);
//        } catch (Exception e) {
//            log.error("Failed by args - SmsPassageAccess[" + JSON.toJSONString(access) + "]", e);
//            throw new ExchangeProcessException(e);
//        }
//    }
//
//    /**
//     * TODO 获取限速信息，基于令牌分桶算法
//     *
//     * @param passageId
//     * @param speed
//     * @return
//     */
//    private static RateLimiter getRateLimiter(Integer passageId, Integer speed) {
//        RateLimiter limiter = SmsProxyManagerTemplate.GLOBAL_RATE_LIMITERS.get(passageId);
//        if (limiter == null) {
//            ReentrantLock reentrantLock = new ReentrantLock();
//            reentrantLock.tryLock();
//            try {
//                limiter = RateLimiter.create((speed == null || speed == 0) ? SmsProxyManagerTemplate.DEFAULT_LIMIT_SPEED : speed);
//                SmsProxyManagerTemplate.GLOBAL_RATE_LIMITERS.put(passageId, limiter);
//            } finally {
//                reentrantLock.unlock();
//            }
//        }
//
//        return limiter;
//    }
//}
