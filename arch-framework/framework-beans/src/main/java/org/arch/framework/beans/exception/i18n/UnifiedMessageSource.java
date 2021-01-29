package org.arch.framework.beans.exception.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * 错误信息国际化处理
 */
@Component
public class UnifiedMessageSource {

    @Autowired
    private MessageSource messageSource;

    /**
     * 获取国际化消息
     *
     * @param code 消息code
     * @return 国际化消息字符串
     */
    public String getMessage(String code) {

        return getMessage(code, null);
    }

    /**
     * 获取国际化消息
     *
     * @param code 消息code
     * @param args 参数
     * @return 国际化消息字符串
     */
    public String getMessage(String code, Object[] args) {

        return getMessage(code, args, "");
    }

    /**
     * 获取国际化消息
     *
     * @param code           消息code
     * @param args           参数
     * @param defaultMessage 默认消息
     * @return 国际化消息字符串
     */
    public String getMessage(String code, Object[] args, String defaultMessage) {
        Locale locale = LocaleContextHolder.getLocale();

        return messageSource.getMessage(code, args, defaultMessage, locale);
    }
}
