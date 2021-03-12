package ${package!""};

import org.arch.framework.crud.CrudMapper;
import org.apache.ibatis.annotations.Mapper;
// TODO 添加对 entity/dao/dto/service/controler 等属性的前缀属性
import ${basePkg!""}.entity.${(name?cap_first)!""};

/**
 * ${comment!""}(${(name?cap_first)!""}) 表数据库 Mapper 层
 *
 * @author ${author!""}
 * @date ${.now}
 * @since  1.0.0
 */
@Mapper
public interface ${(name?cap_first)!""}${suffix!""} extends CrudMapper<${(name?cap_first)!""}> {

}