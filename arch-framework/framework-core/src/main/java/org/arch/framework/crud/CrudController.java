package org.arch.framework.crud;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.arch.framework.api.crud.BaseSearchDto;
import org.arch.framework.beans.Response;
import org.arch.framework.crud.utils.SearchFilter;
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
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toMap;
import static org.arch.framework.beans.exception.constant.ResponseStatusCode.FAILED;
import static org.arch.framework.crud.utils.TenantUtils.removeTenantIdValue;


/**
 * 通用控制器
 * @author lait.zhang@gmail.com
 * @tel 15801818092
 * @date 11/21/2019
 * @description ${Description}
 * @param <R>   实体的 request 类型
 * @param <T>   实体类
 * @param <ID>  实体类的 id 类型
 * @param <DTO> 实体类对应的 {@link BaseSearchDto}
 * @param <CS>  实体类对应的 {@link CrudService}
 */
public interface CrudController<R, T extends Model<T>, ID extends Serializable,
        DTO extends BaseSearchDto, CS extends CrudService<T, ID>> {

    Logger log = LoggerFactory.getLogger(CrudController.class);
    String TENANT_ID_FIELD_NAME = "tenantId";
    String TENANT_ID_CONDITION_EXP = SearchFilter.Operator.EQ.name() + "_tenant_id";

    /**
     * 获取 {@link CrudService} 实例
     * @return 返回 {@link CrudService} 实例
     */
    CS getCrudService();

    /**
     * 获取 {@link BaseSearchDto} 实例
     * @return 返回 {@link BaseSearchDto} 实例
     */
    DTO getSearchDto();

    /**
     * 转换为 {@link BaseSearchDto} 的子类对象.
     * @param obj  R(实体的 request 类型) 或者 T(实体类) 类型
     * @return  返回 {@link BaseSearchDto} 的子类对象.
     */
    default DTO convertSearchDto(Object obj) {
        DTO searchDto = getSearchDto();
        BeanUtils.copyProperties(obj, searchDto);
        return searchDto;
    }


    /**
     * 根据 token info 处理 t 后返回 t.
     * @param token token info
     * @param r     实体的 request 类型
     * @return  返回处理后的实体类
     */
    T resolver(TokenInfo token, R r);

    /**
     * 保存
     * @param r     实体的 request 类型
     * @param token token info
     * @return  {@link Response}
     */
    @PostMapping
    default Response<DTO> save(@Valid @RequestBody R r, TokenInfo token) {
        try {
            T t = resolver(token, r);
            boolean isSave = getCrudService().save(t);
            if (isSave) {
                return Response.success(convertSearchDto(t));
            }
            else {
                return Response.failed(FAILED, "保持失败");
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(),e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

    /**
     * 批量保存
     * @param requestList 实体的 request 类型列表
     * @return  {@link Response}
     */
    @PostMapping("/saves")
    default Response<List<DTO>> saveAll(@Valid @RequestBody List<R> requestList){
        try {
            List<T> tList = requestList.stream().map(request -> resolver(null, request)).collect(Collectors.toList());
            boolean isSave = getCrudService().saveList(tList);
            if (isSave) {
                return Response.success(tList.stream().map(this::convertSearchDto).collect(Collectors.toList()));
            }
            else {
                return Response.failed(FAILED, "保持失败");
            }
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
    default Response<DTO> findById(@PathVariable("id") ID id) {
        try {
            return Response.success(convertSearchDto(getCrudService().findById(id)));
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
     * @param request   实体的 request 类型
     * @param token     token info
     * @return  {@link Response}
     */
    @GetMapping("/single")
    default Response<DTO> findOne(@Valid R request, TokenInfo token) {
        try {
            T entity = resolver(token, request);
            DTO searchDto = convertSearchDto(entity);
            T t = getCrudService().findOneByMapParams(searchDto.searchParams());
            return Response.success(convertSearchDto(t));
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
     * @param request   实体的 request 类型
     * @param token     token info
     * @return  {@link Response}
     */
    @GetMapping("/find")
    default Response<List<DTO>> find(@Valid R request, TokenInfo token) {
        try {
            T entity = resolver(token, request);
            DTO searchDto = convertSearchDto(entity);
            List<T> tList = getCrudService().findAllByMapParams(searchDto.searchParams());
            return Response.success(tList.stream().map(this::convertSearchDto).collect(Collectors.toList()));
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
    default Response<List<DTO>> list(TokenInfo token) {
        return find(null, token);
    }

    /**
     * 分页查询.
     * @param request       实体的 request 类型
     * @param pageNumber    第几页
     * @param pageSize      页大小
     * @param token         token info
     * @return  {@link Response}
     */
    @GetMapping(value = "/page/{pageNumber}/{pageSize}")
    default Response<IPage<DTO>> page(@Valid R request,
                                      @PathVariable(value = "pageNumber") Integer pageNumber,
                                      @PathVariable(value = "pageSize") Integer pageSize,
                                      TokenInfo token) {
        try {
            T entity = resolver(token, request);
            DTO searchDto = convertSearchDto(entity);
            IPage<T> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
            return Response.success(page.convert(this::convertSearchDto));
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
            return Response.success(getCrudService().deleteById(id));
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
     * @param request   实体的 request 类型
     * @param token     token info
     * @return  true 表示更新成功
     */
    @PutMapping
    default Response<Boolean> updateById(@RequestBody @Valid R request, TokenInfo token) {
        try {
            // 检查 id 是否为 null
            Object idValue = ReflectionKit.getFieldValue(request,
                                                         TableInfoHelper.getTableInfo(request.getClass())
                                                                        .getKeyProperty());
            if (isNull(idValue)) {
                return Response.failed("id 不能为 null");
            }
            T entity = resolver(token, request);
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

    /**
     * 根据 entity 条件模糊查询对象; 模糊查询的条件拼接 {@code CONCAT("%", condition ,"%")},
     * 此方法会对不为 null 的 {@link String} 类型的字段都进行模糊查询.
     * @param request   实体的 request 类型
     * @param token     token info
     * @return  {@link Response}
     */
    @GetMapping("/like")
    default Response<List<T>> like(@Valid R request, TokenInfo token) {
        try {
            final T entity = resolver(token, request);
            Field[] fields = entity.getClass().getDeclaredFields();
            final Class<String> stringClass = String.class;
            final String likePrefix = SearchFilter.Operator.LIKE + "_";
            Map<String, Object> likeParamsMap =
                    Arrays.stream(fields)
                          .filter(field -> {
                              field.setAccessible(true);
                              try {
                                  String fieldName = field.getName();
                                  if (TENANT_ID_FIELD_NAME.equals(fieldName)) {
                                      return false;
                                  }
                                  Object value = field.get(entity);
                                  if (isNull(value)) {
                                      return false;
                                  }
                              }
                              catch (IllegalAccessException e) {
                                  log.error(e.getMessage(), e);
                                  return false;
                              }
                              return stringClass.isAssignableFrom(field.getType());
                          })
                          .collect(toMap(field -> likePrefix + field.getName(),
                                         field -> {
                                             try {
                                                 return field.get(entity);
                                             }
                                             catch (IllegalAccessException e) {
                                                 log.error(e.getMessage(), e);
                                                 // 如果出现异常, filter 阶段已处理, 此处结果可以忽略
                                                 return "";
                                             }
                                         })
                          );
            DTO searchDto = getSearchDto();
            searchDto.putNoNull(TENANT_ID_CONDITION_EXP, token.getTenantId(), likeParamsMap);
            return Response.success(getCrudService().findAllByMapParams(likeParamsMap));
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return Response.error(FAILED.getCode(), e.getMessage());
        }
    }

}
