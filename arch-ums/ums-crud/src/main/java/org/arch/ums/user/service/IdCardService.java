package org.arch.ums.user.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.user.dao.IdCardDao;
import org.arch.ums.user.entity.IdCard;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户身份证表(IdCard) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-02-26 13:31:58
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class IdCardService extends CrudService<IdCard, java.lang.Long> {
    private final IdCardDao idCardDao;

    /**
     * 逻辑删除
     *
     * @param id 主键 id
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(@NonNull java.lang.Long id) {
        IdCard entity = new IdCard();
        entity.setId(id);
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<IdCard> updateWrapper = Wrappers.lambdaUpdate(entity)
                .set(IdCard::getDeleted, 1);
        return idCardDao.update(updateWrapper);
    }

    /**
     * 逻辑删除
     *
     * @param entity 实体
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(IdCard entity) {
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<IdCard> updateWrapper = Wrappers.lambdaUpdate(entity)
                .set(IdCard::getDeleted, 1);
        // 逻辑删除
        return idCardDao.update(updateWrapper);
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

        LambdaUpdateWrapper<IdCard> updateWrapper = Wrappers.<IdCard>lambdaUpdate()
                .eq(IdCard::getDeleted, 0)
                .in(IdCard::getId, ids)
                .set(IdCard::getDeleted, 1);

        // 逻辑删除
        return idCardDao.update(updateWrapper);
    }
}
