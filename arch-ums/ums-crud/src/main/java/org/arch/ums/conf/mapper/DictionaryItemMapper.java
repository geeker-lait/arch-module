package org.arch.ums.conf.mapper;

import org.arch.framework.crud.CrudMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.ums.conf.entity.DictionaryItem;

/**
 * 数据字典明细(DictionaryItem) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-04-17 15:22:51
 * @since 1.0.0
 */
@Mapper
public interface DictionaryItemMapper extends CrudMapper<DictionaryItem> {

}
