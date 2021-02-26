package org.arch.ums.account.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.dao.OperateLogDao;
import org.arch.ums.account.entity.OperateLog;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 账号操作记录(OperateLog) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-02-26 13:25:53
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class OperateLogService extends CrudService<OperateLog, java.lang.Long> {
    private final OperateLogDao operateLogDao = (OperateLogDao) crudDao;

    /**
     * 逻辑删除
     *
     * @param id 主键 id
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(@NonNull java.lang.Long id) {
        OperateLog entity = new OperateLog();
        entity.setId(id);
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<OperateLog> updateWrapper = Wrappers.<OperateLog>lambdaUpdate(entity)
                .set(OperateLog::getDeleted, 1);
        return operateLogDao.update(updateWrapper);
    }

    /**
     * 逻辑删除
     *
     * @param entity 实体
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(OperateLog entity) {
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<OperateLog> updateWrapper = Wrappers.<OperateLog>lambdaUpdate(entity)
                .set(OperateLog::getDeleted, 1);
        // 逻辑删除
        return operateLogDao.update(updateWrapper);
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

        LambdaUpdateWrapper<OperateLog> updateWrapper = Wrappers.<OperateLog>lambdaUpdate()
                .eq(OperateLog::getDeleted, 0)
                .and(w -> w.in(OperateLog::getId, ids))
                .set(OperateLog::getDeleted, 1);

        // 逻辑删除
        return operateLogDao.update(updateWrapper);
    }
}
