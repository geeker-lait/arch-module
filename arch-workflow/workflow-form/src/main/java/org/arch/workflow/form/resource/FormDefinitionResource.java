package org.arch.workflow.form.resource;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.arch.workflow.common.constant.CoreConstant;
import org.arch.workflow.common.jpa.Criteria;
import org.arch.workflow.common.jpa.Restrictions;
import org.arch.workflow.common.resource.BaseResource;
import org.arch.workflow.common.resource.PageResponse;
import org.arch.workflow.form.constant.ErrorConstant;
import org.arch.workflow.form.domain.ByteArray;
import org.arch.workflow.form.domain.FormDefinition;
import org.arch.workflow.form.domain.FormField;
import org.arch.workflow.form.domain.FormLayout;
import org.arch.workflow.form.repository.ByteArrayRepository;
import org.arch.workflow.form.repository.FormDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 表单定义资源类
 *
 * @author lait.zhang@gmail.com
 * @date 2018/12/10
 */
@RestController
public class FormDefinitionResource extends BaseResource {
    private final FormDefinitionRepository formDefinitionRepository;
    private final ByteArrayRepository byteArrayRepository;

    @Autowired
    public FormDefinitionResource(FormDefinitionRepository formDefinitionRepository, ByteArrayRepository byteArrayRepository) {
        this.formDefinitionRepository = formDefinitionRepository;
        this.byteArrayRepository = byteArrayRepository;
    }

    private FormDefinition getFormDefinitionFromRequest(Integer id) {
        FormDefinition formDefinition = formDefinitionRepository.findById(id).orElse(null);
        if (formDefinition == null) {
            exceptionFactory.throwObjectNotFound(ErrorConstant.FORM_DEFINITION_NOT_FOUND);
        }
        return formDefinition;
    }

    @GetMapping(value = "/form-definitions")
    @ResponseStatus(value = HttpStatus.OK)
    public PageResponse getFormDefinitions(@RequestParam Map<String, String> requestParams) {
        Criteria<FormDefinition> criteria = new Criteria<>();
        criteria.add(Restrictions.eq("id", requestParams.get("id")));
        criteria.add(Restrictions.eq("relationTable", requestParams.get("relationTable")));
        criteria.add(Restrictions.like("name", requestParams.get("name")));
        criteria.add(Restrictions.like("key", requestParams.get("key")));
        criteria.add(Restrictions.like("category", requestParams.get("category")));
        criteria.add(Restrictions.like("tenantId", requestParams.get("tenantId")));
        return createPageResponse(formDefinitionRepository.findAll(criteria, getPageable(requestParams)));
    }

    @GetMapping(value = "/form-definitions/latest")
    @ResponseStatus(value = HttpStatus.OK)
    public List<FormDefinition> getFormDefinitionLatests() {
        return formDefinitionRepository.findLatestFormDefinitions();
    }

    @GetMapping(value = "/form-definitions/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public FormDefinition getFormDefinition(@PathVariable Integer id) {
        return getFormDefinitionFromRequest(id);
    }

    @GetMapping(value = "/form-definitions/{id}/metadata")
    @ResponseStatus(value = HttpStatus.OK)
    public ObjectNode getFormDefinitionMetadata(@PathVariable Integer id) throws Exception {
        FormDefinition formDefinition = getFormDefinitionFromRequest(id);

        ObjectNode resultNode = objectMapper.createObjectNode();

        ByteArray formFieldJson = byteArrayRepository.getOne(formDefinition.getFieldSourceId());
        List<FormField> formFields = objectMapper.readValue(formFieldJson.getContentByte(), TypeFactory.defaultInstance().constructCollectionType(List.class, FormField.class));
        resultNode.putPOJO("fields", formFields);

        ByteArray layoutJson = byteArrayRepository.getOne(formDefinition.getLayoutSourceId());
        List<FormLayout> formLayouts = objectMapper.readValue(layoutJson.getContentByte(), TypeFactory.defaultInstance().constructCollectionType(List.class, FormLayout.class));
        resultNode.putPOJO("layouts", formLayouts);

        return resultNode;
    }

    @GetMapping("/form-definitions/{formDefinitionId}/{layoutKey}/json")
    @ResponseStatus(HttpStatus.OK)
    public ObjectNode getFormDefinitionJson(@PathVariable Integer formDefinitionId, @PathVariable String layoutKey) throws Exception {
        FormDefinition formDefinition = getFormDefinitionFromRequest(formDefinitionId);
        return getFormDefinitionJson(formDefinition, layoutKey);
    }

    @GetMapping("/form-definitions/{formDefinitionKey}/{layoutKey}/latest/json")
    @ResponseStatus(HttpStatus.OK)
    public ObjectNode getFormDefinitionJson(@PathVariable String formDefinitionKey, @PathVariable String layoutKey) throws Exception {
        FormDefinition formDefinition = formDefinitionRepository.findLatestFormDefinitionByKey(formDefinitionKey);
        return getFormDefinitionJson(formDefinition, layoutKey);
    }

    private ObjectNode getFormDefinitionJson(FormDefinition formDefinition, String layoutKey) throws Exception {
        ObjectNode resultNode = objectMapper.createObjectNode();

        ByteArray formFieldJson = byteArrayRepository.getOne(formDefinition.getFieldSourceId());
        List<FormField> formFields = objectMapper.readValue(formFieldJson.getContentByte(), TypeFactory.defaultInstance().constructCollectionType(List.class, FormField.class));
        resultNode.putPOJO("fields", formFields);

        ByteArray layoutJson = byteArrayRepository.getOne(formDefinition.getLayoutSourceId());
        List<FormLayout> formLayouts = objectMapper.readValue(layoutJson.getContentByte(), TypeFactory.defaultInstance().constructCollectionType(List.class, FormLayout.class));
        FormLayout formLayout = formLayouts.stream().filter(f -> f.getKey().equals(layoutKey)).findFirst().orElse(null);
        if (formLayout != null) {
            ByteArray byteArray = byteArrayRepository.getOne(formLayout.getEditorSourceId());
            resultNode.putPOJO("json", new String(byteArray.getContentByte(), CoreConstant.DEFAULT_CHARSET));
        }

        return resultNode;
    }

}
