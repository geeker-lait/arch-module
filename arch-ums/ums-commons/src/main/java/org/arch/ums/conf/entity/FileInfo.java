package org.arch.ums.conf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;
import org.arch.framework.ums.enums.StorageType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 对象存储文件信息(FileInfo) 实体类
 *
 * @author YongWu zheng
 * @date 2021-02-27 16:41:06
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
    @TableId(value = "id", type = IdType.AUTO)
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
    private Integer appId;

    /**
     * 店铺 id
     */
    private Integer storeId;

    /**
     * 乐观锁, 默认: 0
     */
    private Integer rev;

    /**
     * 时间戳/创建时间
     */
    private LocalDateTime dt;

    /**
     * 是否逻辑删除: 0 未删除(false), 1 已删除(true); 默认: 0
     */
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
