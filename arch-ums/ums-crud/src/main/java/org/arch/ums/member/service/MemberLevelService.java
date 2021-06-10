package org.arch.ums.member.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.arch.ums.member.entity.MemberLevel;
import org.arch.ums.member.dao.MemberLevelDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.arch.framework.crud.CrudService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 会员级别(MemberLevel) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-05-19 15:41:16
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MemberLevelService extends CrudService<MemberLevel, java.lang.Long> {

    private final MemberLevelDao memberLevelDao;

    /**
     * 逻辑删除
     *
     * @param id 主键 id
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(@NonNull java.lang.Long id) {
        MemberLevel entity = new MemberLevel();
        entity.setId(id);
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<MemberLevel> updateWrapper = Wrappers.lambdaUpdate(entity)
                                                                 .set(MemberLevel::getDeleted, 1);
        return memberLevelDao.update(updateWrapper);
    }

    /**
     * 逻辑删除
     *
     * @param entity 实体
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(MemberLevel entity) {
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<MemberLevel> updateWrapper = Wrappers.lambdaUpdate(entity)
                                                                 .set(MemberLevel::getDeleted, 1);
        // 逻辑删除
        return memberLevelDao.update(updateWrapper);
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

        LambdaUpdateWrapper<MemberLevel> updateWrapper = Wrappers.<MemberLevel>lambdaUpdate()
                                                                 .eq(MemberLevel::getDeleted, 0)
                                                                 .in(MemberLevel::getId, ids)
                                                                 .set(MemberLevel::getDeleted, 1);

        // 逻辑删除
        return memberLevelDao.update(updateWrapper);
    }
}
