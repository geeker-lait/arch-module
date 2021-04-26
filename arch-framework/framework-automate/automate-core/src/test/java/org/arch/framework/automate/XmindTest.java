package org.arch.framework.automate;

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
        String fileName = "minds"+ File.separator +"ofs-alarm-center.xmind";
        Resource resource = new ClassPathResource(fileName);

        String res = XmindParser.parseJson(resource.getFile().getAbsolutePath());
        System.out.println(res+ "\n\n\n\n\n\n=================================================" );

        JsonRootBean root = XmindParser.parseObject(resource.getFile().getAbsolutePath(), JsonRootBean.class);

        StringBuilder sb = new StringBuilder();
        String databaseDdl = GeneratorDdl(root, sb).toString();

//        System.out.println(root);
        System.out.println(databaseDdl);


    }

    @NonNull
    private StringBuilder GeneratorDdl(@NonNull JsonRootBean root, @NonNull StringBuilder sb) {
        TiTleType tiTleType = getTiTleType(root.getTitle());
        if (!SHEET.equals(tiTleType)) {
            throw new RuntimeException("xmind 格式错误");
        }
        return GeneratorDdlOfRoot(root.getRootTopic(), sb);
    }

    @NonNull
    private StringBuilder GeneratorDdlOfRoot(@NonNull RootTopic rootTopic, @NonNull StringBuilder sb) {
        String title = rootTopic.getTitle();
        TiTleType tiTleType = getTiTleType(title);
        switch(tiTleType) {
            case MODULE:
                return getSqlOfChildren(rootTopic.getChildren(), sb, MODULE, null);
            case DATABASE:
                return getDatabaseSqlOfRoot(rootTopic);
            case TABLE:
                return getSqlOfChildren(rootTopic.getChildren(), sb, TABLE, title);
            default:
                return sb;
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
    private StringBuilder getSqlOfChildren(@NonNull Children children, @NonNull StringBuilder sql,
                                           @NonNull TiTleType pTiTleType, @Nullable String pTitle) {
        if (TABLE.equals(pTiTleType) && nonNull(pTitle) && pTitle.length() > 3) {
            String[] splits = pTitle.split(SEPARATOR);
            if (splits.length < 2 || splits[1].length() < 1) {
                throw new RuntimeException("title [" + pTitle + "] 格式错误, 标准格式: TitleType/TypeName/[description]");
            }
            return getTableSql(children, sql, pTitle);
        }

        List<Attached> attachedList = children.getAttached();
        for (Attached attached : attachedList) {
            getSqlOfAttached(attached, sql);
        }
        return sql;
    }

    /**
     * 根据 {@link Attached} 的内容生成相应的 SQL 语句, 添加到 sql({@link StringBuilder}).
     * @param attached      {@link Attached}
     * @param sql           sql
     * @return  sql
     */
    @NonNull
    private StringBuilder getSqlOfAttached(@NonNull Attached attached, @NonNull StringBuilder sql) {
        String title = attached.getTitle();
        TiTleType tiTleType = getTiTleType(title);
        switch(tiTleType) {
            case MODULE:
                return getSqlOfChildren(attached.getChildren(), sql, MODULE, null);
            case DATABASE:
                return getDatabaseSqlOfAttached(attached);
            case TABLE:
                return getSqlOfChildren(attached.getChildren(), sql, TABLE, title);
            default:
                return sql;
        }
    }

    @NonNull
    private StringBuilder getTableSql(@NonNull Children children, @NonNull StringBuilder sql, @NonNull String pTitle) {
        String[] splits = pTitle.split(SEPARATOR);
        if (splits.length < 2 || splits[1].length() < 1) {
            throw new RuntimeException("title [" + pTitle + "] 格式错误, 标准格式: TitleType/TypeName/[description]");
        }
        String tableName = splits[1];
        String tableDecr = splits[2];

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
        return sql;
    }

    private void getColumnSql(@NonNull Attached attached, @NonNull StringBuilder sql, @NonNull StringBuilder pkSql) {
        String title = attached.getTitle();
        String[] splits = title.split(SEPARATOR, 3);
        if (splits.length != 3) {
            System.out.println(sql.toString());
            System.out.println("------->: " + title);
            throw new RuntimeException("title [" + title + "] 格式错误, 标准格式: columnType/columnName/[comment]");
        }
        String columnType = splits[0];
        String columnName = splits[1];
        String comment = splits[2];

        // TODO column 属性未实现解析
        if ("id".equals(columnName)) {
            sql.append(String.format("\t`id` %s%s NOT NULL AUTO_INCREMENT COMMENT '%s',\n",
                                     columnType,
                                     getColumnLen(columnType),
                                     comment));
            pkSql.append("\tPRIMARY KEY (`id`)\n");
        }
        sql.append(String.format("\t`%s` %s%s DEFAULT NULL COMMENT '%s',\n",
                                 columnName,
                                 columnType,
                                 getColumnLen(columnType),
                                 comment));

    }

    @NonNull
    private String getColumnLen(@NonNull String columnType) {
        // TODO column 属性未实现解析
        try {
            ColumnType type = ColumnType.valueOf(columnType.toUpperCase());
            return type.getLen();
        }
        catch (Exception e) {
            return "";
            //throw new RuntimeException("column type [" + columnType + "] 不能转换为 ColumnType");
        }
    }

    @NonNull
    private StringBuilder getDatabaseSqlOfRoot(@NonNull RootTopic rootTopic) {
        String[] splits = rootTopic.getTitle().split(SEPARATOR);

        String databaseDdl =
                String.format("CREATE DATABASE IF NOT EXISTS `%s` DEFAULT CHARACTER SET utf8mb4;\nUSE `%s`;\n\n",
                              splits[1], splits[1]);
        return getSqlOfChildren(rootTopic.getChildren(), new StringBuilder(databaseDdl), DATABASE, null);
    }

    @NonNull
    private StringBuilder getDatabaseSqlOfAttached(@NonNull Attached attached) {
        String[] splits = attached.getTitle().split(SEPARATOR);

        String databaseDdl =
                String.format("CREATE DATABASE IF NOT EXISTS `%s` DEFAULT CHARACTER SET utf8mb4;\nUSE `%s`;\n",
                              splits[2], splits[2]);
        return getSqlOfChildren(attached.getChildren(), new StringBuilder(databaseDdl), DATABASE, null);
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
        BININT("(19)"),
        INT("(11)"),
        VARCHAR("(50)"),
        DATE("");
        /**
         * 默认字段类型长度字符串
         */
        private String len;
        ColumnType(String len) {
            this.len = len;
        }

        public String getLen() {
            return len;
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
