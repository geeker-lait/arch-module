package ${package!""};

import org.arch.framework.crud.CrudMapper;
import org.apache.ibatis.annotations.Mapper;
/**
* @description ${comment!""}
*
* @author ${author!""}
* @date ${.now}
*/
@Mapper
public interface ${(name?cap_first)!""}${suffix!""} extends CrudMapper<${(name?cap_first)!""}<#list documents as doc><#if doc.type == "entity">${(doc.type)?cap_first}</#if></#list>> {

}
