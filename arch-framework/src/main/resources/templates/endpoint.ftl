package ${packageName};

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import ${lastRenderResponse.dto.packageName}.${lastRenderResponse.dto.className};
import ${lastRenderResponse.response.packageName}.${lastRenderResponse.response.className};
import ${lastRenderResponse.service.packageName}.${lastRenderResponse.service.className};

/**
 * controller for ${entity.className}
 * ${comments}
 *
 * @author ${author}
 * @since ${date}.
 */
@RestController
@RequestMapping("/${entity.className?uncap_first}")
public class ${className} extends BaseEndpoint {

    @Autowired
    private ${lastRenderResponse.service.className} ${lastRenderResponse.service.className?uncap_first};

    /**
     * 新增
     * 
     * @param ${lastRenderResponse.dto.className?uncap_first} 请求参数
     */
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody ${lastRenderResponse.dto.className} ${lastRenderResponse.dto.className?uncap_first}) {
        ${lastRenderResponse.response.className} ${entity.className?uncap_first}Resp = ${lastRenderResponse.service.className?uncap_first}.create(${lastRenderResponse.dto.className?uncap_first});
        return super.doResource(${entity.className?uncap_first}Resp, this.getClass());
    }

    /**
     * 根据主键删除，支持批量主键删除
     * 
     * @param ids 删除的主键集合
     */
    @DeleteMapping("/{ids}")
    public ResponseDto<?> delete(@PathVariable List<${entity.id.className}> ids) {
        ${lastRenderResponse.service.className?uncap_first}.deleteByIds(ids);
        return ResponseDto.success(null);
    }

    /**
     * 更新
     * 
     * @param ${lastRenderResponse.dto.className?uncap_first} 请求参数
     * @param id 主键id
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable ${entity.id.className} id,@RequestBody ${lastRenderResponse.dto.className} ${lastRenderResponse.dto.className?uncap_first}) {
        ${lastRenderResponse.response.className} ${entity.className?uncap_first}Resp = ${lastRenderResponse.service.className?uncap_first}.update(id,${lastRenderResponse.dto.className?uncap_first});
        return super.doResource(${entity.className?uncap_first}Resp, this.getClass());
    }

    /**
     * 详情
     * 
     * @param id 主键id
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> details(@PathVariable ${entity.id.className} id) {
        ${lastRenderResponse.response.className} ${entity.className?uncap_first}Resp = ${lastRenderResponse.service.className?uncap_first}.details(id);
        return super.doResource(${entity.className?uncap_first}Resp, this.getClass());
    }

    @Override
    @GetMapping("")
    public HttpEntity<PagedModel<?>> getPageData(
            @RequestParam(value = IConstants.DEFAULT_PAGE_NUM_FIELD, defaultValue = IConstants.DEFAULT_PAGE_NUM_VAL) int pageNumber,
            @RequestParam(value = IConstants.DEFAULT_PAGE_SIZE_FIELD, defaultValue = IConstants.DEFAULT_PAGE_SIZE_VAL) int pageSize,
            @RequestParam(value = IConstants.DEFAULT_SORT_TYPES_FIELD, defaultValue = IConstants.DEFAULT_SORT_TYPE_VAL) String sortTypes,
            ServletRequest request) {
        // 获取搜索参数
        Map<String, Object> searchParams = super.getSearchParamStartWith(request, IConstants.EMPTY_SEARCH_PREFIX);
        PageInfo pageInfo = new PageInfo(pageNumber, pageSize, sortTypes);
        Page<${lastRenderResponse.response.className}> page = ${lastRenderResponse.service.className?uncap_first}.getPageList(searchParams, pageInfo);
        return super.doPage(pageNumber, pageSize, sortTypes, request, this.getClass(), page);
    }

    /**
     * 条件搜索，返回不分页的列表
     * 
     * @param request 请求参数
     */
    @GetMapping("/find/params")
    public ResponseEntity<?> findByParams(
            @RequestParam(value = IConstants.DEFAULT_SORT_TYPES_FIELD, defaultValue = IConstants.DEFAULT_SORT_TYPE_VAL) String sortTypes,
            ServletRequest request) {
        // 1.获取搜索参数
        Map<String, Object> searchParams = super.getSearchParamStartWith(request, IConstants.EMPTY_SEARCH_PREFIX);
        // 2.获取数据
        List<${lastRenderResponse.response.className}> dataList = ${lastRenderResponse.service.className?uncap_first}.findByParams(searchParams,sortTypes);
        return super.doListResources(dataList, this.getClass());
    }
}