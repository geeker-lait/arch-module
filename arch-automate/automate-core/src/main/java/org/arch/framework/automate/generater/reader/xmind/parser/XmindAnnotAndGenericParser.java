package org.arch.framework.automate.generater.reader.xmind.parser;

import org.arch.automate.common.api.Annot;
import org.arch.automate.common.api.AnnotVal;
import org.arch.automate.common.api.Interfac;
import org.arch.automate.common.api.Model;
import org.arch.automate.common.api.Param;
import org.arch.automate.common.Module;
import org.arch.automate.common.Import;
import org.arch.framework.automate.generater.reader.xmind.meta.Attached;
import org.arch.framework.automate.generater.reader.xmind.meta.Children;
import org.arch.framework.automate.generater.reader.xmind.nodespace.Annotation;
import org.arch.framework.automate.generater.reader.xmind.nodespace.ParamProperty;
import org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType;
import org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamProperty.ARRAY_TYP;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType.ANNOTATE;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType.ANNOTATION;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType.ANNOTATES;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType.ANNOTATE_VAL;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType.GENERIC;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType.GENERIC_TYP;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType.GENERIC_VAL;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType.IMPORT;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType.IMPORTS;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindImportParser.generateImport;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindImportParser.generateImportOfAttached;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindProjectParser.generateOfAttachedWithModule;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindProjectParser.generateOfChildren;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.*;
import static org.springframework.util.StringUtils.hasText;

/**
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.23 16:56
 */
public class XmindAnnotAndGenericParser {

    public static final Logger LOG = LoggerFactory.getLogger(XmindAnnotAndGenericParser.class);

    @NonNull
    static Annot generateAnnot(@NonNull Attached attached, @NonNull Set<Module> moduleSet,
                               @NonNull Module module, @NonNull String[] tokens,
                               @NonNull Import importObj) {

        // 新增 annot
        String annotName = tokens[1].trim();
        Annotation annotation = getAnnotation(annotName);
        Annot annot;
        if (nonNull(annotation)) {
            annot = new Annot().setName(annotation.getAnnotName());
            importObj.getImports().add(annotation.getPkg());
        }
        else {
            annotName = annotName.contains("_") ? underscoreToUpperCamel(annotName) : annotName;
            annot = new Annot().setName(firstLetterToUpper(annotName));
        }

        Set<AnnotVal> annotVals = annot.getAnnotateVals();
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
                    generateOfChildren(children, moduleSet, module, null, title);
                    continue;
                }
                if (ANNOTATE_VAL.equals(paramType)) {
                    AnnotVal annotVal = generateAnnotVal(annotAttached, moduleSet, module, splits, importObj);
                    if (nonNull(annotVal)) {
                        annotVals.add(annotVal);
                    }
                }
                else if (IMPORT.equals(paramType) || IMPORTS.equals(paramType)) {
                    generateImportOfAttached(attached, moduleSet, module, paramType, splits, importObj);
                }
                else {
                    generateOfAttachedWithModule(attached, moduleSet, module);
                }
            }
        }

        return annot;
    }

    @Nullable
    private static AnnotVal generateAnnotVal(@NonNull Attached attached, @NonNull Set<Module> moduleList,
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

    static void generateUriAnnot(@NonNull Set<Module> moduleList, @NonNull Module module,
                                 @NonNull Interfac interfac, @NonNull Set<AnnotVal> annotValSet,
                                 @Nullable Children uriChildren) {
        if (nonNull(uriChildren)) {
            List<Attached> uriAttachedList = uriChildren.getAttached();
            for (Attached annotValAttached : uriAttachedList) {
                String annotValTitle = annotValAttached.getTitle().trim();
                String[] annotValSplits = splitInto3Parts(annotValTitle);
                ParamType annotValParamType = getParamType(annotValSplits[0].trim());
                if (nonNull(annotValParamType) && ANNOTATE_VAL.equals(annotValParamType)) {
                    AnnotVal annotVal = generateAnnotVal(annotValAttached, moduleList,
                                                         module, annotValSplits, interfac);
                    annotValSet.add(annotVal);
                }
            }
        }
    }


    static void generateAnnotes(@NonNull Children children, @NonNull Set<Module> moduleList,
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
            if (ANNOTATE.equals(paramType) || ANNOTATION.equals(paramType)) {
                Annot annot = generateAnnot(attached, moduleList, module, splits, importObj);
                annotations.add(annot);
            }
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
    static String resolveGenericParamName(@NonNull String genericVal, @NonNull ParamType paramType) {
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

    static void generateAnnotAndGeneric(@NonNull Children children, @NonNull Set<Module> moduleList,
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
                paramProperty = XmindUtils.getParamProperty(paramType);
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
            } else if (ANNOTATES.equals(paramTyp)) {
                Children annotesChildren = paramAttached.getChildren();
                if (nonNull(annotesChildren)) {
                    generateAnnotes(annotesChildren, moduleList, module, pImport, param.getAnnotations());
                }
            } else if (ANNOTATE.equals(paramTyp) || ANNOTATION.equals(paramTyp)) {
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
                if (pImport instanceof Model) {
                    ((Model) pImport).setGenericTyp(genericVal);
                } else if (pImport instanceof Interfac) {
                    ((Interfac) pImport).setGenericTyp(genericVal);
                }
            } else if (IMPORT.equals(paramTyp) || IMPORTS.equals(paramType)) {
                generateImportOfAttached(paramAttached, moduleList, module, paramTyp, splits, pImport);
            } else {
                Children paramAttachedChildren = paramAttached.getChildren();
                if (nonNull(paramAttachedChildren)) {
                    generateOfChildren(children, moduleList, module);
                }
            }
        }
    }
}
