package org.arch.ums.member.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.arch.ums.member.entity.MemberRights;
import org.arch.ums.member.dao.MemberRightsDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.Long;

import org.arch.framework.crud.CrudService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 会员权益(MemberRights) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-05-19 15:41:21
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MemberRightsService extends CrudService<MemberRights, java.lang.Long> {

    private final MemberRightsDao memberRightsDao;

    /**
     * 逻辑删除
     *
     * @param id 主键 id
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(@NonNull java.lang.Long id) {
        MemberRights entity = new MemberRights();
        entity.setId(id);
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<MemberRights> updateWrapper = Wrappers.lambdaUpdate(entity)
                                                                  .set(MemberRights::getDeleted, 1);
        return memberRightsDao.update(updateWrapper);
    }

    /**
     * 逻辑删除
     *
     * @param entity 实体
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(MemberRights entity) {
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<MemberRights> updateWrapper = Wrappers.lambdaUpdate(entity)
                                                                  .set(MemberRights::getDeleted, 1);
        // 逻辑删除
        return memberRightsDao.update(updateWrapper);
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

        LambdaUpdateWrapper<MemberRights> updateWrapper = Wrappers.<MemberRights>lambdaUpdate()
                                                                  .eq(MemberRights::getDeleted, 0)
                                                                  .in(MemberRights::getId, ids)
                                                                  .set(MemberRights::getDeleted, 1);

        // 逻辑删除
        return memberRightsDao.update(updateWrapper);
    }
}
