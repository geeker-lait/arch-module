package org.arch.ums.conf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 对象存储文件信息(FileInfo) 实体类
 *
 * @author YongWu zheng
 * @date 2021-03-17 21:27:00
 * @since 1.0.0
 */
@SuppressWarnings("jol")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName("conf_file_info")
public class FileInfo extends CrudEntity<FileInfo> {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "`id`")
    private Long id;

    /**
     * 账号ID/用户ID/会员ID/商户ID
     */
    @TableField(value = "`aid`")
    private Long aid;

    /**
     * 存储类型: aws/aliyun/minio/tencent/qiniu/local/nginx
     */
    @TableField(value = "`storage_type`")
    private Integer storageType;

    /**
     * 原始文件名称
     */
    @TableField(value = "`original_file_name`")
    private String originalFileName;

    /**
     * 文件大小
     */
    @TableField(value = "`size`")
    private Long size;

    /**
     * 文件后缀
     */
    @TableField(value = "`suffix`")
    private String suffix;

    /**
     * 图片宽度
     */
    @TableField(value = "`width`")
    private Integer width;

    /**
     * 图片高度
     */
    @TableField(value = "`height`")
    private Integer height;

    /**
     * 文件路径(不带域名)
     */
    @TableField(value = "`file_path`")
    private String filePath;

    /**
     * 文件全路径(带域名)
     */
    @TableField(value = "`full_file_path`")
    private String fullFilePath;

    /**
     * 文件hash
     */
    @TableField(value = "`file_hash`")
    private String fileHash;

    /**
     * 上传文件类型
     */
    @TableField(value = "`upload_type`")
    private String uploadType;

    /**
     * 文件上传开始的时间
     */
    @TableField(value = "`upload_start_time`")
    private LocalDateTime uploadStartTime;

    /**
     * 文件上传结束的时间
     */
    @TableField(value = "`upload_end_time`")
    private LocalDateTime uploadEndTime;

    /**
     * 租户 id
     */
    @TableField(value = "`tenant_id`")
    private Integer tenantId;

    /**
     * 应用 id
     */
    @TableField(value = "`app_id`")
    private Integer appId;

    /**
     * 店铺 id
     */
    @TableField(value = "`store_id`")
    private Integer storeId;

    /**
     * 乐观锁, 默认: 0
     */
    @TableField(value = "`rev`")
    private Integer rev;

    /**
     * 时间戳/创建时间
     */
    @TableField(value = "`dt`")
    private LocalDateTime dt;

    /**
     * 是否逻辑删除: 0 未删除(false), 1 已删除(true); 默认: 0
     */
    @TableField(value = "`deleted`")
    private Boolean deleted;


    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return id;
    }
}
