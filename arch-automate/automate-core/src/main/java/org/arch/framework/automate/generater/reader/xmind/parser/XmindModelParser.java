package org.arch.framework.automate.generater.reader.xmind.parser;

import org.arch.automate.common.api.Annot;
import org.arch.automate.common.api.Interfac;
import org.arch.automate.common.api.Model;
import org.arch.automate.common.api.Param;
import org.arch.automate.common.Module;
import org.arch.automate.common.Import;
import org.arch.framework.automate.generater.reader.xmind.meta.Attached;
import org.arch.framework.automate.generater.reader.xmind.meta.Children;
import org.arch.framework.automate.generater.reader.xmind.nodespace.ColumnType;
import org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Set;

import static java.lang.Boolean.FALSE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType.ANNOTATE;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType.ANNOTATES;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType.ANNOTATION;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType.GENERIC_TYP;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType.GENERIC_TYP_E;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType.IMPORT;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType.IMPORTS;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType.OBJ_ANNOTATE;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType.PKG;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindAnnotAndGenericParser.generateAnnot;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindAnnotAndGenericParser.generateAnnotAndGeneric;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindAnnotAndGenericParser.generateAnnotes;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindImportParser.generateImport;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindImportParser.generateImportOfAttached;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindParamParser.generateParam;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindProjectParser.generateOfAttachedWithModule;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindProjectParser.generateOfChildren;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindProjectParser.generatePkg;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.firstLetterToLower;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.firstLetterToUpper;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.getColumnType;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.getParamType;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.removeNewlines;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.splitInto3Parts;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.strToUpperCamelWithPinYin;
import static org.springframework.util.StringUtils.hasText;

/**
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.23 16:56
 */
public class XmindModelParser {

    public static final Logger LOG = LoggerFactory.getLogger(XmindModelParser.class);

    /**
     * 生成 {@link Model}
     *
     * @param children   {@link Children}
     * @param moduleSet {@link Module} 列表
     * @param module     {@link Module}
     * @param pTitle     上级节点 title
     * @param pImport    {@link Import}
     * @param inOrOut    inOrOut: true 表示 {@link ParamType#IN}, false 表示 {@link ParamType#OUT}.
     */
    @SuppressWarnings("AlibabaMethodTooLong")
    @Nullable
    static void generateModel(@NonNull Children children, @NonNull Set<Module> moduleSet,
                              @NonNull Module module, @NonNull String pTitle,
                              @Nullable Import pImport, @Nullable Boolean inOrOut) {
        String[] splits = splitInto3Parts(pTitle);
        //noinspection AlibabaUndefineMagicConstant
        if (splits.length != 3) {
            LOG.debug("title [" + pTitle + "] 格式错误, 标准格式: TitleType/TypeName/[description]");
            generateOfChildren(children, moduleSet, module, null, pTitle);
            return;
        }

        String name = splits[1].trim();
        String commentStr = removeNewlines(splits[2].trim());
        String entityName = strToUpperCamelWithPinYin(name);

        // 新增 model
        Model model = new Model().setName(entityName)
                                 .setDescr(commentStr);

        if (pImport instanceof Model) {
            model.setApi(((Model) pImport).getApi());
        }
        // 是否新建对象
        boolean isNewCreatedEntity = false;

        // 遍历 model 字段
        Set<String> imports = model.getImports();
        Set<Annot> annotSet = model.getAnnotations();
        Set<Param> entityFields = model.getFields();
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
                generateOfAttachedWithModule(attached, moduleSet, module);
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
                    generateAnnotAndGeneric(fieldChildren, moduleSet, module, null, model, field);
                }
            } else if (ParamType.ENTITY.equals(paramType)) {
                Param entityParam = generateParam(attached, moduleSet, module, splits, paramType, model, inOrOut);
                if (nonNull(entityParam)) {
                    entityFields.add(entityParam);
                    isNewCreatedEntity = true;
                }
            } else //noinspection AlibabaAvoidComplexCondition
                if ((IMPORT.equals(paramType) || IMPORTS.equals(paramType)) && nonNull(pImport)) {
                generateImportOfAttached(attached, moduleSet, module, paramType, splits, pImport);
            } else if (PKG.equals(paramType) && nonNull(pImport)) {
                generatePkg(null, moduleSet, module, model, fieldTitle, paramType);
            } else if (ANNOTATES.equals(paramType)) {
                Children annotesChildren = attached.getChildren();
                if (nonNull(annotesChildren)) {
                    if (nonNull(pImport)) {
                        generateAnnotes(annotesChildren, moduleSet, module, pImport, annotSet);
                    } else {
                        generateAnnotes(annotesChildren, moduleSet, module, model, annotSet);
                    }
                }
            } else if (ANNOTATE.equals(paramType) || ANNOTATION.equals(paramType)) {
                Annot annotation;
                if (nonNull(pImport)) {
                    annotation = generateAnnot(attached, moduleSet, module, splits, pImport);
                } else {
                    annotation = generateAnnot(attached, moduleSet, module, splits, model);
                }
                annotSet.add(annotation);
            } else if (OBJ_ANNOTATE.equals(paramType)) {
                Annot annotation = generateAnnot(attached, moduleSet, module, splits, model);
                annotSet.add(annotation);
            } else if (GENERIC_TYP_E.equals(paramType)) {
                String genericTyp = firstLetterToUpper(orgName);
                model.setGenericTyp(genericTyp);
                Children attachedChildren = attached.getChildren();
                if (nonNull(attachedChildren)) {
                    generateImport(attachedChildren, moduleSet, module, model);
                }
            } else if (GENERIC_TYP.equals(paramType) && isNull(pImport)) {
                String genericTyp = firstLetterToUpper(orgName);
                model.setGenericTyp(genericTyp);
                Children attachedChildren = attached.getChildren();
                if (nonNull(attachedChildren)) {
                    generateImport(attachedChildren, moduleSet, module, model);
                }
            } else {
                String paramTypePkg = paramType.getPkg();
                if (hasText(paramTypePkg)) {
                    imports.add(paramTypePkg);
                }
                Param fieldParam = generateParam(attached, moduleSet, module, splits, paramType, model, inOrOut);
                if (nonNull(fieldParam)) {
                    entityFields.add(fieldParam);
                    isNewCreatedEntity = true;
                }
            }
        }
        if (isNewCreatedEntity) {
            module.addModel(model);
            // 存储从 Param 中解析出的字段 Model 类型
            if (pImport instanceof Model) {
                ((Model) pImport).getParamModels().add(model);
            }
        }

        if (pImport instanceof Interfac) {
            model.setApi(((Interfac) pImport).getApi());
            // 存储从出入参数中解析出的字段 Model 类型
            if (nonNull(inOrOut) && inOrOut) {
                ((Interfac) pImport).getInModels().add(model);
            }
            else if (nonNull(inOrOut)) {
                ((Interfac) pImport).getOutModels().add(model);
            }
        }

    }

}
