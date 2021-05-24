package org.arch.workflow.form.repository;


import org.arch.workflow.common.repository.BaseRepository;
import org.arch.workflow.form.domain.FormDefinition;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 表单定义数据类
 *
 * @author lait.zhang@gmail.com
 * @date 2018/12/10
 */
public interface FormDefinitionRepository extends BaseRepository<FormDefinition, Integer> {


    @Query("select f from FormDefinition f where  version = (select max(version) from FormDefinition where key = f.key)")
    List<FormDefinition> findLatestFormDefinitions();

    /**
     * 根据key获取最新的表单定义
     *
     * @param key
     * @return
     */
    @Query("select f from FormDefinition f where f.key = ?1 and (tenantId = ''  or tenantId is null) "
            + " and version = (select max(version) from FormDefinition where key = ?1 and (tenantId = ''  or tenantId is null))")
    FormDefinition findLatestFormDefinitionByKey(String key);


    /**
     * 根据key和分片ID获取最新的表单定义
     *
     * @param key
     * @param tenantId
     * @return
     */
    @Query("select f from FormDefinition f where f.key = ?1 and tenantId = ?2 "
            + " and version = (select max(version) from FormDefinition where key = ?1 and tenantId = ?2)")
    FormDefinition findLatestFormDefinitionByKeyAndTenantId(String key, String tenantId);
}