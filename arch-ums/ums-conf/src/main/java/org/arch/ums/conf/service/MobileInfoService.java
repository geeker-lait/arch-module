package org.arch.ums.conf.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.conf.dao.MobileInfoDao;
import org.arch.ums.conf.entity.MobileInfo;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

/**
 * 手机号归属地信息(MobileInfo) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-03-15 21:48:35
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MobileInfoService extends CrudService<MobileInfo, java.lang.Long> {

    private final MobileInfoDao mobileInfoDao;

    /**
     * 逻辑删除
     *
     * @param id 主键 id
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(@NonNull java.lang.Long id) {
        MobileInfo entity = new MobileInfo();
        entity.setId(id);
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<MobileInfo> updateWrapper = Wrappers.lambdaUpdate(entity)
                                                                .set(MobileInfo::getDeleted, 1);
        return mobileInfoDao.update(updateWrapper);
    }

    /**
     * 逻辑删除
     *
     * @param entity 实体
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(MobileInfo entity) {
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<MobileInfo> updateWrapper = Wrappers.lambdaUpdate(entity)
                                                                .set(MobileInfo::getDeleted, 1);
        // 逻辑删除
        return mobileInfoDao.update(updateWrapper);
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

        LambdaUpdateWrapper<MobileInfo> updateWrapper = Wrappers.<MobileInfo>lambdaUpdate()
                .eq(MobileInfo::getDeleted, 0)
                .in(MobileInfo::getId, ids)
                .set(MobileInfo::getDeleted, 1);

        // 逻辑删除
        return mobileInfoDao.update(updateWrapper);
    }

    /**
     * 批量保存, 如果主键或唯一索引重复则更新.
     * @param mobileInfoList 实体类列表
     * @return  true 表示成功, false 表示失败.
     * @throws SQLException 批量保持失败
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean insertOnDuplicateKeyUpdateBatch(List<MobileInfo> mobileInfoList) throws SQLException {
        return mobileInfoDao.insertOnDuplicateKeyUpdateBatch(mobileInfoList);
    }

}
