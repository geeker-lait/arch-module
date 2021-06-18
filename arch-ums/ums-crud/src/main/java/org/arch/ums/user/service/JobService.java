package org.arch.ums.user.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.user.dao.JobDao;
import org.arch.ums.user.entity.Job;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户工作信息(Job) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-02-26 13:31:57
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class JobService extends CrudService<Job, java.lang.Long> {
    private final JobDao jobDao;

    /**
     * 逻辑删除
     *
     * @param id 主键 id
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(@NonNull java.lang.Long id) {
        Job entity = new Job();
        entity.setId(id);
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<Job> updateWrapper = Wrappers.lambdaUpdate(entity)
                .set(Job::getDeleted, 1);
        return jobDao.update(updateWrapper);
    }

    /**
     * 逻辑删除
     *
     * @param entity 实体
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(Job entity) {
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<Job> updateWrapper = Wrappers.lambdaUpdate(entity)
                .set(Job::getDeleted, 1);
        // 逻辑删除
        return jobDao.update(updateWrapper);
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

        LambdaUpdateWrapper<Job> updateWrapper = Wrappers.<Job>lambdaUpdate()
                .eq(Job::getDeleted, 0)
                .in(Job::getId, ids)
                .set(Job::getDeleted, 1);

        // 逻辑删除
        return jobDao.update(updateWrapper);
    }
}
