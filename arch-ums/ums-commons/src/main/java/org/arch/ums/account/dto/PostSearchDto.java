package org.arch.ums.account.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 账号-岗位(Post) search dto
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:19:17
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PostSearchDto implements BaseSearchDto {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 父id
     */
    private Long postPid;

    /**
     * 岗位名
     */
    private String postName;

    /**
     * 岗位code
     */
    private String postCode;

    /**
     * icon
     */
    private String postIcon;

    /**
     * 薪资
     */
    private BigDecimal salary;

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
        putNoNull("EQ_post_pid", this.getPostPid(), map);
        putNoNull("EQ_deleted", this.getDeleted(), map);
        putNoNull("EQ_app_id", this.getAppId(), map);
        putNoNull("EQ_store_id", this.getStoreId(), map);
        putNoNull("EQ_post_name", this.getPostName(), map);
        putNoNull("EQ_post_code", this.getPostCode(), map);
        putNoNull("EQ_post_icon", this.getPostIcon(), map);
        putNoNull("EQ_salary", this.getSalary(), map);
        putNoNull("EQ_rev", this.getRev(), map);
        putNoNull("EQ_dt", this.getDt(), map);
    }
}
