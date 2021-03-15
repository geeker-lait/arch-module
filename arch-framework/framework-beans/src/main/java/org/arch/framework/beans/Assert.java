package org.arch.framework.beans;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import org.arch.framework.beans.enums.StatusCode;
import org.arch.framework.beans.exception.BusinessException;

import java.util.Collection;
import java.util.Map;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
public class Assert {

    protected Assert() {
        // to do noting
    }

    /**
     * 大于O
     */
    public static void gtZero(Integer num, StatusCode statusCode) {
        if (num == null || num <= 0) {
            fail(statusCode);
        }
    }

    /**
     * 大于等于O
     */
    public static void geZero(Integer num, StatusCode statusCode) {
        if (num == null || num < 0) {
            fail(statusCode);
        }
    }

    /**
     * num1大于num2
     */
    public static void gt(Integer num1, Integer num2, StatusCode statusCode) {
        if (num1 <= num2) {
            fail(statusCode);
        }
    }

    /**
     * num1大于等于num2
     */
    public static void ge(Integer num1, Integer num2, StatusCode statusCode) {
        if (num1 < num2) {
            fail(statusCode);
        }
    }

    /**
     * obj1 eq obj2
     */
    public static void eq(Object obj1, Object obj2, StatusCode statusCode) {
        if (!obj1.equals(obj2)) {
            fail(statusCode);
        }
    }

    public static void isTrue(boolean condition, StatusCode statusCode) {
        if (!condition) {
            fail(statusCode);
        }
    }

    public static void isFalse(boolean condition, StatusCode statusCode) {
        if (condition) {
            fail(statusCode);
        }
    }

    public static void isNull(StatusCode statusCode, Object... conditions) {
        if (ObjectUtil.isNull(conditions)) {
            fail(statusCode);
        }
    }

    public static void notNull(StatusCode statusCode, Object... conditions) {
        if (ObjectUtil.isNotNull(conditions)) {
            fail(statusCode);
        }
    }


    public static void isEmpty(StatusCode statusCode, Object... conditions) {
        if (ObjectUtil.isEmpty(conditions)) {
            fail(statusCode);
        }
    }

    public static void isNotEmpty(StatusCode statusCode, Object... conditions) {
        if (ObjectUtil.isNotEmpty(conditions)) {
            fail(statusCode);
        }
    }
    /**
     * 失败结果
     *
     * @param statusCode 异常错误码
     */
    public static void fail(StatusCode statusCode) {
        throw new BusinessException(statusCode);
    }

    public static void fail(boolean condition, StatusCode statusCode) {
        if (condition) {
            fail(statusCode);
        }
    }

    public static void fail(String message) {
        throw new BusinessException(message);
    }

    public static void fail(boolean condition, String message) {
        if (condition) {
            fail(message);
        }
    }

    public static void notEmpty(Object[] array, StatusCode statusCode) {
        if (ObjectUtil.isEmpty(array)) {
            fail(statusCode);
        }
    }

    public static void noNullElements(Object[] array, StatusCode statusCode) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    fail(statusCode);
                }
            }
        }
    }

    public static void notEmpty(Collection<?> collection, StatusCode statusCode) {
        if (CollUtil.isNotEmpty(collection)) {
            fail(statusCode);
        }
    }

    public static void notEmpty(Map<?, ?> map, StatusCode statusCode) {
        if (ObjectUtil.isEmpty(map)) {
            fail(statusCode);
        }
    }

    public static void isInstanceOf(Class<?> type, Object obj, StatusCode statusCode) {
        notNull(statusCode, type);
        if (!type.isInstance(obj)) {
            fail(statusCode);
        }
    }

    public static void isAssignable(Class<?> superType, Class<?> subType, StatusCode statusCode) {
        notNull(statusCode, superType);
        if (subType == null || !superType.isAssignableFrom(subType)) {
            fail(statusCode);
        }
    }
}
