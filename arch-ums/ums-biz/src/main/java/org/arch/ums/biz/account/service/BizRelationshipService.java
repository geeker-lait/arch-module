package org.arch.ums.biz.account.service;

import lombok.extern.slf4j.Slf4j;
import org.arch.ums.account.dao.RelationshipDao;
import org.arch.ums.account.entity.Relationship;
import org.arch.ums.account.service.RelationshipService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 账号-关系(Relationship) 表业务服务层
 *
 * @author YongWu zheng
 * @date 2021-03-14 14:36:56
 * @since 1.0.0
 */
@Slf4j
@Service
public class BizRelationshipService extends RelationshipService {

    public BizRelationshipService(RelationshipDao relationshipDao) {
        super(relationshipDao);
    }

    /**
     * 保存, 如果 seq 或 org 等于 null, 则通过 sql max(org/seq) + 1 自增
     * @param relationship  {@link Relationship}
     * @return 是否保存成功
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean saveMax(@NonNull Relationship relationship) {
        return relationshipDao.saveMax(relationship);
    }
}
