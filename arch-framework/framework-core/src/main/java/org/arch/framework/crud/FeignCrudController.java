package org.arch.framework.crud;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.arch.framework.beans.Response;
import org.arch.framework.feign.BaseFeignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
import java.util.stream.Collectors;

import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;


/**
 * 面向前端的 feign 通用控制器
 * @author YongWu zheng
 * @date 2021-04-16 14:44
 * @param <T>   实体类
 * @param <ID>  实体类的 id 类型
 * @param <R>   实体类对应的 {@code TRequest}
 * @param <FS>  {@link BaseFeignService} 子类
 */
public interface FeignCrudController<T extends Model<T>, ID extends Serializable, R, FS extends BaseFeignService<T, ID>> {

    Logger log = LoggerFactory.getLogger(FeignCrudController.class);

    /**
     * 获取 {@link BaseFeignService} 子类对象
     * @return 返回 {@link BaseFeignService} 子类对象
     */
    FS getFeignService();

    /**
     * 获取 T 的空对象.
     * @return  返回 T 的空对象.
     */
    T getEntity();

    /**
     * 保存
     * @param request     实体类
     * @return  {@link Response}
     */
    @PostMapping
    default Response<T> save(@Valid @RequestBody R request) {
        try {
            T entity = getEntity();
            BeanUtils.copyProperties(request, entity);
            return getFeignService().save(entity);
        }
        catch (Exception e) {
            log.error(e.getMessage(),e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 批量保存
     * @param entityList 实体类列表
     * @return  {@link Response}
     */
    @PostMapping("/saves")
    default Response<List<T>> saveAll(@Valid @RequestBody List<R> entityList) {
        try {
            List<T> tList = entityList.stream()
                                        .map(r -> {
                                            T entity = getEntity();
                                            BeanUtils.copyProperties(r, entity);
                                            return entity;
                                        })
                                        .collect(Collectors.toList());

            return getFeignService().saveAll(tList);
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
    default Response<T> findById(@PathVariable("id") ID id) {
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
     * @param request   实体类
     * @return  {@link Response}
     */
    @GetMapping("/single")
    default Response<T> findOne(R request) {
        try {
            T entity = getEntity();
            BeanUtils.copyProperties(request, entity);
            return getFeignService().findOne(entity);
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
     * @param request   实体类
     * @return  {@link Response}
     */
    @GetMapping("/find")
    default Response<List<T>> find(R request) {
        try {
            T entity = getEntity();
            BeanUtils.copyProperties(request, entity);
            return getFeignService().find(entity);
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
    default Response<List<T>> list() {
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
     * @param request       实体类
     * @param pageNumber    第几页
     * @param pageSize      页大小
     * @return  {@link Response}
     */
    @GetMapping(value = "/page/{pageNumber:\\d+}/{pageSize:\\d+}")
    default Response<Page<T>> page(R request,
                                   @PathVariable(value = "pageNumber") Integer pageNumber,
                                   @PathVariable(value = "pageSize") Integer pageSize) {
        try {
            T entity = getEntity();
            BeanUtils.copyProperties(request, entity);
            return getFeignService().page(entity, pageNumber, pageSize);
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
     * @param request   实体
     * @return  true 表示更新成功
     */
    @PutMapping
    default Response<Boolean> updateById(@Valid @RequestBody R request) {
        try {
            T entity = getEntity();
            BeanUtils.copyProperties(request, entity);
            return getFeignService().updateById(entity);
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
     * @param request    实体类
     * @return  {@link Response}
     */
    @GetMapping("/like")
    default Response<List<T>> like(R request) {
        try {
            T entity = getEntity();
            BeanUtils.copyProperties(request, entity);
            return getFeignService().like(entity);
        }
        catch (Exception e) {
            log.error(e.getMessage(),e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
