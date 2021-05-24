package org.arch.workflow.form.repository;

import org.arch.workflow.common.repository.BaseRepository;
import org.arch.workflow.form.domain.FormTable;

/**
 * 表格数据类
 *
 * @author lait.zhang@gmail.com
 * @date 2018/12/10
 */
public interface FormTableRepository extends BaseRepository<FormTable, Integer> {
    /**
     * 根据key获取表格
     *
     * @param key
     * @return FormTable
     */
    FormTable findByKey(String key);
}