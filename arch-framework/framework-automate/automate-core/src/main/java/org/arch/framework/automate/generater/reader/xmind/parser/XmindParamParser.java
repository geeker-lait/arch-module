package org.arch.framework.automate.generater.reader.xmind.parser;

import org.arch.framework.automate.common.api.Curl;
import org.arch.framework.automate.common.api.Interfac;
import org.arch.framework.automate.common.api.Model;
import org.arch.framework.automate.common.api.Param;
import org.arch.framework.automate.common.module.Module;
import org.arch.framework.automate.generater.reader.xmind.Import;
import org.arch.framework.automate.generater.reader.xmind.meta.Attached;
import org.arch.framework.automate.generater.reader.xmind.meta.Children;
import org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType.ENTITY;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType.GENERIC;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindAnnotAndGenericParser.generateAnnotAndGeneric;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindAnnotAndGenericParser.resolveGenericParamName;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindModelParser.generateModel;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindProjectParser.generateOfAttachedWithModule;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindProjectParser.generateOfChildren;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.firstLetterToLower;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.firstLetterToUpper;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.removeNewlines;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.strToUpperCamelWithPinYin;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.underscoreToUpperCamel;
import static org.springframework.util.StringUtils.hasText;

/**
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.23 16:56
 */
public class XmindParamParser {

    public static final Logger LOG = LoggerFactory.getLogger(XmindParamParser.class);

    static void resolveParam(@NonNull List<Module> moduleList, @NonNull Module module,
                                    @NonNull Interfac interfac, @NonNull Curl curl,
                                    @NonNull Boolean inOrOut, @NonNull Attached paramAttached,
                                    @NonNull String[] splits, @Nullable ParamType paramType) {
        if (splits.length != 3 || isNull(paramType)) {
            LOG.debug("title [" + paramAttached.getTitle() + "] 格式错误, 标准格式: paramType/paramName/[description]");
            generateOfAttachedWithModule(paramAttached, moduleList, module);
            return;
        }
        List<Param> inputParams = curl.getInputs();
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
            curl.setOutput(generateParam(paramAttached, moduleList, module, splits, paramType, interfac));
        }
    }

    @Nullable
    static Param generateParam(@NonNull Attached attached, @NonNull List<Module> moduleList,
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
                    if (pImport instanceof Model) {
                        generateModel(children, moduleList, module, attached.getTitle(), pImport);
                    } else if (pImport instanceof Interfac) {
                        generateModel(children, moduleList, module, attached.getTitle(), pImport);
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

    @NonNull
    static void generateParamName(@NonNull ParamType paramType, @NonNull String[] typeAndParamNameArray,
                                  @NonNull String inOrOutParamType, @NonNull Boolean inOrOut,
                                  @NonNull Integer paramIndex) {
        // inOrOut: true 表示 {@link ParamType#IN}, false 表示 {@link ParamType#OUT}.

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

}
