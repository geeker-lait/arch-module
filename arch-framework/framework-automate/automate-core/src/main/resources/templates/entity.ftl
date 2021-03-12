package ${package!""};

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.arch.framework.crud.CrudEntity;

import java.io.Serializable;
import java.time.LocalDateTime;
<#if _pkType??>
import ${_pkType}
</#if>

/**
 * ${comment!""}(${(name?cap_first)!""})  实体类
 *
 * @author ${author!""}
 * @date ${.now}
 * @since  1.0.0
 */
@Data
<#if extendClass??>
@EqualsAndHashCode(callSuper = true)
</#if>
@NoArgsConstructor
@Accessors(chain = true)
// TODO 增加对表名(xx_xx)转换为驼峰类型
@TableName("${(name)!""}")
public class ${(name?cap_first)!""}${suffix!""} extends CrudEntity<${(name?cap_first)!""}${suffix!""}>{
    private static final long serialVersionUID = 1L;
<#if columns?? && (columns?size >0)>
    <#list columns as column >
    /**
     * ${column.comment!""}
     */
    <#if pk?? && pk == cloumn.name!"">
    // TODO 增加对主键类型(type)的判断 ,
    @TableId("${column.name!""}")
    </#if>
    // TODO 增加对列(xx_xx)转换为驼峰类型字段, 不能获取数据库类型对应的 JavaType
    private ${column.typ!""} ${column.name!""};
    </#list>
</#if>
}
