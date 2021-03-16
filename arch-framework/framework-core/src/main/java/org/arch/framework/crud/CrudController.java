package org.arch.framework.crud;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.arch.framework.api.crud.BaseSearchDto;
import org.arch.framework.beans.Response;
import org.arch.framework.ums.bean.TokenInfo;
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

import static java.util.Objects.isNull;
import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;
import static org.arch.framework.crud.utils.TenantUtils.removeTenantIdValue;


/**
 * 通用控制器
 * @author lait.zhang@gmail.com
 * @tel 15801818092
 * @date 11/21/2019
 * @description ${Description}
 * @param <T>   实体类
 * @param <ID>  实体类的 id 类型
 * @param <S>   实体类对应的 {@link BaseSearchDto}
 * @param <CS>  实体类对应的 {@link CrudService}
 */
public interface CrudController<T extends Model<T>, ID extends Serializable,
        S extends BaseSearchDto, CS extends CrudService<T, ID>> {

    Logger log = LoggerFactory.getLogger(CrudController.class);

    /**
     * 获取 {@link CrudService} 实例
     * @return 返回 {@link CrudService} 实例
     */
    CS getCrudService();

    /**
     * 获取 {@link BaseSearchDto} 实例
     * @return 返回 {@link BaseSearchDto} 实例
     */
    S getSearchDto();

    /**
     * 转换为 {@link BaseSearchDto} 的子类对象.
     * @param entity 实体类
     * @return  返回 {@link BaseSearchDto} 的子类对象.
     */
    default S convertSearchDto(T entity) {
        S searchDto = getSearchDto();
        BeanUtils.copyProperties(entity, searchDto);
        return searchDto;
    }


    /**
     * 根据 token info 处理 t 后返回 t.
     * @param token token info
     * @param t     实体类
     * @return  返回处理后的实体类
     */
    default T resolver(TokenInfo token, T t) {
        return t;
    }

    /**
     * 保存
     * @param t     实体类
     * @param token token info
     * @return  {@link Response}
     */
    @PostMapping
    default Response<T> save(@Valid @RequestBody T t, TokenInfo token) {
        try {
            resolver(token, t);
            getCrudService().save(t);
            return Response.success(t);
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
    default Response<List<T>> saveAll(@Valid @RequestBody List<T> entityList) {
        try {
            getCrudService().saveList(entityList);
            return Response.success(entityList);
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
    @GetMapping(path = "/{id}")
    default Response<T> findById(@PathVariable("id") ID id) {
        try {
            return Response.success(getCrudService().findById(id));
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
     * 根据 entity 条件查询对象.
     * @param entity    实体类
     * @param token     token info
     * @return  {@link Response}
     */
    @GetMapping("/single")
    default Response<T> findOne(T entity, TokenInfo token) {
        try {
            resolver(token, entity);
            S searchDto = convertSearchDto(entity);
            T t = getCrudService().findOneByMapParams(searchDto.getSearchParams());
            return Response.success(t);
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
     * 根据 entity 条件查询对象列表.
     * @param t         实体类
     * @param token     token info
     * @return  {@link Response}
     */
    @GetMapping("/find")
    default Response<List<T>> find(T t, TokenInfo token) {
        try {
            resolver(token, t);
            S searchDto = convertSearchDto(t);
            return Response.success(getCrudService().findAllByMapParams(searchDto.getSearchParams()));
        }
        catch (Exception e) {
            log.error(e.getMessage(),e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 查询所有列表
     * @param token     token info
     * @return  {@link Response}
     */
    @GetMapping("/list")
    default Response<List<T>> list(TokenInfo token) {
        try {
            T t = resolver(token, null);
            S searchDto = convertSearchDto(t);
            return Response.success(getCrudService().findAllByMapParams(searchDto.getSearchParams()));
        }
        catch (Exception e) {
            log.error(e.getMessage(),e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 分页查询.
     * @param entity        实体类
     * @param pageNumber    第几页
     * @param pageSize      页大小
     * @param token         token info
     * @return  {@link Response}
     */
    @GetMapping(value = "/page/{pageNumber}/{pageSize}")
    default Response<IPage<T>> page(T entity,
                                    @PathVariable(value = "pageNumber") Integer pageNumber,
                                    @PathVariable(value = "pageSize") Integer pageSize,
                                    TokenInfo token) {
        try {
            resolver(token, entity);
            S searchDto = convertSearchDto(entity);
            return Response.success(getCrudService().findPage(searchDto.getSearchParams(), pageNumber, pageSize));
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
            getCrudService().deleteById(id);
            return Response.success(true);
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
     * 根据 id 更新实体, 对实体未进行校验, 直接更新 不为 null 的值.
     * @param entity    实体
     * @param token     token info
     * @return  true 表示更新成功
     */
    @PutMapping
    default Response<Boolean> updateById(@RequestBody T entity, TokenInfo token) {
        try {
            // 检查 id 是否为 null
            Object idValue = ReflectionKit.getFieldValue(entity,
                                                         TableInfoHelper.getTableInfo(entity.getClass()).getKeyProperty());
            if (isNull(idValue)) {
                return Response.failed("id 不能为 null");
            }
            resolver(token, entity);
            // id 具有唯一性, 不需要租户 id 来区分, 对于用户来说租户 id 不会变, 不必要更新;
            // 如果更新租户 id, 对于行级租户同时会更新有租户字段的索引, 影响 sql 执行性能
            removeTenantIdValue(entity);
            return Response.success(getCrudService().updateById(entity));
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

}
