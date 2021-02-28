package ${packageName};

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
<#list imports as import>
import ${import};
</#list>

/**
 *  ${entity.className} Dto generated by auto
 * ${comments}
 *
 * @author ${author}
 * @since ${date}.
 */
public interface ${name!""} {


}
