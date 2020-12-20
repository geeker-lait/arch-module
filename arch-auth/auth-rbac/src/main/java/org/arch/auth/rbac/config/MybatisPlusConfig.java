package org.arch.auth.rbac.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p>
 * MybatisPlusConfig
 * </p>
 *
 * @author lait
 * @description
 * @since 2020-11-13 10:30:40
 **/
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"org.arch.auth.rbac.mapper"})
public class MybatisPlusConfig {

    /**
     * sql性能分析插件，输出sql语句及所需时间
     * 注意：mybatis plus 3.1.2以上版本不支持该插件，请使用p6spy
     *
     * @return
     */
//    @Bean
//    @Profile({"dev", "test", "sit", "uat"})
//    public PerformanceInterceptor performanceInterceptor() {
//        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
//        /** SQL 执行性能分析，开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长 */
//        performanceInterceptor.setMaxTime(5000);
//        /** SQL是否格式化 默认false */
//        performanceInterceptor.setFormat(true);
//        return performanceInterceptor;
//    }
//    @Bean
//    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource);
//        sqlSessionFactoryBean.setTypeAliasesPackage("com.bitter.storage.domain");
//
//        MybatisConfiguration configuration = new MybatisConfiguration();
//        configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
//        configuration.setJdbcTypeForNull(JdbcType.NULL);
//        sqlSessionFactoryBean.setConfiguration(configuration);
//
//        sqlSessionFactoryBean.setTransactionFactory(new SpringManagedTransactionFactory());
//        return sqlSessionFactoryBean.getObject();
//    }

//    @Bean("sqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
//        // 导入mybatissqlsession配置
//        MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
//        // 指明数据源
//        sessionFactory.setDataSource(dataSource/*multipleDataSource(dataSource0(), dataSource1(), dataSource2())*/);
//        // 指明mapper.xml位置(配置文件中指明的xml位置会失效用此方式代替，具体原因未知)
//        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/**Mapper.xml"));
//        // 指明实体扫描(多个package用逗号或者分号分隔)
//        sessionFactory.setTypeAliasesPackage("com.yonghui.arch.demo.entity");
//        // 导入mybatis配置
//        MybatisConfiguration configuration = new MybatisConfiguration();
//        configuration.setJdbcTypeForNull(JdbcType.NULL);
//        configuration.setMapUnderscoreToCamelCase(true);
//        configuration.setCacheEnabled(false);
//        // 配置打印sql语句
//        configuration.setLogImpl(StdOutImpl.class);
//        sessionFactory.setConfiguration(configuration);
//        // 添加分页功能
////        sessionFactory.setPlugins(new Interceptor[]{
////                paginationInterceptor()
////        });
//        // 导入全局配置
//        //sessionFactory.setGlobalConfig(globalConfiguration());
//        return sessionFactory.getObject();
//    }
    /**
     * 这里全部使用mybatis-autoconfigure 已经自动加载的资源。不手动指定
     * 配置文件和mybatis-boot的配置文件同步
     * @return
     */
//    @Bean
//    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean() {
//        MybatisSqlSessionFactoryBean mybatisPlus = new MybatisSqlSessionFactoryBean();
//        mybatisPlus.setDataSource(dataSource);
//        mybatisPlus.setVfs(SpringBootVFS.class);
//        if (StringUtils.hasText(this.properties.getConfigLocation())) {
//            mybatisPlus.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));
//        }
//        mybatisPlus.setConfiguration(properties.getConfiguration());
//        if (!ObjectUtils.isEmpty(this.interceptors)) {
//            mybatisPlus.setPlugins(this.interceptors);
//        }
//        MybatisConfiguration mc = new MybatisConfiguration();
//        mc.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
//        mybatisPlus.setConfiguration(mc);
//        if (this.databaseIdProvider != null) {
//            mybatisPlus.setDatabaseIdProvider(this.databaseIdProvider);
//        }
//        if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
//            mybatisPlus.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
//        }
//        if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
//            mybatisPlus.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
//        }
//        if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
//            mybatisPlus.setMapperLocations(this.properties.resolveMapperLocations());
//        }
//        return mybatisPlus;
//    }


}
