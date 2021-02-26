package org.arch.ums.account.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.dao.RoleGroupDao;
import org.arch.ums.account.entity.RoleGroup;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 账号-角色组织或机构(RoleGroup) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-02-26 13:28:42
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RoleGroupService extends CrudService<RoleGroup, java.lang.Long> {
    private final RoleGroupDao roleGroupDao = (RoleGroupDao) crudDao;

    /**
     * 逻辑删除
     *
     * @param id 主键 id
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(@NonNull java.lang.Long id) {
        RoleGroup entity = new RoleGroup();
        entity.setId(id);
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<RoleGroup> updateWrapper = Wrappers.<RoleGroup>lambdaUpdate(entity)
                .set(RoleGroup::getDeleted, 1);
        return roleGroupDao.update(updateWrapper);
    }

    /**
     * 逻辑删除
     *
     * @param entity 实体
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(RoleGroup entity) {
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<RoleGroup> updateWrapper = Wrappers.<RoleGroup>lambdaUpdate(entity)
                .set(RoleGroup::getDeleted, 1);
        // 逻辑删除
        return roleGroupDao.update(updateWrapper);
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

        LambdaUpdateWrapper<RoleGroup> updateWrapper = Wrappers.<RoleGroup>lambdaUpdate()
                .eq(RoleGroup::getDeleted, 0)
                .and(w -> w.in(RoleGroup::getId, ids))
                .set(RoleGroup::getDeleted, 1);

        // 逻辑删除
        return roleGroupDao.update(updateWrapper);
    }
}
