package org.arch.ums.account.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.arch.framework.crud.CrudMapper;
import org.arch.ums.account.entity.Relationship;
import org.springframework.lang.NonNull;

/**
 * 账号-关系(Relationship) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-03-14 14:36:56
 * @since 1.0.0
 */
@Mapper
public interface RelationshipMapper extends CrudMapper<Relationship> {

    /**
     * 保存, 如果 seq 或 org 等于 null, 则通过 sql max(org/seq) + 1 自增
     * @param relationship  {@link Relationship}
     * @return 是否保存成功
     */
    int saveMax(@NonNull @Param("rs") Relationship relationship);
}
