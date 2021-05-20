package org.arch.workflow.flowable.config;

import org.flowable.common.engine.impl.cfg.IdGenerator;
import org.flowable.common.spring.AutoDeploymentStrategy;
import org.flowable.engine.ProcessEngine;
import org.flowable.http.common.api.client.FlowableHttpClient;
import org.flowable.job.service.impl.asyncexecutor.AsyncExecutor;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.*;
import org.flowable.spring.boot.app.FlowableAppProperties;
import org.flowable.spring.boot.eventregistry.FlowableEventRegistryProperties;
import org.flowable.spring.boot.idm.FlowableIdmProperties;
import org.flowable.spring.boot.process.FlowableProcessProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

/**
 * 流程引擎配置类
 *
 * @author lait.zhang@gmail.com
 * @date 2018年4月20日
 */
@Configuration
public class FlowableEngineConfig extends ProcessEngineAutoConfiguration {

    public FlowableEngineConfig(FlowableProperties flowableProperties, FlowableProcessProperties processProperties, FlowableAppProperties appProperties, FlowableIdmProperties idmProperties, FlowableEventRegistryProperties eventProperties, FlowableMailProperties mailProperties, FlowableHttpProperties httpProperties, FlowableAutoDeploymentProperties autoDeploymentProperties) {
        super(flowableProperties, processProperties, appProperties, idmProperties, eventProperties, mailProperties, httpProperties, autoDeploymentProperties);
    }

    @Override
    public SpringProcessEngineConfiguration springProcessEngineConfiguration(DataSource dataSource, PlatformTransactionManager platformTransactionManager, ObjectProvider<IdGenerator> processIdGenerator, ObjectProvider<IdGenerator> globalIdGenerator, ObjectProvider<AsyncExecutor> asyncExecutorProvider, ObjectProvider<AsyncListenableTaskExecutor> applicationTaskExecutorProvider, ObjectProvider<AsyncExecutor> asyncHistoryExecutorProvider, ObjectProvider<AsyncListenableTaskExecutor> taskExecutor, ObjectProvider<AsyncListenableTaskExecutor> processTaskExecutor, ObjectProvider<FlowableHttpClient> flowableHttpClient, ObjectProvider<List<AutoDeploymentStrategy<ProcessEngine>>> processEngineAutoDeploymentStrategies) throws IOException {
        SpringProcessEngineConfiguration conf = super.springProcessEngineConfiguration(dataSource, platformTransactionManager, processIdGenerator, globalIdGenerator, asyncExecutorProvider, applicationTaskExecutorProvider, asyncHistoryExecutorProvider, taskExecutor, processTaskExecutor, flowableHttpClient, processEngineAutoDeploymentStrategies);
        String databaseSchema = conf.getDatabaseSchema();
        conf.setDatabaseCatalog(databaseSchema);
        conf.setDatabaseTablePrefix(databaseSchema + ".");
        conf.setTablePrefixIsSchema(true);
        conf.setActivityFontName("黑体");
        conf.setLabelFontName("黑体");
        conf.setAnnotationFontName("黑体");
        return conf;
    }


}
