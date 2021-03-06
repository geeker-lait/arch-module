package org.arch.auth.sso.file;

import cn.hutool.core.bean.BeanUtil;
import feign.FeignException;
import org.arch.auth.sso.file.image.ImageClient;
import org.arch.auth.sso.file.image.ImageClientAdapter;
import org.arch.auth.sso.properties.FileProperties;
import org.arch.framework.beans.Response;
import org.arch.framework.beans.exception.AuthenticationException;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.arch.ums.conf.entity.FileInfo;
import org.arch.ums.feign.account.conf.UmsConfFileInfoFeignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;
import top.dcenter.ums.security.core.api.tenant.handler.TenantContextHolder;

import java.io.File;
import java.io.InputStream;

import static java.util.Objects.isNull;
import static org.arch.auth.sso.utils.RegisterUtils.getTraceId;
import static org.arch.framework.utils.RetryUtils.publishRetryEvent;

/**
 * 通用图片上传器.
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.2.27 17:01
 */
public abstract class BaseImageFileUploader implements FileUploader, ApplicationContextAware {

    public static final Logger log = LoggerFactory.getLogger(BaseImageFileUploader.class);

    protected final UmsConfFileInfoFeignService umsConfFileInfoFeignService;
    protected final TenantContextHolder tenantContextHolder;
    protected final ImageClient imageClient;
    protected ApplicationContext applicationContext;

    protected BaseImageFileUploader(UmsConfFileInfoFeignService umsConfFileInfoFeignService,
                                    TenantContextHolder tenantContextHolder,
                                    ImageClient imageClient,
                                    FileProperties fileProperties) {
        this.umsConfFileInfoFeignService = umsConfFileInfoFeignService;
        this.tenantContextHolder = tenantContextHolder;
        this.imageClient = new ImageClientAdapter(fileProperties, imageClient);
    }

    /**
     * 保持对象存储信息到文件存储与数据库
     * @param is            {@link InputStream}
     * @param uploadType    上传类型
     * @param save          是否保存
     * @return  返回修改后的 fileInfoDto
     */
    @NonNull
    protected FileInfoDto saveFile(@NonNull InputStream is, @NonNull String uploadType,
                                   @NonNull String imageUrl, boolean save) {
        FileInfoDto fileInfoDto = this.imageClient.uploadImg(is, imageUrl);
        return saveFileInfo(fileInfoDto, uploadType, save);
    }
    /**
     * 保持对象存储信息到文件存储与数据库
     * @param file          {@link File}
     * @param uploadType    上传类型
     * @param save          是否保存
     * @return  返回修改后的 fileInfoDto
     */
    @NonNull
    protected FileInfoDto saveFile(@NonNull File file, @NonNull String uploadType, boolean save) {
        FileInfoDto fileInfoDto = this.imageClient.uploadImg(file);
        return saveFileInfo(fileInfoDto, uploadType, save);
    }
    /**
     * 保持对象存储信息到文件存储与数据库
     * @param file          {@link MultipartFile}
     * @param uploadType    上传类型
     * @param save          是否保存
     * @return  返回修改后的 fileInfoDto
     */
    @NonNull
    protected FileInfoDto saveFile(@NonNull MultipartFile file, @NonNull String uploadType, boolean save) {
        FileInfoDto fileInfoDto = this.imageClient.uploadImg(file);
        return saveFileInfo(fileInfoDto, uploadType, save);
    }

    /**
     * 保持对象存储信息到数据库
     * @param fileInfoDto   云存储信息
     * @param uploadType    上传类型
     * @param save          是否保存
     * @return  返回修改后的 fileInfoDto
     */
    @NonNull
    private FileInfoDto saveFileInfo(@NonNull FileInfoDto fileInfoDto, @NonNull String uploadType, boolean save) {
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
            FileInfo successData;
            try {
                Response<FileInfo> response = this.umsConfFileInfoFeignService.save(fileInfo);
                successData = response.getSuccessData();
                if (isNull(successData)) {
                    publishRetryEvent(this.applicationContext, getTraceId(),
                                      this.umsConfFileInfoFeignService,
                                      UmsConfFileInfoFeignService.class,
                                      "save",
                                      new Class[] {FileInfo.class},
                                      fileInfo);
                    successData = fileInfo;
                }

            }
            catch (FeignException e) {
                log.error(e.getMessage(), e);
                publishRetryEvent(this.applicationContext, getTraceId(),
                                  this.umsConfFileInfoFeignService,
                                  UmsConfFileInfoFeignService.class,
                                  "save",
                                  new Class[] {FileInfo.class},
                                  fileInfo);
                successData = fileInfo;
            }
            BeanUtil.copyProperties(successData, fileInfoDto);
        }
        return fileInfoDto;
    }

    /**
     * 删除对象存储文件与相关的数据库记录
     * @param filePath      文件路径
     * @param uploadType    上传类型
     * @return  返回 true 表示删除成功
     */
    protected boolean removeFile(@NonNull String filePath, @NonNull String uploadType) {
        boolean removeFile = this.imageClient.removeFile(filePath);
        if (removeFile) {
            FileInfo successData;
            try {
                // 这里不关心对象存储信息是否删除成功, 因为不影响业务逻辑, 对象存储信息的一致性可通过定时任务进行补偿
                Response<FileInfo> response = this.umsConfFileInfoFeignService.deleteByFilePathAndUploadType(filePath,
                                                                                                             uploadType);
                successData = response.getSuccessData();
                if (isNull(successData)) {
                    publishRetryEvent(this.applicationContext, getTraceId(),
                                      this.umsConfFileInfoFeignService,
                                      UmsConfFileInfoFeignService.class,
                                      "deleteByFilePathAndUploadType",
                                      new Class[] {String.class, String.class},
                                      filePath, uploadType);
                }
            }
            catch (FeignException e) {
                log.error(e.getMessage(), e);
                publishRetryEvent(this.applicationContext, getTraceId(),
                                  this.umsConfFileInfoFeignService,
                                  UmsConfFileInfoFeignService.class,
                                  "deleteByFilePathAndUploadType",
                                  new Class[] {String.class, String.class},
                                  filePath, uploadType);
            }
        }
        return removeFile;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
