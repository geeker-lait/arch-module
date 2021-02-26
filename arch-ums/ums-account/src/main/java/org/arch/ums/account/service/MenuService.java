package org.arch.ums.account.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.dao.MenuDao;
import org.arch.ums.account.entity.Menu;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 账号-菜单(Menu) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-02-26 13:24:38
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MenuService extends CrudService<Menu, java.lang.Long> {
    private final MenuDao menuDao = (MenuDao) crudDao;

    /**
     * 逻辑删除
     *
     * @param id 主键 id
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(@NonNull java.lang.Long id) {
        Menu entity = new Menu();
        entity.setId(id);
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<Menu> updateWrapper = Wrappers.<Menu>lambdaUpdate(entity)
                .set(Menu::getDeleted, 1);
        return menuDao.update(updateWrapper);
    }

    /**
     * 逻辑删除
     *
     * @param entity 实体
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(Menu entity) {
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<Menu> updateWrapper = Wrappers.<Menu>lambdaUpdate(entity)
                .set(Menu::getDeleted, 1);
        // 逻辑删除
        return menuDao.update(updateWrapper);
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

        LambdaUpdateWrapper<Menu> updateWrapper = Wrappers.<Menu>lambdaUpdate()
                .eq(Menu::getDeleted, 0)
                .and(w -> w.in(Menu::getId, ids))
                .set(Menu::getDeleted, 1);

        // 逻辑删除
        return menuDao.update(updateWrapper);
    }
}
