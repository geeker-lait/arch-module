package org.arch.ums.conf.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.conf.dao.FileInfoDao;
import org.arch.ums.conf.entity.FileInfo;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 对象存储文件信息(FileInfo) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-02-27 16:41:07
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FileInfoService extends CrudService<FileInfo, java.lang.Long> {

    private final FileInfoDao fileInfoDao;

    /**
     * 逻辑删除
     *
     * @param id 主键 id
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(@NonNull java.lang.Long id) {
        FileInfo entity = new FileInfo();
        entity.setId(id);
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<FileInfo> updateWrapper = Wrappers.lambdaUpdate(entity)
                .set(FileInfo::getDeleted, 1);
        return fileInfoDao.update(updateWrapper);
    }

    /**
     * 逻辑删除
     *
     * @param entity 实体
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(FileInfo entity) {
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<FileInfo> updateWrapper = Wrappers.lambdaUpdate(entity)
                .set(FileInfo::getDeleted, 1);
        // 逻辑删除
        return fileInfoDao.update(updateWrapper);
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

        LambdaUpdateWrapper<FileInfo> updateWrapper = Wrappers.<FileInfo>lambdaUpdate()
                .eq(FileInfo::getDeleted, 0)
                .and(w -> w.in(FileInfo::getId, ids))
                .set(FileInfo::getDeleted, 1);

        // 逻辑删除
        return fileInfoDao.update(updateWrapper);
    }

    /**
     * 根据 filePath 与 uploadType 删除 文件信息
     * @param filePath      文件路径
     * @param uploadType    上传类型
     * @return  删除的文件信息
     */
    @Nullable
    public FileInfo findByFilePathAndUploadType(@NonNull Integer tenantId, @NonNull String filePath,
                                                @NonNull String uploadType) {
        LambdaQueryWrapper<FileInfo> queryWrapper =
                Wrappers.<FileInfo>lambdaQuery()
                        .eq(FileInfo::getTenantId, tenantId)
                        .and(w -> w.eq(FileInfo::getUploadType, uploadType))
                        .and(w -> w.eq(FileInfo::getFilePath, filePath));
        return fileInfoDao.getOne(queryWrapper);
    }
}
