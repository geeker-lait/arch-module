package ${package!""};

import org.arch.framework.crud.CrudMapper;
import org.apache.ibatis.annotations.Mapper;
import ${basePkg!""}.${domain!""}.entity.${(name?cap_first)!""}<#list documents as doc><#if doc.type == "entity">${(doc.suffix)?cap_first}</#if></#list>;

/**
 * ${comment!""}(${(name?cap_first)!""}) 表数据库 Mapper 层
 *
 * @author ${author!""}
 * @date ${.now}
 * @since  1.0.0
 */
@Mapper
public interface ${(name?cap_first)!""}${suffix!""} extends CrudMapper<${(name?cap_first)!""}<#list documents as doc><#if doc.type == "entity">${(doc.suffix)?cap_first}</#if></#list>> {

}