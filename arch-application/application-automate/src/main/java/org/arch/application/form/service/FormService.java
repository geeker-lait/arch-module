package org.arch.application.form.service;

import com.github.pagehelper.PageInfo;
import org.arch.application.form.domain.Form;
import org.arch.application.form.domain.FormAndRows;

public interface FormService {

    int insert(Form obj);

    int deleteById(int id);

    int update(Form obj);

    Form queryOne(int id);

    PageInfo<Form> queryAll(int page, int size);

    /**
     * 查询表单列表的时候加上数据条数、字段个数、是否建表等字段
     *
     * @return
     */
    PageInfo<FormAndRows> queryFormAndRows(String searchValue, int page, int size);

}
