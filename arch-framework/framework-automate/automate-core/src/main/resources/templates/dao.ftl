package ${package!""};

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
*/
@Slf4j
@RequiredArgsConstructor
@Repository
public class ${(name?cap_first)!""}${suffix!""} extends CrudServiceImpl<${(name?cap_first)!""}Mapper, ${(name?cap_first)!""}> implements CrudDao<${(name?cap_first)!""}>{
    private final ${(name?cap_first)!""}Mapper ${name!""}Mapper;
}
