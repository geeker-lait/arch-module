package org.arch.automate.reader.xmind.utils;

import cn.hutool.core.io.resource.ClassPathResource;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.google.common.base.CaseFormat;
import org.arch.automate.Project;
import org.arch.automate.core.ReaderConfiguration;
import org.arch.automate.configuration.SchemaConfiguration;
import org.arch.automate.reader.xmind.meta.Attached;
import org.arch.automate.reader.xmind.meta.JsonRootBean;
import org.arch.automate.reader.xmind.nodespace.Annotation;
import org.arch.automate.reader.xmind.nodespace.ColumnProperty;
import org.arch.automate.reader.xmind.nodespace.ColumnType;
import org.arch.automate.reader.xmind.nodespace.ParamProperty;
import org.arch.automate.reader.xmind.nodespace.ParamType;
import org.arch.automate.reader.xmind.nodespace.TiTleType;
import org.arch.automate.reader.xmind.parser.XmindParser;
import org.arch.automate.reader.xmind.parser.XmindProjectParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.util.StringUtils.hasText;

/**
 * xmind utils
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.4.28 15:08
 */
public class XmindUtils {

    public static final String SEPARATOR = "/";
    public static final String COLUMN_PROPERTY_SEPARATOR = "_";
    public static final String PIN_YIN_SEPARATOR = " ";
    public static final String CLASS_PATH = "classpath:";
    public static final Logger LOG = LoggerFactory.getLogger(XmindUtils.class);

    /**
     * 根据 read properties 获取 {@link Project}, 先从 readerConfiguration.resourceProjectMap 中获取,
     * 没有时再根据 readerConfiguration.resource 读取解析成 {@link Project}, 先 put 到 readerConfiguration.resourceProjectMap
     * 再返回.
     * @param readerConfiguration   reader configuration
     * @param <T> {@link SchemaConfiguration}
     * @return  {@link Project}
     */
    public static <T extends SchemaConfiguration> Project getProject(@NonNull ReaderConfiguration<T> readerConfiguration) {
        // 从缓存中读取
        ConcurrentHashMap<String, Project> resourceProjectMap = readerConfiguration.getResourceProjectMap();
        String res = readerConfiguration.getResource();
        if (nonNull(resourceProjectMap) && resourceProjectMap.containsKey(res)) {

            return resourceProjectMap.get(res);
        }
        // 解析文件
        JsonRootBean root = null;
        try {
            // 从类路劲加载
            if (hasText(res) && res.startsWith(CLASS_PATH)) {
                res = new ClassPathResource(res.split(":")[1]).getAbsolutePath();
            }
            root = XmindParser.parseObject(res, JsonRootBean.class);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        Project project = new Project();
        if (nonNull(root)) {
            XmindProjectParser.generate(root, project);
        }
        // 缓存
        if (isNull(resourceProjectMap)) {
        	resourceProjectMap = new ConcurrentHashMap<>(16);
        }
        resourceProjectMap.put(res, project);
        readerConfiguration.setResourceProjectMap(resourceProjectMap);
        return project;
    }


    /**
     * 驼峰转下划线
     *
     * @param camelStr 小写驼峰字符串
     * @return 小写下划线字符串
     */
    @NonNull
    public static String camelToUnderscore(@NonNull String camelStr) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, camelStr);
    }

    /**
     * 驼峰转下划线
     *
     * @param camelStr 小写驼峰字符串
     * @return 大写下划线字符串
     */
    @NonNull
    public static String camelToUpperUnderscore(@NonNull String camelStr) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, camelStr);
    }

    /**
     * 首字母大写
     *
     * @param str 字符串
     * @return 字母大写的字符串
     */
    @NonNull
    public static String firstLetterToUpper(@NonNull String str) {
        if (str.length() < 1) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        return sb.replace(0, 1, str.substring(0, 1).toUpperCase()).toString();
    }

    /**
     * 去除换行符
     *
     * @param str 字符串
     * @return 去除l换行符的字符串
     */
    @NonNull
    public static String removeNewlines(@NonNull String str) {
        return str.replaceAll("[\n\r]", " ");
    }

    /**
     * 首字母小写
     *
     * @param str 字符串
     * @return 字母小写的字符串
     */
    @NonNull
    public static String firstLetterToLower(@NonNull String str) {
        if (str.length() < 1) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        return sb.replace(0, 1, str.substring(0, 1).toLowerCase()).toString();
    }

    /**
     * 下划线转驼峰
     *
     * @param underscoreStr 小写下划线字符串
     * @return 小写驼峰字符串
     */
    @SuppressWarnings("unused")
    @NonNull
    public static String underscoreToCamel(@NonNull String underscoreStr) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, underscoreStr);
    }

    /**
     * 下划线转大写驼峰
     *
     * @param underscoreStr 小写下划线字符串
     * @return 大写驼峰字符串
     */
    @NonNull
    public static String underscoreToUpperCamel(@NonNull String underscoreStr) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, underscoreStr);
    }

    // ------------------ 中文转汉字  ------------------

    /**
     * str 转换为大写驼峰字符串, 如果 str 为全中文则转换为拼音再转为大写驼峰字符串.
     *
     * @param str 全中文或全英文的字符串
     * @return 大写驼峰字符串
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
     *
     * @param str 全中文或全英文的字符串
     * @return 小写下滑线字符串
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
     * 判断字符串是否全部为中文字符串
     *
     * @param str 字符串
     * @return true 表示字符串全部为中文字符串, false 表示字符串有部分或所有字符串不为中文
     */
    public static boolean isChinese(@NonNull String str) {
        int byteLen = str.getBytes(StandardCharsets.UTF_8).length;
        int strLen = str.length();
        return byteLen != strLen && ((byteLen / strLen) == 3);
    }

    /**
     * 拼音转换为驼峰字符串
     *
     * @param pinYinStr 全中文字符串
     * @return 拼音大写驼峰字符串
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
     *
     * @param pinYinStr 全中文字符串
     * @return 小写下滑线拼音字符串
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

    // ------------------ xmind parse ------------------



    //  ------------------------------- nodeSpace -------------------------------

    /**
     * 获取对应的 {@link Annotation}
     *
     * @param annotation annotation
     * @return {@link Annotation} 或 null
     */
    @Nullable
    public static Annotation getAnnotation(@NonNull String annotation) {
        try {
            return Annotation.valueOf(annotation.toUpperCase());
        } catch (Exception e) {
            try {
                return Annotation.valueOf(camelToUpperUnderscore(annotation));
            } catch (Exception ex) {
                LOG.debug("annotation [" + annotation + "] 不能转换为 Annotation");
                return null;
            }
        }
    }

    /**
     * 获取对应的 {@link ParamType}
     *
     * @param paramType param type
     * @return {@link ParamType} 或 null
     */
    @Nullable
    public static ParamType getParamType(@NonNull String paramType) {
        return getParamType(paramType, Boolean.TRUE);
    }

    /**
     * 获取对应的 {@link ParamProperty}
     *
     * @param paramProperty param property
     * @return {@link ParamProperty} 或 null
     */
    @Nullable
    public static ParamProperty getParamProperty(@NonNull String paramProperty) {
        return getParamProperty(paramProperty, Boolean.TRUE);
    }

    /**
     * 获取对应的 {@link ParamType}
     *
     * @param paramType param type
     * @param isLog     是否打印日志
     * @return {@link ParamType} 或 null
     */
    @Nullable
    public static ParamType getParamType(@NonNull String paramType, @NonNull Boolean isLog) {
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
        } catch (Exception e) {
            try {
                return ParamType.valueOf(camelToUpperUnderscore(type));
            } catch (Exception ex) {
                if (isLog) {
                    XmindUtils.LOG.debug("param type [" + paramType + "] 不能转换为 ParamType");
                }
                return null;
            }
        }
    }

    /**
     * 获取对应的 {@link ParamProperty}
     *
     * @param paramProperty param property
     * @return {@link ParamProperty} 或 null
     */
    @SuppressWarnings("SameParameterValue")
    @Nullable
    private static ParamProperty getParamProperty(@NonNull String paramProperty, @NonNull Boolean isLog) {
        try {
            return ParamProperty.valueOf(paramProperty.toUpperCase());
        } catch (Exception e) {
            try {
                return ParamProperty.valueOf(camelToUpperUnderscore(paramProperty));
            } catch (Exception ex) {
                if (isLog) {
                    LOG.debug("param property [" + paramProperty + "] 不能转换为 ParamProperty");
                }
                return null;
            }
        }
    }

    /**
     * 获取对应的 {@link ColumnProperty}
     *
     * @param columnProperty column property, 格式: length/index_1_2/unique_1_1/unique/index/pk 等
     * @return {@link ColumnProperty} 或 null
     */
    @Nullable
    public static ColumnProperty getColumnProperty(@NonNull String columnProperty) {
        String[] splits = columnProperty.toUpperCase().split(COLUMN_PROPERTY_SEPARATOR);
        String property = splits[0].trim();
        try {
            return ColumnProperty.valueOf(property);
        } catch (Exception e) {
            try {
                return ColumnProperty.valueOf(camelToUpperUnderscore(property));
            } catch (Exception ex) {
                XmindUtils.LOG.debug("column property [" + columnProperty + "] 不能转换为 ColumnProperty");
                return null;
            }
        }
    }

    /**
     * 获取对应的 {@link ColumnType}
     *
     * @param columnType column type
     * @param isLog      是否打印日志
     * @return {@link ColumnType} 或 null
     */
    @Nullable
    public static ColumnType getColumnType(@NonNull String columnType, @NonNull Boolean isLog) {
        String type = columnType;
        int index = type.indexOf("(");
        int index2 = type.indexOf("[");
        // type(len)
        if (index != -1 || index2 != -1) {
            type = type.substring(0, index);
        }
        try {
            return ColumnType.valueOf(type.toUpperCase());
        } catch (Exception e) {
            try {
                return ColumnType.valueOf(camelToUpperUnderscore(type));
            } catch (Exception ex) {
                if (isLog) {
                    XmindUtils.LOG.debug("column type [" + columnType + "] 不能转换为 ColumnType");
                }
                return null;
            }
        }
    }

    /**
     * 获取对应的 {@link ColumnType}
     *
     * @param columnType column type
     * @return {@link ColumnType} 或 null
     */
    @Nullable
    public static ColumnType getColumnType(@NonNull String columnType) {
        return getColumnType(columnType, Boolean.TRUE);
    }

    /**
     * 获取对应的 {@link TiTleType}
     *
     * @param attachedTitle {@link Attached} title
     * @return {@link TiTleType} 或 null
     */
    @Nullable
    public static TiTleType getTiTleType(@NotNull String attachedTitle) {
        String[] splits = splitInto3Parts(attachedTitle);
        if (splits.length != 3) {
            XmindUtils.LOG.debug("title [" + attachedTitle + "] 格式错误, 标准格式: TitleType/TypeName/[description]");
            return null;
        }
        if (attachedTitle.length() > 0) {
            String type = splits[0].trim();
            try {
                return TiTleType.valueOf(type.toUpperCase());
            } catch (Exception e) {
                try {
                    return TiTleType.valueOf(camelToUpperUnderscore(type));
                } catch (Exception ex) {
                    XmindUtils.LOG.debug("title [" + attachedTitle + "] 格式错误, 标准格式: TitleType/TypeName/[description]");
                    return null;
                }
            }
        }
        XmindUtils.LOG.debug("title [" + attachedTitle + "] 格式错误, 标准格式: TitleType/TypeName/[description]");
        return null;
    }

    /**
     * 根据 {@code /} 把字符串分割为长度为 3 的字符串数组
     *
     * @param str 字符串
     * @return 字符串数组
     */
    @NonNull
    public static String[] splitInto3Parts(@NonNull String str) {
        return str.split(SEPARATOR, 3);
    }


}
