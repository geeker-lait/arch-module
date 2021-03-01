package org.arch.framework.crud.utils;

import java.lang.reflect.Field;

/**
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.1 13:36
 */
public class TenantUtils {

    public static final String TENANT_ID_FIELD_NAME = "tenantId";

    /**
     * 移除 entity 中的租户 id 值. 注意 租户 id 字段不存在 或设置 null 值异常, 不做任何操作.
     * @param entity    实体
     * @param <T>       实体类型
     * @return  移除 entity 中的租户 id 值后的实体
     */
    public static <T> T removeTenantIdValue(T entity) {
        Class<?> clz = entity.getClass();
        Field tenantId = null;
        try {
            tenantId = clz.getDeclaredField(TENANT_ID_FIELD_NAME);
            tenantId.setAccessible(true);
            tenantId.set(entity, null);
        }
        catch (NoSuchFieldException | IllegalAccessException e) {
            // tenantId 不存在 或设置 null 值异常, 不做任何操作.
        }
        return entity;
    }
}
