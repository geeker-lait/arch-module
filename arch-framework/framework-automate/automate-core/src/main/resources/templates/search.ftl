package ${package!""};

import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.api.crud.BaseSearchDto;
import java.time.LocalDateTime;

/**
 * ${comment!""}(${(name?cap_first)!""}) search dto
 *
 * @author ${author!""}
 * @date ${.now}
 * @since  1.0.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class ${(name?cap_first)!""}${suffix!""} extends BaseSearchDto{


    <#if columns?? && (columns?size > 0)>
    <#list columns as column>
    /**
     * ${column.comment}
     */
    <#if column?? && ((column == "appId") || (column == "storeId") || (column == "rev"))>
    @JsonIgnore
    </#if>
    // TODO 增加对列(xx_xx)转换为驼峰类型字段, 不能获取数据库类型对应的 JavaType
    private ${column.type} ${column.name!""};

    </#list>
    </#if>
    /**
     * 实现父类的查询条件过滤
     *
     */
    @Override
    public void buildSearchParams(Map<String, Object> map) {
        <#if columns?? && (columns?size > 0)>
            <#list columns as column>
        super.putNoNull("EQ_${column.name!""}", this.${column.name!""}, map);
            </#list>
        </#if>
    }

}