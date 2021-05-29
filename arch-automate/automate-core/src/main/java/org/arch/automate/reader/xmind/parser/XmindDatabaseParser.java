package org.arch.automate.reader.xmind.parser;

import org.arch.framework.beans.schema.database.Database;
import org.arch.automate.Module;
import org.arch.automate.reader.xmind.meta.Attached;
import org.arch.automate.reader.xmind.meta.Children;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.arch.automate.reader.xmind.parser.XmindProjectParser.generateOfAttachedWithModule;
import static org.arch.automate.reader.xmind.parser.XmindProjectParser.generateOfChildren;
import static org.arch.automate.reader.xmind.utils.XmindUtils.camelToUnderscore;
import static org.arch.automate.reader.xmind.utils.XmindUtils.removeNewlines;
import static org.arch.automate.reader.xmind.utils.XmindUtils.splitInto3Parts;
import static org.arch.automate.reader.xmind.utils.XmindUtils.strToUnderscoreWithPinYin;

/**
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.23 16:56
 */
public class XmindDatabaseParser {

    public static final Logger LOG = LoggerFactory.getLogger(XmindDatabaseParser.class);

    static void generateDatabase(@NonNull Children children, @NonNull Set<Module> moduleSet,
                                 @NonNull Module module, @NonNull String pTitle) {
        String[] splits = splitInto3Parts(pTitle);
        if (splits.length != 3) {
            LOG.debug("title [" + pTitle + "] 格式错误, 标准格式: TitleType/TypeName/[description]");
            generateOfChildren(children, moduleSet, module, null, pTitle);
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
                generateOfAttachedWithModule(attached, moduleSet, module);
                continue;
            }

            Children tableChildren = attached.getChildren();
            if (nonNull(tableChildren)) {
                XmindTableParser.generateTable(tableChildren, moduleSet, module, database, splits);
            }
        }
    }

}
