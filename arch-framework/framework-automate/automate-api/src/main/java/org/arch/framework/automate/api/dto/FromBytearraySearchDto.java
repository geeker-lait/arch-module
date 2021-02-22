package org.arch.framework.automate.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 表单数据(FromBytearray) search dto
 *
 * @author lait
 * @date 2021-02-10 15:55:53
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FromBytearraySearchDto implements BaseSearchDto {

    /**
     * id主键
     */
    private Long id;

    /**
     * 表单id
     */
    private Long tableId;

    /**
     * 字段Id
     */
    private Long fieldId;

    /**
     * 名称
     */
    private String name;

    /**
     * 二进制内容
     */
    private String contentByte;

    /**
     * 是否逻辑删除
     */
    private Boolean deleted;

    /**
     * 时间戳
     */
    private LocalDateTime dt;

    @Override
    public void buildSearchParams(Map<String, Object> map) {
        // TODO 需要根据实际业务对条件进行增减(对应的字段也需要增减), 包括条件的顺序问题, 需要对应相应的多索引顺序, 使索引生效.
        putNoNull("EQ_table_id", this.getTableId(), map);
        putNoNull("EQ_field_id", this.getFieldId(), map);
        putNoNull("EQ_name", this.getName(), map);
        putNoNull("EQ_content_byte", this.getContentByte(), map);
        putNoNull("EQ_deleted", this.getDeleted(), map);
        putNoNull("EQ_dt", this.getDt(), map);
    }
}
