package org.arch.framework.automate.generater.reader.xmind.utils;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.google.common.base.CaseFormat;
import org.arch.framework.automate.common.api.Annot;
import org.arch.framework.automate.common.api.AnnotVal;
import org.arch.framework.automate.common.api.Curl;
import org.arch.framework.automate.common.api.Entity;
import org.arch.framework.automate.common.api.Interfac;
import org.arch.framework.automate.common.api.Param;
import org.arch.framework.automate.common.database.Column;
import org.arch.framework.automate.common.database.Database;
import org.arch.framework.automate.common.database.Table;
import org.arch.framework.automate.common.module.Module;
import org.arch.framework.automate.common.module.Project;
import org.arch.framework.automate.generater.reader.xmind.Import;
import org.arch.framework.automate.generater.reader.xmind.meta.Attached;
import org.arch.framework.automate.generater.reader.xmind.meta.Children;
import org.arch.framework.automate.generater.reader.xmind.meta.JsonRootBean;
import org.arch.framework.automate.generater.reader.xmind.meta.RootTopic;
import org.arch.framework.automate.generater.reader.xmind.nodespace.Annotation;
import org.arch.framework.automate.generater.reader.xmind.nodespace.ColumnProperty;
import org.arch.framework.automate.generater.reader.xmind.nodespace.ColumnType;
import org.arch.framework.automate.generater.reader.xmind.nodespace.ParamProperty;
import org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType;
import org.arch.framework.automate.generater.reader.xmind.nodespace.TiTleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ColumnType.BIGINT;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamProperty.ARRAY_TYP;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType.*;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.TiTleType.API;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.TiTleType.INTERFACE;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.TiTleType.MODULE;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.TiTleType.PKG;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.TiTleType.PROJECT;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.TiTleType.TABLE;
import static org.springframework.util.StringUtils.hasText;

/**
 * xmind utils
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.4.28 15:08
 */
@SuppressWarnings("AlibabaAvoidComplexCondition")
public class XmindUtils {

    public static final String SEPARATOR = "/";
    public static final String COLUMN_PROPERTY_SEPARATOR = "_";
    public static final String PIN_YIN_SEPARATOR = " ";
    public static final Logger LOG = LoggerFactory.getLogger(XmindUtils.class);


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

    /**
     * 解析 xmind 为 {@link Project} 的对象.
     *
     * @param root    xmind 的 {@link JsonRootBean}
     * @param project {@link Project} 用于存放 xmind 的解析结果
     */
    public static void generate(@NonNull JsonRootBean root, @NonNull Project project) {
        try {
            String title = root.getTitle().trim();
            TiTleType tiTleType = getTiTleType(title);
            if (isNull(tiTleType)) {
                generateOfRoot(root.getRootTopic(), project, tiTleType);
                return;
            }
            if (PROJECT.equals(tiTleType)) {
                String[] splits = splitInto3Parts(title);
                project.setName(splits[1].trim());
                project.setDescr(removeNewlines(splits[2].trim()));
                generateOfRoot(root.getRootTopic(), project, tiTleType);
            }
            else if (MODULE.equals(tiTleType)) {
                String[] splits = splitInto3Parts(title);
                Module module = new Module();
                module.setTyp(splits[0].trim());
                module.setName(splits[1].trim());
                module.setComment(removeNewlines(splits[2].trim()));
                project.getModules().add(module);
                generateOfRoot(root.getRootTopic(), project, tiTleType);
            }
        } catch (Exception e) {
            generateOfRoot(root.getRootTopic(), project, null);
        }
    }

    private static void generateOfRoot(@NonNull RootTopic rootTopic, @NonNull Project project,
                                       @Nullable TiTleType pTiTleType) {
        String title = rootTopic.getTitle().trim();
        TiTleType tiTleType = getTiTleType(title);
        if (isNull(pTiTleType)) {
            if (nonNull(tiTleType) && PROJECT.equals(tiTleType)) {
                String[] splits = splitInto3Parts(title);
                project.setName(splits[1].trim());
                project.setDescr(removeNewlines(splits[2].trim()));
            }
            Children children = rootTopic.getChildren();
            generate(children, project.getModules(), title, tiTleType);
            return;
        }
        if (PROJECT.equals(pTiTleType)) {
            if (nonNull(tiTleType) && MODULE.equals(tiTleType)) {
                Module module = new Module();
                project.getModules().add(module);
                Children children = rootTopic.getChildren();
                if (isNull(children)) {
                    generate(children, project.getModules(), title, tiTleType);
                    return;
                }
                generateOfChildren(children, project.getModules(), module, MODULE, title);
            }
        }
        else if (MODULE.equals(pTiTleType)) {
            Children children = rootTopic.getChildren();
            if (nonNull(children)) {
                List<Module> modules = project.getModules();
                generateOfChildren(children, modules, modules.get(0));
            }
        }
    }

    /**
     * 根据 {@link Attached} 的内容生成相应的规格内容, 添加到 {@link Module}.
     *
     * @param attached   {@link Attached}
     * @param moduleList {@link Module} list
     */
    private static void generateOfAttached(@NonNull Attached attached, @NonNull List<Module> moduleList) {
        String title = attached.getTitle().trim();
        TiTleType tiTleType = getTiTleType(title);
        Children children = attached.getChildren();
        if (isNull(children)) {
            return;
        }
        generate(children, moduleList, title, tiTleType);
    }

    private static void generate(@Nullable Children children, @NonNull List<Module> moduleList,
                                 @NonNull String title, @Nullable TiTleType pTiTleType) {
        if (isNull(children)) {
            return;
        }
        if (nonNull(pTiTleType) && MODULE.equals(pTiTleType)) {
            Module module = new Module();
            moduleList.add(module);
            generateOfChildren(children, moduleList, module, MODULE, title);
            return;
        }
        List<Attached> attachedList = children.getAttached();
        if (isNull(attachedList) || attachedList.size() == 0) {
            return;
        }
        for (Attached attached : attachedList) {
            generateOfAttached(attached, moduleList);
        }
    }

    /**
     * 根据 {@link Attached} 的内容生成相应的规格内容, 添加到 {@link Module}.
     *
     * @param attached   {@link Attached}
     * @param moduleList {@link Module} list
     * @param module     {@link Module}
     */
    private static void generateOfAttachedWithModule(@NonNull Attached attached, @NonNull List<Module> moduleList,
                                                     @NonNull Module module) {
        String title = attached.getTitle().trim();
        TiTleType tiTleType = getTiTleType(title);
        Children children = attached.getChildren();
        if (isNull(children)) {
            if (nonNull(tiTleType) && PKG.equals(tiTleType)) {
                generatePkg(null, moduleList, module, title, tiTleType);
            }
            return;
        }
        generateWithModule(children, moduleList, module, title, tiTleType);
    }

    private static void generateWithModule(@NonNull Children children, @NonNull List<Module> moduleList,
                                           @NonNull Module module,
                                           @NonNull String title, @Nullable TiTleType tiTleType) {
        if (isNull(tiTleType)) {
            List<Attached> attachedList = children.getAttached();
            if (isNull(attachedList) || attachedList.size() == 0) {
                return;
            }
            for (Attached attached : attachedList) {
                generateOfAttachedWithModule(attached, moduleList, module);
            }
            return;
        }
        if (MODULE.equals(tiTleType)) {
            Module newModule = new Module();
            moduleList.add(newModule);
            generateOfChildren(children, moduleList, newModule, MODULE, title);
            return;
        }
        generateOfChildren(children, moduleList, module, tiTleType, title);
    }

    private static void generateOfChildren(@NonNull Children children, @NonNull List<Module> moduleList,
                                           @NonNull Module module) {
        List<Attached> attachedList = children.getAttached();
        if (isNull(attachedList) || attachedList.size() == 0) {
            return;
        }
        for (Attached attached : attachedList) {
            generateOfAttachedWithModule(attached, moduleList, module);
        }
    }

    /**
     * 根据 {@link Children} 的内容生成相应的规格内容, 添加到 {@link Module}.
     *
     * @param children   {@link Children}
     * @param moduleList {@link Module} list
     * @param module     {@link Module}
     * @param pTiTleType {@link TiTleType}
     * @param pTitle     pTitle
     */
    private static void generateOfChildren(@NonNull Children children, @NonNull List<Module> moduleList,
                                           @NonNull Module module, @Nullable TiTleType pTiTleType, @NonNull String pTitle) {
        if (isNull(pTiTleType)) {
            generateOfChildren(children, moduleList, module);
            return;
        }

        switch (pTiTleType) {
            case MODULE:
                String[] splits = splitInto3Parts(pTitle);
                if (splits.length == 3) {
                    module.setTyp(splits[0].trim())
                            .setName(splits[1].trim())
                            .setComment(removeNewlines(splits[2].trim()));
                }
                generateOfChildren(children, moduleList, module);
                return;
            case DATABASE:
                generateDatabase(children, moduleList, module, pTitle);
                return;
            case API:
                generateApi(children, moduleList, module, pTiTleType, pTitle);
                return;
            case ENTITY:
                generateEntity(children, moduleList, module, pTitle, null);
                return;
            case PKG:
                generatePkg(children, moduleList, module, pTitle, pTiTleType);
                return;
            default:
                generateOfChildren(children, moduleList, module);
        }

    }

    //  ------------------------------- nodeSpace -------------------------------

    /**
     * 获取对应的 {@link Annotation}
     *
     * @param annotation annotation
     * @return {@link Annotation} 或 null
     */
    @Nullable
    private static Annotation getAnnotation(@NonNull String annotation) {
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
    private static ParamType getParamType(@NonNull String paramType) {
        return getParamType(paramType, Boolean.TRUE);
    }

    /**
     * 获取对应的 {@link ParamProperty}
     *
     * @param paramProperty param property
     * @return {@link ParamProperty} 或 null
     */
    @Nullable
    private static ParamProperty getParamProperty(@NonNull String paramProperty) {
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
    private static ParamType getParamType(@NonNull String paramType, @NonNull Boolean isLog) {
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
    private static ColumnProperty getColumnProperty(@NonNull String columnProperty) {
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
    private static ColumnType getColumnType(@NonNull String columnType, @NonNull Boolean isLog) {
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
    private static ColumnType getColumnType(@NonNull String columnType) {
        return getColumnType(columnType, Boolean.TRUE);
    }

    /**
     * 获取对应的 {@link TiTleType}
     *
     * @param attachedTitle {@link Attached} title
     * @return {@link TiTleType} 或 null
     */
    @Nullable
    private static TiTleType getTiTleType(@NotNull String attachedTitle) {
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
    private static String[] splitInto3Parts(@NonNull String str) {
        return str.split(SEPARATOR, 3);
    }


    //  ------------------------------- api -------------------------------

    private static void generatePkg(@Nullable Children children, @NonNull List<Module> moduleList,
                                    @NonNull Module module, @NonNull String pTitle,
                                    @NonNull TiTleType pTiTleType) {
        if (!PKG.equals(pTiTleType)) {
            return;
        }
        String[] tokens = splitInto3Parts(pTitle);
        if (tokens.length > 1) {
            String pkg = tokens[1].trim();
            module.setPkg(pkg);
        }
        if (nonNull(children)) {
            generateOfChildren(children, moduleList, module);
        }
    }

    private static void generateApi(@NonNull Children children, @NonNull List<Module> moduleList,
                                    @NonNull Module module, @NonNull TiTleType pTiTleType,
                                    @NonNull String pTitle) {

        if (!API.equals(pTiTleType)) {
            generateOfChildren(children, moduleList, module);
            return;
        }
        String[] apiTokens = splitInto3Parts(pTitle);
        String apiName = firstLetterToLower(apiTokens[1].trim());
        List<Attached> apiAttachedList = children.getAttached();
        for (Attached interfaceAttached : apiAttachedList) {
            String title = interfaceAttached.getTitle().trim();
            String[] splits = splitInto3Parts(title);
            TiTleType tiTleType = getTiTleType(title);
            if (splits.length != 3 || isNull(tiTleType)) {
                LOG.debug("title [" + title + "] 格式错误, 标准格式: paramType/paramName/[description]");
                generateOfAttachedWithModule(interfaceAttached, moduleList, module);
                continue;
            }
            if (INTERFACE.equals(tiTleType)) {
                // add inteface
                Children interfaceChildren = interfaceAttached.getChildren();
                if (nonNull(interfaceChildren)) {
                    generateInterface(interfaceChildren, moduleList, module, splits, apiName);
                }
            } else {
                generateOfAttachedWithModule(interfaceAttached, moduleList, module);
            }
        }
    }

    private static void generateInterface(@NonNull Children children, @NonNull List<Module> moduleList,
                                          @NonNull Module module, @NonNull String[] tokens,
                                          @NonNull String apiName) {
        // add interface
        String orgName = tokens[1].trim();
        String interfaceName = strToUpperCamelWithPinYin(orgName);
        String comment = removeNewlines(tokens[2].trim());
        Interfac interfac = new Interfac().setName(interfaceName).setDescr(comment).setApi(apiName);
        module.addInterface(interfac);

        // 遍历 interface 的方法
        Set<Annot> interfaceAnnotations = interfac.getAnnotations();
        List<Curl> curls = interfac.getCurls();
        List<Attached> attachedList = children.getAttached();
        if (isNull(attachedList) || attachedList.size() == 0) {
            return;
        }
        String curlTitle;
        for (Attached attached : attachedList) {
            curlTitle = attached.getTitle();
            tokens = splitInto3Parts(curlTitle);
            ParamType paramType = getParamType(tokens[0].trim());
            if (tokens.length != 3 || isNull(paramType)) {
                LOG.debug("title [" + curlTitle + "] 格式错误, 标准格式: paramType/paramName/[description]");
                generateOfAttachedWithModule(attached, moduleList, module);
                continue;
            }
            String name = tokens[1].trim();
            String curlComment = removeNewlines(tokens[2].trim());
            switch (paramType) {
                case GET:
                    curls.add(generateCurl(moduleList, module, interfac, attached, GET, name, curlComment, TRUE));
                    break;
                case POST:
                    curls.add(generateCurl(moduleList, module, interfac, attached, POST, name, curlComment, TRUE));
                    break;
                case PUT:
                    curls.add(generateCurl(moduleList, module, interfac, attached, PUT, name, curlComment, TRUE));
                    break;
                case DEL:
                    curls.add(generateCurl(moduleList, module, interfac, attached, DEL, name, curlComment, TRUE));
                    break;
                case METHOD:
                    curls.add(generateCurl(moduleList, module, interfac, attached, METHOD, name, curlComment, FALSE));
                    break;
                case ANNOTES:
                    Children annotesChildren = attached.getChildren();
                    if (nonNull(annotesChildren)) {
                        generateAnnotes(annotesChildren, moduleList, module, interfac, interfac.getAnnotations());
                    }
                    break;
                case ANNOT:
                case ANNOTATION:
                    Annot annotation = generateAnnot(attached, moduleList, module, tokens, interfac);
                    interfaceAnnotations.add(annotation);
                    break;
                case URI:
                    String rest = tokens[2].trim().toUpperCase();
                    Set<String> imports = interfac.getImports();
                    Annot controllerAnnot;
                    if ("REST".equals(rest)) {
                        controllerAnnot = new Annot().setName(Annotation.REST_CONTROLLER.getAnnotName());
                        imports.add(Annotation.REST_CONTROLLER.getPkg());
                    } else {
                        controllerAnnot = new Annot().setName(Annotation.CONTROLLER.getAnnotName());
                        imports.add(Annotation.CONTROLLER.getPkg());
                    }
                    Annot annot = new Annot().setName(Annotation.REQUEST_MAPPING.getAnnotName());
                    imports.add(Annotation.REQUEST_MAPPING.getPkg());
                    List<AnnotVal> annotValList = annot.getAnnotVals();
                    annotValList.add(new AnnotVal().setKey("value").setValue("/" + tokens[1].trim()));
                    interfaceAnnotations.add(annot);
                    interfaceAnnotations.add(controllerAnnot);
                    Children uriChildren = attached.getChildren();
                    generateUriAnnot(moduleList, module, interfac, annotValList, uriChildren);
                    break;
                case GENERIC_TYP:
                    String genericTyp = firstLetterToUpper(tokens[1].trim());
                    interfac.setGenericTyp(genericTyp);
                    Children attachedChildren = attached.getChildren();
                    if (nonNull(attachedChildren)) {
                        generateImport(attachedChildren, moduleList, module, interfac);
                    }
                    break;
                default:
                    generateOfAttachedWithModule(attached, moduleList, module);
                    break;
            }
        }
    }

    private static void generateUriAnnot(@NonNull List<Module> moduleList, @NonNull Module module,
                                         @NonNull Interfac interfac, @NonNull List<AnnotVal> annotValList,
                                         @Nullable Children uriChildren) {
        if (nonNull(uriChildren)) {
            List<Attached> uriAttachedList = uriChildren.getAttached();
            for (Attached annotValAttached : uriAttachedList) {
                String annotValTitle = annotValAttached.getTitle().trim();
                String[] annotValSplits = splitInto3Parts(annotValTitle);
                ParamType annotValParamType = getParamType(annotValSplits[0].trim());
                if (nonNull(annotValParamType) && ANNOT_VAL.equals(annotValParamType)) {
                    AnnotVal annotVal = generateAnnotVal(annotValAttached, moduleList,
                            module, annotValSplits, interfac);
                    annotValList.add(annotVal);
                }
            }
        }
    }

    @NonNull
    private static Curl generateCurl(@NonNull List<Module> moduleList, @NonNull Module module,
                                     @NonNull Interfac interfac, @NonNull Attached attached,
                                     @NonNull ParamType paramType, @NonNull String curlName,
                                     @NonNull String curlComment, @NonNull Boolean isRestMethod) {
        String methodName = paramType.name();
        if (METHOD.equals(paramType)) {
            methodName = "";
        }
        curlName = firstLetterToLower(strToUpperCamelWithPinYin(curlName));
        Curl curl = new Curl().setName(curlName).setDescr(curlComment)
                .setHttpMethod(methodName).setRestMethod(isRestMethod);
        Children attachedChildren = attached.getChildren();
        if (nonNull(attachedChildren)) {
            resolveMethod(attached.getChildren(), moduleList, module, interfac, curl);
        }
        return curl;
    }

    private static void resolveMethod(@NonNull Children children, @NonNull List<Module> moduleList, @NonNull Module module,
                                      @NonNull Interfac interfac, @NonNull Curl curl) {
        List<Attached> attachedList = children.getAttached();
        if (attachedList.size() == 0) {
            return;
        }

        // 遍历 in/out/注解/泛型
        Set<Annot> annotations = curl.getAnnotations();
        int index = 1;
        for (Attached attached : attachedList) {
            String title = attached.getTitle().trim();
            //noinspection DuplicatedCode
            String[] splits = splitInto3Parts(title);
            ParamType paramType = getParamType(splits[0].trim());
            if (splits.length != 3 || isNull(paramType)) {
                LOG.debug("title [" + title + "] 格式错误, 标准格式: paramType/paramName/[description]");
                generateOfAttachedWithModule(attached, moduleList, module);
                continue;
            }
            switch (paramType) {
                case IN:
                    if (hasText(splits[1].trim())) {
                        // title 格式 in/type/descr
                        generateInOrOut(attached, moduleList, module, interfac, curl, splits, TRUE, index);
                    } else {
                        // title 格式 in//
                        generateInOrOut(attached, moduleList, module, interfac, curl, null, TRUE, 1);
                    }
                    break;
                case OUT:
                    if (hasText(splits[1].trim())) {
                        // title 格式 out/type/descr
                        generateInOrOut(attached, moduleList, module, interfac, curl, splits, FALSE, 1);
                    } else {
                        // title 格式 out//
                        generateInOrOut(attached, moduleList, module, interfac, curl, null, FALSE, 1);
                    }
                    break;
                case ANNOTES:
                    Children annotesChildren = attached.getChildren();
                    if (nonNull(annotesChildren)) {
                        generateAnnotes(annotesChildren, moduleList, module, interfac, curl.getAnnotations());
                    }
                    break;
                case ANNOT:
                case ANNOTATION:
                    Annot annotation = generateAnnot(attached, moduleList, module, splits, interfac);
                    annotations.add(annotation);
                    break;
                case GENERIC_VAL:
                    curl.setGenericVal(firstLetterToUpper(splits[1].trim()));
                    Children genericValChildren = attached.getChildren();
                    if (nonNull(genericValChildren)) {
                        generateImport(genericValChildren, moduleList, module, interfac);
                    }
                    break;
                case URI:
                    String method = curl.getHttpMethod();
                    Set<String> imports = interfac.getImports();
                    if (!hasText(method)) {
                        method = splits[2].trim().toUpperCase();
                    }
                    String annotName;
                    switch (method) {
                        case "GET":
                            annotName = Annotation.GET_MAPPING.getAnnotName();
                            curl.setHttpMethod("GET");
                            curl.setRestMethod(true);
                            imports.add(Annotation.GET_MAPPING.getPkg());
                            break;
                        case "POST":
                            annotName = Annotation.POST_MAPPING.getAnnotName();
                            curl.setHttpMethod("POST");
                            curl.setRestMethod(true);
                            imports.add(Annotation.POST_MAPPING.getPkg());
                            break;
                        case "PUT":
                            annotName = Annotation.PUT_MAPPING.getAnnotName();
                            curl.setHttpMethod("PUT");
                            curl.setRestMethod(true);
                            imports.add(Annotation.PUT_MAPPING.getPkg());
                            break;
                        case "DEL":
                            annotName = Annotation.DELETE_MAPPING.getAnnotName();
                            curl.setHttpMethod("DEL");
                            curl.setRestMethod(true);
                            imports.add(Annotation.DELETE_MAPPING.getPkg());
                            break;
                        default:
                            annotName = Annotation.REQUEST_MAPPING.getAnnotName();
                            curl.setHttpMethod("GET");
                            imports.add(Annotation.REQUEST_MAPPING.getPkg());
                            break;
                    }
                    Annot annot = new Annot().setName(annotName);
                    List<AnnotVal> annotValList = annot.getAnnotVals();
                    annotValList.add(new AnnotVal().setKey("value").setValue("/" + splits[1].trim()));
                    annotations.add(annot);
                    Children uriChildren = attached.getChildren();
                    generateUriAnnot(moduleList, module, interfac, annotValList, uriChildren);
                    break;
                default:
                    generateOfAttachedWithModule(attached, moduleList, module);
                    break;
            }
            index++;
        }

    }

    private static void generateAnnotes(@NonNull Children children, @NonNull List<Module> moduleList,
                                        @NonNull Module module, @NonNull Import importObj,
                                        @NonNull Set<Annot> annotations) {
        List<Attached> attachedList = children.getAttached();
        if (attachedList.size() == 0) {
            return;
        }
        for (Attached attached : attachedList) {
            String title = attached.getTitle();
            @SuppressWarnings("DuplicatedCode")
            String[] splits = splitInto3Parts(title);
            ParamType paramType = getParamType(splits[0].trim());
            if (splits.length != 3 || isNull(paramType)) {
                LOG.debug("title [" + title + "] 格式错误, 标准格式: paramType/paramName/[description]");
                generateOfAttachedWithModule(attached, moduleList, module);
                continue;
            }
            if (ANNOT.equals(paramType) || ANNOTATION.equals(paramType)) {
                Annot annot = generateAnnot(attached, moduleList, module, splits, importObj);
                annotations.add(annot);
            }
        }
    }

    /**
     * 生成输入输出参数
     *
     * @param attached   {@link Attached}
     * @param moduleList {@link Module} 列表
     * @param module     {@link Module}
     * @param interfac   {@link Interfac}
     * @param curl       {@link Curl}
     * @param tokens     当 inOrOut 的 {@link Attached#getTitle()} 的格式为 [in/out]/type/descr 时不为 null,
     *                   当 inOrOut 的 {@link Attached#getTitle()} 的格式为 [in/out]// 时为 null,
     * @param inOrOut    true 表示 {@link ParamType#IN}, false 表示 {@link ParamType#OUT}.
     * @param paramIndex 出入参数索引.
     */
    private static void generateInOrOut(@NonNull Attached attached, @NonNull List<Module> moduleList,
                                        @NonNull Module module, @NonNull Interfac interfac,
                                        @NonNull Curl curl, @Nullable String[] tokens,
                                        @NonNull Boolean inOrOut, @NonNull Integer paramIndex) {
        // 当 inOrOut 的 attached title 的格式为 [in/out]/type/descr 的情况
        if (nonNull(tokens)) {
            String inOrOutParamType = tokens[1].trim();
            ParamType paramType = getParamType(inOrOutParamType);
            if (paramType == null) {
                paramType = ENTITY;
            }
            // index = 0 为 newType, index = 1 为 newParamName
            String[] typeAndParamNameArray = new String[2];
            generateParamName(paramType, typeAndParamNameArray, inOrOutParamType, inOrOut, paramIndex);
            String newType = typeAndParamNameArray[0];
            String newParamName = typeAndParamNameArray[1];
            String newDescr = tokens[2].trim();
            String title = newType.concat("/").concat(newParamName).concat("/").concat(newDescr);
            String[] paramSplits = new String[]{newType, newParamName, newDescr};
            // 新建出入参数的 attached
            Attached paramAttached = new Attached();
            paramAttached.setId(attached.getId());
            paramAttached.setTitle(title);
            paramAttached.setChildren(attached.getChildren());
            paramAttached.setComments(attached.getComments());
            paramAttached.setNotes(attached.getNotes());
            // 解析出入参数的 attached
            resolveParam(moduleList, module, interfac, curl, inOrOut, paramAttached, paramSplits, paramType);
            return;
        }

        // 当 inOrOut 的 attached title 的格式为 [in/out]// 的情况
        Children children = attached.getChildren();
        if (isNull(children)) {
            return;
        }
        List<Attached> attachedList = children.getAttached();
        if (attachedList.size() == 0) {
            return;
        }
        // 遍历获取入参或返回参数
        for (Attached paramAttached : attachedList) {
            String title = paramAttached.getTitle().trim();
            String[] splits = splitInto3Parts(title);
            ParamType paramType = getParamType(splits[0].trim());
            resolveParam(moduleList, module, interfac, curl, inOrOut, paramAttached, splits, paramType);
        }
    }

    // inOrOut: true 表示 {@link ParamType#IN}, false 表示 {@link ParamType#OUT}.
    @NonNull
    private static void generateParamName(@NonNull ParamType paramType, @NonNull String[] typeAndParamNameArray,
                                          @NonNull String inOrOutParamType, @NonNull Boolean inOrOut,
                                          @NonNull Integer paramIndex) {
        int genericIndex = inOrOutParamType.indexOf("<");
        int arrayIndex = inOrOutParamType.indexOf("[");

        String paramName;
        switch (paramType) {
            case ENTITY:
                // entityName<String, String[]>/entityName<String>/entityName/etc
                if (genericIndex != -1 && (arrayIndex == -1 || genericIndex < arrayIndex)) {
                    // <String, String[]>/<String>/etc
                    String genericVal = inOrOutParamType.substring(genericIndex);
                    // type
                    typeAndParamNameArray[0] = ENTITY.name().toLowerCase() + genericVal;
                    // paramName
                    typeAndParamNameArray[1] = inOrOutParamType.substring(0, genericIndex);
                }
                // entityName[]
                else if (arrayIndex != -1) {
                    // type
                    typeAndParamNameArray[0] = ENTITY.name().toLowerCase() + "[]";
                    // paramName
                    typeAndParamNameArray[1] = inOrOutParamType.substring(0, arrayIndex);
                }
                // entityName
                else {
                    // type
                    typeAndParamNameArray[0] = ENTITY.name().toLowerCase();
                    // paramName
                    typeAndParamNameArray[1] = inOrOutParamType;
                }
                break;
            case LIST:
            case MAP:
            case SET:
            case COLLECTION:
                // type
                typeAndParamNameArray[0] = inOrOutParamType;
                // type<String, String[]>/type<String, Map<String, Object[]>>/type<String>/type/etc
                if (genericIndex != -1) {
                    // <String, String[]>/<String, Map<String, Object[]>>/<String>/etc
                    String genericVal = inOrOutParamType.substring(genericIndex);
                    // paramName
                    paramName = resolveGenericParamName(genericVal, paramType);
                }
                // type
                else {
                    paramName = paramType.name().toLowerCase();
                }
                if (inOrOut) {
                    paramName = paramName + paramIndex;
                }
                typeAndParamNameArray[1] = paramName;
                break;
            default:
                // type
                typeAndParamNameArray[0] = inOrOutParamType;
                // type[]
                if (arrayIndex != -1) {
                    paramName = paramType.name().toLowerCase().concat("Array");
                }
                // type
                else {
                    paramName = paramType.name().toLowerCase();
                }
                if (inOrOut) {
                    paramName = paramName + paramIndex;
                }
                typeAndParamNameArray[1] = paramName;
        }
    }

    /**
     * 根据参数泛型值生成参数名称.<br>
     * 示例:
     * <pre>
     *      genericVal                              ParamType               return
     *      &lt;String, String[]&gt;                      Map                     stringStringMap
     *      &lt;String, Map&lt;String, Object[]&gt;&gt;         Map                     stringMapMap
     *      &lt;String&gt;                                list/set/collection     stringList/stringSet/stringCollection
     *      etc                                     ..                      ..
     * </pre>
     *
     * @param genericVal 参数泛型值, 如 // &lt;String, String[]&gt;/&lt;String, Map&lt;String, Object[]&gt;&gt;/String/etc
     * @param paramType  参数类型 {@link ParamType}
     * @return 参数名称
     */
    @NonNull
    private static String resolveGenericParamName(@NonNull String genericVal, @NonNull ParamType paramType) {
        // String, String[]/String, Map<String, Object[]>/String/etc
        genericVal = genericVal.substring(1, genericVal.length() - 1);
        String[] genericTypes = genericVal.split(",");
        // 从泛型中获取两次对象名称.
        int limitCount = 2;
        int loopCount = 1;
        StringBuilder paramNameSb = new StringBuilder();
        for (String type : genericTypes) {
            String genericType = type.trim();
            if (hasText(genericType)) {
                int secGenericIndex = genericType.indexOf("<");
                int secArrayIndex = genericType.indexOf("[");
                // Map<String, Object[]>
                if (secGenericIndex != -1 && (secArrayIndex == -1 || secGenericIndex < secArrayIndex)) {
                    paramNameSb.append(strToUpperCamelWithPinYin(genericType.substring(0, secGenericIndex)));
                }
                // Object[]
                else if (secArrayIndex != -1) {
                    paramNameSb.append(strToUpperCamelWithPinYin(genericType.substring(0, secArrayIndex)));
                }
                // String
                else {
                    paramNameSb.append(strToUpperCamelWithPinYin(genericType));
                }
            }
            if (++loopCount > limitCount) {
                break;
            }
        }
        /*
            genericVal                              ParamType               return
            <String, String[]>                      Map                     stringStringMap
            <String, Map<String, Object[]>>         Map                     stringMapMap
            <String>                                list/set/collection     stringList/stringSet/stringCollection
            etc                                     ..                      ..
         */
        return firstLetterToLower(paramNameSb.append(firstLetterToUpper(paramType.name().toLowerCase())).toString());
    }

    private static void resolveParam(@NonNull List<Module> moduleList, @NonNull Module module,
                                     @NonNull Interfac interfac, @NonNull Curl curl,
                                     @NonNull Boolean inOrOut, @NonNull Attached paramAttached,
                                     @NonNull String[] splits, @Nullable ParamType paramType) {
        if (splits.length != 3 || isNull(paramType)) {
            LOG.debug("title [" + paramAttached.getTitle() + "] 格式错误, 标准格式: paramType/paramName/[description]");
            generateOfAttachedWithModule(paramAttached, moduleList, module);
            return;
        }
        List<Param> inputParams = curl.getInputParams();
        if (!ENTITY.equals(paramType) && !GENERIC.equals(paramType)) {
            String type = paramType.getType();
            // 不是 entity/generic 类型时, 没有类型值则
            if (!hasText(type)) {
                Children attachedChildren = paramAttached.getChildren();
                if (nonNull(attachedChildren)) {
                    generateOfChildren(attachedChildren, moduleList, module);
                    return;
                }
            }
        }
        if (inOrOut) {
            inputParams.add(generateParam(paramAttached, moduleList, module, splits, paramType, interfac));
        } else {
            curl.setOutputParam(generateParam(paramAttached, moduleList, module, splits, paramType, interfac));
        }
    }

    //  ------------------------------- entity -------------------------------

    /**
     * 生成 {@link Entity}
     *
     * @param children   {@link Children}
     * @param moduleList {@link Module} 列表
     * @param module     {@link Module}
     * @param pTitle     上级节点 title
     * @param pImport    {@link Import}
     */
    @Nullable
    private static void generateEntity(@NonNull Children children, @NonNull List<Module> moduleList,
                                       @NonNull Module module, @NonNull String pTitle,
                                       @Nullable Import pImport) {
        String[] splits = splitInto3Parts(pTitle);
        if (splits.length != 3) {
            LOG.debug("title [" + pTitle + "] 格式错误, 标准格式: TitleType/TypeName/[description]");
            generateOfChildren(children, moduleList, module, null, pTitle);
            return;
        }

        String name = splits[1].trim();
        String commentStr = removeNewlines(splits[2].trim());
        String entityName = strToUpperCamelWithPinYin(name);

        // 新增 entity
        Entity entity = new Entity().setName(entityName)
                .setDescr(commentStr);

        if (pImport instanceof Entity) {
            entity.setApi(((Entity) pImport).getApi());
        }
        // 是否新建对象
        boolean isNewCreatedEntity = false;

        // 遍历 entity 字段
        Set<String> imports = entity.getImports();
        Set<Annot> annotSet = entity.getAnnotations();
        List<Param> entityFields = entity.getFields();
        List<Attached> attachedList = children.getAttached();
        if (isNull(attachedList) || attachedList.size() == 0) {
            return;
        }
        String fieldTitle;
        for (Attached attached : attachedList) {
            fieldTitle = attached.getTitle();
            splits = splitInto3Parts(fieldTitle);
            ParamType paramType = getParamType(splits[0].trim(), FALSE);
            ColumnType columnType = null;
            if (isNull(paramType)) {
                columnType = getColumnType(splits[0].trim(), FALSE);
            }
            if (splits.length != 3 || (isNull(paramType) && isNull(columnType))) {
                LOG.debug("title [" + fieldTitle + "] 格式错误, 标准格式: paramType/paramName/[description]");
                generateOfAttachedWithModule(attached, moduleList, module);
                continue;
            }
            String orgName = splits[1].trim();
            if (isNull(paramType)) {
                String javaType = columnType.getJavaType();
                String paramName = firstLetterToLower(strToUpperCamelWithPinYin(orgName));
                Param field = new Param().setTyp(javaType)
                        .setName(paramName)
                        .setDescr(removeNewlines(splits[2].trim()));
                entityFields.add(field);
                isNewCreatedEntity = true;
                Children fieldChildren = attached.getChildren();
                if (nonNull(fieldChildren)) {
                    // 生成 annots/generic/genericTyp
                    generateAnnotAndGeneric(fieldChildren, moduleList, module, null, entity, field);
                }
            } else if (ParamType.ENTITY.equals(paramType)) {
                Param entityParam = generateParam(attached, moduleList, module, splits, paramType, entity);
                if (nonNull(entityParam)) {
                    entityFields.add(entityParam);
                    isNewCreatedEntity = true;
                }
            } else if (IMPORT.equals(paramType) && nonNull(pImport)) {
                generateImportOfAttached(attached, moduleList, module, paramType, splits, pImport);
            } else if (ANNOTES.equals(paramType)) {
                Children annotesChildren = attached.getChildren();
                if (nonNull(annotesChildren)) {
                    if (nonNull(pImport)) {
                        generateAnnotes(annotesChildren, moduleList, module, pImport, annotSet);
                    } else {
                        generateAnnotes(annotesChildren, moduleList, module, entity, annotSet);
                    }
                }
            } else if (ANNOT.equals(paramType) || ANNOTATION.equals(paramType)) {
                Annot annotation;
                if (nonNull(pImport)) {
                    annotation = generateAnnot(attached, moduleList, module, splits, pImport);
                } else {
                    annotation = generateAnnot(attached, moduleList, module, splits, entity);
                }
                annotSet.add(annotation);
            } else if (ANNOT_E.equals(paramType)) {
                Annot annotation = generateAnnot(attached, moduleList, module, splits, entity);
                annotSet.add(annotation);
            } else if (GENERIC_TYP_E.equals(paramType)) {
                String genericTyp = firstLetterToUpper(orgName);
                entity.setGenericTyp(genericTyp);
                Children attachedChildren = attached.getChildren();
                if (nonNull(attachedChildren)) {
                    generateImport(attachedChildren, moduleList, module, entity);
                }
            } else if (GENERIC_TYP.equals(paramType) && isNull(pImport)) {
                String genericTyp = firstLetterToUpper(orgName);
                entity.setGenericTyp(genericTyp);
                Children attachedChildren = attached.getChildren();
                if (nonNull(attachedChildren)) {
                    generateImport(attachedChildren, moduleList, module, entity);
                }
            } else {
                String paramTypePkg = paramType.getPkg();
                if (hasText(paramTypePkg)) {
                    imports.add(paramTypePkg);
                }
                Param fieldParam = generateParam(attached, moduleList, module, splits, paramType, entity);
                if (nonNull(fieldParam)) {
                    entityFields.add(fieldParam);
                    isNewCreatedEntity = true;
                }
            }
        }
        if (isNewCreatedEntity) {
            module.addEntity(entity);
            // 缓存包后置处理信息
            if (pImport instanceof Entity) {
                module.getEntityImports().put(entity.getName(), ((Entity) pImport));
            }
        }

        if (pImport instanceof Interfac) {
            entity.setApi(((Interfac) pImport).getApi());
            // 缓存包后置处理信息
            module.getApiImports().put(entity.getName(), ((Interfac) pImport));
        }

    }

    private static void generateImport(@NonNull Children children, @NonNull List<Module> moduleList,
                                       @NonNull Module module, @NonNull Import importObj) {
        List<Attached> attachedList = children.getAttached();
        if (attachedList.size() == 0) {
            return;
        }
        for (Attached attached : attachedList) {
            String title = attached.getTitle().trim();
            String[] splits = splitInto3Parts(title);
            ParamType paramType = getParamType(splits[0].trim(), FALSE);
            if (splits.length < 2 || isNull(paramType)) {
                LOG.debug("title [" + title + "] 格式错误, 标准格式: type/name/comment");
                generateOfAttachedWithModule(attached, moduleList, module);
                continue;
            }
            generateImportOfAttached(attached, moduleList, module, paramType, splits, importObj);
        }

    }

    private static void generateImportOfAttached(@NonNull Attached attached, @NonNull List<Module> moduleList,
                                                 @NonNull Module module, @NonNull ParamType paramType,
                                                 @NonNull String[] tokens, @NonNull Import importObj) {
        if (IMPORT.equals(paramType)) {
            String pkg = firstLetterToLower(tokens[1].trim());
            String entityName = org.apache.commons.lang3.StringUtils.substringAfterLast(pkg, ".");
            importObj.getImports().add(pkg);
            module.getOtherImports().put(entityName, pkg);
            Children attachedChildren = attached.getChildren();
            if (nonNull(attachedChildren)) {
                generateOfChildren(attachedChildren, moduleList, module);
            }
        } else {
            generateOfAttachedWithModule(attached, moduleList, module);
        }
    }

    @NonNull
    private static Annot generateAnnot(@NonNull Attached attached, @NonNull List<Module> moduleList,
                                       @NonNull Module module, @NonNull String[] tokens,
                                       @NonNull Import importObj) {

        // 新增 annot
        String annotName = tokens[1].trim();
        Annotation annotation = getAnnotation(annotName);
        Annot annot;
        if (nonNull(annotation)) {
            annot = new Annot().setName(annotation.getAnnotName());
            importObj.getImports().add(annotation.getPkg());
        } else {
            annotName = annotName.contains("_") ? underscoreToUpperCamel(annotName) : annotName;
            annot = new Annot().setName(firstLetterToUpper(annotName));
        }

        List<AnnotVal> annotVals = annot.getAnnotVals();
        Children children = attached.getChildren();
        // 添加 annot 的 键值对
        if (nonNull(children)) {
            List<Attached> attachedList = children.getAttached();
            for (Attached annotAttached : attachedList) {
                String title = annotAttached.getTitle().trim();
                String[] splits = splitInto3Parts(title);
                ParamType paramType = getParamType(splits[0].trim());
                if (splits.length != 3 || isNull(paramType)) {
                    LOG.debug("title [" + title + "] 格式错误, 标准格式: annot_val/annot_valKey/annot_valValue");
                    generateOfChildren(children, moduleList, module, null, title);
                    continue;
                }
                if (ANNOT_VAL.equals(paramType)) {
                    AnnotVal annotVal = generateAnnotVal(annotAttached, moduleList, module, splits, importObj);
                    if (nonNull(annotVal)) {
                        annotVals.add(annotVal);
                    }
                } else if (IMPORT.equals(paramType)) {
                    generateImportOfAttached(attached, moduleList, module, paramType, splits, importObj);
                } else {
                    generateOfAttachedWithModule(attached, moduleList, module);
                }
            }
        }

        return annot;
    }

    @Nullable
    private static AnnotVal generateAnnotVal(@NonNull Attached attached, @NonNull List<Module> moduleList,
                                             @NonNull Module module, @NonNull String[] tokens,
                                             @NonNull Import importObj) {

        // 新增 annot 的 键值对
        String annotValKey = firstLetterToUpper(tokens[1].trim());
        String annotValValue = removeNewlines(tokens[2].trim());

        // 添加 import
        Children children = attached.getChildren();
        if (nonNull(children)) {
            generateImport(children, moduleList, module, importObj);
        }

        return new AnnotVal().setKey(annotValKey)
                .setValue(annotValValue);
    }

    @Nullable
    private static Param generateParam(@NonNull Attached attached, @NonNull List<Module> moduleList,
                                       @NonNull Module module, @NonNull String[] tokens,
                                       @NonNull ParamType pParamType, @NonNull Import pImport) {

        // 新增 param
        String typ = pParamType.getType();
        String orgType = tokens[0].trim();
        String orgName = tokens[1].trim();
        String paramName = strToUpperCamelWithPinYin(orgName);
        String entityArrayParamName = null;
        switch (pParamType) {
            // 实体对象
            case ENTITY:
                Children children = attached.getChildren();
                if (nonNull(children)) {
                    if (pImport instanceof Entity) {
                        generateEntity(children, moduleList, module, attached.getTitle(), pImport);
                    } else if (pImport instanceof Interfac) {
                        generateEntity(children, moduleList, module, attached.getTitle(), pImport);
                    }
                }
                if (orgType.contains("[")) {
                    typ = firstLetterToUpper(paramName) + "[]";
                    entityArrayParamName = firstLetterToLower(paramName).concat("Array");
                } else {
                    typ = firstLetterToUpper(paramName);
                }
                break;
            // 泛型值
            case LIST:
            case SET:
            case MAP:
            case COLLECTION:
                typ = firstLetterToUpper(orgType);
                break;
            default:
                // 数组
                if (orgType.contains("[") && !orgType.contains("<")) {
                    typ = orgType.contains("_") ? underscoreToUpperCamel(orgType) : firstLetterToUpper(orgType);
                }
                break;
        }

        if (!hasText(typ)) {
            String orgTyp = tokens[0].trim();
            typ = orgTyp.contains("_") ? underscoreToUpperCamel(orgTyp) : firstLetterToUpper(orgTyp);
        }
        Param param = new Param().setTyp(typ)
                .setName(isNull(entityArrayParamName) ? firstLetterToLower(paramName)
                        : entityArrayParamName)
                .setDescr(removeNewlines(tokens[2].trim()));

        Children children = attached.getChildren();
        if (isNull(children)) {
            return param;
        }
        // 生成 annots/generic/genericTyp
        generateAnnotAndGeneric(children, moduleList, module, pParamType, pImport, param);

        return param;
    }

    private static void generateAnnotAndGeneric(@NonNull Children children, @NonNull List<Module> moduleList,
                                                @NonNull Module module, @Nullable ParamType pParamType,
                                                @NonNull Import pImport, @NonNull Param param) {
        // 遍历 annots/generic/genericTyp
        Set<Annot> annots = param.getAnnotations();
        List<Attached> attachedList = children.getAttached();
        String paramTitle;
        String[] splits;
        for (Attached paramAttached : attachedList) {
            paramTitle = paramAttached.getTitle();
            splits = splitInto3Parts(paramTitle);
            String paramType = splits[0].trim();
            ParamType paramTyp = getParamType(paramType, false);
            ParamProperty paramProperty = null;
            if (isNull(paramTyp)) {
                paramProperty = getParamProperty(paramType);
            }
            if (splits.length != 3 || (isNull(paramTyp) && isNull(paramProperty))) {
                LOG.debug("title [" + paramTitle + "] 格式错误, 标准格式: type/key/value");
                generateOfAttachedWithModule(paramAttached, moduleList, module);
                continue;
            }
            if (isNull(paramTyp)) {
                if (ARRAY_TYP.equals(paramProperty)) {
                    // 基本数据类型处理
                    param.setTyp(param.getTyp().concat("[]"));
                } else {
                    generateOfAttachedWithModule(paramAttached, moduleList, module);
                }
            } else if (ANNOTES.equals(paramTyp)) {
                Children annotesChildren = paramAttached.getChildren();
                if (nonNull(annotesChildren)) {
                    generateAnnotes(annotesChildren, moduleList, module, pImport, param.getAnnotations());
                }
            } else if (ANNOT.equals(paramTyp) || ANNOTATION.equals(paramTyp)) {
                Annot annotation = generateAnnot(paramAttached, moduleList, module, splits, pImport);
                annots.add(annotation);
            } else if (GENERIC_TYP.equals(paramTyp)) {
                String genericTyp = firstLetterToUpper(splits[1].trim());
                if (hasText(genericTyp)) {
                    param.setTyp(param.getTyp().concat("<").concat(genericTyp).concat(">"));
                }
                Children attachedChildren = paramAttached.getChildren();
                if (nonNull(attachedChildren)) {
                    generateImport(attachedChildren, moduleList, module, pImport);
                }
            } else if (nonNull(pParamType) && GENERIC.equals(pParamType) && GENERIC_VAL.equals(paramTyp)) {
                String genericVal = firstLetterToUpper(splits[1].trim());
                param.setTyp(genericVal);
                Children paramAttachedChildren = paramAttached.getChildren();
                if (nonNull(paramAttachedChildren)) {
                    generateImport(paramAttachedChildren, moduleList, module, pImport);
                    generateOfChildren(children, moduleList, module);
                }
                // 给对象添加泛型值
                if (pImport instanceof Entity) {
                    ((Entity) pImport).setGenericTyp(genericVal);
                } else if (pImport instanceof Interfac) {
                    ((Interfac) pImport).setGenericTyp(genericVal);
                }
            } else if (IMPORT.equals(paramTyp)) {
                generateImportOfAttached(paramAttached, moduleList, module, paramTyp, splits, pImport);
            } else {
                Children paramAttachedChildren = paramAttached.getChildren();
                if (nonNull(paramAttachedChildren)) {
                    generateOfChildren(children, moduleList, module);
                }
            }
        }
    }

    //  ------------------------------- database -------------------------------

    private static void generateDatabase(@NonNull Children children, @NonNull List<Module> moduleList,
                                         @NonNull Module module, @NonNull String pTitle) {
        String[] splits = splitInto3Parts(pTitle);
        if (splits.length != 3) {
            LOG.debug("title [" + pTitle + "] 格式错误, 标准格式: TitleType/TypeName/[description]");
            generateOfChildren(children, moduleList, module, null, pTitle);
            return;
        }
        // 新增 database
        String databaseName = strToUnderscoreWithPinYin(splits[1].trim());
        String commentStr = removeNewlines(splits[2].trim());
        Database database = new Database().setName(camelToUnderscore(databaseName))
                .setComment(commentStr);
        module.addDatabase(database);

        // 遍历 table
        List<Attached> attachedList = children.getAttached();
        if (isNull(attachedList) || attachedList.size() == 0) {
            return;
        }
        String tableTitle;
        for (Attached attached : attachedList) {
            tableTitle = attached.getTitle();
            splits = splitInto3Parts(tableTitle);
            if (splits.length != 3) {
                LOG.debug("title [" + tableTitle + "] 格式错误, 标准格式: TitleType/TypeName/[description]");
                generateOfAttachedWithModule(attached, moduleList, module);
                continue;
            }

            Children tableChildren = attached.getChildren();
            if (nonNull(tableChildren)) {
                generateTable(tableChildren, moduleList, module, database, splits);
            }
        }
    }

    private static void generateTable(@NonNull Children children, @NonNull List<Module> moduleList,
                                      @NonNull Module module, @NonNull Database database,
                                      @NonNull String[] tokens) {
        // 判断是否 table 命名空间
        String type = tokens[0].trim();
        try {
            TiTleType tiTleType = TiTleType.valueOf(type.toUpperCase());
            if (!TABLE.equals(tiTleType)) {
                generateOfChildren(children, moduleList, module, null, "");
                return;
            }
        } catch (Exception e) {
            LOG.error("title [" + type + "] 不能转换为 TitleType", e);
            generateOfChildren(children, moduleList, module, null, "");
            return;
        }

        // add table
        String tableName = strToUnderscoreWithPinYin(tokens[1].trim());
        String tableDecr = removeNewlines(tokens[2].trim());
        Table table = new Table().setName(tableName).setComment(tableDecr);
        database.getTables().add(table);

        // 遍历 column
        // Map(pkProp, column)
        Map<String, String> pkMap = new TreeMap<>();
        // Map(uniqueGroupProp, column)
        Map<String, String> uniqueMap = new TreeMap<>();
        // Map(indexGroupProp, column)
        Map<String, String> indexMap = new TreeMap<>();
        List<Attached> attachedList = children.getAttached();
        if (isNull(attachedList) || attachedList.size() == 0) {
            return;
        }
        for (Attached attached : attachedList) {
            generateColumn(attached, moduleList, module, table, pkMap, uniqueMap, indexMap);
        }

        // 拼装 pk 索引
        if (pkMap.size() == 0) {
            // 遍历 columns 查找是否有 colName = id 的字段
            Optional<Column> columnOptional = table.getColumns()
                    .stream()
                    .filter(col -> "id".equals(col.getName()))
                    .findFirst();


            // 有 id 字段设置为主键
            if (columnOptional.isPresent()) {
                Column column = columnOptional.get();
                String columnType = column.getTyp();
                int indexOf = columnType.indexOf("(");
                columnType = indexOf != -1 ? columnType.substring(0, indexOf) : columnType;
                if (ColumnType.isInteger(columnType)) {
                    column.setAutoIncrement(true);
                }
                column.setPk(true).setNotnull(true);
            }
            // 没有 id 字段添加 id 字段并设置为自增主键
            else {
                String typ = BIGINT.getType().concat("(").concat(BIGINT.getDefValue()).concat(")");
                table.getColumns().add(new Column().setName("id")
                        .setTyp(typ)
                        .setComment("主键id")
                        .setPk(true)
                        .setNotnull(true)
                        .setAutoIncrement(true));
            }
            table.setPkStatement("PRIMARY KEY (`id`)");
        } else {
            // 拼装
            final StringBuilder pkStat = new StringBuilder();
            pkStat.append("PRIMARY KEY (`");
            pkMap.forEach((pkGroup, column) -> pkStat.append(column).append("`, `"));
            pkStat.setLength(pkStat.length() - 3);
            pkStat.append(")");
            table.setPkStatement(pkStat.toString());
        }

        // 拼装 unique 索引
        resolveIndexStat(table.getUniquesStatements(), uniqueMap, "UNIQUE KEY");
        // 拼装 index 索引
        resolveIndexStat(table.getIndexesStatements(), indexMap, "INDEX");
    }

    /**
     * 解析为索引语句, 不包含末尾逗号
     *
     * @param indexStatList 存放索引语句列表
     * @param indexMap      Map(idxGroupProp, column)
     * @param idxKeyword    索引关键字, 如: UNIQUE KEY/INDEX/KEY
     */
    private static void resolveIndexStat(@NonNull List<String> indexStatList, @NonNull Map<String, String> indexMap,
                                         @NonNull String idxKeyword) {
        if (indexMap.isEmpty()) {
            return;
        }
        // 分组: TreeMap(indexGroup[indexPropPre], TreeMap(indexProp, column))
        TreeMap<String, TreeMap<String, String>> groupMap =
                indexMap.entrySet()
                        .stream()
                        .collect(groupingBy(entry -> {
                                    String indexGroup = entry.getKey();
                                    String[] splits = indexGroup.split(COLUMN_PROPERTY_SEPARATOR);
                                    if (splits.length <= 2) {
                                        return indexGroup;
                                    } else {
                                        return splits[0].concat(COLUMN_PROPERTY_SEPARATOR).concat(splits[1]);
                                    }
                                },
                                TreeMap::new,
                                toMap(Map.Entry::getKey, Map.Entry::getValue,
                                        (ignore, ignore2) -> ignore, TreeMap::new)));

        // TreeMap(indexGroup[indexPropPre], TreeMap(indexProp, column))
        groupMap.forEach((indexGroup, map) -> {
            final StringBuilder idxStat = new StringBuilder();
            final StringBuilder idxColumns = new StringBuilder();
            idxColumns.append("(`");
            idxStat.append(idxKeyword).append(" `").append(indexGroup.toUpperCase()).append(COLUMN_PROPERTY_SEPARATOR);
            map.forEach((propName, colName) -> {
                idxStat.append(colName.toUpperCase()).append(COLUMN_PROPERTY_SEPARATOR).append("AND_");
                idxColumns.append(colName).append("`, `");
            });
            idxColumns.setLength(idxColumns.length() - 3);
            idxColumns.append(")");
            idxStat.setLength(idxStat.length() - (3 + 2 * COLUMN_PROPERTY_SEPARATOR.length()));
            idxStat.append("` ").append(idxColumns).append(" USING BTREE");
            indexStatList.add(idxStat.toString());
        });
    }

    private static void generateColumn(@NonNull Attached attached, @NonNull List<Module> moduleList,
                                       @NonNull Module module, @NonNull Table table,
                                       @NonNull Map<String, String> pkMap, @NonNull Map<String, String> uniqueMap,
                                       @NonNull Map<String, String> indexMap) {
        // add column
        String title = attached.getTitle().trim();
        String[] splits = splitInto3Parts(title);
        if (splits.length != 3) {
            LOG.debug("title [" + title + "] 格式错误, 标准格式: columnType/columnName/[comment]");
            generateOfAttachedWithModule(attached, moduleList, module);
            return;
        }
        String columnTypeStr = splits[0].trim();
        String columnName = strToUnderscoreWithPinYin(splits[1].trim());
        String comment = removeNewlines(splits[2].trim());
        ColumnType columnType = getColumnType(columnTypeStr);
        if (isNull(columnType)) {
            generateOfAttachedWithModule(attached, moduleList, module);
            return;
        }
        String typ;
        String defValue = columnType.getDefValue();
        if (hasText(defValue)) {
            typ = columnType.getType().concat("(").concat(columnType.getDefValue()).concat(")");
        } else {
            typ = columnType.getType();
        }
        Column column = new Column().setTyp(typ)
                .setLength(ofNullable(defValue).orElse(null))
                .setName(columnName)
                .setComment(comment);

        table.getColumns().add(column);

        // add column property
        Children children = attached.getChildren();
        if (isNull(children)) {
            return;
        }
        generateProperty(children, moduleList, module, column, columnType, pkMap, uniqueMap, indexMap);

    }

    private static void generateProperty(@NonNull Children children, @NonNull List<Module> moduleList,
                                         @NonNull Module module, @NonNull Column column,
                                         @NonNull ColumnType columnType, @NonNull Map<String, String> pkMap,
                                         @NonNull Map<String, String> uniqueMap, @NonNull Map<String, String> indexMap) {

        List<Attached> attachedList = children.getAttached();
        if (isNull(attachedList) || attachedList.size() == 0) {
            return;
        }

        // add column property
        for (Attached attached : attachedList) {
            String propTitle = attached.getTitle().trim();
            String[] propSplits = splitInto3Parts(propTitle);
            String propName = propSplits[0].trim();
            ColumnProperty columnProperty = getColumnProperty(propName);
            if (propSplits.length < 2 || isNull(columnProperty)) {
                LOG.debug("title [" + propTitle + "] 格式错误, 标准格式: propType/propValue/[description]");
                generateOfAttachedWithModule(attached, moduleList, module);
                continue;
            }
            String propValue = propSplits[1].trim();
            switch (columnProperty) {
                case LEN:
                case LENGTH:
                    if (hasText(propValue)) {
                        column.setTyp(columnType.getType().concat("(").concat(propValue).concat(")"));
                        column.setLength(propValue);
                    }
                    break;
                case PK:
                    pkMap.put(propName, column.getName());
                    column.setPk(true).setNotnull(true);
                    break;
                case UNIQUE:
                case UNQ:
                    uniqueMap.put(propName, column.getName());
                    column.setUnique(propValue).setNotnull(true);
                    break;
                case NOTNULL:
                    column.setNotnull(true);
                    break;
                case DEFAULT:
                case DEF:
                    column.setDef(propValue);
                    break;
                case UNSIGNED:
                    column.setUnsigned(true);
                    break;
                case ON_UPDATE:
                    if (hasText(propValue)) {
                        column.setOnUpdate(propValue);
                    } else {
                        column.setOnUpdate("ON UPDATE CURRENT_TIMESTAMP");
                    }
                    break;
                case AUTO_INCREMENT:
                case INCR:
                    column.setAutoIncrement(true);
                    break;
                case INDEX:
                case IDX:
                    indexMap.put(propName, column.getName());
                    column.setIndex(propValue).setNotnull(true);
                    break;
                default:
                    generateOfAttachedWithModule(attached, moduleList, module);
                    break;
            }
        }

    }

}
