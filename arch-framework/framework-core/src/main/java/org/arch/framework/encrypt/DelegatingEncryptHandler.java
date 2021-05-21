package org.arch.framework.encrypt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import top.dcenter.ums.security.common.utils.JsonUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.regex.Pattern.matches;
import static org.springframework.util.StringUtils.*;
import static org.springframework.util.StringUtils.hasText;

/**
 * 根据 {@link EncryptType} 类型委托到另一个 {@link EncryptService} 的统一加解密处理器
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.20 21:04
 */
@Slf4j
public class DelegatingEncryptHandler {

    private final Map<String, EncryptService> encryptServiceMap;

    public DelegatingEncryptHandler(Map<String, EncryptService> encryptServiceMap) {
        this.encryptServiceMap = new HashMap<>(encryptServiceMap.size());
        for (Map.Entry<String, EncryptService> encryptServiceEntry : encryptServiceMap.entrySet()) {
            EncryptService encryptService = encryptServiceEntry.getValue();
            if (nonNull(encryptService)) {
                this.encryptServiceMap.put(encryptService.getEncryptType().toUpperCase(), encryptService);
            }
        }
    }

    /**
     * 加密或 hash
     * @param fields        有 {@link EncryptClass} 注解的类的字段
     * @param encryptObj    有 {@link EncryptClass} 注解的类对象
     */
    @NonNull
    public void encode(@NonNull Field[] fields, @NonNull Object encryptObj) {
        encodeOrDecode(fields, encryptObj, true);
    }

    /**
     * 解密
     * @param fields        有 {@link EncryptClass} 注解的类的字段
     * @param encryptObj    有 {@link EncryptClass} 注解的类对象
     */
    @NonNull
    public void decode(@NonNull Field[] fields, @NonNull Object encryptObj) {
        encodeOrDecode(fields, encryptObj, false);
    }

    private void encodeOrDecode(@NonNull Field[] fields, @NonNull Object encryptObj, boolean encodeOrDecode) {
        // encodeOrDecode = true 表示 encode 模式, encodeOrDecode = false 表示 decode 模式,
        for (Field field : fields) {
            try {
                // 获取 EncryptField 注解
                EncryptField encryptField = field.getAnnotation(EncryptField.class);
                if (isNull(encryptField)) {
                    continue;
                }
                String encryptType = encryptField.encryptType();
                String filterRegx = encryptField.filterRegx();
                boolean isIdCard = encryptField.idCard();
                if (!hasText(encryptType)) {
                    continue;
                }
                field.setAccessible(true);
                Object value = field.get(encryptObj);
                if (hasText(filterRegx) && !matches(filterRegx, (String) value)) {
                    continue;
                }
                if (isNull(value)) {
                    continue;
                }
                // 获取 EncryptService
                EncryptService encryptService = this.encryptServiceMap.get(encryptType.toUpperCase());
                if (isNull(encryptService)) {
                    continue;
                }
                // 获取原始值
                String rawValue;
                if (value instanceof String) {
                    if (!hasLength((String) value)) {
                        continue;
                    }
                    rawValue = (String) value;
                }
                else {
                    rawValue = JsonUtil.toJsonString(value);
                }
                // 对原始值加解密
                String coderValue;
                if (encodeOrDecode) {
                    if (!isIdCard) {
                        coderValue = encryptService.encode(rawValue);
                    }
                    else {
                        coderValue = encryptService.encodeIdCard(rawValue);
                    }
                }
                else {
                    if (!isIdCard) {
                        coderValue = encryptService.decode(rawValue);
                    }
                    else {
                        coderValue = encryptService.decodeIdCard(rawValue);
                    }
                }
                field.set(encryptObj, coderValue);
            }
            catch (IllegalAccessException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

}
