package org.arch.framework.automate.generater.reader.xmind.parser;

import org.arch.automate.common.api.Annot;
import org.arch.automate.common.api.AnnotVal;
import org.arch.automate.common.api.Curl;
import org.arch.automate.common.api.Interfac;
import org.arch.automate.common.Module;
import org.arch.framework.automate.generater.reader.xmind.meta.Attached;
import org.arch.framework.automate.generater.reader.xmind.meta.Children;
import org.arch.framework.automate.generater.reader.xmind.nodespace.Annotation;
import org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType;
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
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType.ENTITY;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType.METHOD;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindAnnotAndGenericParser.generateAnnot;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindAnnotAndGenericParser.generateAnnotes;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindAnnotAndGenericParser.generateUriAnnot;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindImportParser.generateImport;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindParamParser.generateParamName;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindParamParser.resolveParam;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindProjectParser.generateOfAttachedWithModule;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.firstLetterToLower;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.firstLetterToUpper;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.getParamType;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.splitInto3Parts;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.strToUpperCamelWithPinYin;
import static org.springframework.util.StringUtils.hasText;

/**
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.23 16:56
 */
public class XmindMethodParser {

    public static final Logger LOG = LoggerFactory.getLogger(XmindMethodParser.class);

    @NonNull
    static Curl generateCurl(@NonNull Set<Module> moduleSet, @NonNull Module module,
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
            resolveMethod(attached.getChildren(), moduleSet, module, interfac, curl);
        }

        // 如果没有设置 RequestMapping 类型的注解, 则自动添加
        boolean isMappingAnnot =
                curl.getAnnotations()
                        .stream()
                        .anyMatch(a -> "RequestMapping".equals(a.getName())
                                  || "GetMapping".equals(a.getName())
                                  || "PostMapping".equals(a.getName())
                                  || "PutMapping".equals(a.getName())
                                  || "DeleteMapping".equals(a.getName()));
        if (!isMappingAnnot) {
            Annot annot = getAnnot(interfac, curl, "");
            Set<AnnotVal> annotValSet = annot.getAnnotateVals();
            annotValSet.add(new AnnotVal().setKey("value").setValue("/" + curlName));
            curl.getAnnotations().add(annot);
        }

        return curl;
    }

    private static Annot getAnnot(@NonNull Interfac interfac, Curl curl, String defaultMethodName) {
        String method = curl.getHttpMethod();
        Set<String> imports = interfac.getImports();
        if (!hasText(method)) {
            method = defaultMethodName;
        }
        String annotName;
        switch (method) {
            case "GET":
                annotName = Annotation.GET_MAPPING.getAnnotName();
                curl.setRestMethod(true);
                imports.add(Annotation.GET_MAPPING.getPkg());
                break;
            case "POST":
                annotName = Annotation.POST_MAPPING.getAnnotName();
                curl.setRestMethod(true);
                imports.add(Annotation.POST_MAPPING.getPkg());
                break;
            case "PUT":
                annotName = Annotation.PUT_MAPPING.getAnnotName();
                curl.setRestMethod(true);
                imports.add(Annotation.PUT_MAPPING.getPkg());
                break;
            case "DEL":
                annotName = Annotation.DELETE_MAPPING.getAnnotName();
                curl.setRestMethod(true);
                imports.add(Annotation.DELETE_MAPPING.getPkg());
                break;
            default:
                annotName = Annotation.REQUEST_MAPPING.getAnnotName();
                curl.setHttpMethod("GET");
                imports.add(Annotation.REQUEST_MAPPING.getPkg());
                break;
        }
        return new Annot().setName(annotName);
    }

    @SuppressWarnings("AlibabaMethodTooLong")
    private static void resolveMethod(@NonNull Children children, @NonNull Set<Module> moduleSet, @NonNull Module module,
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
                generateOfAttachedWithModule(attached, moduleSet, module);
                continue;
            }
            switch (paramType) {
                case IN:
                    if (hasText(splits[1].trim())) {
                        // title 格式 in/type/descr
                        generateInOrOut(attached, moduleSet, module, interfac, curl, splits, TRUE, index);
                    } else {
                        // title 格式 in//
                        generateInOrOut(attached, moduleSet, module, interfac, curl, null, TRUE, 1);
                    }
                    break;
                case OUT:
                    if (hasText(splits[1].trim())) {
                        // title 格式 out/type/descr
                        generateInOrOut(attached, moduleSet, module, interfac, curl, splits, FALSE, 1);
                    } else {
                        // title 格式 out//
                        generateInOrOut(attached, moduleSet, module, interfac, curl, null, FALSE, 1);
                    }
                    break;
                case ANNOTATES:
                    Children annotesChildren = attached.getChildren();
                    if (nonNull(annotesChildren)) {
                        generateAnnotes(annotesChildren, moduleSet, module, interfac, curl.getAnnotations());
                    }
                    break;
                case ANNOTATE:
                case ANNOTATION:
                    Annot annotation = generateAnnot(attached, moduleSet, module, splits, interfac);
                    annotations.add(annotation);
                    break;
                case GENERIC_VAL:
                    curl.setGenericVal(firstLetterToUpper(splits[1].trim()));
                    Children genericValChildren = attached.getChildren();
                    if (nonNull(genericValChildren)) {
                        generateImport(genericValChildren, moduleSet, module, interfac);
                    }
                    break;
                case URI:
                    Annot annot = getAnnot(interfac, curl, splits[2].trim().toUpperCase());
                    Set<AnnotVal> annotValSet = annot.getAnnotateVals();
                    annotValSet.add(new AnnotVal().setKey("value").setValue("/" + splits[1].trim()));
                    annotations.add(annot);
                    Children uriChildren = attached.getChildren();
                    generateUriAnnot(moduleSet, module, interfac, annotValSet, uriChildren);
                    break;
                default:
                    generateOfAttachedWithModule(attached, moduleSet, module);
                    break;
            }
            index++;
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
    private static void generateInOrOut(@NonNull Attached attached, @NonNull Set<Module> moduleList,
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

}
