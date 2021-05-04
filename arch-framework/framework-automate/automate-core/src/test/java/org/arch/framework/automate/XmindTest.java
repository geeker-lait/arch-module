package org.arch.framework.automate;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.ArchiveException;
import org.arch.framework.automate.generater.service.xmind.meta.Attached;
import org.arch.framework.automate.generater.service.xmind.meta.Children;
import org.arch.framework.automate.generater.service.xmind.meta.JsonRootBean;
import org.arch.framework.automate.generater.service.xmind.meta.RootTopic;
import org.arch.framework.automate.generater.service.xmind.parser.XmindParser;
import org.arch.framework.automate.xmind.Import;
import org.arch.framework.automate.xmind.api.Annot;
import org.arch.framework.automate.xmind.api.AnnotVal;
import org.arch.framework.automate.xmind.api.Curl;
import org.arch.framework.automate.xmind.api.Entity;
import org.arch.framework.automate.xmind.api.Interfac;
import org.arch.framework.automate.xmind.api.Param;
import org.arch.framework.automate.xmind.module.Module;
import org.arch.framework.automate.xmind.nodespace.Annotation;
import org.arch.framework.automate.xmind.nodespace.ColumnProperty;
import org.arch.framework.automate.xmind.nodespace.ColumnType;
import org.arch.framework.automate.xmind.nodespace.ParamProperty;
import org.arch.framework.automate.xmind.nodespace.ParamType;
import org.arch.framework.automate.xmind.nodespace.TiTleType;
import org.arch.framework.automate.xmind.table.Column;
import org.arch.framework.automate.xmind.table.Database;
import org.arch.framework.automate.xmind.table.Property;
import org.arch.framework.automate.xmind.table.Table;
import org.arch.framework.automate.xmind.utils.FreeMarkerUtil;
import org.dom4j.DocumentException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.arch.framework.automate.xmind.nodespace.ParamType.*;
import static org.arch.framework.automate.xmind.nodespace.TiTleType.API;
import static org.arch.framework.automate.xmind.nodespace.TiTleType.MODULE;
import static org.arch.framework.automate.xmind.nodespace.TiTleType.PKG;
import static org.arch.framework.automate.xmind.nodespace.TiTleType.SHEET;
import static org.arch.framework.automate.xmind.nodespace.TiTleType.TABLE;
import static org.arch.framework.automate.xmind.utils.XmindUtils.*;
import static org.springframework.util.StringUtils.hasText;

/**
 * @ClassName XmindTest
 * @Author http://github.com/geeker-lait
 * @Tel 15801818092
 * @Date 11:09 AM 11/24/2018
 * @Version 1.0.0
 * @Description //TODO
 */
@SpringBootTest
@Slf4j
public class XmindTest {

    @Test
    public void parse() throws DocumentException, ArchiveException, IOException {
        //        String fileName = "minds\\ofs-alarm-er.xmind";
        String fileName = "minds"+ File.separator +"ofs-alarm-center-1.xmind";
        Resource resource = new ClassPathResource(fileName);
        String absolutePath = resource.getFile().getAbsolutePath();

        String res = XmindParser.parseJson(absolutePath);
        System.out.println(res+ "\n\n\n\n\n\n=================================================" );

        JsonRootBean root = XmindParser.parseObject(absolutePath, JsonRootBean.class);

        List<Module> moduleList = new ArrayList<>();

        generate(root, moduleList);

        moduleList.forEach(module -> {
            String pkg = module.getPkg();
            if (hasText(pkg)) {
                module.modulePkgPostHandle(pkg, false);
            }
        });
        Database database = moduleList.get(0).getDatabases().get(0);
        FreeMarkerUtil.geneFile("ddl","templates","ddl.sql",database);

        System.out.println(JSON.toJSONString(moduleList));
    }

    private void generate(@NonNull JsonRootBean root, @NonNull List<Module> moduleList) {
        try {
            TiTleType tiTleType = TiTleType.valueOf(root.getTitle().trim().toUpperCase());
            if (!SHEET.equals(tiTleType)) {
                return;
            }
            generateOfRoot(root.getRootTopic(), moduleList);
        }
        catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }

    private void generateOfRoot(@NonNull RootTopic rootTopic, @NonNull List<Module> moduleList) {
        String title = rootTopic.getTitle().trim();
        TiTleType tiTleType = getTiTleType(title, log);
        Children children = rootTopic.getChildren();
        generate(children, moduleList, title, tiTleType);
    }

    /**
     * 根据 {@link Attached} 的内容生成相应的规格内容, 添加到 {@link Module}.
     * @param attached      {@link Attached}
     * @param moduleList    {@link Module} list
     */
    private void generateOfAttached(@NonNull Attached attached, @NonNull List<Module> moduleList) {
        String title = attached.getTitle().trim();
        TiTleType tiTleType = getTiTleType(title, log);
        Children children = attached.getChildren();
        if (isNull(children)) {
        	return;
        }
        generate(children, moduleList, title, tiTleType);
    }

    private void generate(@Nullable Children children, @NonNull List<Module> moduleList,
                          @NonNull String title, @Nullable TiTleType tiTleType) {
        if (isNull(children)) {
            return;
        }
        if (nonNull(tiTleType) && MODULE.equals(tiTleType)) {
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
     * @param attached      {@link Attached}
     * @param moduleList    {@link Module} list
     * @param module        {@link Module}
     */
    private void generateOfAttachedWithModule(@NonNull Attached attached, @NonNull List<Module> moduleList,
                                              @NonNull Module module) {
        String title = attached.getTitle().trim();
        TiTleType tiTleType = getTiTleType(title, log);
        Children children = attached.getChildren();
        if (isNull(children)) {
            if (nonNull(tiTleType) && PKG.equals(tiTleType)) {
                generatePkg(null, moduleList, module, title, tiTleType);
            }
        	return;
        }
        generateWithModule(children, moduleList, module, title, tiTleType);
    }

    private void generateWithModule(@NonNull Children children, @NonNull List<Module> moduleList,
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

    private void generateOfChildren(@NonNull Children children, @NonNull List<Module> moduleList, @NonNull Module module) {
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
    private void generateOfChildren(@NonNull Children children, @NonNull List<Module> moduleList,
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
                generateApi(children, moduleList, module, pTitle, pTiTleType);
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

    //  ------------------------------- api -------------------------------

    private void generatePkg(@Nullable Children children, @NonNull List<Module> moduleList,
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

    private void generateApi(@NonNull Children children, @NonNull List<Module> moduleList,
                             @NonNull Module module, @NonNull String pTitle, @NonNull TiTleType pTiTleType) {

        if (!API.equals(pTiTleType)) {
            generateOfChildren(children, moduleList, module);
            return;
        }
        // add interface
        String[] splits = splitInto3Parts(pTitle);
        String interfaceName = firstLetterToUpper(splits[1].trim());
        String comment = removeNewlines(splits[2].trim());
        Interfac interfac = new Interfac().setName(interfaceName).setDescr(comment);
        module.addInterface(interfac);

        // 遍历 interface 的方法
        Set<Annot> entityAnnotations = interfac.getAnnotations();
        List<Curl> curls = interfac.getCurls();
        List<Attached> attachedList = children.getAttached();
        if (isNull(attachedList) || attachedList.size() == 0) {
            return;
        }
        String curlTitle;
        for (Attached attached : attachedList) {
            curlTitle = attached.getTitle();
            splits = splitInto3Parts(curlTitle);
            ParamType paramType = getParamType(splits[0].trim(), log);
            if (splits.length != 3 || isNull(paramType)) {
                log.info("title [" + curlTitle + "] 格式错误, 标准格式: paramType/paramName/[description]");
                generateOfAttachedWithModule(attached, moduleList, module);
                continue;
            }
            String name = firstLetterToLower(splits[1].trim());
            String curlComment = removeNewlines(splits[2].trim());
            switch(paramType) {
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
                case INTERFACE:
                    curls.add(generateCurl(moduleList, module, interfac, attached, GET, name, curlComment, FALSE));
                    break;
                case METHOD:
                    curls.add(generateCurl(moduleList, module, interfac, attached, METHOD, name, curlComment, FALSE));
                    break;
                case ANNOT: case ANNOTATION:
                    Annot annotation = generateAnnot(attached, moduleList, module, splits);
                    if (nonNull(annotation)) {
                        entityAnnotations.add(annotation);
                    }
                    break;
                case URI:
                    Annot annot = new Annot().setName("RequestMapping");
                    annot.getAnnotVals().add(new AnnotVal().setKey("value").setValue("/" + splits[1].trim()));
                    entityAnnotations.add(annot);
                    break;
                case GENERIC_TYP:
                    String genericTyp = firstLetterToUpper(splits[1].trim());
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

    @NonNull
    private Curl generateCurl(@NonNull List<Module> moduleList, @NonNull Module module,
                              @NonNull Interfac interfac, @NonNull Attached attached,
                              @NonNull ParamType paramType, @NonNull String curlName,
                              @NonNull String curlComment, @NonNull Boolean isRestMethod) {
        String methodName = paramType.name();
        if (INTERFACE.equals(paramType)) {
            methodName = GET.name();
        }
        else if (METHOD.equals(paramType)){
            methodName = "";
        }
        Curl curl = new Curl().setName(curlName).setDescr(curlComment)
                              .setHttpMethod(methodName).setRestMethod(isRestMethod);
        Children attachedChildren = attached.getChildren();
        if (nonNull(attachedChildren)) {
            resolveMethod(attached.getChildren(), moduleList, module, interfac, curl);
        }
        return curl;
    }

    private void resolveMethod(@NonNull Children children, @NonNull List<Module> moduleList, @NonNull Module module,
                               @NonNull Interfac interfac, @NonNull Curl curl) {
        List<Attached> attachedList = children.getAttached();
        if (attachedList.size() == 0) {
            return;
        }

        Set<Annot> annotations = curl.getAnnotations();
        for (Attached attached : attachedList) {
            String title = attached.getTitle().trim();
            String[] splits = splitInto3Parts(title);
            ParamType paramType = getParamType(splits[0].trim(), log);
            if (splits.length != 3 || isNull(paramType)) {
                log.info("title [" + title + "] 格式错误, 标准格式: paramType/paramName/[description]");
                generateOfAttachedWithModule(attached, moduleList, module);
                return;
            }
            switch(paramType) {
                case IN:
                    generateInOrOut(attached, moduleList, module, interfac, curl, TRUE);
                    break;
                case OUT:
                    generateInOrOut(attached, moduleList, module, interfac, curl, FALSE);
                    break;
                case ANNOT: case ANNOTATION:
                    Annot annotation = generateAnnot(attached, moduleList, module, splits);
                    if (nonNull(annotation)) {
                        annotations.add(annotation);
                    }
                    break;
                case GENERIC_VAL:
                    curl.setGenericVal(firstLetterToUpper(splits[1].trim()));
                    break;
                case URI:
                    String method = splits[2].trim().toUpperCase();
                    String httpMethod;
                    switch(method) {
                        case "GET":
                            httpMethod = "GetMapping";
                            break;
                        case "POST":
                            httpMethod = "PostMapping";
                            break;
                        case "PUT":
                            httpMethod = "PutMapping";
                            break;
                        case "DEL":
                            httpMethod = "DeleteMapping";
                            break;
                        default:
                            httpMethod = "RequestMapping";
                            break;
                    }
                    Annot annot = new Annot().setName(httpMethod);
                    annot.getAnnotVals().add(new AnnotVal().setKey("value").setValue("/" + splits[1].trim()));
                    annotations.add(annot);
                    break;
                default:
                    generateOfAttachedWithModule(attached, moduleList, module);
                    break;
            }
        }

    }

    /**
     * 生成输入输出参数
     * @param attached      {@link Attached}
     * @param moduleList    {@link Module} 列表
     * @param module        {@link Module}
     * @param interfac      {@link Interfac}
     * @param curl          {@link Curl}
     * @param inOrOut       true 表示 {@link ParamType#IN}, false 表示 {@link ParamType#OUT}.
     */
    private void generateInOrOut(@NonNull Attached attached, @NonNull List<Module> moduleList,
                                 @NonNull Module module, @NonNull Interfac interfac,
                                 @NonNull Curl curl, @NonNull Boolean inOrOut) {

        Children children = attached.getChildren();
        if (isNull(children)) {
            return;
        }
        List<Attached> attachedList = children.getAttached();
        if (attachedList.size() == 0) {
            return;
        }
        // 遍历获取入参或返回参数
        List<Param> inputParams = curl.getInputParams();
        for (Attached paramAttached : attachedList) {
            String title = paramAttached.getTitle().trim();
            String[] splits = splitInto3Parts(title);
            ParamType paramType = getParamType(splits[0].trim(), log);
            if (splits.length != 3 || isNull(paramType)) {
                log.info("title [" + title + "] 格式错误, 标准格式: paramType/paramName/[description]");
                generateOfAttachedWithModule(paramAttached, moduleList, module);
                continue;
            }
            if (!ENTITY.equals(paramType)) {
                String type = paramType.getType();
                if (!hasText(type)) {
                    Children attachedChildren = paramAttached.getChildren();
                    if (nonNull(attachedChildren)) {
                        generateOfChildren(children, moduleList, module);
                    }
                }
            }
            if (inOrOut) {
                inputParams.add(generateParam(paramAttached, moduleList, module, splits, paramType, interfac));
            }
            else {
                curl.setOutputParam(generateParam(paramAttached, moduleList, module, splits, paramType, interfac));
            }

        }

    }

    @Nullable
    private Param generateParam(@NonNull Attached attached, @NonNull List<Module> moduleList,
                                @NonNull Module module, @NonNull String[] tokens,
                                @NonNull ParamType paramType, @NonNull Interfac interfac) {
        // 新增 param
        String typ = paramType.getType();
        if (ENTITY.equals(paramType)) {
            Children children = attached.getChildren();
            if (nonNull(children)) {
                resolveEntity(children, moduleList, module, attached.getTitle(), interfac);
            }
            typ = firstLetterToUpper(tokens[1].trim());
        }
        if (!hasText(typ)) {
            typ = firstLetterToUpper(tokens[0].trim());
        }
        Param param = new Param().setTyp(typ)
                                 .setName(firstLetterToLower(tokens[1].trim()))
                                 .setDescr(removeNewlines(tokens[2].trim()));

        Children children = attached.getChildren();
        if (isNull(children)) {
            return param;
        }

        // 遍历 annots/generic/genericTyp
        List<Annot> annots = param.getAnnotations();
        List<Attached> attachedList = children.getAttached();
        List<Property> properties = param.getProperties();
        String paramTitle;
        String[] splits;
        for (Attached paramAttached : attachedList) {
            paramTitle = paramAttached.getTitle();
            splits = splitInto3Parts(paramTitle);
            String title = splits[0].trim();
            ParamType paramTyp = getParamType(title, log, false);
            ParamProperty paramProperty = null;
            if (isNull(paramTyp)) {
                paramProperty = getParamProperty(title, log);
            }
            if (splits.length != 3 || (isNull(paramTyp) && isNull(paramProperty))) {
                log.info("title [" + paramTitle + "] 格式错误, 标准格式: type/Key/Value");
                generateOfAttachedWithModule(paramAttached, moduleList, module);
                continue;
            }
            if (nonNull(paramTyp) && (ANNOT.equals(paramTyp) || ANNOTATION.equals(paramType))) {
                Annot annotation = generateAnnot(paramAttached, moduleList, module, splits);
                if (nonNull(annotation)) {
                    annots.add(annotation);
                }
            }
            else if (nonNull(paramTyp) && GENERIC_TYP.equals(paramTyp)) {
                String genericTyp = firstLetterToUpper(splits[1].trim());
                if (hasText(genericTyp)) {
                    param.setGenericTyp(genericTyp);
                }
                Children attachedChildren = paramAttached.getChildren();
                if (nonNull(attachedChildren)) {
                    generateImport(attachedChildren, moduleList, module, interfac);
                }
            }
            else if (nonNull(paramTyp) && GENERIC.equals(paramType) && GENERIC_VAL.equals(paramTyp)) {
                String genericVal = splits[1].trim();
                param.setTyp(genericVal);
                Children paramAttachedChildren = paramAttached.getChildren();
                if (nonNull(paramAttachedChildren)) {
                    generateOfChildren(children, moduleList, module);
                }
            }
            else if (nonNull(paramProperty)) {
                switch (paramProperty) {
                    case ARRAY_TYP:
                        properties.add(new Property().setName(paramProperty.getType()).setValue("true"));
                        break;
                    case LIST_TYP: case SET_TYP: case MAP_TYP: case COLLECTION_TYP:
                        properties.add(new Property().setName(paramProperty.getType()).setValue(splits[1].trim()));
                        break;
                    default:
                        generateOfAttachedWithModule(paramAttached, moduleList, module);
                }
            }
            else {
                Children paramAttachedChildren = paramAttached.getChildren();
                if (nonNull(paramAttachedChildren)) {
                    generateOfChildren(children, moduleList, module);
                }
            }
        }

        return param;
    }

    private void resolveEntity(@NonNull Children children, @NonNull List<Module> moduleList,
                               @NonNull Module module, @NonNull String title, @NonNull Interfac interfac) {
        String[] splits = splitInto3Parts(title);
        if (splits.length != 3) {
            log.info("title [" + title + "] 格式错误, 标准格式: TitleType/TypeName/[description]");
            generateOfChildren(children, moduleList, module, null, title);
            return;
        }

        String name = splits[1].trim();
        String commentStr = removeNewlines(splits[2].trim());
        String entityName = firstLetterToUpper(name);

        // 新增 entity
        Entity entity = new Entity().setName(entityName)
                                    .setDescr(commentStr);
        // 是否新建对象
        boolean isNewCreatedEntity = false;
        // 缓存包后置处理信息
        module.getApiImports().put(entity.getName(), interfac);

        // 遍历 entity 字段
        Set<String> imports = entity.getImports();
        Set<Annot> entityAnnotations = entity.getAnnotations();
        List<Param> params = entity.getFields();
        List<Attached> attachedList = children.getAttached();
        if (isNull(attachedList) || attachedList.size() == 0) {
            return;
        }
        String fieldTitle;
        for (Attached attached : attachedList) {
            fieldTitle = attached.getTitle();
            splits = splitInto3Parts(fieldTitle);
            ParamType paramType = getParamType(splits[0].trim(), log);
            if (splits.length != 3 || isNull(paramType)) {
                log.info("title [" + fieldTitle + "] 格式错误, 标准格式: paramType/paramName/[description]");
                generateOfAttachedWithModule(attached, moduleList, module);
                continue;
            }
            if (ParamType.ENTITY.equals(paramType)) {
                Param entityParam = generateEntity(attached.getChildren(), moduleList, module, fieldTitle, entity);
                if (nonNull(entityParam)) {
                    params.add(entityParam);
                    isNewCreatedEntity = true;
                }
            }
            else if (IMPORT.equals(paramType)) {
                String objName = firstLetterToUpper(splits[1].trim());
                String pkg = splits[2].trim();
                interfac.getImports().add(pkg);
                module.getOtherImports().put(objName, pkg);
                Children attachedChildren = attached.getChildren();
                if (nonNull(attachedChildren)) {
                    generateOfChildren(attachedChildren, moduleList, module);
                }
            }
            else if ((ANNOT.equals(paramType) || ANNOTATION.equals(paramType))) {
                Annot annotation = generateAnnot(attached, moduleList, module, splits);
                if (nonNull(annotation)) {
                    entityAnnotations.add(annotation);
                }
            }
            else if (ANNOT_E.equals(paramType)) {
                Annot annotation = generateAnnot(attached, moduleList, module, splits);
                if (nonNull(annotation)) {
                    entityAnnotations.add(annotation);
                }
            }
            else if (GENERIC_TYP.equals(paramType)) {
                String genericTyp = firstLetterToUpper(splits[1].trim());
                entity.setGenericTyp(genericTyp);
                Children attachedChildren = attached.getChildren();
                if (nonNull(attachedChildren)) {
                    generateImport(attachedChildren, moduleList, module, entity);
                }
            }
            else {
                String paramTypePkg = paramType.getPkg();
                if (hasText(paramTypePkg)) {
                    imports.add(paramTypePkg);
                }
                Param fieldParam = generateField(attached, moduleList, module, splits, paramType, entity);
                if (nonNull(fieldParam)) {
                    params.add(fieldParam);
                    isNewCreatedEntity = true;
                }
            }
        }
        if (isNewCreatedEntity) {
            module.addEntity(entity);
        }
    }


    //  ------------------------------- entity -------------------------------

    @Nullable
    private Param generateEntity(@NonNull Children children, @NonNull List<Module> moduleList,
                                 @NonNull Module module, @NonNull String title, @Nullable Entity pEntity) {
        String[] splits = splitInto3Parts(title);
        if (splits.length != 3) {
            log.info("title [" + title + "] 格式错误, 标准格式: TitleType/TypeName/[description]");
            generateOfChildren(children, moduleList, module, null, title);
            return null;
        }

        String name = splits[1].trim();
        String commentStr = removeNewlines(splits[2].trim());
        String entityName = firstLetterToUpper(name);
        // add param
        Param param = null;
        List<Annot> paramAnnotations = null;
        if (nonNull(pEntity)) {
            param = new Param().setTyp(entityName)
                               .setName(firstLetterToLower(firstLetterToLower(name)))
                               .setDescr(commentStr);
            paramAnnotations = param.getAnnotations();
        }
        // 新增 entity
        Entity entity = new Entity().setName(entityName)
                                    .setDescr(commentStr);
        // 是否新建对象
        boolean isNewCreatedEntity = false;

        // 遍历 entity 字段
        Set<String> imports = entity.getImports();
        Set<Annot> entityAnnotations = entity.getAnnotations();
        List<Param> params = entity.getFields();
        List<Attached> attachedList = children.getAttached();
        if (isNull(attachedList) || attachedList.size() == 0) {
            return null;
        }
        String fieldTitle;
        for (Attached attached : attachedList) {
            fieldTitle = attached.getTitle();
            splits = splitInto3Parts(fieldTitle);
            ParamType paramType = getParamType(splits[0].trim(), log);
            if (splits.length != 3 || isNull(paramType)) {
                log.info("title [" + fieldTitle + "] 格式错误, 标准格式: paramType/paramName/[description]");
                generateOfAttachedWithModule(attached, moduleList, module);
                continue;
            }
            if (ParamType.ENTITY.equals(paramType)) {
                Param entityParam = generateEntity(attached.getChildren(), moduleList, module, fieldTitle, entity);
                if (nonNull(entityParam)) {
                    params.add(entityParam);
                    isNewCreatedEntity = true;
                }
            }
            else if (IMPORT.equals(paramType) && nonNull(pEntity)) {
                String objName = firstLetterToUpper(splits[1].trim());
                String pkg = splits[2].trim();
                pEntity.getImports().add(pkg);
                module.getOtherImports().put(objName, pkg);
                Children attachedChildren = attached.getChildren();
                if (nonNull(attachedChildren)) {
                    generateOfChildren(attachedChildren, moduleList, module);
                }
            }
            else if ((ANNOT.equals(paramType) || ANNOTATION.equals(paramType)) && nonNull(pEntity)) {
                Annot annotation = generateAnnot(attached, moduleList, module, splits);
                if (nonNull(annotation)) {
                    paramAnnotations.add(annotation);
                }
            }
            else if (ANNOT_E.equals(paramType)) {
                Annot annotation = generateAnnot(attached, moduleList, module, splits);
                if (nonNull(annotation)) {
                    entityAnnotations.add(annotation);
                }
            }
            else if (GENERIC_TYP.equals(paramType)) {
                String genericTyp = firstLetterToUpper(splits[1].trim());
                entity.setGenericTyp(genericTyp);
                Children attachedChildren = attached.getChildren();
                if (nonNull(attachedChildren)) {
                    generateImport(attachedChildren, moduleList, module, entity);
                }
            }
            else {
                String paramTypePkg = paramType.getPkg();
                if (hasText(paramTypePkg)) {
                    imports.add(paramTypePkg);
                }
                Param fieldParam = generateField(attached, moduleList, module, splits, paramType, entity);
                if (nonNull(fieldParam)) {
                    params.add(fieldParam);
                    isNewCreatedEntity = true;
                }
            }
        }
        if (isNewCreatedEntity) {
            module.addEntity(entity);
            // 缓存包后置处理信息
            if (nonNull(pEntity)) {
                module.getEntityImports().put(entity.getName(), pEntity);
            }
        }
        return param;
    }

    private void generateImport(@NonNull Children children, @NonNull List<Module> moduleList,
                                @NonNull Module module, @NonNull Import importObj) {
        List<Attached> attachedList = children.getAttached();
        if (attachedList.size() == 0) {
            return;
        }
        for (Attached attached : attachedList) {
            String title = attached.getTitle().trim();
            String[] splits = splitInto3Parts(title);
            ParamType paramType = getParamType(splits[0].trim(), log, FALSE);
            if (splits.length != 3 && nonNull(paramType)) {
                log.info("title [" + title + "] 格式错误, 标准格式: type/name/comment");
                generateOfAttachedWithModule(attached, moduleList, module);
                continue;
            }
            if (GENERIC_TYP.equals(paramType)) {
                String entityName = firstLetterToUpper(splits[1].trim());
                String pkg = splits[2].trim();
                importObj.getImports().add(pkg);
                module.getOtherImports().put(entityName, pkg);
                Children attachedChildren = attached.getChildren();
                if (nonNull(attachedChildren)) {
                    generateOfChildren(attachedChildren, moduleList, module);
                }
            }
            else {
                generateOfAttachedWithModule(attached, moduleList, module);
            }
        }

    }

    @Nullable
    private Annot generateAnnot(@NonNull Attached attached, @NonNull List<Module> moduleList,
                                @NonNull Module module, @NonNull String[] tokens) {

        // 新增 annot
        String annotName = firstLetterToUpper(tokens[1].trim());
        Annotation annotation = getAnnotation(annotName, log);
        Children children = attached.getChildren();
        if (isNull(annotation)) {
            if (nonNull(children)) {
                generateOfChildren(children, moduleList, module, null, "");
            }
            return null;
        }
        Annot annot = new Annot().setName(annotName);
        List<AnnotVal> annotVals = annot.getAnnotVals();

        // 添加 annot 的 键值对
        if (nonNull(children)) {
            List<Attached> attachedList = children.getAttached();
            for (Attached annotValAttached : attachedList) {
                String title = annotValAttached.getTitle().trim();
                String[] splits = splitInto3Parts(title);
                if (splits.length != 3) {
                    log.info("title [" + title + "] 格式错误, 标准格式: annot_val/annot_valKey/annot_valValue");
                    generateOfChildren(children, moduleList, module, null, title);
                    continue;
                }
                AnnotVal annotVal = generateAnnotVal(annotValAttached, moduleList, module, splits);
                if (nonNull(annotVal)) {
                    annotVals.add(annotVal);
                }
            }
        }

        return annot;
    }

    @Nullable
    private AnnotVal generateAnnotVal(@NonNull Attached attached, @NonNull List<Module> moduleList,
                                      @NonNull Module module, @NonNull String[] tokens) {

        ParamType paramType = getParamType(tokens[0].trim(), log);
        if (isNull(paramType) || !ANNOT_VAL.equals(paramType)) {
            generateOfAttachedWithModule(attached, moduleList, module);
            return null;
        }

        // 新增 annot 的 键值对
        String annotValKey = firstLetterToUpper(tokens[1].trim());
        String annotValValue = removeNewlines(tokens[2].trim());

        return new AnnotVal().setKey(annotValKey)
                             .setValue(annotValValue);
    }

    @Nullable
    private Param generateField(@NonNull Attached attached, @NonNull List<Module> moduleList,
                                @NonNull Module module, @NonNull String[] tokens,
                                @NonNull ParamType paramType, @NonNull Entity entity) {

        // 新增 param
        String typ = paramType.getType();
        if (!hasText(typ)) {
            typ = firstLetterToUpper(tokens[0].trim());
        }
        Param param = new Param().setTyp(typ)
                                 .setName(firstLetterToLower(tokens[1].trim()))
                                 .setDescr(removeNewlines(tokens[2].trim()));

        Children children = attached.getChildren();
        if (isNull(children)) {
        	return param;
        }

        // 遍历 annots/generic/genericTyp
        List<Annot> annots = param.getAnnotations();
        List<Attached> attachedList = children.getAttached();
        List<Property> properties = param.getProperties();
        String paramTitle;
        String[] splits;
        for (Attached paramAttached : attachedList) {
            paramTitle = paramAttached.getTitle();
            splits = splitInto3Parts(paramTitle);
            String title = splits[0].trim();
            ParamType paramTyp = getParamType(title, log, false);
            ParamProperty paramProperty = null;
            if (isNull(paramTyp)) {
                paramProperty = getParamProperty(title, log);
            }
            if (splits.length != 3 || (isNull(paramTyp) && isNull(paramProperty))) {
                log.info("title [" + paramTitle + "] 格式错误, 标准格式: type/key/value");
                generateOfAttachedWithModule(paramAttached, moduleList, module);
                continue;
            }
            if (nonNull(paramTyp) && (ANNOT.equals(paramTyp) || ANNOTATION.equals(paramType))) {
                Annot annotation = generateAnnot(paramAttached, moduleList, module, splits);
                if (nonNull(annotation)) {
                    annots.add(annotation);
                }
            }
            else if (nonNull(paramTyp) && GENERIC_TYP.equals(paramTyp)) {
                String genericTyp = firstLetterToUpper(splits[1].trim());
                if (hasText(genericTyp)) {
                    param.setGenericTyp(genericTyp);
                }
                Children attachedChildren = paramAttached.getChildren();
                if (nonNull(attachedChildren)) {
                    generateImport(attachedChildren, moduleList, module, entity);
                }
            }
            else if (nonNull(paramTyp) && GENERIC.equals(paramType) && GENERIC_VAL.equals(paramTyp)) {
                String genericVal = splits[1].trim();
                param.setTyp(genericVal);
                Children paramAttachedChildren = paramAttached.getChildren();
                if (nonNull(paramAttachedChildren)) {
                    generateOfChildren(children, moduleList, module);
                }
            }
            else if (nonNull(paramProperty)) {
                switch (paramProperty) {
                    case ARRAY_TYP:
                        properties.add(new Property().setName(paramProperty.getType()).setValue("true"));
                        break;
                    case LIST_TYP: case SET_TYP: case MAP_TYP: case COLLECTION_TYP:
                        properties.add(new Property().setName(paramProperty.getType()).setValue(splits[1].trim()));
                        break;
                    default:
                        generateOfAttachedWithModule(paramAttached, moduleList, module);
                }
            }
            else {
                generateOfAttachedWithModule(paramAttached, moduleList, module);
            }
        }

        return param;
    }

    //  ------------------------------- database -------------------------------

    private void generateDatabase(@NonNull Children children, @NonNull List<Module> moduleList,
                                  @NonNull Module module, @NonNull String pTitle) {
        String[] splits = splitInto3Parts(pTitle);
        if (splits.length != 3) {
            log.info("title [" + pTitle + "] 格式错误, 标准格式: TitleType/TypeName/[description]");
            generateOfChildren(children, moduleList, module, null, pTitle);
            return;
        }
        // 新增 database
        String databaseName = splits[1].trim();
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
                log.info("title [" + tableTitle + "] 格式错误, 标准格式: TitleType/TypeName/[description]");
                generateOfAttachedWithModule(attached, moduleList, module);
                continue;
            }
            generateTable(attached.getChildren(), moduleList, module, database, splits);
        }
    }

    private void generateTable(@NonNull Children children, @NonNull List<Module> moduleList,
                               @NonNull Module module, @NonNull Database database,
                               @NonNull String[] tokens) {
        // 判断是否 table 命名空间
        String type = tokens[0].trim();
        try {
            TiTleType tiTleType = TiTleType.valueOf(type.toUpperCase());
            if (!TABLE.equals(tiTleType)) {
                generateOfChildren(children, moduleList, module, null, "");
            }
        }
        catch (Exception e) {
            log.error("title [" + type + "] 不能转换为 TitleType", e);
            generateOfChildren(children, moduleList, module, null, "");
            return;
        }

        // add table
        String tableName = camelToUnderscore(tokens[1].trim());
        String tableDecr = removeNewlines(tokens[2].trim());
        Table table = new Table().setName(tableName).setComment(tableDecr);
        database.getTables().add(table);

        // 遍历 column
        List<Attached> attachedList = children.getAttached();
        if (isNull(attachedList) || attachedList.size() == 0) {
            return;
        }
        for (Attached attached : attachedList) {
            generateColumn(attached, moduleList, module, table);
        }

    }

    private void generateColumn(@NonNull Attached attached, @NonNull List<Module> moduleList,
                                @NonNull Module module, @NonNull Table table) {
        // add column
        String title = attached.getTitle().trim();
        String[] splits = splitInto3Parts(title);
        if (splits.length != 3) {
            log.info("title [" + title + "] 格式错误, 标准格式: columnType/columnName/[comment]");
            generateOfAttachedWithModule(attached, moduleList, module);
            return ;
        }
        String columnTypeStr = splits[0].trim();
        String columnName = splits[1].trim();
        String comment = removeNewlines(splits[2].trim());
        ColumnType columnType = getColumnType(columnTypeStr, log);
        if (isNull(columnType)) {
            generateOfAttachedWithModule(attached, moduleList, module);
            return ;
        }
        Column column = new Column().setTyp(columnTypeStr)
                                    .setName(columnName)
                                    .setComment(comment);
        table.getColumns().add(column);

        // add column property
        Children children = attached.getChildren();
        if (isNull(children)) {
        	return;
        }
        generateProperty(children, moduleList, module, column, columnType);

    }

    private void generateProperty(@NonNull Children children, @NonNull List<Module> moduleList,
                                  @NonNull Module module, @NonNull Column column,
                                  @NonNull ColumnType columnType) {

        List<Attached> attachedList = children.getAttached();
        if (isNull(attachedList) || attachedList.size() == 0) {
            return;
        }

        // add column property
        List<Property> properties = column.getProperties();
        for (Attached attached : attachedList) {
            String propTitle = attached.getTitle().trim();
            String[] propSplits = splitInto3Parts(propTitle);
            if (propSplits.length != 3) {
                log.info("title [" + propTitle + "] 格式错误, 标准格式: propType/propValue/[description]");
                generateOfAttachedWithModule(attached, moduleList, module);
                continue;
            }
            String propName = propSplits[0].trim();
            String propValue = propSplits[1].trim();
            if (propValue.length() == 0) {
            	propValue = columnType.getDefValue();
            }
            ColumnProperty columnProperty = getColumnProperty(propName, log);
            if (isNull(columnProperty)) {
                generateOfAttachedWithModule(attached, moduleList, module);
                continue;
            }
            properties.add(new Property().setName(propName).setValue(propValue));
        }

    }

}
