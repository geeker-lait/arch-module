package org.arch.framework.automate.from.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.arch.framework.automate.from.entity.Field;
import org.arch.framework.automate.from.entity.FieldType;
import org.arch.framework.automate.from.entity.Option;
import org.arch.framework.crud.CrudMapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
public interface FieldMapper extends CrudMapper<Field> {

    @Override
    int insert(Field obj);

    int deleteById(int id);

    int update(Field obj);

    Field queryOne(int id);

    List<Field> queryAll();

    List<Field> queryAll(Integer formId);

    int updateFieldCode(Field obj);

    List<FieldType> getFieldTypes();

    int countFieldByFieldNameAndFormId(Field field);

    /**
     * 新增选项
     *
     * @param obj
     * @return
     */
    int insertOption(Option obj);


    /**
     * 删除选项
     *
     * @param optionId
     * @return
     */
    int deleteOption(Integer optionId);

    /**
     * 根据选项名称计数
     *
     * @param obj
     * @return
     */
    int countOptionByOptionName(Option obj);

    /**
     * 根据字段Id查询选项列表
     *
     * @param fieldId
     * @return
     */
    List<Option> getOptionList(Integer fieldId);

    /**
     * 根据表单ID查询已配置好的字段个数
     *
     * @param formId
     * @return
     */
    int countFields(Integer formId);

    /**
     * 删除表单的时候，需要删除相关字段。
     *
     * @return
     */
    int deleteByFormId(Integer formId);

    /**
     * 删除表单时，要查出类型是radio或者checkbox的字段
     * 并把相关的选项删除掉，要不然会有垃圾数据
     *
     * @param formId
     * @return
     */
    int deleteOptionByFormId(Integer formId);

    /**
     * 删除字段时，并把相关的选项删除掉，要不然会有垃圾数据
     *
     * @param fieldId
     * @return
     */
    int deleteOptionByFieldId(Integer fieldId);

}
