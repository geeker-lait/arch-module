package org.arch.framework.automate;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.ArchiveException;
import org.arch.framework.automate.generater.service.xmind.meta.Attached;
import org.arch.framework.automate.generater.service.xmind.meta.Children;
import org.arch.framework.automate.generater.service.xmind.meta.JsonRootBean;
import org.arch.framework.automate.generater.service.xmind.meta.RootTopic;
import org.arch.framework.automate.generater.service.xmind.parser.XmindParser;
import org.arch.framework.automate.xmind.module.Module;
import org.arch.framework.automate.xmind.nodespace.ColumnProperty;
import org.arch.framework.automate.xmind.nodespace.ColumnType;
import org.arch.framework.automate.xmind.nodespace.TiTleType;
import org.arch.framework.automate.xmind.table.Column;
import org.arch.framework.automate.xmind.table.Database;
import org.arch.framework.automate.xmind.table.Property;
import org.arch.framework.automate.xmind.table.Table;
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

import static java.util.Objects.isNull;
import static org.arch.framework.automate.xmind.nodespace.TiTleType.API;
import static org.arch.framework.automate.xmind.nodespace.TiTleType.DATABASE;
import static org.arch.framework.automate.xmind.nodespace.TiTleType.ENTITY;
import static org.arch.framework.automate.xmind.nodespace.TiTleType.MODULE;
import static org.arch.framework.automate.xmind.nodespace.TiTleType.SHEET;
import static org.arch.framework.automate.xmind.nodespace.TiTleType.TABLE;
import static org.arch.framework.automate.xmind.utils.XmindUtils.camelToUnderscore;
import static org.arch.framework.automate.xmind.utils.XmindUtils.getColumnProperty;
import static org.arch.framework.automate.xmind.utils.XmindUtils.getColumnType;
import static org.arch.framework.automate.xmind.utils.XmindUtils.getTiTleType;
import static org.arch.framework.automate.xmind.utils.XmindUtils.splitInto3Parts;

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
        String fileName = "minds"+ File.separator +"ofs-alarm-center.xmind";
        Resource resource = new ClassPathResource(fileName);
        String absolutePath = resource.getFile().getAbsolutePath();

        String res = XmindParser.parseJson(absolutePath);
        System.out.println(res+ "\n\n\n\n\n\n=================================================" );

        JsonRootBean root = XmindParser.parseObject(absolutePath, JsonRootBean.class);

        List<Module> moduleList = new ArrayList<>();

        generate(root, moduleList);

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
        if (isNull(tiTleType)) {
            List<Attached> attachedList = children.getAttached();
            if (isNull(attachedList) || attachedList.size() == 0) {
                return;
            }
            for (Attached attached : attachedList) {
                generateOfAttached(attached, moduleList);
            }
            return;
        }
        Module module = new Module();
        moduleList.add(module);
        generateChildrenByTitleType(moduleList, module, title, tiTleType, children);
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
        	return;
        }
        generateWithModule(children, moduleList, module, title, tiTleType);
    }

    private void generateWithModule(@Nullable Children children, @NonNull List<Module> moduleList,
                                    @NonNull Module module,
                                    @NonNull String title, @Nullable TiTleType tiTleType) {
        if (isNull(children)) {
            return;
        }
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
        generateChildrenByTitleType(moduleList, module, title, tiTleType, children);
    }

    /**
     * 根据 {@link Children} 的内容生成相应的规格内容, 添加到 {@link Module}.
     * @param moduleList    {@link Module} list
     * @param module        {@link Module}
     * @param pTitle        pTitle
     * @param pTiTleType    {@link TiTleType}
     * @param children      {@link Children}
     */
    private void generateChildrenByTitleType(@NonNull List<Module> moduleList, @NonNull Module module,
                                             @NonNull String pTitle, @NonNull TiTleType pTiTleType,
                                             @NonNull Children children) {

        switch (pTiTleType) {
            case MODULE:
                generateOfChildren(children, moduleList, module, MODULE, pTitle);
                break;
            case DATABASE:
                generateOfChildren(children, moduleList, module, DATABASE, pTitle);
                break;
            case API:
                generateOfChildren(children, moduleList, module, API, pTitle);
                break;
            case ENTITY:
                generateOfChildren(children, moduleList, module, ENTITY, pTitle);
                break;
            default:
                generateOfChildren(children, moduleList, module, null, pTitle);
        }
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
        // module
        else if (MODULE.equals(pTiTleType) && pTitle.length() > 3) {
            String[] splits = splitInto3Parts(pTitle);
            if (splits.length == 3) {
                module.setTyp(splits[0].trim())
                      .setName(splits[1].trim())
                      .setComment(splits[2].trim());
            }
        }
        // 数据库
        else if (DATABASE.equals(pTiTleType) && pTitle.length() > 3) {
            generateDatabase(children, moduleList, module, pTitle);
            return;
        }
        // api
        else if (API.equals(pTiTleType) && pTitle.length() > 3) {
            generateApi(children, moduleList, module, pTitle);
            return;
        }
        // entity
        else if (ENTITY.equals(pTiTleType) && pTitle.length() > 3) {
            generateEntity(children, moduleList, module, pTitle);
            return;
        }

        generateOfChildren(children, moduleList, module);
    }

    //  ------------------------------- api -------------------------------

    private void generateApi(@NonNull Children children, @NonNull List<Module> moduleList,
                                @NonNull Module module, @NonNull String title) {
        // TODO: 2021.4.27 生成 api
    }

    //  ------------------------------- entity -------------------------------

    private void generateEntity(@NonNull Children children, @NonNull List<Module> moduleList,
                                @NonNull Module module, @NonNull String title) {
        // TODO: 2021.4.27 生成 entity
    }

    //  ------------------------------- database -------------------------------

    private void generateDatabase(@NonNull Children children, @NonNull List<Module> moduleList,
                                  @NonNull Module module, @NonNull String title) {
        String[] splits = splitInto3Parts(title);
        if (splits.length != 3) {
            log.warn("title [" + title + "] 格式错误, 标准格式: TitleType/TypeName/[description]");
            generateOfChildren(children, moduleList, module, null, title);
            return;
        }
        // 新增 database
        String databaseName = splits[1].trim();
        String commentStr = splits[2].trim();
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
                log.warn("title [" + tableTitle + "] 格式错误, 标准格式: TitleType/TypeName/[description]");
                generateOfAttachedWithModule(attached, moduleList, module);
                return;
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
        String tableDecr = tokens[2].trim().replaceAll("[\n\r]", " ");
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
            log.warn("title [" + title + "] 格式错误, 标准格式: columnType/columnName/[comment]");
            generateOfAttachedWithModule(attached, moduleList, module);
            return ;
        }
        String columnTypeStr = splits[0].trim();
        String columnName = splits[1].trim();
        String comment = splits[2].trim().replaceAll("[\n\r]", " ");
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
                log.warn("title [" + propTitle + "] 格式错误, 标准格式: propType/propValue/[description]");
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
