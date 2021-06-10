package org.arch.ums.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.arch.framework.crud.CrudMapper;
import org.arch.ums.user.entity.IdCard;

/**
 * 用户身份证表(IdCard) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 23:06:43
 * @since 1.0.0
 */
@Mapper
public interface IdCardMapper extends CrudMapper<IdCard> {

}