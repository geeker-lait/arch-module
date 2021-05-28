package org.arch.auth.sso.mobilelogin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.dcenter.ums.security.core.api.validate.code.ValidateCode;
import top.dcenter.ums.security.core.api.validate.code.sms.SmsCodeSender;
import top.dcenter.ums.security.core.auth.properties.ValidateCodeProperties;
import top.dcenter.ums.security.core.util.ValidateCodeUtil;

import static java.util.Objects.nonNull;

/**
 * 验证码发送实现。
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.14 20:05
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ArchSmsCoderSender implements SmsCodeSender {

    private final ValidateCodeProperties validateCodeProperties;

    @Override
    public boolean sendSms(String mobile, ValidateCode validateCode) {
        // TODO: 2021.5.14 待实现
        log.info("手机号：{}, 验证码：{}, {} 秒后失效", mobile, validateCode.getCode(), validateCode.getExpireIn());
        return true;
    }

    @Override
    public ValidateCode getCode() {
        ValidateCodeProperties.SmsCodeProperties smsCodeProp = this.validateCodeProperties.getSms();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String mobile = null;
        if (nonNull(requestAttributes)) {
            mobile = requestAttributes.getRequest().getParameter(smsCodeProp.getRequestParamMobileName());
        }

        int expireIn = smsCodeProp.getExpire();
        int codeLength = smsCodeProp.getLength();

        String code = ValidateCodeUtil.generateNumberVerifyCode(codeLength);

        return new ValidateCode(mobile + SMS_CODE_SEPARATOR + code, expireIn);
    }
}
