package org.arch.ums.conf.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.conf.dao.MobileSegmentDao;
import org.arch.ums.conf.entity.MobileSegment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

/**
 * 手机号段信息(MobileSegment) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-03-15 21:48:34
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MobileSegmentService extends CrudService<MobileSegment, java.lang.Long> {

    private final MobileSegmentDao mobileSegmentDao;

    /**
     * 逻辑删除
     *
     * @param id 主键 id
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(@NonNull java.lang.Long id) {
        MobileSegment entity = new MobileSegment();
        entity.setId(id);
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<MobileSegment> updateWrapper = Wrappers.lambdaUpdate(entity)
                .set(MobileSegment::getDeleted, 1);
        return mobileSegmentDao.update(updateWrapper);
    }

    /**
     * 逻辑删除
     *
     * @param entity 实体
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(MobileSegment entity) {
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<MobileSegment> updateWrapper = Wrappers.lambdaUpdate(entity)
                .set(MobileSegment::getDeleted, 1);
        // 逻辑删除
        return mobileSegmentDao.update(updateWrapper);
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

        LambdaUpdateWrapper<MobileSegment> updateWrapper = Wrappers.<MobileSegment>lambdaUpdate()
                .eq(MobileSegment::getDeleted, 0)
                .in(MobileSegment::getId, ids)
                .set(MobileSegment::getDeleted, 1);

        // 逻辑删除
        return mobileSegmentDao.update(updateWrapper);
    }

    /**
     * 批量保存, 如果主键或唯一索引重复则更新.
     * @param mobileSegmentList 实体类列表
     * @return  true 表示成功, false 表示失败.
     * @throws SQLException 批量保持失败
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean insertOnDuplicateKeyUpdateBatch(List<MobileSegment> mobileSegmentList) throws SQLException {
        return mobileSegmentDao.insertOnDuplicateKeyUpdateBatch(mobileSegmentList);
    }
}
