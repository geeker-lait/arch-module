package org.arch.automate.reader.xmind.parser;

import org.arch.automate.Module;
import org.arch.automate.reader.xmind.meta.Attached;
import org.arch.automate.reader.xmind.meta.Children;
import org.arch.automate.reader.xmind.nodespace.ParamType;
import org.arch.framework.beans.schema.Import;
import org.arch.framework.beans.schema.api.Curl;
import org.arch.framework.beans.schema.api.Interfac;
import org.arch.framework.beans.schema.api.Model;
import org.arch.framework.beans.schema.api.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Set;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.arch.automate.reader.xmind.nodespace.ParamType.ENTITY;
import static org.arch.automate.reader.xmind.nodespace.ParamType.GENERIC;
import static org.arch.automate.reader.xmind.parser.XmindAnnotAndGenericParser.generateAnnotAndGeneric;
import static org.arch.automate.reader.xmind.parser.XmindAnnotAndGenericParser.resolveGenericParamName;
import static org.arch.automate.reader.xmind.parser.XmindImportParser.generateImportOfAttached;
import static org.arch.automate.reader.xmind.parser.XmindModelParser.addModel;
import static org.arch.automate.reader.xmind.parser.XmindModelParser.generateModel;
import static org.arch.automate.reader.xmind.parser.XmindProjectParser.generateOfAttachedWithModule;
import static org.arch.automate.reader.xmind.parser.XmindProjectParser.generateOfChildren;
import static org.arch.automate.reader.xmind.utils.XmindUtils.firstLetterToLower;
import static org.arch.automate.reader.xmind.utils.XmindUtils.firstLetterToUpper;
import static org.arch.automate.reader.xmind.utils.XmindUtils.getParamType;
import static org.arch.automate.reader.xmind.utils.XmindUtils.removeNewlines;
import static org.arch.automate.reader.xmind.utils.XmindUtils.splitInto3Parts;
import static org.arch.automate.reader.xmind.utils.XmindUtils.strToUpperCamelWithPinYin;
import static org.arch.automate.reader.xmind.utils.XmindUtils.underscoreToUpperCamel;
import static org.springframework.util.StringUtils.hasText;

/**
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.23 16:56
 */
public class XmindParamParser {

    public static final Logger LOG = LoggerFactory.getLogger(XmindParamParser.class);

    static void smartResolveParam(@NonNull Attached attached, @NonNull Set<Module> moduleSet,
                                  @NonNull Module module, @NonNull Interfac interfac,
                                  @NonNull Curl curl, @NonNull Boolean inOrOut,
                                  @NonNull String[] tokens, @NonNull Integer paramIndex) {
        /**
         * 智能判断 attached 根据子节点的信息来判读是否为原始类型参数还是对象参数.
         * 1. 当当前节点 paramType == null 且子节点有 0 个参数类型时, 设置为对象参数, 但不生成 Model, (可选)引入 import
         * 2. 当当前节点 paramType == null 且子节点大于等于 1 个参数类型时, 设置为对象参数, 生成 Model
         * 3. 当当前节点 paramType != null 且子节点有 1 个参数类型时, 设置为原型参数, paramType 与 paramName 以 子节点为准
         * 4. 当当前节点 paramType == ENTITY 且子节点大于一个参数类型时, 设置为对象参数, 生成 Model
         */
        String inOrOutParam = strToUpperCamelWithPinYin(tokens[1].trim());
        String inOrOutParamDescr = strToUpperCamelWithPinYin(removeNewlines(tokens[2].trim()));
        Children children = attached.getChildren();
        ParamType paramType = getParamType(inOrOutParam);
        // index = 0 为 newType, index = 1 为 newParamName
        String[] typeAndParamNameArray = new String[2];
        generateParamName(paramType, typeAndParamNameArray, inOrOutParam, inOrOut, paramIndex);

        String newType = typeAndParamNameArray[0];
        String newParamName = typeAndParamNameArray[1];
        String newTitle = newType.concat("/").concat(newParamName).concat("/").concat(inOrOutParamDescr);
        if (isNull(paramType) || ENTITY.equals(paramType)) {
            Param param = new Param().setName(firstLetterToLower(newParamName))
                                     .setDescr(inOrOutParamDescr)
                                     .setTyp(inOrOutParam);
            if (nonNull(children)) {
                /**
                 * 2. 当当前节点 paramType == null 且子节点大于等于 1 个参数类型时, 设置为对象参数, 生成 Model
                 * 4. 当当前节点 paramType == ENTITY 且子节点大于一个参数类型时, 设置为对象参数, 生成 Model
                 */
                Model model = generateModel(children, moduleSet, module, newTitle, interfac, inOrOut, FALSE);
                if (nonNull(model)) {
                    Set<Param> fields = model.getFields();
                    int size = fields.size();
                    if (size == 1) {
                        Param field = fields.stream().findFirst().get();
                        param.setName(field.getName())
                             .setTyp(field.getTyp())
                             .setDescr(field.getDescr())
                             .getAnnotations().addAll(field.getAnnotations());
                    }
                    else if (size > 1) {
                        addModel(module, interfac, inOrOut, model);
                    }
                }
            }
            // 1. 当当前节点 paramType == null 且子节点有 0 个参数类型时, 设置为对象参数, 但不生成 Model, (可选)引入 import
            if (inOrOut) {
                curl.getInputs().add(param);
            }
            else {
                curl.setOutput(param);
            }
            return;
        }

        // 从in/out命名空间解析的忽略非参数的命名空间类型
        switch(paramType) {
            case IMPORT: case IMPORTS:
            case GENERIC: case GENERIC_TYP: case GENERIC_TYP_E: case GENERIC_VAL:
            case ANNOTATES: case ANNOTATE: case ANNOTATE_VAL: case ANNOTATION: case OBJ_ANNOTATE: case URI:
            case METHOD: case PKG: case IN: case OUT:
            case GET: case POST: case PUT: case DEL:
                return;
            default:
                break;
        }
        // 3. 当当前节点 paramType != null 且子节点有 1 个参数类型时, 设置为原型参数, paramType 与 paramName 以 子节点为准
        Param preParam = new Param().setName(firstLetterToLower(newParamName))
                                    .setDescr(inOrOutParamDescr)
                                    .setTyp(newType);
        if (nonNull(children)) {
            Param postParam = resolveParamNotModel(moduleSet, module, interfac, inOrOut, children, preParam, paramType);
            if (nonNull(postParam)) {
                // 子节点参数覆盖父节点参数
                preParam.setTyp(postParam.getTyp())
                        .setName(postParam.getName())
                        .setDescr(postParam.getDescr())
                        .getAnnotations().addAll(postParam.getAnnotations());

            }
        }
        if (inOrOut) {
            curl.getInputs().add(preParam);
        }
        else {
            curl.setOutput(preParam);
        }
    }

    @Nullable
    static Param resolveParamNotModel(@NonNull Set<Module> moduleSet, @NonNull Module module,
                                      @NonNull Interfac interfac, @NonNull Boolean inOrOut,
                                      @NonNull Children children, @NonNull Param preParam,
                                      @NonNull ParamType pParamType) {

        // 3. 当当前节点 pParamType != null 且子节点有 1 个参数类型时, 设置为原型参数, paramType 与 paramName 以 子节点为准
        List<Attached> attachedList = children.getAttached();
        Param postParam = null;
        for (Attached attached : attachedList) {
            String title = attached.getTitle();
            String[] splits = splitInto3Parts(title);
            ParamType paramType = getParamType(splits[0].trim());
            //noinspection AlibabaUndefineMagicConstant
            if (splits.length != 3 || isNull(paramType)) {
                LOG.debug("title [" + attached.getTitle() + "] 格式错误, 标准格式: paramType/paramName/[description]");
                return null;
            }
            switch (paramType) {
                case IMPORT:
                case IMPORTS:
                    generateImportOfAttached(attached, moduleSet, module, paramType, splits, interfac);
                    break;
                case GENERIC:
                case GENERIC_TYP:
                case GENERIC_TYP_E:
                case GENERIC_VAL:
                case ANNOTATES:
                case ANNOTATE:
                case ANNOTATE_VAL:
                case ANNOTATION:
                case OBJ_ANNOTATE:
                    // 生成 annots/generic/genericTyp
                    generateAnnotAndGeneric(children, moduleSet, module, pParamType, interfac, preParam);
                    break;
                case METHOD:
                case PKG:
                case IN:
                case OUT:
                case URI:
                case GET:
                case POST:
                case PUT:
                case DEL:
                    break;
                default:
                    // 此方法默认子节点有 1 个参数, 如果出现多个参数的情况直接覆盖前一个参数, 最后只生效最后一个参数
                    postParam = generateParam(attached, moduleSet, module, splits, paramType, interfac, inOrOut);
            }
        }
        return postParam;
    }

    @Nullable
    static void resolveParam(@NonNull Set<Module> moduleSet, @NonNull Module module,
                             @NonNull Interfac interfac, @NonNull Curl curl,
                             @NonNull Boolean inOrOut, @NonNull Attached paramAttached,
                             @NonNull String[] splits, @Nullable ParamType paramType) {

        //noinspection AlibabaUndefineMagicConstant
        if (splits.length != 3 && isNull(paramType)) {
            LOG.debug("title [" + paramAttached.getTitle() + "] 格式错误, 标准格式: paramType/paramName/[description]");
            generateOfAttachedWithModule(paramAttached, moduleSet, module);
            return ;
        }

        Set<Param> inputParams = curl.getInputs();
        if (!ENTITY.equals(paramType) && !GENERIC.equals(paramType)) {
            String type = paramType.getType();
            // 不是 entity/generic 类型时, 没有类型值则
            if (!hasText(type)) {
                Children attachedChildren = paramAttached.getChildren();
                if (nonNull(attachedChildren)) {
                    generateOfChildren(attachedChildren, moduleSet, module);
                    return ;
                }
            }
        }
        Param param = generateParam(paramAttached, moduleSet, module, splits, paramType, interfac, inOrOut);
        if (inOrOut) {
            inputParams.add(param);
        } else {
            curl.setOutput(param);
        }
    }

    @Nullable
    static Param generateParam(@NonNull Attached attached, @NonNull Set<Module> moduleSet,
                               @NonNull Module module, @NonNull String[] tokens,
                               @NonNull ParamType pParamType, @NonNull Import pImport,
                               @Nullable Boolean inOrOut) {

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
                        generateModel(children, moduleSet, module, attached.getTitle(), pImport, inOrOut, TRUE);
                    } else if (pImport instanceof Interfac) {
                        generateModel(children, moduleSet, module, attached.getTitle(), pImport, inOrOut, TRUE);
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
        generateAnnotAndGeneric(children, moduleSet, module, pParamType, pImport, param);

        return param;
    }

    @NonNull
    static void generateParamName(@Nullable ParamType paramType,
                                  @NonNull String[] typeAndParamNameArray, @NonNull String inOrOutParamType,
                                  @NonNull Boolean inOrOut, @NonNull Integer paramIndex) {
        // inOrOut: true 表示 {@link ParamType#IN}, false 表示 {@link ParamType#OUT}.

        int genericIndex = inOrOutParamType.indexOf("<");
        int arrayIndex = inOrOutParamType.indexOf("[");

        if (isNull(paramType)) {
            parseEntityInfo(typeAndParamNameArray, inOrOutParamType, genericIndex, arrayIndex);
            return;
        }

        String paramName;
        switch (paramType) {
            case ENTITY:
                // entityName<String, String[]>/entityName<String>/entityName/etc
                parseEntityInfo(typeAndParamNameArray, inOrOutParamType, genericIndex, arrayIndex);
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

    private static void parseEntityInfo(@NonNull String[] typeAndParamNameArray, @NonNull String inOrOutParamType,
                                        int genericIndex, int arrayIndex) {
        //noinspection AlibabaAvoidComplexCondition
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
    }

}
