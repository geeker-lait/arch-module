package org.arch.ums.account.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.dao.MemberDao;
import org.arch.ums.account.entity.Member;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 账号-会员账号(Member) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-02-26 13:24:20
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService extends CrudService<Member, java.lang.Long> {
    private final MemberDao memberDao;

    /**
     * 逻辑删除
     *
     * @param id 主键 id
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(@NonNull java.lang.Long id) {
        Member entity = new Member();
        entity.setId(id);
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<Member> updateWrapper = Wrappers.<Member>lambdaUpdate(entity)
                .set(Member::getDeleted, 1);
        return memberDao.update(updateWrapper);
    }

    /**
     * 逻辑删除
     *
     * @param entity 实体
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(Member entity) {
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<Member> updateWrapper = Wrappers.<Member>lambdaUpdate(entity)
                .set(Member::getDeleted, 1);
        // 逻辑删除
        return memberDao.update(updateWrapper);
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

        LambdaUpdateWrapper<Member> updateWrapper = Wrappers.<Member>lambdaUpdate()
                .eq(Member::getDeleted, 0)
                .in(Member::getId, ids)
                .set(Member::getDeleted, 1);

        // 逻辑删除
        return memberDao.update(updateWrapper);
    }
}
