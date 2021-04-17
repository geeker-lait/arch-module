package org.arch.ums.conf.mapper;

import org.arch.framework.crud.CrudMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.ums.conf.entity.Dictionary;

/**
 * 数据字典(Dictionary) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-04-17 15:22:54
 * @since 1.0.0
 */
@Mapper
public interface DictionaryMapper extends CrudMapper<Dictionary> {

}
