package ${package!""};

import ${basePkg!""}.${domain!""}.entity.${(name?cap_first)!""}<#list documents as doc><#if doc.type == "entity">${(doc.suffix)?cap_first}</#if></#list>;
import ${basePkg!""}.${domain!""}.mapper.${(name?cap_first)!""}<#list documents as doc><#if doc.type == "mapper">${(doc.suffix)?cap_first}</#if></#list>;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * @description 项目业务(${(name?cap_first)!""}) 表数据库访问层
 *
 * @author ${author!""}
 * @date ${.now}
 * @since  1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class ${(name?cap_first)!""}${suffix!""} extends CrudServiceImpl<${(name?cap_first)!""}Mapper, ${(name?cap_first)!""}Entity> implements CrudDao<${(name?cap_first)!""}Entity>{
    private final ${(name?cap_first)!""}Mapper ${(name?uncap_first)!""}Mapper;
}