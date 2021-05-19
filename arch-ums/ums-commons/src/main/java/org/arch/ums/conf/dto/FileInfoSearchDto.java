package org.arch.ums.conf.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;
import org.arch.framework.ums.enums.StorageType;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 对象存储文件信息(FileInfo) search dto
 *
 * @author YongWu zheng
 * @date 2021-02-27 16:41:10
 * @since 1.0.0
 */
@SuppressWarnings("jol")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FileInfoSearchDto implements BaseSearchDto {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;

    /**
     * 账号ID/用户ID/会员ID/商户ID
     */
    private Long aid;

    /**
     * 存储类型: aws/aliyun/minio/tencent/qiniu/local/nginx
     */
    private StorageType storageType;

    /**
     * 原始文件名称
     */
    private String originalFileName;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 文件后缀
     */
    private String suffix;

    /**
     * 图片宽度
     */
    private Integer width;

    /**
     * 图片高度
     */
    private Integer height;

    /**
     * 文件路径(不带域名)
     */
    private String filePath;

    /**
     * 文件全路径(带域名)
     */
    private String fullFilePath;

    /**
     * 文件hash
     */
    private String fileHash;

    /**
     * 上次的类型
     */
    private String uploadType;

    /**
     * 文件上传开始的时间
     */
    private LocalDateTime uploadStartTime;

    /**
     * 文件上传结束的时间
     */
    private LocalDateTime uploadEndTime;

    /**
     * 租户 id
     */
    private Integer tenantId;

    /**
     * 应用 id
     */
    @JsonIgnore
    private Integer appId;

    /**
     * 店铺 id
     */
    @JsonIgnore
    private Integer storeId;

    /**
     * 乐观锁, 默认: 0
     */
    @JsonIgnore
    private Integer rev;

    /**
     * 时间戳/创建时间
     */
    private LocalDateTime dt;

    /**
     * 是否逻辑删除: 0 未删除(false), 1 已删除(true); 默认: 0
     */
    private Boolean deleted;

    @Override
    public void buildSearchParams(Map<String, Object> map) {
        putNoNull("EQ_id", this.getId(), map);
        putNoNull("EQ_tenant_id", this.getTenantId(), map);
        putNoNull("EQ_aid", this.getAid(), map);
        putNoNull("EQ_deleted", this.getDeleted(), map);
        putNoNull("EQ_app_id", this.getAppId(), map);
        putNoNull("EQ_store_id", this.getStoreId(), map);
        putNoNull("EQ_storage_type", this.getStorageType(), map);
        putNoNull("EQ_original_file_name", this.getOriginalFileName(), map);
        putNoNull("EQ_size", this.getSize(), map);
        putNoNull("EQ_suffix", this.getSuffix(), map);
        putNoNull("EQ_width", this.getWidth(), map);
        putNoNull("EQ_height", this.getHeight(), map);
        putNoNull("EQ_file_path", this.getFilePath(), map);
        putNoNull("EQ_full_file_path", this.getFullFilePath(), map);
        putNoNull("EQ_file_hash", this.getFileHash(), map);
        putNoNull("EQ_upload_type", this.getUploadType(), map);
        putNoNull("EQ_upload_start_time", this.getUploadStartTime(), map);
        putNoNull("EQ_upload_end_time", this.getUploadEndTime(), map);
        putNoNull("EQ_rev", this.getRev(), map);
        putNoNull("EQ_dt", this.getDt(), map);
    }
}
