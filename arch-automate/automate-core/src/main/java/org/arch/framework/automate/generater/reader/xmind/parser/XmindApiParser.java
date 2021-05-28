package org.arch.framework.automate.generater.reader.xmind.parser;

import org.arch.automate.common.api.Api;
import org.arch.automate.common.Module;
import org.arch.framework.automate.generater.reader.xmind.meta.Attached;
import org.arch.framework.automate.generater.reader.xmind.meta.Children;
import org.arch.framework.automate.generater.reader.xmind.nodespace.TiTleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.TiTleType.API;
import static org.arch.framework.automate.generater.reader.xmind.nodespace.TiTleType.INTERFACE;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindInterfaceParser.generateInterface;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindProjectParser.generateOfAttachedWithModule;
import static org.arch.framework.automate.generater.reader.xmind.parser.XmindProjectParser.generateOfChildren;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.firstLetterToLower;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.getTiTleType;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.removeNewlines;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.splitInto3Parts;
import static org.arch.framework.automate.generater.reader.xmind.utils.XmindUtils.underscoreToCamel;

/**
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.23 16:56
 */
public class XmindApiParser {

    public static final Logger LOG = LoggerFactory.getLogger(XmindApiParser.class);

    static void generateApi(@NonNull Children children, @NonNull Set<Module> moduleSet,
                            @NonNull Module module, @NonNull TiTleType pTiTleType,
                            @NonNull String pTitle) {

        if (!API.equals(pTiTleType)) {
            generateOfChildren(children, moduleSet, module);
            return;
        }
        String[] apiTokens = splitInto3Parts(pTitle);
        String apiName = firstLetterToLower(underscoreToCamel(apiTokens[1].trim()));
        Api api = new Api().setName(apiName).setDescr(removeNewlines(apiTokens[2].trim()));
        module.addApi(api);
        List<Attached> apiAttachedList = children.getAttached();
        for (Attached interfaceAttached : apiAttachedList) {
            String title = interfaceAttached.getTitle().trim();
            String[] splits = splitInto3Parts(title);
            TiTleType tiTleType = getTiTleType(title);
            if (splits.length != 3 || isNull(tiTleType)) {
                LOG.debug("title [" + title + "] 格式错误, 标准格式: paramType/paramName/[description]");
                generateOfAttachedWithModule(interfaceAttached, moduleSet, module);
                continue;
            }
            if (INTERFACE.equals(tiTleType)) {
                // add inteface
                Children interfaceChildren = interfaceAttached.getChildren();
                if (nonNull(interfaceChildren)) {
                    generateInterface(interfaceChildren, moduleSet, module, api, splits);
                }
            } else {
                generateOfAttachedWithModule(interfaceAttached, moduleSet, module);
            }
        }
    }
}
