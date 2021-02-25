package ${pkg!""};

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
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
public class ${(name?cap_first)!""}${suffix!""} extends CrudService<${(name?cap_first)!""}, java.lang.Long>{
    private final ${(name?cap_first)!""}Dao ${name!""}Dao = (${(name?cap_first)!""}Dao) crudDao;
}
