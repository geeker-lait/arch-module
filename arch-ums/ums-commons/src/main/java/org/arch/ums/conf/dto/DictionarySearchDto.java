package org.arch.ums.conf.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 数据字典(Dictionary) search dto
 *
 * @author YongWu zheng
 * @date 2021-04-17 15:29:48
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class DictionarySearchDto implements BaseSearchDto {

    /**
     * id
     */
    private Long id;

    /**
     * 业务码
     */
    private String code;

    /**
     * 标题
     */
    private String title;

    /**
     * 备注
     */
    private String mark;

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

    @Override
    public void buildSearchParams(Map<String, Object> map) {
        putNoNull("EQ_id", this.getId(), map);
        putNoNull("EQ_tenant_id", this.getTenantId(), map);
        putNoNull("EQ_code", this.getCode(), map);
        putNoNull("EQ_title", this.getTitle(), map);
        putNoNull("EQ_mark", this.getMark(), map);
        putNoNull("EQ_app_id", this.getAppId(), map);
        putNoNull("EQ_store_id", this.getStoreId(), map);
        putNoNull("EQ_deleted", this.getDeleted(), map);
        putNoNull("EQ_rev", this.getRev(), map);
        putNoNull("EQ_dt", this.getDt(), map);
    }
}