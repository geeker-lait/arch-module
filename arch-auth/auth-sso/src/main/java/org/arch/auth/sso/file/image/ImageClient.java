package org.arch.auth.sso.file.image;

import org.arch.auth.sso.file.FileInfoDto;
import org.arch.framework.ums.enums.StorageType;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

/**
 * 图片操作客户端
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.2.27 11:35
 */
public interface ImageClient {
    /**
     * 上传文件
     * @param file  {@link MultipartFile}
     * @return  上传成功的文件信息
     */
    @NonNull
    FileInfoDto uploadImg(@NonNull MultipartFile file);

    /**
     * 上传文件
     * @param file  文件
     * @return  上传成功的文件信息
     */
    @NonNull
    FileInfoDto uploadImg(@NonNull File file);

    /**
     * 上次文件
     * @param is        文件流
     * @param pathOrUrl 保持的文件路径 或 url
     * @return  上传成功的文件信息
     */
    @NonNull
    FileInfoDto uploadImg(@NonNull InputStream is, @NonNull String pathOrUrl);

    /**
     * 移除文件
     * @param pathOrUrl 文件路径 或 url
     * @return  是否删除成功
     */
    boolean removeFile(@NonNull String pathOrUrl);

    /**
     * 获取云存储类型
     * @return  {@link StorageType}
     */
    Integer getStorageType();
}
