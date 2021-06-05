package org.arch.auth.sso.file;

import org.arch.auth.sso.exception.GlobalFileException;
import org.arch.auth.sso.file.image.ImageClient;
import org.arch.auth.sso.properties.FileProperties;
import org.arch.ums.conf.client.ConfFileInfoApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.io.File;
import java.io.InputStream;

/**
 * 图片的文件上传类
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.2.27 14:45
 */
@Component
public class ImageFileUploader extends BaseImageFileUploader implements FileUploader {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public ImageFileUploader(ConfFileInfoApi confFileInfoApi,
                             TenantContextHolder tenantContextHolder,
                             @Autowired(required = false) ImageClient imageClient,
                             FileProperties fileProperties) {
        super(confFileInfoApi, tenantContextHolder, imageClient, fileProperties);
    }

    @Override
    @NonNull
    public FileInfoDto upload(@NonNull InputStream file, @NonNull String uploadType, @NonNull String imageUrl, boolean save) {
        return this.saveFile(file, uploadType, imageUrl, save);
    }

    @Override
    @NonNull
    public FileInfoDto upload(@NonNull File file, @NonNull String uploadType, boolean save) {
        return this.saveFile(file, uploadType, save);
    }

    @Override
    @NonNull
    public FileInfoDto upload(@NonNull MultipartFile file, @NonNull String uploadType, boolean save) {
        return this.saveFile(file, uploadType, save);
    }

    @Override
    public boolean delete(@NonNull String filePath, @NonNull String uploadType) {
        if (!StringUtils.hasText(filePath)) {
            throw new GlobalFileException("[文件服务]文件删除失败，文件为空！");
        }
        return this.removeFile(filePath, uploadType);
    }
}
