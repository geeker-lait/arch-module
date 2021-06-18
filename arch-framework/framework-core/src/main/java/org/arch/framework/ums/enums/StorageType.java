package org.arch.framework.ums.enums;

/**
 * 对象存储类型
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.2.27 15:55
 */
public enum StorageType implements DictionaryItemInfo {
    /**
     * 阿里云-oss
     */
    ALIYUN("阿里云-oss", "文件存储在阿里云-oss"),
    /**
     * 腾讯云-cos
     */
    TENCENT("腾讯云-cos", "文件存储在腾讯云-cos"),
    /**
     * 七牛-kodo
     */
    QINIU("七牛-kodo", "文件存储在七牛-kodo"),
    /**
     * aws-s3
     */
    AWS("aws-s3", "文件存储在aws-s3"),
    /**
     * minio
     */
    MINIO("minio", "文件存储在 minio"),
    /**
     * 本地
     */
    LOCAL("本地文件存储", "文件存储在本地文件系统"),
    /**
     * nginx 服务器
     */
    NGINX("nginx 服务器", "文件存储在 nginx 服务器");

    /**
     * title
     */
    private final String title;
    /**
     * 备注
     */
    private final String mark;

    StorageType(String title, String mark) {
        this.title = title;
        this.mark = mark;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getVal() {
        return this.name().toLowerCase();
    }

    @Override
    public int getSeq() {
        return this.ordinal();
    }

    @Override
    public String getMark() {
        return mark;
    }
}
