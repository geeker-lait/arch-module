package org.arch.framework.automate.from.service;

import org.arch.framework.automate.from.entity.Field;
import org.arch.framework.automate.from.entity.FieldType;
import org.arch.framework.automate.from.entity.Option;

import java.util.List;

public interface FieldService {

    int insert(Field obj);

    int deleteById(int id);

    int update(Field obj);

    Field queryOne(int id);

    List<Field> queryAll(Integer formId);

    List<Field> ifTableExistAndFieldList(Integer formId) ;

    List<FieldType> getFieldTypes();
    /**
     * 新增选项
     * @param obj
     * @return
     */
    int insertOption(Option obj);

    /**
     * 删除选项
     * @param optionId
     * @return
     */
    int deleteOption(Integer optionId);

    /**
     * 根据字段Id查询选项列表
     * @param fieldId
     * @return
     */
    List<Option> getOptionList(Integer fieldId);

    /**
     * 根据表单ID查询已配置好的字段个数
     * @param formId
     * @return
     */
    int countFields(Integer formId);
}
