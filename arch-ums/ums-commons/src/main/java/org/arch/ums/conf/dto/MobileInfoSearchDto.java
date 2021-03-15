package org.arch.ums.conf.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 手机号归属地信息(MobileInfo) search dto
 *
 * @author YongWu zheng
 * @date 2021-03-15 21:47:56
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MobileInfoSearchDto implements BaseSearchDto {

    /**
     * 手机号归属地信息id
     */
    private Long id;

    /**
     * 手机前缀(7)
     */
    private Integer prefix;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 运营商
     */
    private String mno;

    /**
     * 是否虚拟号段: 1 是, 0 否, 默认: 0
     */
    private Boolean virtual;

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
        // TODO 需要根据实际业务对条件进行增减(对应的字段也需要增减), 包括条件的顺序问题, 需要对应相应的多索引顺序, 使索引生效.
        putNoNull("EQ_id", this.getId(), map);
        putNoNull("EQ_prefix", this.getPrefix(), map);
        putNoNull("EQ_province", this.getProvince(), map);
        putNoNull("EQ_city", this.getCity(), map);
        putNoNull("EQ_mno", this.getMno(), map);
        putNoNull("EQ_virtual", this.getVirtual(), map);
        putNoNull("EQ_rev", this.getRev(), map);
        putNoNull("EQ_dt", this.getDt(), map);
        putNoNull("EQ_deleted", this.getDeleted(), map);
    }
}
