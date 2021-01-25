package org.arch.framework.crud;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.arch.framework.crud.dto.BaseSearchDto;
import org.arch.framework.ums.bean.TokenInfo;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

import static org.arch.framework.exception.constant.ResponseStatusCode.FAILED;


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
        getCrudService().mapper(entity, searchDto);
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
        resolver(token, t);
        getCrudService().save(t);
        return Response.success(t);
    }

    /**
     * 批量保存
     * @param entityList 实体类列表
     * @return  {@link Response}
     */
    @PostMapping("/saves")
    default Response<List<T>> saveAll(@Valid @RequestBody List<T> entityList) {
        getCrudService().saveList(entityList);
        return Response.success(entityList);
    }

    /**
     * 根据 id 查询对象
     * @param id    id
     * @return  {@link Response}
     */
    @GetMapping(path = "/{id}")
    default Response<T> findById(@PathVariable("id") ID id) {
        return Response.success(getCrudService().findById(id));
    }

    /**
     * 根据 entity 条件查询对象
     * @param entity    实体类
     * @param token     token info
     * @return  {@link Response}
     */
    @GetMapping("single")
    default Response<T> findOne(T entity, TokenInfo token) {
        try {
            resolver(token, entity);
            S searchDto = convertSearchDto(entity);
            T t = getCrudService().findOneByMapParams(searchDto.getSearchParams());
            return Response.success(t);
        } catch (Exception e) {
            if (e instanceof IncorrectResultSizeDataAccessException) {
                return Response.error(FAILED.getCode(),"查询到多个结果");
            } else {
                return Response.error(FAILED.getCode(), e.getMessage());
            }
        }
    }

    /**
     * 根据 entity 条件查询对象列表
     * @param t         实体类
     * @param token     token info
     * @return  {@link Response}
     */
    @GetMapping("find")
    default Response<List<T>> find(T t, TokenInfo token) {
        resolver(token, t);
        S searchDto = convertSearchDto(t);
        return Response.success(getCrudService().findAllByMapParams(searchDto.getSearchParams()));
    }

    /**
     * 查询所有列表
     * @param token     token info
     * @return  {@link Response}
     */
    @GetMapping("list")
    default Response<List<T>> list(TokenInfo token) {
        resolver(token,null);
        return Response.success(getCrudService().findAll());
    }

    /**
     * 分页查询
     * @param entity        实体类
     * @param pageNumber    第几页
     * @param pageSize      页大小
     * @param token         token info
     * @return  {@link Response}
     */
    @GetMapping("page")
    default Response<IPage<T>> page(T entity, Integer pageNumber, Integer pageSize, TokenInfo token) {
        resolver(token, entity);
        S searchDto = convertSearchDto(entity);
        return Response.success(getCrudService().findPage(searchDto.getSearchParams(), pageNumber, pageSize));
    }

    /**
     * 根据 id 删除
     * @param id    id
     * @return  {@link Response}
     */
    @DeleteMapping(path = "/{id}")
    default Response<Boolean> deleteById(@PathVariable("id") ID id) {
        getCrudService().deleteById(id);
        return Response.success(true);
    }

//public interface CrudController<T extends CrudService> {


//    CrudService getCrudService();
//
//    default E resolver(TokenInfo token, E e) {
//        return e;
//    }
//
//    @PostMapping
//    default Response save(@Valid @RequestBody E e, TokenInfo token) {
//        resolver(token, e);
//        getCrudService().save(e);
//        return Response.ok(e);
//    }
//
//    @Transactional
//    @PostMapping("/saves")
//    default Response saveAll(@Valid @RequestBody List<E> es) {
//        getCrudService().saveAll(es);
//        return Response.ok(es);
//    }
//
//    @GetMapping(path = "/{id:.+}")
//    default Response findById(@PathVariable Long id) {
//        return Response.success(getCrudService().findById(id));
//    }
//
//    @GetMapping("single")
//    default Response findOne(E entity,TokenInfo token) {
//        try {
//            resolver(token, entity);
//            Object o = getCrudService().findOne(entity);
//            return Response.ok(o);
//        } catch (Exception e) {
//            if (e instanceof IncorrectResultSizeDataAccessException) {
//                return Response.error("查询到多个结果");
//            } else {
//                return Response.error(e.getMessage());
//            }
//        }
//    }
//
//    @GetMapping("find")
//    default Response find(E e,TokenInfo token) {
//        resolver(token, e);
//        return Response.ok(getCrudService().find(e));
//    }
//
//    @GetMapping("list")
//    default Response list(Token token) {
//        //resolver(token,null);
//        return Response.ok(getCrudService().findAll());
//    }
//
//    @GetMapping("page")
//    default Response page(QueryRequest<E> queryRequest,TokenInfo token) {
//        resolver(token, queryRequest.getParam());
//        return Response.ok(getCrudService().findBypage(queryRequest));
//    }
//
//
//    @DeleteMapping(path = "/{id:.+}")
//    default Response deleteById(@PathVariable Long id) {
//        getCrudService().deleteById(id);
//        return Response.ok(true);
//    }


}
