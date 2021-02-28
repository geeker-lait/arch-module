package org.arch.auth.sso.file;

import cn.hutool.core.bean.BeanUtil;
import org.arch.auth.sso.file.image.ImageClient;
import org.arch.auth.sso.file.image.ImageClientAdapter;
import org.arch.auth.sso.properties.FileProperties;
import org.arch.framework.beans.Response;
import org.arch.framework.beans.exception.AuthenticationException;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.conf.entity.FileInfo;
import org.arch.ums.feign.account.conf.UmsConfFileInfoFeignService;
import org.springframework.lang.NonNull;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

/**
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.2.27 17:01
 */
public abstract class BaseImageFileUploader implements FileUploader {
    protected final UmsConfFileInfoFeignService umsConfFileInfoFeignService;
    protected final TenantContextHolder tenantContextHolder;
    protected final ImageClient imageClient;

    protected BaseImageFileUploader(UmsConfFileInfoFeignService umsConfFileInfoFeignService,
                                    TenantContextHolder tenantContextHolder,
                                    ImageClient imageClient,
                                    FileProperties fileProperties) {
        this.umsConfFileInfoFeignService = umsConfFileInfoFeignService;
        this.tenantContextHolder = tenantContextHolder;
        this.imageClient = new ImageClientAdapter(fileProperties, imageClient);
    }

    /**
     * 保持云存储信息到数据库
     * @param fileInfoDto   云存储信息
     * @param uploadType    上传类型
     * @param save          是否保存
     * @return  返回 fileInfoDto
     */
    @NonNull
    protected FileInfoDto saveFile(@NonNull FileInfoDto fileInfoDto, @NonNull String uploadType, boolean save) {
        if (save) {
            FileInfo fileInfo = new FileInfo();
            BeanUtil.copyProperties(fileInfoDto, fileInfo);
            try {
                TokenInfo currentUser = SecurityUtils.getCurrentUser();
                fileInfo.setAid(currentUser.getAccountId());
            }
            catch (AuthenticationException e) {
                // 未登录用户, 不做任何处理
            }
            fileInfo.setUploadType(uploadType);
            fileInfo.setStorageType(this.imageClient.getStorageType());
            fileInfo.setTenantId(Integer.valueOf(this.tenantContextHolder.getTenantId()));
            fileInfo.setDeleted(Boolean.FALSE);
            this.umsConfFileInfoFeignService.save(fileInfo);
        }
        return fileInfoDto;
    }

    /**
     * 删除云存储文件与相关的数据库记录
     * @param filePath      文件路径
     * @param uploadType    上传类型
     * @return  返回 true 表示删除成功
     */
    protected boolean removeFile(@NonNull String filePath, @NonNull String uploadType) {
        Response<FileInfo> response = this.umsConfFileInfoFeignService.deleteByPathAndUploadType(filePath, uploadType);
        FileInfo fileInfo = response.getSuccessData();

        String fileKey = filePath;
        if (null != fileInfo) {
            fileKey = fileInfo.getFilePath();
        }
        return this.imageClient.removeFile(fileKey);
    }
}
