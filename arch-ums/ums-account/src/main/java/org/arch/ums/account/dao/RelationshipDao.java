package org.arch.ums.account.dao;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.arch.ums.account.entity.Relationship;
import org.arch.ums.account.mapper.RelationshipMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

/**
 * 账号-关系(Relationship) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-03-14 14:36:56
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class RelationshipDao extends CrudServiceImpl<RelationshipMapper, Relationship> implements CrudDao<Relationship> {
    private final RelationshipMapper relationshipMapper;

    /**
     * 保存, 如果 seq 或 org 等于 null, 则通过 sql max(org/seq) + 1 自增
     * @param relationship  {@link Relationship}
     * @return 是否保存成功
     */
    public boolean saveMax(@NonNull Relationship relationship) {
        return SqlHelper.retBool(relationshipMapper.saveMax(relationship));
    }
}
