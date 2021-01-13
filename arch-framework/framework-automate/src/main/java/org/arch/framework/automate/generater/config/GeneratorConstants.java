package org.arch.framework.automate.generater.config;

/**
 * 定义自动生成相关的常量
 */
public interface GeneratorConstants {

    /**
     * 程序配置项的默认前缀
     */
    String APP_DEFAULT_CONFIG_PREFIX = "uni.";

    /**
     * 文件作者配置
     */
    String AUTHOR_CONFIG = APP_DEFAULT_CONFIG_PREFIX + "author";

    /**
     * 文件说明配置
     */
    String COMMENTS_CONFIG = APP_DEFAULT_CONFIG_PREFIX + "comments";

    /**
     * 默认的class的配置后缀
     */
    String DEFAULT_CLASS_SUFFIX = ".suffix";

    /**
     * 默认的template的配置后缀
     */
    String DEFAULT_CLASS_TEMPLATE = ".template";

    /**
     * 默认的package的配置后缀
     */
    String DEFAULT_CLASS_PACKAGE = ".package";

    /**
     * 默认的template的配置后缀
     */
    String DEFAULT_TEMPLATE_NAME_SUFFIX = ".ftl";

    /**
     * src的目录前缀
     */
    String SRC_PATH_PREFIX = "src/main/";

    /**
     * 配置的自动生成的代码地址路径
     */
    String BASE_PROJECT_PATH = APP_DEFAULT_CONFIG_PREFIX + "base.project.path";

    /**
     * 系统默认的自动代码生成的保存地址
     */
    String DEFAULT_PROJECT_PATH = SRC_PATH_PREFIX + "java";

    /**
     * 是否转换完成的标志配置
     */
    String COVER = APP_DEFAULT_CONFIG_PREFIX + "cover";

    /**
     * 默认的cover的值
     */
    String DEFAULT_COVER = "false";

    /**
     * 配置的模板路径
     */
    String TEMPLATE_DIR = APP_DEFAULT_CONFIG_PREFIX + "template.dir";

    /**
     * 默认的模板所在路径
     */
    String DEFAULT_TEMPLATE_DIR = "templates/";

    /**
     * 实体的模板前缀
     */
    String ENTITY_MODULE = "entity";

    /**
     * 请求dto的模板前缀
     */
    String DTO_MODULE = "dto";

    /**
     * 响应dto的模板前缀
     */
    String RESPONSE_DTO_MODULE = "response";

    /**
     * 搜索dto的模板前缀
     */
    String SEARCH_MODULE = "search";

    /**
     * repository的模板前缀
     */
    String REPOSITORY_MODULE = "repository";

    /**
     * service的模板前缀
     */
    String SERVICE_MODULE = "service";

    /**
     * biz的模板
     */
    String BIZ_MODULE = "biz";

    /**
     * controller的模板前缀
     */
    String CONTROLLER_MODULE = "controller";

    /**
     * 默认的实体类的父类包名
     */
    //String DEFAULT_ENTITY_SUPER_CLASS = BaseEntity.class.getName();

    /**
     * 配置文件是properties的后缀
     */
    String PROPERTIES_CONFIG_SUFFIX = "properties";

    /**
     * 配置文件是yml的后缀
     */
    String YML_CONFIG_SUFFIX = "yml";

    /**
     * 实体相关的配置常量
     */
    interface EntityConstants {

        /**
         * Entity配置的默认前缀
         */
        String DEFAULT_ENTITY_PREFIX = APP_DEFAULT_CONFIG_PREFIX + ENTITY_MODULE + ".";

        /**
         * 包名，如：com.xxx.entity
         */
        String PACKAGE = DEFAULT_ENTITY_PREFIX + "package";

        /**
         * 模板名，如：entity.ftl
         */
        String TEMPLATE = DEFAULT_ENTITY_PREFIX + "template";

        /**
         * 实体名称后缀，如：Entity，则实体名称为：xxxEntity
         */
        String SUFFIX = DEFAULT_ENTITY_PREFIX + "suffix";

        /**
         * 实体主键的类，如：Integer
         */
        String ID_CLASS = DEFAULT_ENTITY_PREFIX + "id.class";

        /**
         * 实体的默认主键类为Integer
         */
        String DEFAULT_ID_CLASS = "Integer";

        /**
         * 实体主键类的包名，如：java.lang.Integer<br>
         * 说明：如果是java.lang包下的可以为空
         */
        String ID_PACKAGE = DEFAULT_ENTITY_PREFIX + "id.package";
    }

    /**
     * db相关的配置常量
     */
    interface DBConstants {

        /**
         * 数据库配置的默认前缀
         */
        String DEFAULT_DB_PREFIX = APP_DEFAULT_CONFIG_PREFIX + "db.";

        /**
         * 数据库的驱动类名
         */
        String DRIVER_CLASS_NAME = DEFAULT_DB_PREFIX + "driver";

        /**
         * 数据库的链接
         */
        String URL = DEFAULT_DB_PREFIX + "url";

        /**
         * 数据库的用户名
         */
        String USERNAME = DEFAULT_DB_PREFIX + "username";

        /**
         * 数据库的密码
         */
        String PASSWORD = DEFAULT_DB_PREFIX + "password";

    }
}
