package ${package!""};
<#if pk??>
    <#if columns?? && (columns?size > 0)>
        <#list columns as column>
            <#if column!"" == pk>
                // TODO 不能获取数据库类型对应的 JavaType
                <#assign _pkType = column.typ>
            </#if>
        </#list>
    </#if>
</#if>
<#if _pkType??>
<#else>
    <#assign _pkType = "java.lang.Long">
</#if>

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import ${basePkg!""}.entity.${(name?cap_first)!""};
import ${basePkg!""}.dao.${(name?cap_first)!""}Dao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ${_pkType!""};
import org.arch.framework.crud.CrudService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


/**
 * ${comment!""}(${(name?cap_first)!""}) 表服务层
 *
 * @author ${author!""}
 * @date ${.now}
 * @since  1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ${(name?cap_first)!""}${suffix!""} extends CrudService<${(name?cap_first)!""}, ${_pkType!"java.lang.Long"}>{
    // TODO 增加对列(xx_xx)转换为驼峰类型字段
    private final ${(name?cap_first)!""}Dao ${(name?uncap_first)!""}Dao = (${(name?cap_first)!""}Dao) crudDao;

    /**
    * 逻辑删除
    *
    * @param id 主键 id
    * @return 是否删除成功
    */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(@NonNull ${_pkType!"java.lang.Long"} id) {
        ${(name?cap_first)!""} entity = new ${(name?cap_first)!""}();
        entity.setId(id);
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<${(name?cap_first)!""}> updateWrapper =
                Wrappers.<${(name?cap_first)!""}>lambdaUpdate(entity)
                        .set(${(name?cap_first)!""}::getDeleted, 1);
        return ${(name?uncap_first)!""}Dao.update(updateWrapper);
    }

    /**
    * 逻辑删除
    * @param entity  实体
    * @return  是否删除成功
    */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(${(name?cap_first)!""} entity) {
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<${(name?cap_first)!""}> updateWrapper =
                Wrappers.<${(name?cap_first)!""}>lambdaUpdate(entity)
                        .set(${(name?cap_first)!""}::getDeleted, 1);
        // 逻辑删除
        return ${(name?uncap_first)!""}Dao.update(updateWrapper);
    }

    /**
    * 批量逻辑删除
    * @param ids 主键集合
    * @return  是否删除成功
    */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean deleteAllById(@NonNull List<${_pkType!"java.lang.Long"}> ids) {

        LambdaUpdateWrapper<${(name?cap_first)!""}> updateWrapper =
                Wrappers.<${(name?cap_first)!""}>lambdaUpdate()
                        .eq(${(name?cap_first)!""}::getDeleted, 0)
                        .in(${(name?cap_first)!""}::getId, ids)
                        .set(${(name?cap_first)!""}::getDeleted, 1);

        // 逻辑删除
        return ${(name?uncap_first)!""}Dao.update(updateWrapper);
    }
}