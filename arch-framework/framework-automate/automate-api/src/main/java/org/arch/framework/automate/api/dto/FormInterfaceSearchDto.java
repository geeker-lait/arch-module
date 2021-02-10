package org.arch.framework.automate.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 表单接口(FormInterface) search dto
 *
 * @author lait
 * @date 2021-02-10 15:55:47
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FormInterfaceSearchDto implements BaseSearchDto {

    /**
     * id主键
     */
    private Long id;

    /**
     * 接口名称
     */
    private String formCategory;

    /**
     * 接口
     */
    private String interfaceName;

    /**
     * 接口参数
     */
    private String interfaceCode;

    /**
     * 接口uri
     */
    private String interfaceUri;

    /**
     * json 参数
     */
    private String paramJson;

    /**
     * 接口描述
     */
    private String descr;

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
        putNoNull("EQ_form_category", this.getFormCategory(), map);
        putNoNull("EQ_interface_name", this.getInterfaceName(), map);
        putNoNull("EQ_interface_code", this.getInterfaceCode(), map);
        putNoNull("EQ_interface_uri", this.getInterfaceUri(), map);
        putNoNull("EQ_param_json", this.getParamJson(), map);
        putNoNull("EQ_descr", this.getDescr(), map);
        putNoNull("EQ_deleted", this.getDeleted(), map);
        putNoNull("EQ_dt", this.getDt(), map);
    }
}
