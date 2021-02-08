package ${packageName};

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.arch.framework.api.crud.BaseSearchDto;
import org.arch.framework.crud.CrudService;

import ${entity.packageName}.${entity.className};
import ${lastRenderResponse.dto.packageName}.${lastRenderResponse.dto.className};
import ${lastRenderResponse.response.packageName}.${lastRenderResponse.response.className};
import ${lastRenderResponse.search.packageName}.${lastRenderResponse.search.className};

import lombok.extern.slf4j.Slf4j;

/**
 * service for ${entity.className}
 * ${comments}
 *
 * @author ${author}
 * @since ${date}.
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class ${className} extends BaseService<${entity.className}, ${entity.id.className}> {

    //@Autowired
    //private ${lastRenderResponse.repository.className} ${lastRenderResponse.repository.className?uncap_first};

    /**
     * 创建实体
     *
     * @param ${lastRenderResponse.dto.className?uncap_first} 表单
     * @return 实体对象
     */
    @Transactional(readOnly = false)
    public ${lastRenderResponse.response.className} create(${lastRenderResponse.dto.className} ${lastRenderResponse.dto.className?uncap_first}) {
        ${entity.className} ${entity.className?uncap_first} = new ${entity.className}();
        super.mapper(${entity.className?uncap_first}, ${lastRenderResponse.dto.className?uncap_first});
        ${entity.className?uncap_first} = super.save(${entity.className?uncap_first});
        return super.mapperByClass(${entity.className?uncap_first}, ${lastRenderResponse.response.className}.class);
    }

    /**
     * 删除实体
     * @param id 实体id
     */
    @Override
    @Transactional(readOnly = false)
    public void deleteById(${entity.id.className} id) {
        super.findById(id).ifPresent(t -> {
            t.setIsActive(Boolean.FALSE);
            super.save(t);
        });
    }

    /**
     * 根据主键集合批量删除
     *
     * @param ids
     */
    @Transactional(readOnly = false)
    public void deleteByIds(List<Integer> ids) {
        super.deleteAllById(ids);
    }

    /**
     * 更新实体
     *
     * @param ${lastRenderResponse.dto.className?uncap_first} 表单
     * @param id      实体id
     * @return 实体对象
     */
    @Transactional(readOnly = false)
    public ${lastRenderResponse.response.className} update(${entity.id.className} id, ${lastRenderResponse.dto.className} ${lastRenderResponse.dto.className?uncap_first}) {
        ${entity.className} ${entity.className?uncap_first} = super.findById(id).get();
        super.mapper(${entity.className?uncap_first}, ${lastRenderResponse.dto.className?uncap_first});
        ${entity.className?uncap_first} = super.save(${entity.className?uncap_first});
        return super.mapperByClass(${entity.className?uncap_first}, ${lastRenderResponse.response.className}.class);
    }

    /**
     * 获取一个实体对象
     *
     * @param id 实体id
     * @return 实体对象
     */
    public ${lastRenderResponse.response.className} details(${entity.id.className} id) {
        ${entity.className} ${entity.className?uncap_first} = super.findById(id).get();
        return super.mapperByClass(${entity.className?uncap_first}, ${lastRenderResponse.response.className}.class);
    }

    /**
     * 分页列表
     *
     * @param searchParams 搜索参数
     * @param pageInfo     分页信息
     * @return 分页列表
     */
    public Page<${lastRenderResponse.response.className}> getPageList(Map<String, Object> searchParams, PageInfo pageInfo) {
        BaseSearchDto searchDto = super.conver(searchParams, ${entity.className}SearchDto.class);
        Map<String, Object> searchMap = searchDto.getSearchParams();
        log.debug("${entity.className}的分页搜索的条件是={},排序的字段为={}", searchMap, pageInfo.getSortType());
        Page<${entity.className}> page = super.findPageBySort(searchMap, pageInfo.getNumber(), pageInfo.getSize(), Direction.DESC,
                pageInfo.getSortType().split(","));
        Page<${lastRenderResponse.response.className}> respPage = new PageImpl<>(super.mapperList(page.getContent(), ${lastRenderResponse.response.className}.class),
                page.getPageable(), page.getTotalElements());
        return respPage;
    }

    /**
     * 根据搜索条件搜索所有符合条件的信息列表
     *
     * @param searchParams 搜索参数
     * @return 信息列表
     */
    public List<${lastRenderResponse.response.className}> findByParams(Map<String, Object> searchParams, String sortTypes) {
        BaseSearchDto searchDto = super.conver(searchParams, ${entity.className}SearchDto.class);
        log.debug("${entity.className}的不分页搜索的参数是={}", searchDto);
        List<${entity.className}> list = super.findAllBySort(searchDto.getSearchParams(), Direction.DESC, sortTypes.split(","));
        return super.mapperList(list, ${lastRenderResponse.response.className}.class);
    }

}
