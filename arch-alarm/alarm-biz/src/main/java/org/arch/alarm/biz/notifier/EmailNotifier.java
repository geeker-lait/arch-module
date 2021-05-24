package org.arch.alarm.biz.notifier;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.arch.alarm.api.dto.AlarmChannelDto;
import org.arch.alarm.api.dto.AlarmNoticeDto;
import org.arch.alarm.api.dto.AlarmTemplateDto;
import org.arch.alarm.api.enums.MsgNotifier;
import org.arch.alarm.api.enums.UserTyp;
import org.arch.alarm.api.pojo.AlarmMsgData;
import org.arch.alarm.api.pojo.ToEmail;
import org.arch.alarm.biz.MsgNoticeable;
import org.arch.alarm.biz.notifier.mail.MailService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 5/8/2021 6:25 PM
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EmailNotifier extends AbstractMsgNotifier implements MsgNoticeable {
    private final MailService mailService;

    @Override
    public MsgNotifier getNotifier() {
        return MsgNotifier.EMAIL;
    }

    @Override
    public void notice(AlarmTemplateDto alarmTemplateDto, AlarmChannelDto alarmChannelDto, AlarmNoticeDto and, AlarmMsgData alarmMsgData) {
        log.info("邮件通知.....");


        String conetent = alarmTemplateDto.getContent();
        // ums 根据仓店号 和 用户id 获取用户通信信息
        //UserCommunication userCommunication = getUserCommunication(alarmNoticeDto.getStoreNo(), alarmNoticeDto.getUserId());
        StrSubstitutor strSubstitutor = new StrSubstitutor(alarmMsgData.toMap());
        String nc = strSubstitutor.replace(conetent);


        ToEmail toEmail = new ToEmail();
        toEmail.setMailFrom(alarmChannelDto.getAppKey());
        // 别称设置为:YH-仓店号
        toEmail.setMailFromNick("YH-" + alarmMsgData.getStoreNo());
        // 设置为预警模板类容,替换后的内容
        toEmail.setContent(nc);
        // 设置主题为 规则名称
        toEmail.setSubject(alarmTemplateDto.getRegName());
        // 用户邮箱
        List<String> emails = new ArrayList<>();
        Integer userTyp = and.getUserTyp();
        // 用户
        if (userTyp == UserTyp.USER.getTyp()) {
            //emails.add(getUserCommunication(and.getStoreNo(), and.getUserId()).getEmail());
        }
        //用户组
        if (userTyp == UserTyp.GROUP.getTyp()) {
            //emails.addAll(getUserGroupCommunication(and.getStoreNo(), and.getUserId()).stream().map(UserCommunication::getEmail).filter(StringUtils::isNotBlank).collect(Collectors.toList()));
        }
        // todo 增加收件人
        toEmail.setMailTos(emails);
        // 为每个仓店切换发送账号,否则使用默认邮箱发送
        if (StringUtils.isNotBlank(alarmChannelDto.getChannelUrl()) &&
                StringUtils.isNotBlank(alarmChannelDto.getAppKey()) &&
                StringUtils.isNotBlank(alarmChannelDto.getSecrtKey())) {
            mailService.changeMailAccount(alarmChannelDto.getChannelUrl(), alarmChannelDto.getAppKey(), alarmChannelDto.getSecrtKey());
        }
        mailService.sendSimpleMail(toEmail);
    }
}
