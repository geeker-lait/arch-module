package org.arch.ums.account.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.dao.RelationshipDao;
import org.arch.ums.account.entity.Relationship;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.isNull;

/**
 * 账号-关系(Relationship) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-03-14 14:36:56
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RelationshipService extends CrudService<Relationship, java.lang.Long> {

    private final RelationshipDao relationshipDao;

    /**
     * 逻辑删除
     *
     * @param id 主键 id
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(@NonNull java.lang.Long id) {
        Relationship entity = new Relationship();
        entity.setId(id);
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<Relationship> updateWrapper = Wrappers.lambdaUpdate(entity)
                .set(Relationship::getDeleted, 1);
        return relationshipDao.update(updateWrapper);
    }

    /**
     * 逻辑删除
     *
     * @param entity 实体
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(Relationship entity) {
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<Relationship> updateWrapper = Wrappers.lambdaUpdate(entity)
                .set(Relationship::getDeleted, 1);
        // 逻辑删除
        return relationshipDao.update(updateWrapper);
    }

    /**
     * 批量逻辑删除
     *
     * @param ids 主键集合
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean deleteAllById(@NonNull List<java.lang.Long> ids) {

        LambdaUpdateWrapper<Relationship> updateWrapper = Wrappers.<Relationship>lambdaUpdate()
                .eq(Relationship::getDeleted, 0)
                .in(Relationship::getId, ids)
                .set(Relationship::getDeleted, 1);

        // 逻辑删除
        return relationshipDao.update(updateWrapper);
    }

    /**
     * 保存, 如果 seq 或 org 等于 null, 则通过 sql max(org/seq) + 1 自增
     * @param relationship  {@link Relationship}
     * @return 是否保存成功
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean saveMax(@NonNull Relationship relationship) {
        boolean saveMax = relationshipDao.saveMax(relationship);
        if (isNull(relationship.getOrg()) || isNull(relationship.getSeq())) {
            Relationship byId = relationshipDao.getById(relationship.getId());
            relationship.setOrg(byId.getOrg());
            relationship.setSeq(byId.getSeq());
        }
        return saveMax;
    }
}
