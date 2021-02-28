package org.arch.ums.account.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.dao.GroupDao;
import org.arch.ums.account.entity.Group;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 账号-组织机构(Group) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-02-26 13:23:11
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class GroupService extends CrudService<Group, java.lang.Long> {
    private final GroupDao groupDao;

    /**
     * 逻辑删除
     *
     * @param id 主键 id
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(@NonNull java.lang.Long id) {
        Group entity = new Group();
        entity.setId(id);
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<Group> updateWrapper = Wrappers.<Group>lambdaUpdate(entity)
                .set(Group::getDeleted, 1);
        return groupDao.update(updateWrapper);
    }

    /**
     * 逻辑删除
     *
     * @param entity 实体
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(Group entity) {
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<Group> updateWrapper = Wrappers.<Group>lambdaUpdate(entity)
                .set(Group::getDeleted, 1);
        // 逻辑删除
        return groupDao.update(updateWrapper);
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

        LambdaUpdateWrapper<Group> updateWrapper = Wrappers.<Group>lambdaUpdate()
                .eq(Group::getDeleted, 0)
                .and(w -> w.in(Group::getId, ids))
                .set(Group::getDeleted, 1);

        // 逻辑删除
        return groupDao.update(updateWrapper);
    }
}
