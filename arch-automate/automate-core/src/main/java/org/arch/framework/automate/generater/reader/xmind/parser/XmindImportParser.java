package org.arch.framework.automate.generater.reader.xmind.parser;

import org.arch.framework.automate.common.module.Module;
import org.arch.framework.automate.generater.reader.xmind.Import;
import org.arch.framework.automate.generater.reader.xmind.meta.Attached;
import org.arch.framework.automate.generater.reader.xmind.meta.Children;
import org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.ParamType.IMPORT;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindProjectParser.generateOfAttachedWithModule;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindProjectParser.generateOfChildren;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.firstLetterToLower;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.getParamType;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.splitInto3Parts;

/**
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.23 16:56
 */
public class XmindImportParser {

    public static final Logger LOG = LoggerFactory.getLogger(XmindImportParser.class);

    static void generateImportOfAttached(@NonNull Attached attached, @NonNull List<Module> moduleList,
                                         @NonNull Module module, @NonNull ParamType paramType,
                                         @NonNull String[] tokens, @NonNull Import importObj) {
        if (IMPORT.equals(paramType)) {
            String pkg = firstLetterToLower(tokens[1].trim());
            importObj.getImports().add(pkg);
            Children attachedChildren = attached.getChildren();
            if (nonNull(attachedChildren)) {
                generateOfChildren(attachedChildren, moduleList, module);
            }
        } else {
            generateOfAttachedWithModule(attached, moduleList, module);
        }
    }

    static void generateImport(@NonNull Children children, @NonNull List<Module> moduleList,
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
}
