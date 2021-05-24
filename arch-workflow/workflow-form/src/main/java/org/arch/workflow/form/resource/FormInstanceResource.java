package org.arch.workflow.form.resource;

import org.arch.workflow.common.client.jdbc.JdbcClient;
import org.arch.workflow.common.jpa.Criteria;
import org.arch.workflow.common.jpa.Restrictions;
import org.arch.workflow.common.resource.BaseResource;
import org.arch.workflow.common.resource.PageResponse;
import org.arch.workflow.form.constant.ErrorConstant;
import org.arch.workflow.form.constant.TableConstant;
import org.arch.workflow.form.domain.FormDefinition;
import org.arch.workflow.form.domain.FormInstance;
import org.arch.workflow.form.repository.FormDefinitionRepository;
import org.arch.workflow.form.repository.FormInstanceRepository;
import org.arch.workflow.form.request.FormInstanceCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 表单定义资源类
 *
 * @author lait.zhang@gmail.com
 * @date 2018/12/10
 */
@RestController
public class FormInstanceResource extends BaseResource {
    private final FormInstanceRepository formInstanceRepository;
    private final FormDefinitionRepository formDefinitionRepository;
    private final JdbcClient jdbcClient;

    @Autowired
    public FormInstanceResource(FormInstanceRepository formInstanceRepository, FormDefinitionRepository formDefinitionRepository, JdbcClient jdbcClient) {
        this.formInstanceRepository = formInstanceRepository;
        this.formDefinitionRepository = formDefinitionRepository;
        this.jdbcClient = jdbcClient;
    }

    private FormInstance getFormInstanceFromRequest(Integer id) {
        FormInstance formInstance = formInstanceRepository.getOne(id);
        if (formInstance == null) {
            exceptionFactory.throwObjectNotFound(ErrorConstant.FORM_DEFINITION_NOT_FOUND);
        }
        return formInstance;
    }

    @GetMapping(value = "/form-instances")
    @ResponseStatus(value = HttpStatus.OK)
    public PageResponse getFormInstances(@RequestParam Map<String, String> requestParams) {
        Criteria<FormInstance> criteria = new Criteria<>();
        criteria.add(Restrictions.eq("id", requestParams.get("id")));
        criteria.add(Restrictions.eq("formDefinitionId", requestParams.get("formDefinitionId")));
        criteria.add(Restrictions.like("relationTable", requestParams.get("relationTable")));
        criteria.add(Restrictions.like("tenantId", requestParams.get("tenantId")));
        return createPageResponse(formInstanceRepository.findAll(criteria, getPageable(requestParams)));
    }

    @GetMapping(value = "/form-instances/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public FormInstance getFormInstance(@PathVariable Integer id) {
        return getFormInstanceFromRequest(id);
    }

    @PostMapping(value = "/form-instances")
    @ResponseStatus(value = HttpStatus.CREATED)
    public FormInstance createFormInstance(@RequestBody FormInstanceCreateRequest createRequest) {
        if (createRequest.getFormDefinitionId() == null && createRequest.getFormDefinitionKey() == null) {
            exceptionFactory.throwIllegalArgument(ErrorConstant.FORM_KEY_ID_NOT_FOUND);
        }

        FormDefinition formDefinition;
        if (createRequest.getFormDefinitionId() != null) {
            formDefinition = formDefinitionRepository.getOne(createRequest.getFormDefinitionId());
        } else if (createRequest.getTenantId() != null) {
            formDefinition = formDefinitionRepository.findLatestFormDefinitionByKeyAndTenantId(createRequest.getFormDefinitionKey(), createRequest.getTenantId());
        } else {
            formDefinition = formDefinitionRepository.findLatestFormDefinitionByKey(createRequest.getFormDefinitionKey());
        }

        if (formDefinition == null) {
            exceptionFactory.throwObjectNotFound(ErrorConstant.FORM_DEFINITION_NOT_FOUND);
        }

        int tableRelationId = createFormData(formDefinition.getRelationTable(), createRequest.getFormData());
        FormInstance formInstance = new FormInstance();
        formInstance.setFormDefinitionId(formDefinition.getId());
        formInstance.setFormDefinitionName(formDefinition.getName());
        formInstance.setTableRelationId(tableRelationId);
        formInstance.setRelationTable(formDefinition.getRelationTable());
        formInstance.setTenantId(formDefinition.getTenantId());

        return formInstanceRepository.save(formInstance);
    }


    @GetMapping("/form-instances/{id}/data")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getFormInstanceData(@PathVariable Integer id) {
        FormInstance formInstance = getFormInstanceFromRequest(id);

        return getFormData(formInstance.getRelationTable(), formInstance.getTableRelationId());
    }

    @PutMapping("/form-instances/{id}/data")
    @ResponseStatus(HttpStatus.OK)
    public int getFormInstanceData(@PathVariable Integer id, @RequestBody Map<String, String> formData) {
        FormInstance formInstance = getFormInstanceFromRequest(id);

        return updateFormData(formInstance.getRelationTable(), formInstance.getTableRelationId(), formData);
    }

    private Map<String, Object> getFormData(String tableName, int id) {
        String sql = "SELECT * FROM " + TableConstant.DATABASE_NAME + "`" + tableName + "` WHERE ID_=" + id;

        return jdbcClient.queryForMap(sql);
    }

    private int createFormData(String tableName, Map<String, String> formData) {
        if (formData == null) {
            formData = new HashMap<>(1);
            formData.put("id_", "0");
        }
        String keySql = formData.keySet().stream().map(e -> "`" + e + "`").collect(Collectors.joining(","));
        String valueSql = formData.values().stream().map(e -> "'" + (e == null ? "" : e) + "'").collect(Collectors.joining(","));

        String sql = "INSERT INTO " + TableConstant.DATABASE_NAME + "`" + tableName + "`(" + keySql + ") values(" + valueSql + ")";

        return jdbcClient.insert(sql, "id_");
    }

    private int updateFormData(String tableName, int id, Map<String, String> formData) {
        String updateSql = formData.entrySet().stream().map(e -> "`" + e.getKey() + "`='" + e.getValue() + "' ").collect(Collectors.joining(","));

        String sql = "UPDATE " + TableConstant.DATABASE_NAME + "`" + tableName + "` SET " + updateSql + " WHERE ID_=" + id;

        return jdbcClient.update(sql);
    }

}
