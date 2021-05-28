package org.arch.framework.automate.generater.reader.xmind.parser;

import org.arch.framework.automate.common.api.Annot;
import org.arch.framework.automate.common.api.AnnotVal;
import org.arch.framework.automate.common.api.Api;
import org.arch.framework.automate.common.api.Curl;
import org.arch.framework.automate.common.api.Interfac;
import org.arch.framework.automate.common.module.Module;
import org.arch.framework.automate.generater.reader.xmind.meta.Attached;
import org.arch.framework.automate.generater.reader.xmind.meta.Children;
import org.arch.framework.automate.generater.reader.xmind.nodespace.Annotation;
import org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType;
import org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Set;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType.DEL;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType.GET;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType.METHOD;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType.POST;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType.PUT;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindAnnotAndGenericParser.generateAnnot;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindAnnotAndGenericParser.generateAnnotes;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindAnnotAndGenericParser.generateUriAnnot;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindImportParser.generateImport;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindMethodParser.generateCurl;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindProjectParser.generateOfAttachedWithModule;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.firstLetterToUpper;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.removeNewlines;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.splitInto3Parts;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.strToUpperCamelWithPinYin;

/**
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.23 16:56
 */
public class XmindInterfaceParser {

    public static final Logger LOG = LoggerFactory.getLogger(XmindInterfaceParser.class);

    @SuppressWarnings("AlibabaMethodTooLong")
    static void generateInterface(@NonNull Children children, @NonNull List<Module> moduleList,
                                  @NonNull Module module, @NonNull Api api, @NonNull String[] tokens) {
        // add interface
        String orgName = tokens[1].trim();
        String interfaceName = strToUpperCamelWithPinYin(orgName);
        String comment = removeNewlines(tokens[2].trim());
        Interfac interfac = new Interfac().setName(interfaceName).setDescr(comment)
                                          .setApi(api.getName()).setApiDescr(api.getDescr());
        api.addInterface(interfac);

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
            ParamType paramType = XmindUtils.getParamType(tokens[0].trim());
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
}
