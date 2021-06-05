package org.arch.ums.account.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.dao.RoleDao;
import org.arch.ums.account.entity.Role;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 账号-角色(Role) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-02-26 13:28:42
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RoleService extends CrudService<Role, java.lang.Long> {
    private final RoleDao roleDao;

    /**
     * 逻辑删除
     *
     * @param id 主键 id
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(@NonNull java.lang.Long id) {
        Role entity = new Role();
        entity.setId(id);
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<Role> updateWrapper = Wrappers.<Role>lambdaUpdate(entity)
                .set(Role::getDeleted, 1);
        return roleDao.update(updateWrapper);
    }

    /**
     * 逻辑删除
     *
     * @param entity 实体
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(Role entity) {
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<Role> updateWrapper = Wrappers.<Role>lambdaUpdate(entity)
                .set(Role::getDeleted, 1);
        // 逻辑删除
        return roleDao.update(updateWrapper);
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

        LambdaUpdateWrapper<Role> updateWrapper = Wrappers.<Role>lambdaUpdate()
                .eq(Role::getDeleted, 0)
                .in(Role::getId, ids)
                .set(Role::getDeleted, 1);

        // 逻辑删除
        return roleDao.update(updateWrapper);
    }
}
