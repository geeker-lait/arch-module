package org.arch.ums.biz.conf.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.arch.ums.conf.dao.FileInfoDao;
import org.arch.ums.conf.entity.FileInfo;
import org.arch.ums.conf.service.FileInfoService;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 对象存储文件信息(FileInfo) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-02-27 16:41:07
 * @since 1.0.0
 */
@Slf4j
@Service
public class BizFileInfoService extends FileInfoService {

    public BizFileInfoService(FileInfoDao fileInfoDao) {
        super(fileInfoDao);
    }

    /**
     * 根据 filePath 与 uploadType 删除 文件信息
     * @param filePath      文件路径
     * @param uploadType    上传类型
     * @return  删除的文件信息
     */
    @Nullable
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public FileInfo deleteByFilePathAndUploadType(@NonNull Integer tenantId, @NonNull String filePath,
                                                @NonNull String uploadType) {
        LambdaQueryWrapper<FileInfo> queryWrapper =
                Wrappers.<FileInfo>lambdaQuery()
                        .eq(FileInfo::getTenantId, tenantId)
                        .eq(FileInfo::getUploadType, uploadType)
                        .eq(FileInfo::getFilePath, filePath);
        FileInfo fileInfo = fileInfoDao.getOne(queryWrapper);
        deleteById(fileInfo.getId());
        fileInfo.setDeleted(Boolean.TRUE);
        return fileInfo;
    }
}
