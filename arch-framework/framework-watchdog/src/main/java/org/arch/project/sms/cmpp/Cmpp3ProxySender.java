package org.arch.project.sms.cmpp;

import com.alibaba.fastjson.JSON;
import com.huawei.insa2.comm.cmpp.message.CMPPMessage;
import com.huawei.insa2.comm.cmpp30.message.CMPP30DeliverMessage;
import com.huawei.insa2.comm.cmpp30.message.CMPP30SubmitMessage;
import com.huawei.insa2.comm.cmpp30.message.CMPP30SubmitRepMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.arch.project.sms.AbstractSmsProxySender;
import org.arch.project.sms.RequestTemplateHandler;
import org.arch.project.sms.Sender;
import org.arch.project.sms.SmsPassageParameter;
import org.arch.project.sms.SmsProxySendable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
@Slf4j
@Component
public class Cmpp3ProxySender extends AbstractSmsProxySender implements SmsProxySendable {

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }
    /**
     * 长短信消息ID映射（因为长短信会有多次消息报告回执，但实际只需要解析任何一条有意义的即可） KEY: 因为长短信需要多次代理发送交互，所以产生多次MSG_ID，顾KEY存每一次的消息ID
     * VALUE：存储的是发送给HSSMS应用的msgId,即只存长短信中的第一次索引对应的msgId
     */
    // private static List<String> ignoredMsgIds = new ArrayList<>();

    /**
     * 对于Proxy分发短信的接收
     *
     * @param cmppMessage
     */
    public void doProcessDeliverMessage(CMPPMessage cmppMessage) {
        if (cmppMessage == null) {
            return;
        }

        CMPP30DeliverMessage deliverMsg = (CMPP30DeliverMessage) cmppMessage;
        // deliverMsg.getRegisteredDeliver()等于0时，为上行短信；等于1时为状态报告
        if (deliverMsg.getRegisteredDeliver() == 0) {
            moReceive(deliverMsg);
        } else {
            mtDeliver(deliverMsg);
        }
    }


    /**
     * TODO 转义回执的消息ID（包括下行状态和上行回复信息）
     *
     * @param bt
     * @return
     */
    public static String getMsgId(byte[] bt) {
        int mask = 0xf0;
        int month = (bt[0] & mask) >> 4;

        mask = 0xF;
        int date = (bt[0] & mask) << 1;
        mask = 0x80;
        int date2 = bt[1] >> 7;
        date = (date & (date2 | 0x1E));

        // 01111100
        mask = 0x7C;
        int hour = (bt[1] & mask) >> 2;

        mask = 0x3;
        int minute = (bt[1] & mask) << 4;
        minute |= (bt[2] & 0xF0) >> 4;

        mask = 0xf;
        int second = (bt[2] & mask) << 2;
        second |= (bt[3] & 0xff) >> 6;

        mask = 0x3F;
        int Msg_Id = (bt[3] & mask) << 16;
        Msg_Id |= (bt[4] & 0xFF) << 8;
        Msg_Id |= (bt[5] & 0xFF);

        mask = 0xFF;
        int seq_id = (bt[6] & mask) << 8;
        seq_id |= (bt[7] & mask);

        StringBuffer sb = new StringBuffer();
        sb.append(month).append(date + 1).append((hour < 10) ? "0" + hour : hour).append(minute < 10 ? "0" + minute : minute).append(second < 10 ? "0"
                + second : second).append(Msg_Id).append(seq_id);
        return sb.toString();
    }

    /**
     * 长短信计数器
     */
    private final AtomicInteger longTextCounter = new AtomicInteger(1);

    /**
     * 短信内容转换为 字节数。普通短信 GBK编码。长短信 UCS2编码
     *
     * @param message
     * @return
     * @throws UnsupportedEncodingException
     */
    private List<byte[]> getLongByte(String message, Integer feeByWords) throws UnsupportedEncodingException {
        List<byte[]> list = new ArrayList<byte[]>();
        byte[] messageUCS2 = message.getBytes("UnicodeBigUnmarked");
        // int messageGBKLen = messageGBK.length;// 短信长度
        // 超过70字符以长短信发送
        if (message.length() > 70) {
            // if (messageGBKLen > maxMessageGBKLen) {
            // 如果字节数大于 GBK，则使用UCS2编码以长短信发送
            // byte[] messageUCS2 = message.getBytes("UnicodeBigUnmarked");
            int messageUCS2Len = messageUCS2.length;
            int yushu = messageUCS2Len % (CmppConstant.MAX_MESSAGE_UCS2_LENGTH - 6);
            int add = 0;
            if (yushu > 0) {
                add = 1;
            }
            int messageUCS2Count = messageUCS2Len / (CmppConstant.MAX_MESSAGE_UCS2_LENGTH - 6) + add;// 长短信分为多少条发送

            byte[] tp_udhiHead = new byte[6];
            tp_udhiHead[0] = 0x05;
            tp_udhiHead[1] = 0x00;
            tp_udhiHead[2] = 0x03;
            tp_udhiHead[3] = (byte) longTextCounter.get();// 0x0A
            tp_udhiHead[4] = (byte) messageUCS2Count;
            tp_udhiHead[5] = 0x01;// 默认为第一条
            if (longTextCounter.incrementAndGet() == 256) {
                longTextCounter.set(1);
            }

            for (int i = 0; i < messageUCS2Count; i++) {
                tp_udhiHead[5] = (byte) (i + 1);
                byte[] msgContent;
                if (i != messageUCS2Count - 1) {// 不为最后一条
                    msgContent = byteAdd(tp_udhiHead, messageUCS2, i * (CmppConstant.MAX_MESSAGE_UCS2_LENGTH - 6),
                            (i + 1) * (CmppConstant.MAX_MESSAGE_UCS2_LENGTH - 6), i, feeByWords);
                    list.add(msgContent);
                } else {
                    msgContent = byteAdd(tp_udhiHead, messageUCS2, i * (CmppConstant.MAX_MESSAGE_UCS2_LENGTH - 6),
                            messageUCS2Len, i, feeByWords);
                    list.add(msgContent);
                }
                // log.debug("msgContent "+new
                // String(msgContent,"UnicodeBigUnmarked"));
            }
        } else {
            if (isSignatureBelongtoPointCustomer(feeByWords)) {
                list.add(message.substring(UserBalanceConstant.WORDS_SIZE_PER_NUM - feeByWords).getBytes("GBK"));
            } else {
                list.add(message.getBytes("GBK"));
            }

        }
        return list;
    }

    /**
     * TODO 是否为一客一签模式
     *
     * @param feeByWords
     * @return
     */
    private static boolean isSignatureBelongtoPointCustomer(Integer feeByWords) {
        return feeByWords != null && feeByWords != UserBalanceConstant.WORDS_SIZE_PER_NUM;
    }

    private byte[] byteAdd(byte[] tpUdhiHead, byte[] messageUCS2, int i, int j, int index, Integer feeByWords)
            throws UnsupportedEncodingException {

        // 如果为一客一签并且为第一条短信则预留签名字节数
        if (isSignatureBelongtoPointCustomer(feeByWords) && index == 0) {
            int signLength = (UserBalanceConstant.WORDS_SIZE_PER_NUM - feeByWords) * 2;
            i = i + signLength;
            // 移动872通道需要 第一段预留 字节,截取去掉 签名字节
            byte[] msgb = new byte[j - i + 6];
            System.arraycopy(tpUdhiHead, 0, msgb, 0, 6);
            System.arraycopy(messageUCS2, i, msgb, 6, j - i);
            return msgb;
        }

        byte[] msgb = new byte[j - i + 6];
        System.arraycopy(tpUdhiHead, 0, msgb, 0, 6);
        System.arraycopy(messageUCS2, i, msgb, 6, j - i);
        return msgb;
    }

    /**
     * TODO 发送短信接口
     *
     * @param parameter 通道参数信息
     * @param extNumber 客户扩展号码
     * @param mobile 手机号码（支持多个）
     * @param content 短信内容
     * @return
     */
    public List<ProviderSendResponse> send(SmsPassageParameter parameter, String extNumber, String mobile,
                                           String content) {
        try {
            TParameter tparameter = RequestTemplateHandler.parse(parameter.getParams());
            if (MapUtils.isEmpty(tparameter)) {
                throw new RuntimeException("CMPP 参数信息为空");
            }

            Cmpp3ManageProxy cmppManageProxy = (Cmpp3ManageProxy)getSmManageProxy(parameter);
            if (cmppManageProxy == null) {
                log.error("CMPP3代理获取失败，手机号码：{}， 短信内容：{}，扩展号码：{}", mobile, content, extNumber);

                return null;
            }

            // 接入号码（如果扩展号码不为空，则加入扩展号码）
            String srcTerminalId = tparameter.getString("src_terminal_id")
                    + (StringUtils.isEmpty(extNumber) ? "" : extNumber);

            long startTime = System.currentTimeMillis();
            // 获取发送回执信息
            CMPP30SubmitRepMessage submitRepMsg = getCMPPSubmitResponseMessage(tparameter.getString("service_id"),
                    tparameter.getString("spid"),
                    StringUtils.isEmpty(tparameter.getString("msg_fmt")) ? CmppConstant.MSG_FMT_GBK : Integer.parseInt(tparameter.getString("msg_fmt")),
                    StringUtils.isEmpty(tparameter.getString("mobile")) ? "000" : tparameter.getString("mobile"),
                    srcTerminalId, mobile, content, "",
                    cmppManageProxy,
                    parameter.getFeeByWords());
            if (submitRepMsg == null) {
                log.error("CMPP3SubmitRepMessage 网关提交信息为空");
                smsProxyManageService.plusSendErrorTimes(parameter.getPassageId());
                return null;
            }

            log.info("CMPP3 通道ID ["+parameter.getPassageId()+"]，提交耗时：" + (System.currentTimeMillis() - startTime) + "ms，回执内容：{}", submitRepMsg.toString());

            List<ProviderSendResponse> list = new ArrayList<>();
            ProviderSendResponse response = new ProviderSendResponse();

            if (submitRepMsg.getResult() == MessageSubmitStatus.SUCCESS.getCode()) {
                // 发送成功清空
                smsProxyManageService.clearSendErrorTimes(parameter.getPassageId());
                response.setMobile(mobile);
                response.setStatusCode(submitRepMsg.getResult() + "");
                response.setSid(getMsgId(submitRepMsg.getMsgId()));
                response.setSuccess(true);
                response.setRemark(String.format("{msgId:%s, sequenceId:%d, commandId:%d}", response.getSid(),
                        submitRepMsg.getSequenceId(), submitRepMsg.getCommandId()));

                list.add(response);
            } else {
                response.setMobile(mobile);
                response.setStatusCode(submitRepMsg.getResult() + "");
                response.setSid(getMsgId(submitRepMsg.getMsgId()));
                response.setSuccess(false);
                response.setRemark(String.format("{result:%d, sequenceId:%d, commandId:%d}", submitRepMsg.getResult(),
                        submitRepMsg.getSequenceId(), submitRepMsg.getCommandId()));

                list.add(response);
            }

            return list;
        } catch (Exception e) {
            // 累加发送错误次数
            smsProxyManageService.plusSendErrorTimes(parameter.getPassageId());

            log.error("CMPP3发送失败", e);
            throw new RuntimeException("CMCP3发送失败");
        }
    }

    /**
     * TODO 组装待提交短信CMPP结构体数据
     *
     * @param serviceId 业务类型
     * @param spid 企业代码
     * @param chargeNumber 计费号码
     * @param msgFmt 消息编码方式（默认GBK）
     * @param srcTerminalId 接入号（包含用于配置的扩展号码）
     * @param mobile 接收短信手机号码
     * @param content 短信内容
     * @param reserve 扩展项，用于自定义内容
     * @param feeByWords 首条短信计费字数（专用于一客一签判断）
     * @return
     * @throws IOException
     */
    private CMPP30SubmitRepMessage getCMPPSubmitResponseMessage(String serviceId, String spid, int msgFmt,
                                                                String chargeNumber, String srcTerminalId,
                                                                String mobile, String content, String reserve,
                                                                Cmpp3ManageProxy cmppManageProxy, Integer feeByWords)
            throws IOException {

        // 存活有效期（2天）
        Date validTime = new Date(System.currentTimeMillis() + (long) 0xa4cb800);
        // 定时发送时间
        Date atTime = null;// new Date(System.currentTimeMillis() + (long)//
        // 0xa4cb800); //new Date();

        // 是否属于长短信
        // boolean isLongtext = false;
        int tpUdhi = CmppConstant.TP_UDHI;
        // 全都改成以 UTF 16 格式提交
        List<byte[]> contentList = getLongByte(content, feeByWords);
        if (contentList.size() > 1) {
            // 长短信格式发送 增加协议头 改变编码方式
            tpUdhi = 1;
            msgFmt = CmppConstant.MSG_FMT_UCS2;
            // isLongtext = true;
        }

        CMPP30SubmitMessage submitMsg = null;
        CMPP30SubmitRepMessage submitRepMsg = null;
        for (int index = 1; index <= contentList.size(); index++) {
            submitMsg = getCMPPSubmitMessage(contentList.size(), index, serviceId, tpUdhi, msgFmt, spid, validTime,
                    atTime, chargeNumber, srcTerminalId,
                    mobile.split(MobileNumberCatagoryUtils.DATA_SPLIT_CHARCATOR),
                    contentList.get(index - 1), reserve);

            CMPP30SubmitRepMessage repMsg = (CMPP30SubmitRepMessage) cmppManageProxy.send(submitMsg);
            if (index == 1) {
                // 以长短信 拆分发送时，目前状态报告的 msgid 是 拆分后第一条的 msgid
                submitRepMsg = repMsg;
            } else if (submitRepMsg == null) {
                submitRepMsg = repMsg;
            }

            // 存入单条长短信对应关系数据
            // if (isLongtext && index > 1) {
            // ignoredMsgIds.add(getMsgId(repMsg.getMsgId()));
            // }
        }

        return submitRepMsg;
    }

    /**
     * TODO 组装CMPP提交信息
     *
     * @param pkTotal 相同msg_Id消息总条数
     * @param pkNumber 相同msg_Id的消息序号
     * @param serviceId 业务类型
     * @param tpUdhi GSM协议类型
     * @param msgFmt 消息格式
     * @param spid
     * @param validTime
     * @param atTime
     * @param chargeNumber 计费号码
     * @param srcTerminalId 源终端MSISDN号码（包含扩展号码）
     * @param mobiles 手机号码
     * @param msgContent 短信内容
     * @param reserve 保留
     * @return
     */
    private CMPP30SubmitMessage getCMPPSubmitMessage(int pkTotal, int pkNumber, String serviceId, int tpUdhi,
                                                     int msgFmt, String spid, Date validTime, Date atTime,
                                                     String chargeNumber, String srcTerminalId, String[] mobiles,
                                                     byte[] msgContent, String reserve) {

        return new CMPP30SubmitMessage(pkTotal, pkNumber, CmppConstant.REGISTERED_DELIVERY, CmppConstant.MSG_LEVEL,
                serviceId, CmppConstant.FEE_USERTYPE, chargeNumber,
                CmppConstant.FEE_TERMINAL_TYPE, CmppConstant.TP_PID, tpUdhi, msgFmt, spid,
                CmppConstant.FEE_TYPE, CmppConstant.FEE_CODE, validTime, atTime, srcTerminalId,
                mobiles, CmppConstant.DEST_TERMINAL_TYPE, msgContent, CmppConstant.LINK_ID);
    }

    /**
     * TODO CMPP下行状态报文
     *
     * @param report
     * @return
     */
    public void mtDeliver(CMPP30DeliverMessage report) {
        if (report == null) {
            return;
        }

        try {
            String msgId = getMsgId(report.getStatusMsgId());
            if (StringUtils.isEmpty(msgId)) {
                return;
            }

            if (isInIgnoredMsgId(msgId)) {
                log.info("CMPP3状态报告, msgId: {} 长短信已处理，忽略", msgId);
                return;
            }

            // 发送时手机号码拼接86，回执需去掉86前缀
            String mobile = report.getSrcterminalId();
            if (mobile != null && mobile.startsWith("86")) {
                mobile = mobile.substring(2);
            }

            List<SmsMtMessageDeliver> list = new ArrayList<>();

            SmsMtMessageDeliver response = new SmsMtMessageDeliver();
            response.setMsgId(msgId);
            response.setMobile(mobile);
            response.setCmcp(CMCP.local(response.getMobile()).getCode());
            response.setStatusCode(report.getStat());
            response.setStatus((StringUtils.isNotEmpty(report.getStat())
                    && CmppConstant.COMMON_MT_STATUS_SUCCESS_CODE.equalsIgnoreCase(report.getStat()) ? DeliverStatus.SUCCESS.getValue() : DeliverStatus.FAILED.getValue()));
            response.setDeliverTime(DateUtils.getDate());
            response.setCreateTime(new Date());
            response.setRemarks(String.format("DestnationId:%s,ServiceId:%s", report.getDestnationId(),
                    report.getServiceId()));

            list.add(response);

            if (CollectionUtils.isNotEmpty(list)) {
                // 发送异步消息
                rabbitTemplate.convertAndSend(RabbitConstant.MQ_SMS_MT_WAIT_RECEIPT, list);
            }

            // 解析返回结果并返回
        } catch (Exception e) {
            log.error("CMPP3状态回执解析失败：{}", e);
            throw new RuntimeException("CMPP3状态回执解析失败");
        }
    }

    /**
     * TODO 检查是否是长短信 消息ID模式, 返回null则可跳过本次执行
     *
     * @param msgId
     * @return
     */
    private boolean isInIgnoredMsgId(String msgId) {
        if (StringUtils.isEmpty(msgId)) {
            return true;
        }

        // 如果长短信消息ID映射关系中不存在，则证明非长短信模式，直接返回
        // if (ignoredMsgIds.contains(msgId)) {
        // ignoredMsgIds.remove(msgId);
        // return true;
        // }

        return false;
    }

    /**
     * TODO CMPP上行报文回执
     *
     * @param report
     */
    public void moReceive(CMPP30DeliverMessage report) {
        if (report == null) {
            return;
        }

        try {

            log.info("CMPP3上行报告数据: {}", report);

            List<SmsMoMessageReceive> list = new ArrayList<>();

            // 发送时手机号码拼接86，回执需去掉86前缀
            String mobile = report.getSrcterminalId();
            if (mobile != null && mobile.startsWith("86")) {
                mobile = mobile.substring(2);
            }

            SmsMoMessageReceive response = new SmsMoMessageReceive();
            response.setPassageId(null);
            response.setMsgId(getMsgId(report.getMsgId()));
            response.setMobile(mobile);
            response.setDestnationNo(report.getDestnationId());
            response.setReceiveTime(DateUtils.getDate());
            response.setCreateTime(new Date());
            // 编号方式
            if (CmppConstant.MSG_FMT_UCS2 == report.getMsgFmt()) {
                response.setContent(new String(report.getMsgContent(), "UTF-16"));
            } else {
                response.setContent(new String(report.getMsgContent(), "GBK"));
            }

            list.add(response);

            if (CollectionUtils.isNotEmpty(list)) {
                rabbitTemplate.convertAndSend(RabbitConstant.MQ_SMS_MO_RECEIVE, list);
            }

        } catch (Exception e) {
            log.error("CMPP3上行解析失败 {}", JSON.toJSONString(report), e);
            throw new RuntimeException("CMPP3上行解析失败");
        }
    }
}
