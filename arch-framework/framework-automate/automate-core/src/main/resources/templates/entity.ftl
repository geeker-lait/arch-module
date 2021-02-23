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

/**
* @description ${comment!""}
*
* @author ${author!""}
* @date ${.now}
*/
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Accessors(chain = true)
@TableName
public class ${(name?cap_first)!""}${stuffix!""} <#if extendClass??> extends CrudEntity<FormBiz></#if> {
<#if colums?? && (colums?size >0)>
    <#list colums as column >
    private ${column.typ!""} ${column.name!""}
    </#list>
</#if>
}
