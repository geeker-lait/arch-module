package org.arch.ums.member.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.member.dao.MemberLifeDao;
import org.arch.ums.member.entity.MemberLife;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 会员生命周期(MemberLife) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-05-19 15:41:18
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MemberLifeService extends CrudService<MemberLife, java.lang.Long> {

    private final MemberLifeDao memberLifeDao;

    /**
     * 逻辑删除
     *
     * @param id 主键 id
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(@NonNull java.lang.Long id) {
        MemberLife entity = new MemberLife();
        entity.setId(id);
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<MemberLife> updateWrapper = Wrappers.lambdaUpdate(entity)
                                                                .set(MemberLife::getDeleted, 1);
        return memberLifeDao.update(updateWrapper);
    }

    /**
     * 逻辑删除
     *
     * @param entity 实体
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(MemberLife entity) {
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<MemberLife> updateWrapper = Wrappers.lambdaUpdate(entity)
                                                                .set(MemberLife::getDeleted, 1);
        // 逻辑删除
        return memberLifeDao.update(updateWrapper);
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

        LambdaUpdateWrapper<MemberLife> updateWrapper = Wrappers.<MemberLife>lambdaUpdate()
                                                                .eq(MemberLife::getDeleted, 0)
                                                                .in(MemberLife::getId, ids)
                                                                .set(MemberLife::getDeleted, 1);

        // 逻辑删除
        return memberLifeDao.update(updateWrapper);
    }
}
