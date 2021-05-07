package org.arch.framework.automate.xmind.utils;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.google.common.base.CaseFormat;
import org.arch.framework.automate.generater.service.xmind.meta.Attached;
import org.arch.framework.automate.xmind.nodespace.Annotation;
import org.arch.framework.automate.xmind.nodespace.ColumnProperty;
import org.arch.framework.automate.xmind.nodespace.ColumnType;
import org.arch.framework.automate.xmind.nodespace.ParamProperty;
import org.arch.framework.automate.xmind.nodespace.ParamType;
import org.arch.framework.automate.xmind.nodespace.TiTleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;

import static java.util.Objects.isNull;

/**
 * xmind utils
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.4.28 15:08
 */
public class XmindUtils {

    public static final String SEPARATOR = "/";
    public static final String COLUMN_PROPERTY_SEPARATOR = "_";
    public static final String PIN_YIN_SEPARATOR = " ";
    public static final Logger LOG = LoggerFactory.getLogger(XmindUtils.class);

    /**
     * 获取对应的 {@link Annotation}
     * @param annotation    annotation
     * @param log           log
     * @return  {@link Annotation} 或 null
     */
    @Nullable
    public static Annotation getAnnotation(@NonNull String annotation, @Nullable Logger log) {
        try {
            return Annotation.valueOf(annotation.toUpperCase());
        }
        catch (Exception e) {
            try {
                return Annotation.valueOf(camelToUpperUnderscore(annotation));
            }
            catch (Exception ex) {
                if (isNull(log)) {
                	log = LOG;
                }
                log.debug("annotation [" + annotation + "] 不能转换为 Annotation");
                return null;
            }
        }
    }

    /**
     * 获取对应的 {@link ParamType}
     * @param paramType         param type
     * @param log               log
     * @return  {@link ParamType} 或 null
     */
    @Nullable
    public static ParamType getParamType(@NonNull String paramType, @Nullable Logger log) {
        return getParamType(paramType, log, Boolean.TRUE);
    }

    /**
     * 获取对应的 {@link ParamProperty}
     * @param paramProperty     param property
     * @param log               log
     * @return  {@link ParamProperty} 或 null
     */
    @Nullable
    public static ParamProperty getParamProperty(@NonNull String paramProperty, @Nullable Logger log) {
        return getParamProperty(paramProperty, log, Boolean.TRUE);
    }

    /**
     * 获取对应的 {@link ParamType}
     * @param paramType         param type
     * @param log               log
     * @param isLog             是否打印日志
     * @return  {@link ParamType} 或 null
     */
    @Nullable
    public static ParamType getParamType(@NonNull String paramType, @Nullable Logger log, @NonNull Boolean isLog) {
        String type = paramType;
        int genericIndex = type.indexOf("<");
        int arrIndex = type.indexOf("[");
        // Map<String, String[]>/List<String>/Set<String>
        if (genericIndex != -1 && (arrIndex == -1 || genericIndex < arrIndex)) {
            type = type.substring(0, genericIndex);
        }
        // String[]
        else if (arrIndex != -1) {
            type = type.substring(0, arrIndex);
        }

        try {
            return ParamType.valueOf(type.toUpperCase());
        }
        catch (Exception e) {
            try {
                return ParamType.valueOf(camelToUpperUnderscore(type));
            }
            catch (Exception ex) {
                if (isLog) {
                    if (isNull(log)) {
                        log = LOG;
                    }
                    log.debug("param type [" + paramType + "] 不能转换为 ParamType");
                }
                return null;
            }
        }
    }

    /**
     * 获取对应的 {@link ParamProperty}
     * @param paramProperty     param property
     * @param log               log
     * @param isLog             是否打印日志
     * @return  {@link ParamProperty} 或 null
     */
    @Nullable
    public static ParamProperty getParamProperty(@NonNull String paramProperty, @Nullable Logger log,
                                                 @NonNull Boolean isLog) {
        try {
            return ParamProperty.valueOf(paramProperty.toUpperCase());
        }
        catch (Exception e) {
            try {
                return ParamProperty.valueOf(camelToUpperUnderscore(paramProperty));
            }
            catch (Exception ex) {
                if (isLog) {
                    if (isNull(log)) {
                        log = LOG;
                    }
                    log.debug("param property [" + paramProperty + "] 不能转换为 ParamProperty");
                }
                return null;
            }
        }
    }

    /**
     * 获取对应的 {@link ColumnProperty}
     * @param columnProperty    column property, 格式: length/index_1_2/unique_1_1/unique/index/pk 等
     * @param log               log
     * @return  {@link ColumnProperty} 或 null
     */
    @Nullable
    public static ColumnProperty getColumnProperty(@NonNull String columnProperty, @Nullable Logger log) {
        String[] splits = columnProperty.toUpperCase().split(COLUMN_PROPERTY_SEPARATOR);
        String property = splits[0].trim();
        try {
            return ColumnProperty.valueOf(property);
        }
        catch (Exception e) {
            try {
                return ColumnProperty.valueOf(camelToUpperUnderscore(property));
            }
            catch (Exception ex) {
                if (isNull(log)) {
                	log = LOG;
                }
                log.debug("column property [" + columnProperty + "] 不能转换为 ColumnProperty");
                return null;
            }
        }
    }

    /**
     * 获取对应的 {@link ColumnType}
     * @param columnType    column type
     * @param log           log
     * @param isLog         是否打印日志
     * @return  {@link ColumnType} 或 null
     */
    @Nullable
    public static ColumnType getColumnType(@NonNull String columnType, @Nullable Logger log, @NonNull Boolean isLog) {
        String type = columnType;
        int index = type.indexOf("(");
        int index2 = type.indexOf("[");
        // type(len)
        if (index != -1 || index2 != -1) {
            type = type.substring(0, index);
        }
        try {
            return ColumnType.valueOf(type.toUpperCase());
        }
        catch (Exception e) {
            try {
                return ColumnType.valueOf(camelToUpperUnderscore(type));
            }
            catch (Exception ex) {
                if (isLog) {
                    if (isNull(log)) {
                        log = LOG;
                    }
                    log.debug("column type [" + columnType + "] 不能转换为 ColumnType");
                }
                return null;
            }
        }
    }

    /**
     * 获取对应的 {@link ColumnType}
     * @param columnType    column type
     * @param log           log
     * @return  {@link ColumnType} 或 null
     */
    @Nullable
    public static ColumnType getColumnType(@NonNull String columnType, @Nullable Logger log) {
        return getColumnType(columnType, log, Boolean.TRUE);
    }

    /**
     * 获取对应的 {@link TiTleType}
     * @param attachedTitle     {@link Attached} title
     * @param log               log
     * @return  {@link TiTleType} 或 null
     */
    @Nullable
    public static TiTleType getTiTleType(@NotNull String attachedTitle, @Nullable Logger log) {
        String[] splits = splitInto3Parts(attachedTitle);
        if (splits.length != 3) {
            if (isNull(log)) {
                log = LOG;
            }
            log.debug("title [" + attachedTitle + "] 格式错误, 标准格式: TitleType/TypeName/[description]");
            return null;
        }
        if (attachedTitle.length() > 0) {
            String type = splits[0].trim();
            try {
                return TiTleType.valueOf(type.toUpperCase());
            }
            catch (Exception e) {
                try {
                    return TiTleType.valueOf(camelToUpperUnderscore(type));
                }
                catch (Exception ex) {
                    if (isNull(log)) {
                        log = LOG;
                    }
                    log.debug("title [" + attachedTitle + "] 格式错误, 标准格式: TitleType/TypeName/[description]");
                    return null;
                }
            }
        }
        if (isNull(log)) {
            log = LOG;
        }
        log.debug("title [" + attachedTitle + "] 格式错误, 标准格式: TitleType/TypeName/[description]");
        return null;
    }

    /**
     * 驼峰转下划线
     * @param camelStr  小写驼峰字符串
     * @return  小写下划线字符串
     */
    @NonNull
    public static String camelToUnderscore(@NonNull String camelStr) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, camelStr);
    }

    /**
     * 驼峰转下划线
     * @param camelStr  小写驼峰字符串
     * @return  大写下划线字符串
     */
    @NonNull
    public static String camelToUpperUnderscore(@NonNull String camelStr) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, camelStr);
    }

    /**
     * 首字母大写
     * @param str  字符串
     * @return  字母大写的字符串
     */
    @NonNull
    public static String firstLetterToUpper(@NonNull String str) {
        if (str.length() < 1) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        return sb.replace(0,1, str.substring(0, 1).toUpperCase()).toString();
    }

    /**
     * 去除换行符
     * @param str  字符串
     * @return  去除l换行符的字符串
     */
    @NonNull
    public static String removeNewlines(@NonNull String str) {
        return str.replaceAll("[\n\r]", " ");
    }

    /**
     * 首字母小写
     * @param str  字符串
     * @return  字母小写的字符串
     */
    @NonNull
    public static String firstLetterToLower(@NonNull String str) {
        if (str.length() < 1) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        return sb.replace(0,1, str.substring(0, 1).toLowerCase()).toString();
    }

    /**
     * 下划线转驼峰
     * @param underscoreStr  小写下划线字符串
     * @return  小写驼峰字符串
     */
    @NonNull
    public static String underscoreToCamel(@NonNull String underscoreStr) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, underscoreStr);
    }

    /**
     * 下划线转大写驼峰
     * @param underscoreStr  小写下划线字符串
     * @return  大写驼峰字符串
     */
    @NonNull
    public static String underscoreToUpperCamel(@NonNull String underscoreStr) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, underscoreStr);
    }

    /**
     * 根据 {@code /} 把字符串分割为长度为 3 的字符串数组
     * @param str   字符串
     * @return  字符串数组
     */
    @NonNull
    public static String[] splitInto3Parts(@NonNull String str) {
        return str.split(SEPARATOR, 3);
    }

    /**
     * str 转换为大写驼峰字符串, 如果 str 为全中文则转换为拼音再转为大写驼峰字符串.
     * @param str   全中文或全英文的字符串
     * @return  大写驼峰字符串
     */
    @NonNull
    public static String strToUpperCamelWithPinYin(@NonNull String str) {
        String pinYin = null;
        if (isChinese(str)) {
            pinYin = changeToTonePinYin(str);
        }
        if (isNull(pinYin)) {
            return str.contains("_") ? underscoreToUpperCamel(str) : firstLetterToUpper(str);
        }
        return pinYinToUpperCamel(pinYin);
    }

    /**
     * str 转换为小写下滑线字符串, 如果 str 为全中文则转换为拼音再转为小写下滑线字符串.
     * @param str   全中文或全英文的字符串
     * @return  小写下滑线字符串
     */
    @NonNull
    public static String strToUnderscoreWithPinYin(@NonNull String str) {
        String pinYin = null;
        if (isChinese(str)) {
            pinYin = changeToTonePinYin(str);
        }
        if (isNull(pinYin)) {
            return str.contains("_") ? firstLetterToLower(str) : camelToUnderscore(str);
        }
        return pinYinToUnderscore(pinYin);
    }

    /**
     * 转换为不带音调的拼音字符串
     *
     * @param pinYinStr 需转换的汉字
     * @return 拼音字符串
     */
    @Nullable
    public static String changeToTonePinYin(@NonNull String pinYinStr) {
        try {
            return PinyinHelper.convertToPinyinString(pinYinStr, " ", PinyinFormat.WITHOUT_TONE);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 拼音转换为驼峰字符串
     * @param pinYinStr 全中文字符串
     * @return  拼音大写驼峰字符串
     */
    @NonNull
    private static String pinYinToUpperCamel(@NonNull String pinYinStr) {
        String[] pinYinArr = pinYinStr.split(PIN_YIN_SEPARATOR);
        StringBuilder sb = new StringBuilder();
        for (String pinYin : pinYinArr) {
            sb.append(firstLetterToUpper(pinYin));
        }
        return sb.toString();
    }

    /**
     * 拼音转换为小写下滑线拼音字符串
     * @param pinYinStr 全中文字符串
     * @return  小写下滑线拼音字符串
     */
    @NonNull
    private static String pinYinToUnderscore(@NonNull String pinYinStr) {
        String[] pinYinArr = pinYinStr.split(PIN_YIN_SEPARATOR);
        StringBuilder sb = new StringBuilder();
        for (String pinYin : pinYinArr) {
            sb.append(pinYin).append(COLUMN_PROPERTY_SEPARATOR);
        }
        int length = sb.length();
        if (length > 0) {
            sb.setLength(length - 1);
        }
        return sb.toString();
    }

    /**
     * 判断字符串是否全部为中文字符串
     * @param str   字符串
     * @return  true 表示字符串全部为中文字符串, false 表示字符串有部分或所有字符串不为中文
     */
    public static boolean isChinese(@NonNull String str) {
        int byteLen = str.getBytes(StandardCharsets.UTF_8).length;
        int strLen = str.length();
        return byteLen != strLen && ((byteLen / strLen) == 3);
    }

}
