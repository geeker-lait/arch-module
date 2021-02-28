package org.arch.auth.sso.file.image;

import cn.hutool.core.date.DateUtil;
import org.arch.auth.sso.exception.GlobalFileException;
import org.arch.auth.sso.file.FileInfoDto;
import org.arch.auth.sso.utils.FileUtil;
import org.arch.framework.ums.enums.StorageType;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import top.dcenter.ums.security.common.utils.UuidUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import static java.util.Objects.isNull;

/**
 * 通用图片操作客户端
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.2.27 11:51
 */
public abstract class BaseImageClient implements ImageClient {
    protected StorageType storageType;
    protected String newFileName;
    protected String suffix;

    public BaseImageClient(StorageType storageType) {
        this.storageType = storageType;
    }

    @Override
    @NonNull
    public FileInfoDto uploadImg(@NonNull MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            if (!StringUtils.hasText(originalFilename)) {
                throw new GlobalFileException("[" + this.storageType.name() + "]文件上传失败：获取不到原始的文件名称");
            }
            return this.uploadImg(file.getInputStream(), originalFilename);
        } catch (IOException e) {
            throw new GlobalFileException("[" + this.storageType.name() + "]文件上传失败：" + e.getMessage());
        }
    }

    @Override
    @NonNull
    public FileInfoDto uploadImg(@NonNull File file) {
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(file));
            return this.uploadImg(is, file.getName());
        } catch (FileNotFoundException e) {
            throw new GlobalFileException("[" + this.storageType.name() + "]文件上传失败：" + e.getMessage());
        }
    }

    /**
     * 根据 fileName 与 pathPrefix 创建新的文件名
     * @param fileName      临时文件名
     * @param pathPrefix    路径前缀
     */
    protected void createNewFileName(@NonNull String fileName, @Nullable String pathPrefix) {
        if (isNull(pathPrefix)) {
            pathPrefix = "";
        }
        this.suffix = FileUtil.getSuffix(fileName);
        if (!FileUtil.isPicture(this.suffix)) {
            throw new GlobalFileException("[" + this.storageType.name() + "] 非法的图片文件[" + fileName + "]！目前只支持以下图片格式：[jpg, jpeg, png, gif, bmp]");
        }
        String newFileName = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
        String uuid = UuidUtils.getUUID().substring(0, 6);
        this.newFileName = pathPrefix + (newFileName  + uuid + this.suffix);
    }

    /**
     * 检查配置参数是否已配置
     */
    protected abstract void check();

    @Override
    public StorageType getStorageType() {
        return this.storageType;
    }
}
