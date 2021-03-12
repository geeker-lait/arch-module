package org.arch.framework.api.crud;

import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 搜索参数的基类
 */
@Accessors(chain = true)
public interface BaseSearchDto {
    /**
     * 是否逻辑删除: 0 未删除, 1 已删除; 默认: 0
     */
    Boolean DELETED = false;

    /**
     * 获取查询条件与值
     */
    default Map<String, Object> getSearchParams() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("EQ_deleted", DELETED);
        buildSearchParams(map);
        return map;
    }

    /**
     * 子类实现该方法，将查询的条件和值封装到map参数中<br>
     * key为查询条件，如：LIKE_name<br>
     * value为查询条件对应的值，如：123<br>
     * 则封装后的SQL语句为：where name like '%123%'
     *
     * @param map 保持查询条件和值的集合, 为 {@link LinkedHashMap} 类型, 根据添加的条件顺序转换为 SQL 语句
     */
    void buildSearchParams(Map<String, Object> map);

    /**
     * 查询的值不为空才放入集合中<br>
     * 1.如果时字符串，字符串必须不为空<br>
     * 2.如果是List集合类型，集合必须不为空<br>
     * 3.其他情况，value != null<br>
     *
     * @param key   查询的key，如：LIKE_name
     * @param value 对应的值，不为空，如：123
     * @param map   存放搜索参数map
     */
    default void putNoNull(String key, Object value, Map<String, Object> map) {
        boolean isCanSearch;
        if (value instanceof String) {
            isCanSearch = !"".equals(value.toString());
        } else if (value instanceof Collection) {
            isCanSearch = !((Collection<?>) value).isEmpty();
        } else {
            isCanSearch = (value != null);
        }
        if (isCanSearch) {
            map.put(key, value);
        }
    }

}
