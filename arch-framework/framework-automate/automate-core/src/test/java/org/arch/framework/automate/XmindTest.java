package org.arch.framework.automate;

import com.google.common.base.CaseFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.compress.archivers.ArchiveException;
import org.arch.framework.automate.generater.service.xmind.meta.Attached;
import org.arch.framework.automate.generater.service.xmind.meta.Children;
import org.arch.framework.automate.generater.service.xmind.meta.JsonRootBean;
import org.arch.framework.automate.generater.service.xmind.meta.RootTopic;
import org.arch.framework.automate.generater.service.xmind.parser.XmindParser;
import org.dom4j.DocumentException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static org.arch.framework.automate.XmindTest.TiTleType.API;
import static org.arch.framework.automate.XmindTest.TiTleType.DATABASE;
import static org.arch.framework.automate.XmindTest.TiTleType.ENTITY;
import static org.arch.framework.automate.XmindTest.TiTleType.MODULE;
import static org.arch.framework.automate.XmindTest.TiTleType.P;
import static org.arch.framework.automate.XmindTest.TiTleType.SHEET;
import static org.arch.framework.automate.XmindTest.TiTleType.TABLE;

/**
 * @ClassName XmindTest
 * @Author http://github.com/geeker-lait
 * @Tel 15801818092
 * @Date 11:09 AM 11/24/2018
 * @Version 1.0.0
 * @Description //TODO
 */
@SpringBootTest
public class XmindTest {

    public static final String SEPARATOR = "/";

    @Test
    public void parse() throws DocumentException, ArchiveException, IOException {
//        String fileName = "minds\\ofs-alarm-er.xmind";
        String fileName = "minds"+ File.separator +"ofs-alarm-center.xmind";
        Resource resource = new ClassPathResource(fileName);
        String absolutePath = resource.getFile().getAbsolutePath();

        String res = XmindParser.parseJson(absolutePath);
        System.out.println(res+ "\n\n\n\n\n\n=================================================" );

        JsonRootBean root = XmindParser.parseObject(absolutePath, JsonRootBean.class);

        StringBuilder ddl = new StringBuilder();
        StringBuilder doc = new StringBuilder();
        StringBuilder api = new StringBuilder();
        generate(root, ddl, doc, api);

        // 写入文件
        String absPathPrefix = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));
        String xmindFileName = resource.getFilename();
        String textFileName = xmindFileName.substring(0, xmindFileName.lastIndexOf(".")).concat(".txt");
        String mdFileName = xmindFileName.substring(0, xmindFileName.lastIndexOf(".")).concat("-doc").concat(".md");
        String xlsxFileName = xmindFileName.substring(0, xmindFileName.lastIndexOf(".")).concat(".xlsx");
        String jsonFileName = xmindFileName.substring(0, xmindFileName.lastIndexOf(".")).concat("-json").concat(".txt");

        Path path = Paths.get(absPathPrefix, File.separator, textFileName);
        Files.write(path, ddl.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

        System.out.println("输出文件路径: " + path.getParent() + File.separator + textFileName);
        System.out.println(res+ "\n=================================================\n" );
        System.out.println(ddl.toString());
    }

    @NonNull
    private void generate(@NonNull JsonRootBean root, @NonNull StringBuilder ddl,
                          @NonNull StringBuilder doc, @NonNull StringBuilder api) {
        TiTleType tiTleType = getTiTleType(root.getTitle().trim());
        if (!SHEET.equals(tiTleType)) {
            throw new RuntimeException("xmind 格式错误");
        }
        generateOfRoot(root.getRootTopic(), ddl, doc, api);
    }

    @NonNull
    private void generateOfRoot(@NonNull RootTopic rootTopic, @NonNull StringBuilder ddl,
                                @NonNull StringBuilder doc, @NonNull StringBuilder api) {
        String title = rootTopic.getTitle().trim();
        TiTleType tiTleType = getTiTleType(title);
        generateChildrenByTitleType(ddl, doc, api, title, tiTleType, rootTopic.getChildren());
    }

    /**
     * 根据 {@link Attached} 的内容生成相应的规格内容, 添加到 ddl/doc/api({@link StringBuilder}).
     * @param attached      {@link Attached}
     * @param ddl           ddl
     * @param doc        doc, 当 {@code pTiTleType} 为 {@link TiTleType#DATABASE}/{@link TiTleType#TABLE} 时, 可以为 null
     * @param api        api, 当 {@code pTiTleType} 为 {@link TiTleType#DATABASE}/{@link TiTleType#TABLE} 时, 可以为 null
     * @param pTiTleType {@link TiTleType}
     */
    @NonNull
    private void generateOfAttached(@NonNull Attached attached, @NonNull StringBuilder ddl,
                                    @Nullable StringBuilder doc, @Nullable StringBuilder api,
                                    @NonNull TiTleType pTiTleType) {
        if (!(DATABASE.equals(pTiTleType) || TABLE.equals(pTiTleType))) {
            requireNonNull(doc, "doc cannot be null");
            requireNonNull(api, "api cannot be null");
        }

        String title = attached.getTitle().trim();
        TiTleType tiTleType = getTiTleType(title);
        generateChildrenByTitleType(ddl, doc, api, title, tiTleType, attached.getChildren());
    }

    /**
     * 根据 {@link Children} 的内容生成相应的规格内容, 添加到 ddl/doc/api({@link StringBuilder}).
     * @param ddl           ddl
     * @param doc           doc, 当 {@code pTiTleType} 为 {@link TiTleType#DATABASE}/{@link TiTleType#TABLE} 时, 可以为 null
     * @param api           api, 当 {@code pTiTleType} 为 {@link TiTleType#DATABASE}/{@link TiTleType#TABLE} 时, 可以为 null
     * @param pTitle        {@link Children}pTitle
     * @param pTiTleType    {@link TiTleType}
     * @param children      {@link Children}
     */
    private void generateChildrenByTitleType(@NonNull StringBuilder ddl, @Nullable StringBuilder doc,
                                             @Nullable StringBuilder api, @NonNull String pTitle,
                                             @NonNull TiTleType pTiTleType, @NonNull Children children) {

        if (!(DATABASE.equals(pTiTleType) || TABLE.equals(pTiTleType))) {
            requireNonNull(doc, "doc cannot be null");
            requireNonNull(api, "api cannot be null");
        }

        switch (pTiTleType) {
            case MODULE:
                generateOfChildren(children, ddl, doc, api, MODULE, pTitle);
                break;
            case DATABASE:
                getDatabaseSql(children, ddl, pTitle);
                break;
            case TABLE:
                generateOfChildren(children, ddl, doc, api, TABLE, pTitle);
                break;
            case API:
                generateOfChildren(children, ddl, doc, api, API, pTitle);
                break;
            case ENTITY:
                generateOfChildren(children, ddl, doc, api, ENTITY, pTitle);
                break;
            default:
        }
    }

    /**
     * 根据 {@link Children} 的内容生成相应的 SQL 语句, 添加到 sql({@link StringBuilder}).
     *
     * @param children   {@link Children}
     * @param sql        sql
     * @param doc        doc, 当 {@code pTiTleType} 为 {@link TiTleType#DATABASE}/{@link TiTleType#TABLE} 时, 可以为 null
     * @param api        api, 当 {@code pTiTleType} 为 {@link TiTleType#DATABASE}/{@link TiTleType#TABLE} 时, 可以为 null
     * @param pTiTleType {@link TiTleType}
     * @param pTitle     pTitle
     */
    @NonNull
    private void generateOfChildren(@NonNull Children children, @NonNull StringBuilder sql,
                                    @Nullable StringBuilder doc, @Nullable StringBuilder api,
                                    @NonNull TiTleType pTiTleType, @NonNull String pTitle) {

        // 生成表 ddl
        if (TABLE.equals(pTiTleType) && pTitle.length() > 3) {
            String[] splits = pTitle.split(SEPARATOR);
            if (splits.length < 2 || splits[1].length() < 1) {
                throw new RuntimeException("title [" + pTitle + "] 格式错误, 标准格式: TitleType/TypeName/[description]");
            }
            getTableSql(children, sql, pTitle);
            return;
        }

        // TODO: 2021.4.27 生成 module doc
        if (MODULE.equals(pTiTleType) && pTitle.length() > 3) {

        }

        // TODO: 2021.4.27 生成 api 及对应的 doc
        if (API.equals(pTiTleType) && pTitle.length() > 3) {
            return;
        }

        // TODO: 2021.4.27 生成 entity 及对应的 doc
        if (ENTITY.equals(pTiTleType) && pTitle.length() > 3) {
            return;
        }

        List<Attached> attachedList = children.getAttached();
        for (Attached attached : attachedList) {
            generateOfAttached(attached, sql, doc, api, pTiTleType);
        }
    }

    @NonNull
    private void getTableSql(@NonNull Children children, @NonNull StringBuilder sql, @NonNull String pTitle) {
        String[] splits = pTitle.split(SEPARATOR);
        if (splits.length < 2 || splits[1].length() < 1) {
            throw new RuntimeException("title [" + pTitle + "] 格式错误, 标准格式: TitleType/TypeName/[description]");
        }
        String tableName = camelToUnderscore(splits[1].trim());
        String tableDecr = splits[2].trim().replaceAll("[\n\r]", " ");

        String createdTableSqlPrefix =
                String.format("DROP TABLE IF EXISTS `%s`;\n" +
                                      "CREATE TABLE `%s` (\n",
                              tableName,
                              tableName);
        sql.append(createdTableSqlPrefix);

        List<Attached> attachedList = children.getAttached();
        StringBuilder idxSql = new StringBuilder();
        for (Attached attached : attachedList) {
            getColumnSql(attached, sql, idxSql);
        }

        // 去除末尾逗号
        int idxSqlLength = idxSql.length();
        if (idxSqlLength > 0 && ',' == idxSql.charAt(idxSqlLength - 2)) {
            idxSql.deleteCharAt(idxSqlLength - 2);
        }
        sql.append(idxSql);

        String createdTableSqlSuffix =
                String.format(") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE = utf8mb4_general_ci COMMENT='%s' " +
                                      "ROW_FORMAT = Dynamic;\n\n",
                      tableDecr);
        sql.append(createdTableSqlSuffix);
    }

    private void getColumnSql(@NonNull Attached attached, @NonNull StringBuilder sql, @NonNull StringBuilder idxSql) {

        ColumnProperties props = getColumnProperties(attached);

        String columnName = camelToUnderscore(props.getColumnName());

        boolean isDefPrimaryKey = false;
        // TODO: 2021.4.27 扩展: 处理复合索引
        // 索引处理
        if (props.getPk()) {
            idxSql.append(String.format("\tPRIMARY KEY (`%s`),\n", columnName));
        }
        else if (props.isId()) {
            isDefPrimaryKey = true;
            idxSql.append("\tPRIMARY KEY (`id`),\n");
        }
        if (props.getUnique()) {
            idxSql.append(String.format("\tUNIQUE INDEX `uk_%s`(`%s`) USING BTREE,\n",
                                        columnName, columnName));
        }
        if (props.getIndex()) {
            idxSql.append(String.format("\tINDEX `idx_%s`(`%s`) USING BTREE,\n",
                                        columnName, columnName));
        }

        // column sql
        String columnType = props.getColumnType();
        sql.append(String.format("\t`%s` %s%s %s %s %s %s COMMENT '%s',\n",
                                 columnName,
                                 columnType,
                                 props.columnLen(),
                                 ColumnType.isString(columnType)
                                         ? "CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci"
                                         : props.getUnSigned() ? "unsigned" : "",
                                 props.getNotNull() || props.getPk() || props.getUnique() || props.getIndex() || isDefPrimaryKey
                                         ? "NOT NULL"
                                         : isNull(props.getDef()) ? "DEFAULT NULL" : "DEFAULT " + props.getDef(),
                                 props.getAutoIncrement() || isDefPrimaryKey ? "AUTO_INCREMENT" : "",
                                 props.getOnUpdate() ? "ON UPDATE CURRENT_TIMESTAMP" : "",
                                 props.getComment()));

    }

    @NonNull
    private ColumnProperties getColumnProperties(@NonNull Attached attached) {
        String title = attached.getTitle().trim();
        String[] splits = title.split(SEPARATOR, 3);
        if (splits.length != 3) {
            throw new RuntimeException("title [" + title + "] 格式错误, 标准格式: columnType/columnName/[comment]");
        }
        String columnTypeStr = splits[0].trim();
        String columnName = splits[1].trim();
        String comment = splits[2].trim().replaceAll("[\n\r]", " ");

        ColumnType columnType = getColumnType(columnTypeStr);

        ColumnProperties props = new ColumnProperties().setColumnName(columnName)
                                                       .setColumnType(columnType.getType())
                                                       .setComment(comment)
                                                       .setLen(columnType.getLen());


        Children children = attached.getChildren();
        if (nonNull(children)) {
            List<Attached> attachedList = children.getAttached();
            for (Attached propAttached : attachedList) {
                // TODO: 2021.4.27 去掉 P 命名空间
                String propTitle = propAttached.getTitle().trim();
                String[] propSplits = propTitle.split(SEPARATOR, 4);
                if (propSplits.length < 4) {
                    continue;
                }
                String p = propSplits[0].trim();
                if (!(P.name().equalsIgnoreCase(p))) {
                    continue;
                }
                String property = propSplits[1].trim();
                String propValue = propSplits[2].trim();
                ColumnProperty columnProperty = getColumnProperty(property);
                switch(columnProperty) {
                    case LENGTH:
                        props.setLen(propValue);
                        break;
                    case PK:
                        props.setPk("true".equalsIgnoreCase(propValue));
                        props.setUnique(false);
                        props.setIndex(false);
                        props.setNotNull(true);
                        break;
                    case UNIQUE:
                        props.setUnique("true".equalsIgnoreCase(propValue));
                        props.setIndex(false);
                        props.setNotNull(true);
                        break;
                    case INDEX:
                        props.setIndex("true".equalsIgnoreCase(propValue));
                        props.setNotNull(true);
                        break;
                    case NOTNULL:
                        props.setNotNull("true".equalsIgnoreCase(propValue));
                        break;
                    case DEFAULT:
                        props.setDef(propValue);
                        break;
                    case UNSIGNED:
                        props.setUnSigned("true".equalsIgnoreCase(propValue));
                        break;
                    case ON_UPDATE:
                        props.setOnUpdate("true".equalsIgnoreCase(propValue));
                        break;
                    case AUTO_INCREMENT:
                        props.setAutoIncrement("true".equalsIgnoreCase(propValue));
                        break;
                    default:
                }
            }
        }

        return props;
    }

    @NonNull
    private void getDatabaseSql(@NonNull Children children, @NonNull StringBuilder ddl, @NonNull String title) {
        String[] splits = title.trim().split(SEPARATOR);

        String databaseName = splits[1].trim();
        String databaseDdl =
                String.format("CREATE DATABASE IF NOT EXISTS `%s` DEFAULT CHARACTER SET utf8mb4;\nUSE `%s`;\n\n",
                              databaseName, databaseName);
        ddl.append(databaseDdl);
        generateOfChildren(children, ddl, null, null, DATABASE, title);
    }

    @NonNull
    private static ColumnProperty getColumnProperty(@NonNull String columnProperty) {
        try {
            return ColumnProperty.valueOf(columnProperty.toUpperCase());
        }
        catch (Exception e) {
            try {
                return ColumnProperty.valueOf(camelToUpperUnderscore(columnProperty));
            }
            catch (Exception ex) {
                throw new RuntimeException("column property [" + columnProperty + "] 不能转换为 ColumnProperty");
            }
        }
    }

    @NonNull
    private static ColumnType getColumnType(@NonNull String columnType) {
        try {
            return ColumnType.valueOf(columnType.toUpperCase());
        }
        catch (Exception e) {
            try {
                return ColumnType.valueOf(camelToUpperUnderscore(columnType));
            }
            catch (Exception ex) {
                throw new RuntimeException("column type [" + columnType + "] 不能转换为 ColumnType");
            }
        }
    }

    @NonNull
    private static TiTleType getTiTleType(@NotNull String title) {
        String[] splits = title.split(SEPARATOR);
        if (splits.length == 0) {
            throw new RuntimeException("title [" + title + "] 不能转换为 TitleType");
        }
        if (title.length() > 0) {
            try {
                return TiTleType.valueOf(splits[0].toUpperCase());
            }
            catch (Exception e) {
                try {
                    return TiTleType.valueOf(camelToUpperUnderscore(ofNullable(splits[0]).orElse("")));
                }
                catch (Exception ex) {
                    throw new RuntimeException("title [" + title + "] 不能转换为 TitleType");
                }
            }
        }
        throw new RuntimeException("title [" + title + "] 不能转换为 TitleType");
    }

    @NonNull
    private static String camelToUnderscore(@NonNull String camelStr) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, camelStr);
    }

    @NonNull
    private static String camelToUpperUnderscore(@NonNull String camelStr) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, camelStr);
    }

    @NonNull
    private static String underscoreToCamel(@NonNull String underscoreStr) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, underscoreStr);
    }

    /**
     * Xmind title type
     */
    enum TiTleType {
        /**
         * Xmind 脑图
         */
        SHEET,
        /**
         * 模块
         */
        MODULE,
        /**
         * 数据库
         */
        DATABASE,
        /**
         * table
         */
        TABLE,
        /**
         * API
         */
        API,
        /**
         * 实体对象
         */
        ENTITY,
        /**
         * 接口
         */
        INTERFACE,
        /**
         * 接口入参
         */
        IN,
        /**
         * 接口返回值
         */
        OUT,
        /**
         * 请求头参数
         */
        HEAD,
        /**
         * 注释(as ANNOT)
         */
        ANNOTATION,
        /**
         * 查询请求
         */
        GET,
        /**
         * 新增请求
         */
        POST,
        /**
         * 修改请求
         */
        PUT,
        /**
         * 删除请求
         */
        DELETE,
        /**
         * 参数: column 或 in
         */
        P,

    }

    /**
     * database column type
     */
    enum ColumnType {
        BIGINT("bigint", "19"),
        LONG("bigint", "19"),
        INTEGER("int", "11"),
        INT("int", "11"),
        CHAR("char", "50"),
        VARCHAR("varchar", "50"),
        STRING("varchar", "50"),
        TINYTEXT("tinytext", "255"),
        TEXT("text", "65535"),
        MEDIUMTEXT("mediumtext", "16777215"),
        LONGTEXT("longtext", "4294967295"),
        BIT("bit", "1"),
        BINARY("binary", ""),
        BLOB("blob", ""),
        MEDIUMBLOB("mediumblob", ""),
        LONGBLOB("longblob", ""),
        BOOLEAN("tinyint", "1"),
        TINYINT("tinyint", "4"),
        DECIMAL("decimal", "11,2"),
        FLOAT("decimal", "11,2"),
        DOUBLE("decimal", "11,2"),
        TIMESTAMP("timestamp", "14"),
        DATETIME("datetime", ""),
        TIME("time", ""),
        DATE("datetime", "");
        /**
         * 默认字段类型长度字符串
         */
        private final String len;
        /**
         * column 类型
         */
        private final String type;
        ColumnType(String type, String len) {
            this.type = type;
            this.len = len;
        }

        public String getLen() {
            return len;
        }

        public String getType() {
            return type;
        }

        public static boolean isString(@NonNull String columnType) {
            return VARCHAR.name().equalsIgnoreCase(columnType)
                    || CHAR.name().equalsIgnoreCase(columnType)
                    || STRING.name().equalsIgnoreCase(columnType)
                    || TINYTEXT.name().equalsIgnoreCase(columnType)
                    || TEXT.name().equalsIgnoreCase(columnType)
                    || MEDIUMTEXT.name().equalsIgnoreCase(columnType)
                    || LONGTEXT.name().equalsIgnoreCase(columnType);
        }
    }

    /**
     * database column property
     */
    enum ColumnProperty {
        LENGTH,
        PK,
        UNIQUE,
        NOTNULL,
        DEFAULT,
        UNSIGNED,
        ON_UPDATE,
        AUTO_INCREMENT,
        INDEX,
    }

    /**
     * Prams property
     */
    enum PramProperty {
        LONG("Long"),
        STRING("String"),
        INT("Integer"),
        INTEGER("Integer"),
        VOID("void"),
        BOOLEAN("Boolean"),
        FLOAT("Float"),
        DOUBLE("Double"),
        BYTE("Byte"),
        SHORT("Short"),
        DATE("Date");

        /**
         * 类型
         */
        private final String type;

        PramProperty(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    /**
     * 列属性
     */
    @Data
    @Accessors(chain = true)
    static class ColumnProperties {
        private String columnName;
        private String columnType;
        private String comment;
        private String len;
        private Boolean pk = FALSE;
        private Boolean unique = FALSE;
        private Boolean notNull = FALSE;
        private String def;
        private Boolean unSigned = FALSE;
        private Boolean onUpdate = FALSE;
        private Boolean autoIncrement = FALSE;
        private Boolean index = FALSE;

        public boolean isId() {
            return "id".equals(columnName);
        }

        @NonNull
        public String columnLen() {
            if (isNull(len)) {
                return XmindTest.getColumnType(columnType).getLen();
            }
            return len.length() > 0 ? "(" + len + ")" : "";
        }

    }

}
