package org.arch.auth.sso.file;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 文件信息
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.2.27 11:37
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FileInfoDto implements Serializable {
    private static final long serialVersionUID = -6503120743364498088L;
    /**
     * 文件大小
     */
    public Long size;
    /**
     * 文件后缀（Suffix）
     */
    public String suffix;
    /**
     * 图片文件的宽
     */
    public Integer width;
    /**
     * 图片文件的高
     */
    public Integer height;
    /**
     * 文件hash
     */
    private String fileHash;
    /**
     * 文件路径 （不带域名）
     */
    private String filePath;
    /**
     * 文件全路径 （带域名）
     */
    private String fullFilePath;
    /**
     * 原始文件名
     */
    private String originalFileName;
    /**
     * 文件上传开始的时间
     */
    private LocalDateTime uploadStartTime;
    /**
     * 文件上传结束的时间
     */
    private LocalDateTime uploadEndTime;

    public FileInfoDto setFileHash(String fileHash) {
        this.fileHash = fileHash;
        return this;
    }

    public FileInfoDto setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public FileInfoDto setFullFilePath(String fullFilePath) {
        this.fullFilePath = fullFilePath;
        return this;
    }

    public FileInfoDto setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
        return this;
    }

    public FileInfoDto setUploadStartTime(LocalDateTime uploadStartTime) {
        this.uploadStartTime = uploadStartTime;
        return this;
    }

    public FileInfoDto setUploadEndTime(LocalDateTime uploadEndTime) {
        this.uploadEndTime = uploadEndTime;
        return this;
    }

    public long getUseTime() {
        LocalDateTime uploadEndTime = this.getUploadEndTime();
        LocalDateTime uploadStartTime = this.getUploadStartTime();
        if (null == uploadStartTime || null == uploadEndTime) {
            return -1;
        }
        return uploadEndTime.toEpochSecond(ZoneOffset.UTC) - uploadStartTime.toEpochSecond(ZoneOffset.UTC);
    }

    public FileInfoDto setSize(long size) {
        this.size = size;
        return this;
    }

    public FileInfoDto setSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public FileInfoDto setWidth(int width) {
        this.width = width;
        return this;
    }

    public FileInfoDto setHeight(int height) {
        this.height = height;
        return this;
    }
}
