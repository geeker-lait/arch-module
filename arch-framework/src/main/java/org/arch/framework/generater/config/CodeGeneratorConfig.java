package org.arch.framework.generater.config;

import lombok.Data;
import org.arch.framework.generater.db.DataBaseProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自动生成代码配置类
 */
@Data
public class CodeGeneratorConfig {

    /**
     * 生成的文件保存路径
     */
    private String baseProjectPath;

    /**
     * 实体的所在包名
     */
    private String entityFlag;

    /**
     * 实体的主键id类型.<br>
     * 如Integer、Long、String <br>
     * 从数据库生成实体才需要，从实体生成不需要填写
     */
    private String entityIdClass;

    /**
     * 实体的主键id类型的包名，非java.lang才需要填写.<br>
     * 如java.math.BigDecimal<br>
     * 从数据库生成实体才需要，从实体生成不需要填写
     */
    private String entityIdPackName;

    /**
     * 实体集合，本地实体时需要
     */
    private List<Class<?>> entityClasses = new ArrayList<>(256);

    /**
     * 实体需要继承的父级实体，用来排除不需要作为实体属性的字段<br>
     * 从数据库生成实体才需要，从实体生成不需要填写
     */
    private Class<?> superEntityClass;

    /**
     * 模板文件所在目录
     */
    private String ftlPath;

    /**
     * 文件的作者
     */
    private String author;

    /**
     * 创建的时间
     */
    private String date;

    /**
     * 注释
     */
    private String comments;

    /**
     * 转换是否完成
     */
    private boolean cover;

    /**
     * 各个类型模板的配置文件
     */
    private Map<String, ModuleConfig> moduleConfigMap = new HashMap<>();

    /**
     * 其他个性配置
     */
    private Map<String, String> otherParams;

    /**
     * 是否启用数据表进行自动生成<br>
     * false为使用已有的实体类进行生成
     */
    private boolean useDb;

    /**
     * 数据库的连接配置,useDb=true才有用
     */
    private DataBaseProperties dbProperties = new DataBaseProperties();
}
