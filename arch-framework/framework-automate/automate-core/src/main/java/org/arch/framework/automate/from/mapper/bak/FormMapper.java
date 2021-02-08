package org.arch.framework.automate.from.mapper.bak;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.arch.framework.automate.from.entity.bak.Form;
import org.arch.framework.automate.from.entity.bak.FormAndRows;
import org.arch.framework.crud.CrudMapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FormMapper extends CrudMapper<Form> {

    void alertForm(Form form);

    int insert(Form obj);

    int deleteById(int id);

    int update(Form obj);

    Form queryOne(int id);

    List<Form> queryAll();

    /**
     * 查询表单列表的时候加上数据条数、字段个数、是否建表等字段
     *
     * @return
     */
    List<FormAndRows> queryFormAndRows(Map<String, Object> map);

    void updateFormTableName(Form obj);

    int countByFormName(@Param("formName") String formName);

}
