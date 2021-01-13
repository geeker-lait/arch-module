package org.arch.framework.crud.utils;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 构造搜索条件
 */
public class SearchFilter {

    /**
     * 数据搜索的枚举值，含义<br>
     * EQ 等于，相当于SQL：username=xxx<br>
     * NE 不等于，相当于SQL：username !=xxx<br>
     * LIKE 模糊搜索，相当于SQL：username like '%xxx%'<br>
     * GT 大于，相当于于SQL：age > xx <br>
     * LT 小于，相当于SQL：age < xx <br>
     * GTE 大于等于，相当于SQL：age >=xx<br>
     * LTE 小于等于，相当于SQL：age <=xx<br>
     * IN 在其中，相当于SQL：age in (xx)<br>
     * NOTIN 不在当中，相当于SQL：age not in (xx)<br>
     * ISNULL 为空，相当于SQL：username is null<br>
     * ISNOTNULL 不为空，相当于SQL：username is not null<br>
     *
     * @author qsr
     */
    public enum Operator {
        EQ, NE, LIKE, GT, LT, GTE, LTE, IN, NOTIN, ISNULL, ISNOTNULL, BETWEEN
    }

    /**
     * 搜索的字段名
     */
    public String fieldName;

    /**
     * 搜索字段的对应的搜索值
     */
    public Object value;

    /**
     * 搜索的方式
     */
    public Operator operator;

    /**
     * 使用：EQ, LIKE, GT, LT, GTE, LTE 时调用
     *
     * @param fieldName 搜索字段
     * @param operator  搜索方式
     * @param value     搜索的值
     */
    public SearchFilter(String fieldName, Operator operator, Object value) {
        this.fieldName = fieldName;
        this.value = value;
        this.operator = operator;
    }

    /**
     * searchParams中key的格式为OPERATOR_FIELDNAME<br>
     * 如：EQ_id，生成的sql为：id=xxx（为map的value的值）<br>
     * 第一个"_"之前必须是Operator的枚举值，后面是字段，<br>
     * 如果是联表查询，字段取值方式可以是"b.id"，也可以是"b_id"<br>
     *
     * @param searchParams 传递进来的搜索map参数
     * @return 搜索条件集合
     */
    public static List<SearchFilter> parse(Map<String, Object> searchParams) {
        List<SearchFilter> filters = new ArrayList<>(searchParams.size());
        searchParams.entrySet().forEach(entry -> {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (Objects.nonNull(value)) {
                // 拆分operator与filedAttribute，如["EQ","id"]
                String[] names = key.split("_");
                if (names.length < 2) {
                    throw new IllegalArgumentException(key + " is not a valid search filter name");
                }
                // 获取搜索的字段名
                String filedName = key.substring(names[0].length() + 1).replaceAll("_", ".");
                boolean isCanSearch;
                if (value instanceof String) {
                    isCanSearch = StringUtils.hasText(value.toString());
                } else if (value instanceof Collection) {
                    isCanSearch = !CollectionUtils.isEmpty((Collection<?>) value);
                } else {
                    isCanSearch = (value != null);
                }
                if (isCanSearch) {
                    // 创建searchFilter
                    filters.add(new SearchFilter(filedName, Operator.valueOf(names[0]), value));
                }
            }
        });
        return filters;
    }

}
