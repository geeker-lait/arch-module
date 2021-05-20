package org.arch.workflow.form.resource;

import org.arch.workflow.common.jpa.Criteria;
import org.arch.workflow.common.jpa.Restrictions;
import org.arch.workflow.common.resource.BaseResource;
import org.arch.workflow.common.resource.PageResponse;
import org.arch.workflow.form.constant.ErrorConstant;
import org.arch.workflow.form.domain.FormField;
import org.arch.workflow.form.repository.FormFieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 表单字段资源类
 *
 * @author lait.zhang@gmail.com
 * @date 2018年9月19日
 */
@RestController
public class FormFieldResource extends BaseResource {
    private final FormFieldRepository formFieldRepository;

    @Autowired
    public FormFieldResource(FormFieldRepository formFieldRepository) {
        this.formFieldRepository = formFieldRepository;
    }

    private FormField getFormFieldFromRequest(Integer id) {
        FormField formField = formFieldRepository.getOne(id);
        if (formField == null) {
            exceptionFactory.throwObjectNotFound(ErrorConstant.FORM_FIELD_NOT_FOUND);
        }
        return formField;
    }

    @GetMapping(value = "/form-fields")
    @ResponseStatus(value = HttpStatus.OK)
    public PageResponse getFormFields(@RequestParam Map<String, String> requestParams) {
        Criteria<FormField> criteria = new Criteria<>();
        criteria.add(Restrictions.eq("id", requestParams.get("id")));
        criteria.add(Restrictions.eq("tableId", requestParams.get("tableId")));
        criteria.add(Restrictions.like("key", requestParams.get("key")));
        criteria.add(Restrictions.like("name", requestParams.get("name")));
        criteria.add(Restrictions.like("remark", requestParams.get("remark")));
        criteria.add(Restrictions.like("tenantId", requestParams.get("tenantId")));
        return createPageResponse(formFieldRepository.findAll(criteria, getPageable(requestParams)));
    }

    @GetMapping(value = "/form-fields/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public FormField getFormField(@PathVariable Integer id) {
        return getFormFieldFromRequest(id);
    }

    @PostMapping("/form-fields")
    @ResponseStatus(HttpStatus.CREATED)
    public FormField createFormField(@RequestBody FormField formFieldRequest) {
        return formFieldRepository.save(formFieldRequest);
    }

    @PutMapping(value = "/form-fields/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public FormField updateFormField(@PathVariable Integer id, @RequestBody FormField formFieldRequest) {
        FormField formField = getFormFieldFromRequest(id);
        formField.setName(formFieldRequest.getName());
        formField.setRemark(formFieldRequest.getRemark());
        formField.setTenantId(formFieldRequest.getTenantId());
        return formFieldRepository.save(formField);
    }

    @DeleteMapping(value = "/form-fields/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteFormField(@PathVariable Integer id) {
        FormField formField = getFormFieldFromRequest(id);
        formFieldRepository.delete(formField);
    }
}
