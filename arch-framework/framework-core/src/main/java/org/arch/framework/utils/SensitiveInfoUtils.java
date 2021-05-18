package org.arch.framework.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;

import java.util.Objects;

/**
 * 字符串脱敏工具类
 *
 * @author YongWu zheng
 * @version V2.0  Created by 2021-05-18 22:05
 */
public class SensitiveInfoUtils {

    /**
     * 默认填充字符
     */
    public static final String DEFAULT_PAD_STR = "*";

    /**
     * 字符串脱敏
     * 当字符创为空串或字符串长度小于等于 2 时，直接返回
     * 当【字符串总长度】减 {@code leftLen} 减 {@code rightLen} 小于等于 【字符串总长度】的三分之一时，则左右各展示【字符串总长度】的三分之一
     *
     * @param str       需要脱敏的字符串
     * @param leftLen   左边展示几位
     * @param rightLen  右边展示几位
     * @param padStr    填充字符串，如果长度为 1， 则按位填充；否则否则直接填充，可用于颜文字填充场景
     * @return  脱敏后的字符串
     */
    public static String desensitize(String str, Integer leftLen, Integer rightLen, String padStr) {
        int length = StringUtils.length(str);
        if (StringUtils.isBlank(str) || length <= 2) {
            return str;
        }

        int oneThird = length / 3;

        // 设置默认值
        leftLen = Objects.isNull(leftLen) ? oneThird : leftLen;
        rightLen = Objects.isNull(rightLen) ? oneThird : rightLen;
        padStr = StringUtils.isBlank(padStr) ? DEFAULT_PAD_STR : padStr;

        if (length - leftLen - rightLen <= oneThird) {
            leftLen = oneThird;
            rightLen = oneThird;
        }


        String left = StringUtils.left(str, leftLen);
        String right = StringUtils.right(str, rightLen);
        if (StringUtils.length(padStr) == 1) {
            return StringUtils.rightPad(left, str.length() - rightLen, padStr).concat(right);
        }
        return left + padStr + right;
    }

    /**
     * 字符串脱敏
     * @param str       需要脱敏的字符串
     * @param leftLen   左边展示几位
     * @param rightLen  左边展示几位
     * @return  脱敏后的字符串
     */
    public static String desensitize(String str, Integer leftLen, Integer rightLen) {
        return desensitize(str, leftLen, rightLen, DEFAULT_PAD_STR);
    }

    /**
     * 身份证号码脱敏，输出【前六后四】: 330726********1234
     * @param idCard    身份证号码
     * @return  脱敏后的身份证号码
     */
    @NonNull
    public static String desensitizeOfIdCard(@NonNull String idCard) {
        return desensitize(idCard, 6, 4);
    }

}