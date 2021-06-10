package org.arch.framework.crud;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.arch.framework.api.crud.BaseSearchDto;
import org.arch.framework.beans.Response;
import org.arch.framework.beans.exception.BusinessException;
import org.arch.framework.crud.utils.SearchFilter;
import org.arch.framework.ums.bean.TokenInfo;
import org.arch.framework.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;
import static org.arch.framework.crud.utils.TenantUtils.removeTenantIdValue;


/**
 * 通用控制器
 * @author lait.zhang@gmail.com
 * @tel 15801818092
 * @date 11/21/2019
 * @description ${Description}
 * @param <R>   实体的 request 封装类型
 * @param <T>   实体类
 * @param <ID>  实体类的 id 类型
 * @param <DTO> 返回的 DTO 类型
 * @param <SearchDTO> 实体类对应的 {@link BaseSearchDto}
 * @param <CS>  实体类对应的 {@link CrudService}
 */
public interface CrudBiz<R, T extends Model<T>, ID extends Serializable,
        DTO, SearchDTO extends BaseSearchDto, CS extends CrudService<T, ID>>
                                            extends CrudRest<R, ID, DTO>{

    Logger log = LoggerFactory.getLogger(CrudBiz.class);
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
    SearchDTO getSearchDto();

    /**
     * 转换为 {@link BaseSearchDto} 的子类对象.
     * @param obj  R(实体的 request 封装类型) 或者 T(实体类) 类型
     * @return  返回 {@link BaseSearchDto} 的子类对象.
     */
    default SearchDTO convertSearchDto(Object obj) {
        SearchDTO searchDto = getSearchDto();
        BeanUtils.copyProperties(obj, searchDto);
        return searchDto;
    }

    /**
     * 转换为 {@code DTO} 的子类对象. 默认实现为 {@link #convertSearchDto(Object)}.
     * @param obj  R(实体的 request 封装类型) 或者 T(实体类) 类型
     * @return  返回 {@code DTO}
     */
    default DTO convertReturnDto(Object obj) {
        //noinspection unchecked
        return (DTO) convertSearchDto(obj);
    }


    /**
     * 根据 token info 处理 t 后返回 t.
     * @param token token info
     * @param r     实体的 request 封装类型
     * @return  返回处理后的实体类
     */
    T resolver(TokenInfo token, R r);

    /**
     * 保存
     * @param r     实体的 request 封装类型
     * @return  {@link Response}
     */
    @Override
    default DTO save(R r) {
        TokenInfo tokenInfo = SecurityUtils.getTokenInfo();
        T t = resolver(tokenInfo, r);
        boolean isSave = getCrudService().save(t);
        if (isSave) {
            return convertReturnDto(t);
        }
        else {
            throw new BusinessException("保存失败");
        }
    }

    /**
     * 批量保存
     * @param requestList 实体的 request 封装类型列表
     * @return  {@link Response}
     */
    @Override
    default List<DTO> saveAll(List<R> requestList){
        TokenInfo tokenInfo = SecurityUtils.getTokenInfo();
        List<T> tList = requestList.stream().map(request -> resolver(tokenInfo, request)).collect(Collectors.toList());
        boolean isSave = getCrudService().saveList(tList);
        if (isSave) {
            return tList.stream().map(this::convertReturnDto).collect(Collectors.toList());
        }
        else {
            throw new BusinessException("保存失败");
        }
    }

    /**
     * 根据 id 查询对象
     * @param id    id
     * @return  {@link Response}
     */
    @Override
    default DTO findById(ID id) {
        return convertReturnDto(getCrudService().findById(id));
    }

    /**
     * 根据 entity 条件查询对象.
     * @param request   实体的 request 封装类型
     * @return  {@link Response}
     */
    @Override
    default DTO findOne(R request) {
        TokenInfo tokenInfo = SecurityUtils.getTokenInfo();
        T entity = resolver(tokenInfo, request);
        SearchDTO searchDto = convertSearchDto(entity);
        T t = getCrudService().findOneByMapParams(searchDto.searchParams());
        return convertReturnDto(t);
    }

    /**
     * 根据 entity 条件查询对象列表.
     * @param request   实体的 request 封装类型
     * @return  {@link Response}
     */
    @Override
    default List<DTO> find(R request) {
        TokenInfo tokenInfo = SecurityUtils.getTokenInfo();
        T entity = resolver(tokenInfo, request);
        SearchDTO searchDto = convertSearchDto(entity);
        List<T> tList = getCrudService().findAllByMapParams(searchDto.searchParams());
        return tList.stream().map(this::convertReturnDto).collect(Collectors.toList());
    }

    /**
     * 查询所有列表
     * @return  {@link Response}
     */
    @Override
    default List<DTO> list() {
        return find(null);
    }

    /**
     * 分页查询.
     *
     * @param request    实体的 request 封装类型
     * @param pageNumber 第几页
     * @param pageSize   页大小
     * @return {@link Response}
     */
    @Override
    default IPage<DTO> page(R request, Integer pageNumber, Integer pageSize) {
        TokenInfo tokenInfo = SecurityUtils.getTokenInfo();
        T entity = resolver(tokenInfo, request);
        SearchDTO searchDto = convertSearchDto(entity);
        IPage<T> page = getCrudService().findPage(searchDto.searchParams(), pageNumber, pageSize);
        return page.convert(this::convertReturnDto);
    }

    /**
     * 根据 id 删除
     * @param id    id
     * @return  {@link Response}
     */
    @Override
    default Boolean deleteById(ID id) {
        return getCrudService().deleteById(id);
    }

    /**
     * 批量逻辑删除
     *
     * @param ids 主键集合
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    default Boolean deleteAllById(@RequestBody List<ID> ids) {
        return getCrudService().deleteAllById(ids);
    };

    /**
     * 根据 id 更新实体, 对实体未进行校验, 直接更新 不为 null 的值.
     * @param request   实体的 request 封装类型
     * @return  true 表示更新成功
     */
    @Override
    default Boolean updateById(R request) {
        // 检查 id 是否为 null
        Object idValue = ReflectionKit.getFieldValue(request,
                                                     TableInfoHelper.getTableInfo(request.getClass())
                                                                    .getKeyProperty());
        if (isNull(idValue)) {
            throw new BusinessException("id 不能为 null");
        }
        TokenInfo tokenInfo = SecurityUtils.getTokenInfo();
        T entity = resolver(tokenInfo, request);
        // id 具有唯一性, 不需要租户 id 来区分, 对于用户来说租户 id 不会变, 不必要更新;
        // 如果更新租户 id, 对于行级租户同时会更新有租户字段的索引, 影响 sql 执行性能
        removeTenantIdValue(entity);
        return getCrudService().updateById(entity);
    }

    /**
     * 根据 entity 条件模糊查询对象; 模糊查询的条件拼接 {@code CONCAT("%", condition ,"%")},
     * 此方法会对不为 null 的 {@link String} 类型的字段都进行模糊查询.
     * @param request   实体的 request 类型
     * @return  {@link Response}
     */
    @Override
    default List<DTO> like(R request) {
        TokenInfo tokenInfo = SecurityUtils.getTokenInfo();
        final T entity = resolver(tokenInfo, request);
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
        SearchDTO searchDto = getSearchDto();
        if (nonNull(tokenInfo)) {
            searchDto.putNoNull(TENANT_ID_CONDITION_EXP, tokenInfo.getTenantId(), likeParamsMap);
        }
        List<T> likeList = getCrudService().findAllByMapParams(likeParamsMap);
        return likeList.stream().map(this::convertReturnDto).collect(Collectors.toList());
    }

}
