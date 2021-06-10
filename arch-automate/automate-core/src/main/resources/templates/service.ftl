package ${package!""};

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import ${basePkg!""}.${domain!""}.entity.${(name?cap_first)!""}<#list documents as doc><#if doc.type == "entity">${(doc.suffix)?cap_first}</#if></#list>;
import org.springframework.stereotype.Service;

/**
* @description 项目业务(${(name?cap_first)!""}) 表服务层
*
* @author ${author!""}
* @date ${.now}
*/
@Slf4j
@RequiredArgsConstructor
@Service
public class ${(name?cap_first)!""}${suffix!""} extends CrudService<${(name?cap_first)!""}<#list documents as doc><#if doc.type == "entity">${(doc.suffix)?cap_first}</#if></#list>, Long> <#list interfaces as ints><#if ints.name?contains(name?cap_first)>implements ${(ints.name?cap_first)!""}Api</#if></#list>{
    private final ${(name?cap_first)!""}Dao ${(name?uncap_first)!""}Dao = (${(name?cap_first)!""}Dao) crudDao;
}
