package org.arch.framework.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.arch.framework.api.crud.BaseSearchDto;
import org.arch.framework.beans.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;


/**
 * 面向前端的 feign 通用控制器
 * @author YongWu zheng
 * @date 2021-04-16 14:44
 * @param <DTO> 实体类对应的 {@link BaseSearchDto}
 * @param <ID>  实体类的 id 类型
 * @param <R>   实体类对应的 {@code TRequest}
 * @param <FS>  {@link BaseFeignService} 子类
 */
public interface FeignCrudController<DTO, ID extends Serializable, R,
        FS extends BaseFeignService<DTO, R, ID>> {

    Logger log = LoggerFactory.getLogger(FeignCrudController.class);

    /**
     * 获取 {@link BaseFeignService} 子类对象
     * @return 返回 {@link BaseFeignService} 子类对象
     */
    FS getFeignService();

    /**
     * 保存
     * @param request  实体类对应的 {@code TRequest}
     * @return  {@link Response}
     */
    @PostMapping
    default Response<DTO> save(@Valid @RequestBody R request) {
        try {
            return getFeignService().save(request);
        }
        catch (Exception e) {
            log.error(e.getMessage(),e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 批量保存
     * @param requestList 实体类对应的 {@code TRequest} 列表
     * @return  {@link Response}
     */
    @PostMapping("/saves")
    default Response<List<DTO>> saveAll(@Valid @RequestBody List<R> requestList) {
        try {
            return getFeignService().saveAll(requestList);
        }
        catch (Exception e) {
            log.error(e.getMessage(),e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 根据 id 查询对象
     * @param id    id
     * @return  {@link Response}
     */
    @GetMapping(path = "/{id:\\d+}")
    default Response<DTO> findById(@PathVariable("id") ID id) {
        try {
            return getFeignService().findById(id);
        }
        catch (Exception e) {
            log.error(e.getMessage(),e);
            if (e instanceof IncorrectResultSizeDataAccessException) {
                return Response.error(FAILED.getCode(),"查询到多个结果");
            } else {
                return Response.error(FAILED.getCode(), e.getMessage());
            }
        }
    }

    /**
     * 根据 request 条件查询对象.
     * @param request   实体类对应的 {@code TRequest}
     * @return  {@link Response}
     */
    @GetMapping("/single")
    default Response<DTO> findOne(@Valid R request) {
        try {
            return getFeignService().findOne(request);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            if (e instanceof IncorrectResultSizeDataAccessException) {
                return Response.error(FAILED.getCode(),"查询到多个结果");
            } else {
                return Response.error(FAILED.getCode(), e.getMessage());
            }
        }
    }

    /**
     * 根据 request 条件查询对象列表.
     * @param request   实体类对应的 {@code TRequest}
     * @return  {@link Response}
     */
    @GetMapping("/find")
    default Response<List<DTO>> find(@Valid R request) {
        try {
            return getFeignService().find(request);
        }
        catch (Exception e) {
            log.error(e.getMessage(),e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 查询所有列表
     * @return  {@link Response}
     */
    @GetMapping("/list")
    default Response<List<DTO>> list() {
        try {
            return getFeignService().list();
        }
        catch (Exception e) {
            log.error(e.getMessage(),e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 分页查询.
     * @param request       实体类对应的 {@code TRequest}
     * @param pageNumber    第几页
     * @param pageSize      页大小
     * @return  {@link Response}
     */
    @GetMapping(value = "/page/{pageNumber:\\d+}/{pageSize:\\d+}")
    default Response<Page<DTO>> page(@Valid R request,
                                   @PathVariable(value = "pageNumber") Integer pageNumber,
                                   @PathVariable(value = "pageSize") Integer pageSize) {
        try {
            return getFeignService().page(request, pageNumber, pageSize);
        }
        catch (Exception e) {
            log.error(e.getMessage(),e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 根据 id 删除
     * @param id    id
     * @return  {@link Response}
     */
    @DeleteMapping(path = "/{id}")
    default Response<Boolean> deleteById(@PathVariable("id") ID id) {
        try {
            return getFeignService().deleteById(id);
        }
        catch (Exception e) {
            log.error(e.getMessage(),e);
            if (e instanceof IncorrectResultSizeDataAccessException) {
                return Response.error(FAILED.getCode(),"查询到多个结果");
            } else {
                return Response.error(FAILED.getCode(), e.getMessage());
            }
        }
    }

    /**
     * 根据 id 更新实体, 直接更新 不为 null 的值.
     * @param request   实体类对应的 {@code TRequest}
     * @return  true 表示更新成功
     */
    @PutMapping
    default Response<Boolean> updateById(@Valid @RequestBody R request) {
        try {
            return getFeignService().updateById(request);
        }
        catch (Exception e) {
            log.error(e.getMessage(),e);
            if (e instanceof IncorrectResultSizeDataAccessException) {
                return Response.error(FAILED.getCode(),"查询到多个结果");
            } else {
                return Response.error(FAILED.getCode(), e.getMessage());
            }
        }
    }

    /**
     * 根据 request 条件模糊查询对象; 模糊查询的条件拼接 {@code CONCAT("%", condition ,"%")},
     * 此方法会对不为 null 的 {@link String} 类型的字段都进行模糊查询.
     * @param request    实体类对应的 {@code TRequest}
     * @return  {@link Response}
     */
    @GetMapping("/like")
    default Response<List<DTO>> like(@Valid R request) {
        try {
            return getFeignService().like(request);
        }
        catch (Exception e) {
            log.error(e.getMessage(),e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
