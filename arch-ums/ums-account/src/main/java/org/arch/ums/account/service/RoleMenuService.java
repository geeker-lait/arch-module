package org.arch.ums.account.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.dao.RoleMenuDao;
import org.arch.ums.account.entity.RoleMenu;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 账号-角色菜单(RoleMenu) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-02-26 13:28:42
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RoleMenuService extends CrudService<RoleMenu, java.lang.Long> {
    private final RoleMenuDao roleMenuDao = (RoleMenuDao) crudDao;

    /**
     * 逻辑删除
     *
     * @param id 主键 id
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(@NonNull java.lang.Long id) {
        RoleMenu entity = new RoleMenu();
        entity.setId(id);
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<RoleMenu> updateWrapper = Wrappers.<RoleMenu>lambdaUpdate(entity)
                .set(RoleMenu::getDeleted, 1);
        return roleMenuDao.update(updateWrapper);
    }

    /**
     * 逻辑删除
     *
     * @param entity 实体
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(RoleMenu entity) {
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<RoleMenu> updateWrapper = Wrappers.<RoleMenu>lambdaUpdate(entity)
                .set(RoleMenu::getDeleted, 1);
        // 逻辑删除
        return roleMenuDao.update(updateWrapper);
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

        LambdaUpdateWrapper<RoleMenu> updateWrapper = Wrappers.<RoleMenu>lambdaUpdate()
                .eq(RoleMenu::getDeleted, 0)
                .and(w -> w.in(RoleMenu::getId, ids))
                .set(RoleMenu::getDeleted, 1);

        // 逻辑删除
        return roleMenuDao.update(updateWrapper);
    }
}
