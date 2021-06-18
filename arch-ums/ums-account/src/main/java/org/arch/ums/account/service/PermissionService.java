package org.arch.ums.account.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.dao.PermissionDao;
import org.arch.ums.account.entity.Permission;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 账号-权限(Permission) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-02-26 13:28:08
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PermissionService extends CrudService<Permission, java.lang.Long> {
    private final PermissionDao permissionDao;

    /**
     * 逻辑删除
     *
     * @param id 主键 id
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(@NonNull java.lang.Long id) {
        Permission entity = new Permission();
        entity.setId(id);
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<Permission> updateWrapper = Wrappers.<Permission>lambdaUpdate(entity)
                .set(Permission::getDeleted, 1);
        return permissionDao.update(updateWrapper);
    }

    /**
     * 逻辑删除
     *
     * @param entity 实体
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(Permission entity) {
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<Permission> updateWrapper = Wrappers.<Permission>lambdaUpdate(entity)
                .set(Permission::getDeleted, 1);
        // 逻辑删除
        return permissionDao.update(updateWrapper);
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

        LambdaUpdateWrapper<Permission> updateWrapper = Wrappers.<Permission>lambdaUpdate()
                .eq(Permission::getDeleted, 0)
                .in(Permission::getId, ids)
                .set(Permission::getDeleted, 1);

        // 逻辑删除
        return permissionDao.update(updateWrapper);
    }
}
