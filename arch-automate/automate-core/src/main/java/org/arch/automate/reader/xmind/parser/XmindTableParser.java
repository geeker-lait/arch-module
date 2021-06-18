package org.arch.automate.reader.xmind.parser;

import org.arch.framework.beans.schema.database.Column;
import org.arch.framework.beans.schema.database.Database;
import org.arch.framework.beans.schema.database.Table;
import org.arch.automate.Module;
import org.arch.automate.reader.xmind.meta.Attached;
import org.arch.automate.reader.xmind.meta.Children;
import org.arch.automate.reader.xmind.nodespace.ColumnProperty;
import org.arch.automate.reader.xmind.nodespace.ColumnType;
import org.arch.automate.reader.xmind.nodespace.TiTleType;
import org.arch.automate.reader.xmind.utils.XmindUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
import static org.arch.automate.reader.xmind.nodespace.ColumnType.BIGINT;
import static org.arch.automate.reader.xmind.nodespace.TiTleType.TABLE;
import static org.arch.automate.reader.xmind.parser.XmindProjectParser.generateOfAttachedWithModule;
import static org.arch.automate.reader.xmind.parser.XmindProjectParser.generateOfChildren;
import static org.arch.automate.reader.xmind.utils.XmindUtils.COLUMN_PROPERTY_SEPARATOR;
import static org.arch.automate.reader.xmind.utils.XmindUtils.getColumnProperty;
import static org.arch.automate.reader.xmind.utils.XmindUtils.removeNewlines;
import static org.arch.automate.reader.xmind.utils.XmindUtils.splitInto3Parts;
import static org.arch.automate.reader.xmind.utils.XmindUtils.strToUnderscoreWithPinYin;
import static org.springframework.util.StringUtils.hasText;

/**
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.23 16:56
 */
public class XmindTableParser {

    public static final Logger LOG = LoggerFactory.getLogger(XmindTableParser.class);

    public static void generateTable(@NonNull Children children, @NonNull Set<Module> moduleSet,
                                      @NonNull Module module, @NonNull Database database,
                                      @NonNull String[] tokens) {
        // 判断是否 table 命名空间
        String type = tokens[0].trim();
        try {
            TiTleType tiTleType = TiTleType.valueOf(type.toUpperCase());
            if (!TABLE.equals(tiTleType)) {
                generateOfChildren(children, moduleSet, module, null, "");
                return;
            }
        } catch (Exception e) {
            LOG.error("title [" + type + "] 不能转换为 TitleType", e);
            generateOfChildren(children, moduleSet, module, null, "");
            return;
        }

        // add table
        String tableName = strToUnderscoreWithPinYin(tokens[1].trim());
        String tableDecr = removeNewlines(tokens[2].trim());
        Table table = new Table().setName(tableName).setComment(tableDecr);
        database.getTables().add(table);

        // 遍历 column
        // Map(pkProp, column)
        Map<String, String> pkMap = new TreeMap<>();
        // Map(uniqueGroupProp, column)
        Map<String, String> uniqueMap = new TreeMap<>();
        // Map(indexGroupProp, column)
        Map<String, String> indexMap = new TreeMap<>();
        List<Attached> attachedList = children.getAttached();
        if (isNull(attachedList) || attachedList.size() == 0) {
            return;
        }
        for (Attached attached : attachedList) {
            generateColumn(attached, moduleSet, module, table, pkMap, uniqueMap, indexMap);
        }

        // 拼装 pk 索引
        if (pkMap.size() == 0) {
            // 遍历 columns 查找是否有 colName = id 的字段
            Optional<Column> columnOptional = table.getColumns()
                                                   .stream()
                                                   .filter(col -> "id".equals(col.getName()))
                                                   .findFirst();


            // 有 id 字段设置为主键
            if (columnOptional.isPresent()) {
                Column column = columnOptional.get();
                String columnType = column.getTyp();
                int indexOf = columnType.indexOf("(");
                columnType = indexOf != -1 ? columnType.substring(0, indexOf) : columnType;
                if (ColumnType.isInteger(columnType)) {
                    column.setAutoIncrement(true);
                }
                column.setPk(true).setNotnull(true);
            }
            // 没有 id 字段添加 id 字段并设置为自增主键
            else {
                String typ = BIGINT.getType().concat("(").concat(BIGINT.getDefValue()).concat(")");
                table.getColumns().add(new Column().setName("id")
                                                   .setTyp(typ)
                                                   .setComment("主键id")
                                                   .setPk(true)
                                                   .setNotnull(true)
                                                   .setAutoIncrement(true));
            }
            table.setPkStatement("PRIMARY KEY (`id`)");
        } else {
            // 拼装
            final StringBuilder pkStat = new StringBuilder();
            pkStat.append("PRIMARY KEY (`");
            pkMap.forEach((pkGroup, column) -> pkStat.append(column).append("`, `"));
            pkStat.setLength(pkStat.length() - 3);
            pkStat.append(")");
            table.setPkStatement(pkStat.toString());
        }

        // 拼装 unique 索引
        resolveIndexStat(table.getUniquesStatements(), uniqueMap, "UNIQUE KEY");
        // 拼装 index 索引
        resolveIndexStat(table.getIndexesStatements(), indexMap, "INDEX");
    }

    /**
     * 解析为索引语句, 不包含末尾逗号
     *
     * @param indexStatList 存放索引语句列表
     * @param indexMap      Map(idxGroupProp, column)
     * @param idxKeyword    索引关键字, 如: UNIQUE KEY/INDEX/KEY
     */
    private static void resolveIndexStat(@NonNull Set<String> indexStatList, @NonNull Map<String, String> indexMap,
                                         @NonNull String idxKeyword) {
        if (indexMap.isEmpty()) {
            return;
        }
        // 分组: TreeMap(indexGroup[indexPropPre], TreeMap(indexProp, column))
        TreeMap<String, TreeMap<String, String>> groupMap =
                indexMap.entrySet()
                        .stream()
                        .collect(groupingBy(entry -> {
                                                String indexGroup = entry.getKey();
                                                String[] splits = indexGroup.split(COLUMN_PROPERTY_SEPARATOR);
                                                //noinspection AlibabaUndefineMagicConstant
                                                if (splits.length <= 2) {
                                                    return indexGroup;
                                                } else {
                                                    return splits[0].concat(COLUMN_PROPERTY_SEPARATOR).concat(splits[1]);
                                                }
                                            },
                                            TreeMap::new,
                                            toMap(Map.Entry::getKey, Map.Entry::getValue,
                                                  (ignore, ignore2) -> ignore, TreeMap::new)));

        // TreeMap(indexGroup[indexPropPre], TreeMap(indexProp, column))
        groupMap.forEach((indexGroup, map) -> {
            final StringBuilder idxStat = new StringBuilder();
            final StringBuilder idxColumns = new StringBuilder();
            idxColumns.append("(`");
            idxStat.append(idxKeyword).append(" `").append(indexGroup.toUpperCase()).append(COLUMN_PROPERTY_SEPARATOR);
            map.forEach((propName, colName) -> {
                idxStat.append(colName.toUpperCase()).append(COLUMN_PROPERTY_SEPARATOR).append("AND_");
                idxColumns.append(colName).append("`, `");
            });
            idxColumns.setLength(idxColumns.length() - 3);
            idxColumns.append(")");
            idxStat.setLength(idxStat.length() - (3 + 2 * COLUMN_PROPERTY_SEPARATOR.length()));
            idxStat.append("` ").append(idxColumns).append(" USING BTREE");
            indexStatList.add(idxStat.toString());
        });
    }

    private static void generateColumn(@NonNull Attached attached, @NonNull Set<Module> moduleSet,
                                       @NonNull Module module, @NonNull Table table,
                                       @NonNull Map<String, String> pkMap, @NonNull Map<String, String> uniqueMap,
                                       @NonNull Map<String, String> indexMap) {
        // add column
        String title = attached.getTitle().trim();
        String[] splits = XmindUtils.splitInto3Parts(title);
        //noinspection AlibabaUndefineMagicConstant
        if (splits.length != 3) {
            LOG.debug("title [" + title + "] 格式错误, 标准格式: columnType/columnName/[comment]");
            generateOfAttachedWithModule(attached, moduleSet, module);
            return;
        }
        String columnTypeStr = splits[0].trim();
        String columnName = strToUnderscoreWithPinYin(splits[1].trim());
        String comment = removeNewlines(splits[2].trim());
        ColumnType columnType = XmindUtils.getColumnType(columnTypeStr);
        if (isNull(columnType)) {
            generateOfAttachedWithModule(attached, moduleSet, module);
            return;
        }
        String defValue = columnType.getDefValue();
        String type = columnType.getType();
        if (!hasText(type)) {
            type = null;
        }
        Column column = new Column().setTyp(type)
                                    .setLength(ofNullable(defValue).orElse(null))
                                    .setName(columnName)
                                    .setComment(comment);

        table.getColumns().add(column);

        // add column property
        Children children = attached.getChildren();
        if (isNull(children)) {
            return;
        }
        generateProperty(children, moduleSet, module, column, pkMap, uniqueMap, indexMap);

    }

    private static void generateProperty(@NonNull Children children, @NonNull Set<Module> moduleSet,
                                         @NonNull Module module, @NonNull Column column,
                                         @NonNull Map<String, String> pkMap,
                                         @NonNull Map<String, String> uniqueMap, @NonNull Map<String, String> indexMap) {

        List<Attached> attachedList = children.getAttached();
        if (isNull(attachedList) || attachedList.size() == 0) {
            return;
        }

        // add column property
        for (Attached attached : attachedList) {
            String propTitle = attached.getTitle().trim();
            String[] propSplits = splitInto3Parts(propTitle);
            String propName = propSplits[0].trim();
            ColumnProperty columnProperty = getColumnProperty(propName);
            if (propSplits.length < 2 || isNull(columnProperty)) {
                LOG.debug("title [" + propTitle + "] 格式错误, 标准格式: propType/propValue/[description]");
                generateOfAttachedWithModule(attached, moduleSet, module);
                continue;
            }
            String propValue = propSplits[1].trim();
            switch (columnProperty) {
                case LEN:
                case LENGTH:
                    if (hasText(propValue)) {
                        column.setLength(propValue);
                    }
                    break;
                case PK:
                    pkMap.put(propName, column.getName());
                    column.setPk(true).setNotnull(true);
                    break;
                case UNIQUE:
                case UNQ:
                    uniqueMap.put(propName, column.getName());
                    column.setUnique(propValue).setNotnull(true);
                    break;
                case NOTNULL:
                    column.setNotnull(true);
                    break;
                case DEFAULT:
                case DEF:
                    column.setDef(propValue);
                    break;
                case UNSIGNED:
                    column.setUnsigned(true);
                    break;
                case ON_UPDATE:
                    if (hasText(propValue)) {
                        column.setOnUpdate(propValue);
                    } else {
                        column.setOnUpdate("ON UPDATE CURRENT_TIMESTAMP");
                    }
                    break;
                case AUTO_INCREMENT:
                case INCR:
                    column.setAutoIncrement(true);
                    break;
                case INDEX:
                case IDX:
                    indexMap.put(propName, column.getName());
                    column.setIndex(propValue).setNotnull(true);
                    break;
                default:
                    generateOfAttachedWithModule(attached, moduleSet, module);
                    break;
            }
        }

    }
}
