//package org.arch.framework.crud.utils;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import org.arch.framework.crud.SearchBuildable;
//import org.springframework.util.CollectionUtils;
//
//import java.time.LocalDateTime;
//import java.util.*;
//import java.util.Map.Entry;
//
///**
// * 页面搜索参数构造工具类
// */
//public class MybatisSearchBuilder implements SearchBuildable {
//
//    /**
//     * 空字符串
//     */
//    private static final String NULL_STRING = "";
//
//    /**
//     * 百分号的字符串
//     */
//    private static final String PERCENT_STRING = "%";
//
//    /**
//     * 英文下的"."字符串，需要转义
//     */
//    private static final String POINT_STRING = "\\.";
//
//    /**
//     * 组合Parameters生成Query String的Parameter部分, 并在paramter name上加上prefix.
//     */
//    public static String enParamStrWithPrefix(Map<String, Object> params, String prefix) {
//        if (params == null || params.isEmpty()) {
//            return NULL_STRING;
//        }
//        prefix = Optional.ofNullable(prefix).orElse(NULL_STRING);
//        StringBuilder queryStringBuilder = new StringBuilder();
//        Iterator<Entry<String, Object>> it = params.entrySet().iterator();
//        while (it.hasNext()) {
//            Entry<String, Object> entry = it.next();
//            queryStringBuilder.append(prefix).append(entry.getKey()).append('=').append(entry.getValue());
//            if (it.hasNext()) {
//                queryStringBuilder.append('&');
//            }
//        }
//        return queryStringBuilder.toString();
//    }
//
//    /**
//     * 构造一般搜索标准查询
//     *
//     * @param searchParams 搜索参数
//     * @return QueryWrapper
//     */
//    public static <T> QueryWrapper<T> buildWrapper(Map<String, Object> searchParams) {
//        return MybatisSearchBuilder.buildWrapper(SearchFilter.parse(searchParams));
//    }
//
//    /**
//     * 实现复杂对象查询，基于接口实现
//     * 用JPA（实现toPredicate方法，去构造Specification对象查询）
//     * 用MP 创建QueryWrapper对象
//     */
//    public static <T> QueryWrapper<T> buildWrapper(final Collection<SearchFilter> filters) {
//
//        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
//        if (!CollectionUtils.isEmpty(filters)) {
//            // 保存查询条件集
//            filters.forEach(f -> {
//                // 使用原生的java API进行分割，防止过度依赖第三方包
//                String[] names = f.fieldName.split(POINT_STRING);
//                switch (f.operator) {
//                    case EQ:
//                        // 等于查询构造
//                        queryWrapper.eq(f.fieldName, f.value);
//                        break;
//                    case NE:
//                        // 不等于查询构造
//                        queryWrapper.ne(f.fieldName, f.value);
//                        break;
//                    case LIKE:
//                        // 模糊查询构造
//                        String likeValue = f.value.toString();
//                        if (!likeValue.startsWith(PERCENT_STRING)) {
//                            likeValue = likeValue.concat(PERCENT_STRING);
//                        }
//                        if (!likeValue.endsWith(PERCENT_STRING)) {
//                            likeValue = likeValue.concat(PERCENT_STRING);
//                        }
//                        queryWrapper.like(f.fieldName, likeValue);
//                        break;
//                    case GT:
//                        // 大于查询构造
//                        queryWrapper.gt(f.fieldName, f.value);
//                        break;
//                    case LT:
//                        // 小于查询构造
//                        queryWrapper.lt(f.fieldName, f.value);
//                        break;
//                    case GTE:
//                        // 大于等于查询
//                        queryWrapper.ge(f.fieldName, f.value);
//                        break;
//                    case LTE:
//                        // 小于等于查询构造
//                        queryWrapper.le(f.fieldName, f.value);
//                        break;
//                    case IN:
//                        // 使用IN的查询构造，需要以数组为Value;
//                        Object[] queryObj;
//                        if (f.value instanceof Collection) {
//                            queryObj = ((Collection) f.value).toArray();
//                        } else {
//                            queryObj = (Object[]) f.value;
//                        }
//                        queryWrapper.in(f.fieldName, queryObj);
//                        break;
//                    case BETWEEN:
//                        if (f.value instanceof List) {
//                            List c = (List) f.value;
//                            Object param1 = c.get(0);
//                            Object param2 = c.get(1);
//                            if (param1 instanceof Number) {
//                                param1 = ((Number) param1).longValue();
//                                param2 = ((Number) param2).longValue();
//
//                            } else if (param1 instanceof Date) {
//                                param1 = (Date) param1;
//                                param2 = (Date) param2;
//                            } else if (param1 instanceof LocalDateTime) {
//                                param1 = (LocalDateTime) param1;
//                                param2 = (LocalDateTime) param2;
//                            } else {
//                                param1 = param1.toString();
//                                param2 = param2.toString();
//                            }
//                            queryWrapper.between(f.fieldName, param1, param2);
//                        }
//                        break;
//                    case ISNULL:
//                        queryWrapper.isNull(f.fieldName);
//                        break;
//                    case ISNOTNULL:
//                        queryWrapper.isNotNull(f.fieldName);
//                        break;
//                    default:
//                        break;
//                }
//            });
//        }
//        return queryWrapper;
//    }
//}
