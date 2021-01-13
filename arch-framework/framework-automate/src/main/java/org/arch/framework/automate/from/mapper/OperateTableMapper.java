package org.arch.framework.automate.from.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.arch.framework.automate.from.entity.FieldAndOptionDetail;

import java.util.List;
import java.util.Map;

@Mapper
public interface OperateTableMapper {
    /**
     * 判断表名在数据库中是否存在
     *
     * @param tableName
     * @return
     */
    Integer existTable(String tableName);

    /**
     * 返回已存在的表中的数据行数
     *
     * @param map
     * @return
     */
    Integer countTableRow(Map<String, Object> map);

    /**
     * 从数据库中删除表
     *
     * @param map
     */
    Integer dropTable(Map<String, Object> map);

    /**
     * 新建表
     *
     * @param map
     * @return
     */
    Integer createNewTable(Map<String, Object> map);

    /**
     * 返回表单字段列表
     *
     * @param formId
     * @return
     */
    List<String> getFieldList(Integer formId);

    /**
     * 查询所有明细
     *
     * @param map
     * @return
     */
    List<Map<String, Object>> queryAll(Map<String, Object> map);

    /**
     * 获取字段和字段默认值详情列表
     *
     * @return
     */
    List<FieldAndOptionDetail> getFieldAndOptionDetailList(Integer formId);

    /**
     * 插入数据到动态表单
     *
     * @param map
     * @return
     */
    Integer insertTable(Map<String, Object> map);

    /**
     * 更新动态表单
     *
     * @param map
     * @return
     */
    Integer updateTable(Map<String, Object> map);

    /**
     * 删除动态表单的一条数据
     *
     * @param map
     * @return
     */
    Integer deleteTable(Map<String, Object> map);

    /**
     * 查询当行数据
     *
     * @param map
     * @return
     */
    List<Map<String, Object>> queryOne(Map<String, Object> map);
}
