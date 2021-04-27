package org.arch.framework.automate;

import com.google.common.base.CaseFormat;
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
import java.io.IOException;
import java.util.List;

import static java.util.Objects.nonNull;
import static org.arch.framework.automate.XmindTest.TiTleType.DATABASE;
import static org.arch.framework.automate.XmindTest.TiTleType.MODULE;
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
        String fileName = "minds\\ofs-alarm-center.xmind";
        Resource resource = new ClassPathResource(fileName);

        String res = XmindParser.parseJson(resource.getFile().getAbsolutePath());
        System.out.println(res+ "\n\n\n\n\n\n=================================================" );

        JsonRootBean root = XmindParser.parseObject(resource.getFile().getAbsolutePath(), JsonRootBean.class);

        StringBuilder sb = new StringBuilder();
        GeneratorDdl(root, sb);

        System.out.println(sb.toString());


    }

    @NonNull
    private void GeneratorDdl(@NonNull JsonRootBean root, @NonNull StringBuilder sb) {
        TiTleType tiTleType = getTiTleType(root.getTitle().trim());
        if (!SHEET.equals(tiTleType)) {
            throw new RuntimeException("xmind 格式错误");
        }
        GeneratorDdlOfRoot(root.getRootTopic(), sb);
    }

    @NonNull
    private void GeneratorDdlOfRoot(@NonNull RootTopic rootTopic, @NonNull StringBuilder sb) {
        String title = rootTopic.getTitle().trim();
        TiTleType tiTleType = getTiTleType(title);
        switch(tiTleType) {
            case MODULE:
                getSqlOfChildren(rootTopic.getChildren(), sb, MODULE, null);
                break;
            case DATABASE:
                sb.append(getDatabaseSql(rootTopic.getChildren(), rootTopic.getTitle()));
                break;
            case TABLE:
                getSqlOfChildren(rootTopic.getChildren(), sb, TABLE, title);
                break;
            default:
        }
    }

    /**
     * 根据 {@link Children} 的内容生成相应的 SQL 语句, 添加到 sql({@link StringBuilder}).
     * @param children      {@link Children}
     * @param sql           sql
     * @param pTiTleType    {@link TiTleType}
     * @param pTitle        当 pTitle 为 {@link TiTleType#TABLE} 时 pTitle 不为 null, 其他类型时可以为 null.
     * @return  sql
     */
    @NonNull
    private void getSqlOfChildren(@NonNull Children children, @NonNull StringBuilder sql,
                                           @NonNull TiTleType pTiTleType, @Nullable String pTitle) {
        if (TABLE.equals(pTiTleType) && nonNull(pTitle) && pTitle.length() > 3) {
            String[] splits = pTitle.split(SEPARATOR);
            if (splits.length < 2 || splits[1].length() < 1) {
                throw new RuntimeException("title [" + pTitle + "] 格式错误, 标准格式: TitleType/TypeName/[description]");
            }
            getTableSql(children, sql, pTitle);
            return ;
        }

        List<Attached> attachedList = children.getAttached();
        for (Attached attached : attachedList) {
            getSqlOfAttached(attached, sql);
        }
    }

    /**
     * 根据 {@link Attached} 的内容生成相应的 SQL 语句, 添加到 sql({@link StringBuilder}).
     * @param attached      {@link Attached}
     * @param sql           sql
     * @return  sql
     */
    @NonNull
    private void getSqlOfAttached(@NonNull Attached attached, @NonNull StringBuilder sql) {
        String title = attached.getTitle().trim();
        TiTleType tiTleType = getTiTleType(title);
        switch(tiTleType) {
            case MODULE:
                getSqlOfChildren(attached.getChildren(), sql, MODULE, null);
                break;
            case DATABASE:
                sql.append(getDatabaseSql(attached.getChildren(), attached.getTitle()));
                break;
            case TABLE:
                getSqlOfChildren(attached.getChildren(), sql, TABLE, title);
                break;
            default:
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
        StringBuilder pkSql = new StringBuilder();
        for (Attached attached : attachedList) {
            // TODO: 2021.4.26 column 属性未实现解析
            getColumnSql(attached, sql, pkSql);
        }

        sql.append(pkSql);

        String createdTableSqlSuffix =
                String.format(") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='%s';\n\n",
                      tableDecr);
        sql.append(createdTableSqlSuffix);
    }

    private void getColumnSql(@NonNull Attached attached, @NonNull StringBuilder sql, @NonNull StringBuilder pkSql) {
        String title = attached.getTitle().trim();
        String[] splits = title.split(SEPARATOR, 3);
        if (splits.length != 3) {
            System.out.println(sql.toString());
            System.out.println("------->: " + title);
            throw new RuntimeException("title [" + title + "] 格式错误, 标准格式: columnType/columnName/[comment]");
        }
        String columnTypeStr = splits[0].trim();
        String columnName = camelToUnderscore(splits[1].trim());
        String comment = splits[2].trim().replaceAll("[\n\r]", " ");

        ColumnType columnType = getColumnType(columnTypeStr);

        // TODO column 属性未实现解析
        if ("id".equals(columnName)) {
            sql.append(String.format("\t`id` %s%s NOT NULL AUTO_INCREMENT COMMENT '%s',\n",
                                     columnType.getType(),
                                     columnType.getLen(),
                                     comment));
            pkSql.append("\tPRIMARY KEY (`id`)\n");
            return;
        }
        sql.append(String.format("\t`%s` %s%s DEFAULT NULL COMMENT '%s',\n",
                                 columnName,
                                 columnType.getType(),
                                 columnType.getLen(),
                                 comment));

    }

    @NonNull
    private ColumnType getColumnType(@NonNull String columnType) {
        try {
            return ColumnType.valueOf(columnType.toUpperCase());
        }
        catch (Exception e) {
            throw new RuntimeException("column type [" + columnType + "] 不能转换为 ColumnType");
        }
    }

    @NonNull
    private StringBuilder getDatabaseSql(@NonNull Children children, @NonNull String title) {
        String[] splits = title.trim().split(SEPARATOR);

        String databaseName = splits[1].trim();
        String databaseDdl =
                String.format("CREATE DATABASE IF NOT EXISTS `%s` DEFAULT CHARACTER SET utf8mb4;\nUSE `%s`;\n\n",
                              databaseName, databaseName);
        StringBuilder databaseDdlSb = new StringBuilder(databaseDdl);
        getSqlOfChildren(children, databaseDdlSb, DATABASE, null);
        return databaseDdlSb;
    }

    @NonNull
    private TiTleType getTiTleType(@NotNull String title) {
        String[] splits = title.split(SEPARATOR);
        if (splits.length == 0) {
            throw new RuntimeException("title [" + title + "] 不能转换为 TitleType");
        }
        if (title.length() > 0) {
            try {
                return TiTleType.valueOf(splits[0].toUpperCase());
            }
            catch (Exception e) {
                throw new RuntimeException("title [" + title + "] 不能转换为 TitleType");
            }
        }
        throw new RuntimeException("title [" + title + "] 不能转换为 TitleType");
    }

    private static String camelToUnderscore(String camelStr) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, camelStr);
    }


    /**
     * Xmind title type
     */
    static enum TiTleType {
        SHEET,
        MODULE,
        DATABASE,
        TABLE,
        API,
        ENTITY,
        INTERFACE
    }

    /**
     * database column type
     */
    static enum ColumnType {
        BIGINT("bigint", "(19)"),
        INT("int", "(11)"),
        VARCHAR("varchar", "(50)"),
        BOOLEAN("tinyint", "(1)"),
        TINYINT("tinyint", "(4)"),
        DATE("datetime", "");
        /**
         * 默认字段类型长度字符串
         */
        private String len;
        /**
         * column 类型
         */
        private String type;
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
    }

    /**
     * database column property
     */
    static enum ColumnProperty {
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

}
