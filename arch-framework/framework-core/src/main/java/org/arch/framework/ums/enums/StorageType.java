package org.arch.framework.ums.enums;

/**
 * 对象存储类型
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.2.27 15:55
 */
public enum StorageType {
    /**
     * aws-s3
     */
    AWS,
    /**
     * 阿里云-oss
     */
    ALIYUN,
    /**
     * 腾讯云-cos
     */
    TENCENT,
    /**
     * 七牛-kodo
     */
    QINIU,
    /**
     * minio
     */
    MINIO,
    /**
     * 本地
     */
    LOCAL,
    /**
     * nginx 服务器
     */
    NGINX
}
