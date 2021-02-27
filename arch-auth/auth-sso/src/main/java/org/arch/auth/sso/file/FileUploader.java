package org.arch.auth.sso.file;

import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

/**
 * 文件上传接口
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.2.27 11:35
 */
public interface FileUploader {

    /**
     * 上传文件
     *
     * @param file       待上传的文件流
     * @param uploadType 文件上传类型，用来区分文件
     * @param pathOrUrl  文件路径或 url
     * @param save       是否保存
     * @return 上传后的文件信息
     */
    @NonNull
    FileInfoDto upload(@NonNull InputStream file, @NonNull String uploadType, @NonNull String pathOrUrl, boolean save);

    /**
     * 上传文件
     *
     * @param file       待上传的文件
     * @param uploadType 文件上传类型，用来区分文件
     * @param save       是否保存
     * @return 上传后的文件信息
     */
    @NonNull
    FileInfoDto upload(@NonNull File file, @NonNull String uploadType, boolean save);

    /**
     * 上传文件
     *
     * @param file       待上传的文件
     * @param uploadType 文件上传类型，用来区分文件
     * @param save       是否保存
     * @return 上传后的文件信息
     */
    @NonNull
    FileInfoDto upload(@NonNull MultipartFile file, @NonNull String uploadType, boolean save);

    /**
     * 删除文件
     *
     * @param filePath   文件路径
     * @param uploadType 文件类型
     * @return 返回 true 表示删除成功
     */
    boolean delete(@NonNull String filePath, @NonNull String uploadType);
}