package org.arch.framework.automate.xmind.utils;

import com.google.common.base.CaseFormat;
import org.arch.framework.automate.xmind.nodespace.Annotation;
import org.arch.framework.automate.xmind.nodespace.ColumnProperty;
import org.arch.framework.automate.xmind.nodespace.ColumnType;
import org.arch.framework.automate.xmind.nodespace.ParamType;
import org.arch.framework.automate.xmind.nodespace.TiTleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

import static java.util.Objects.isNull;

/**
 * xmind utils
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.4.28 15:08
 */
public class XmindUtils {

    public static final String SEPARATOR = "/";
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
                log.error("annotation [" + annotation + "] 不能转换为 Annotation");
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
        try {
            return ParamType.valueOf(paramType.toUpperCase());
        }
        catch (Exception e) {
            try {
                return ParamType.valueOf(camelToUpperUnderscore(paramType));
            }
            catch (Exception ex) {
                if (isNull(log)) {
                	log = LOG;
                }
                log.error("param type [" + paramType + "] 不能转换为 ParamType");
                return null;
            }
        }
    }

    /**
     * 获取对应的 {@link ColumnProperty}
     * @param columnProperty    column property
     * @param log               log
     * @return  {@link ColumnProperty} 或 null
     */
    @Nullable
    public static ColumnProperty getColumnProperty(@NonNull String columnProperty, @Nullable Logger log) {
        try {
            return ColumnProperty.valueOf(columnProperty.toUpperCase());
        }
        catch (Exception e) {
            try {
                return ColumnProperty.valueOf(camelToUpperUnderscore(columnProperty));
            }
            catch (Exception ex) {
                if (isNull(log)) {
                	log = LOG;
                }
                log.error("column property [" + columnProperty + "] 不能转换为 ColumnProperty");
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
        try {
            return ColumnType.valueOf(columnType.toUpperCase());
        }
        catch (Exception e) {
            try {
                return ColumnType.valueOf(camelToUpperUnderscore(columnType));
            }
            catch (Exception ex) {
                if (isNull(log)) {
                    log = LOG;
                }
                log.error("column type [" + columnType + "] 不能转换为 ColumnType");
                return null;
            }
        }
    }

    /**
     * 获取对应的 {@link TiTleType}
     * @param title    title
     * @param log      log
     * @return  {@link TiTleType} 或 null
     */
    @Nullable
    public static TiTleType getTiTleType(@NotNull String title, @Nullable Logger log) {
        String[] splits = splitInto3Parts(title);
        if (splits.length != 3) {
            if (isNull(log)) {
                log = LOG;
            }
            log.error("title [" + title + "] 格式错误, 标准格式: TitleType/TypeName/[description]");
            return null;
        }
        if (title.length() > 0) {
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
                    log.error("title [" + title + "] 格式错误, 标准格式: TitleType/TypeName/[description]");
                    return null;
                }
            }
        }
        if (isNull(log)) {
            log = LOG;
        }
        log.error("title [" + title + "] 格式错误, 标准格式: TitleType/TypeName/[description]");
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
     * 根据 {@code /} 把字符串分割为长度为 3 的字符串数组
     * @param str   字符串
     * @return  字符串数组
     */
    @NonNull
    public static String[] splitInto3Parts(@NonNull String str) {
        return str.split(SEPARATOR, 3);
    }

}
