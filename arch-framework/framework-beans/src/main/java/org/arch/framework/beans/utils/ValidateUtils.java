package org.arch.framework.beans.utils;

import org.apache.commons.lang3.StringUtils;

import javax.validation.*;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class ValidateUtils {
    private static final Validator validator;
    public static Predicate<Object> NOT_NULL;
    public static Predicate<Object> NULL;
    public static Predicate<String> NOT_BLANK;
    public static Predicate<String> PHONE;
    public static Predicate<Object> NOT_EMPTY;
    public static Function<String, Predicate<String>> PATTERN;
    public static Function<Number, Predicate<Number>> LESS_THAN;
    public static Function<Number, Predicate<Number>> GREATER_THAN;

    public ValidateUtils() {
    }

    public static <T> void validate(T t) {
        validate(validator.validate(t, new Class[0]));
    }

    private static <T> void validate(Set<ConstraintViolation<T>> constraintViolations) {
        if (constraintViolations != null && !constraintViolations.isEmpty()) {
            Optional<ConstraintViolation<T>> optional = constraintViolations.stream().findFirst();
            if (optional.isPresent()) {
                ConstraintViolation<T> constraintViolation = (ConstraintViolation) optional.get();
                throw new ValidationException(constraintViolation.getPropertyPath() + " error: " + constraintViolation.getMessage());
            }
        }

    }

    public static <T> void validate(T t, String... fields) {
        String[] var2 = fields;
        int var3 = fields.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            String field = var2[var4];
            validate(validator.validateProperty(t, field, new Class[0]));
        }

    }

    public static <T> void validate(Class<T> beanType, String field, Object value) {
        validate(validator.validateValue(beanType, field, value, new Class[0]));
    }

    public static <T> void validate(T t, Predicate<T> predicate, String message) {
        if (!predicate.test(t)) {
            throw new ValidationException(message);
        }
    }

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        NOT_NULL = (v) -> {
            return v != null;
        };
        NULL = (v) -> {
            return v == null;
        };
        NOT_BLANK = (v) -> {
            return !StringUtils.isEmpty(v);
        };
        PHONE = (v) -> {
            return v.matches("1[\\d]{10}");
        };
        NOT_EMPTY = (v) -> {
            if (v == null) {
                return false;
            } else {
                Class<?> clazz = v.getClass();
                if (clazz.isArray()) {
                    return ((Object[]) ((Object[]) v)).length != 0;
                } else if (Iterable.class.isAssignableFrom(clazz)) {
                    return ((Iterable) v).iterator().hasNext();
                } else if (Map.class.isAssignableFrom(clazz)) {
                    return !((Map) v).isEmpty();
                } else {
                    return true;
                }
            }
        };
        PATTERN = (t) -> {
            return (r) -> {
                return r.matches(t);
            };
        };
        LESS_THAN = (t) -> {
            return (r) -> {
                return ((Comparable) r).compareTo((Comparable) t) < 0;
            };
        };
        GREATER_THAN = (t) -> {
            return (r) -> {
                return ((Comparable) r).compareTo((Comparable) t) > 0;
            };
        };
    }
}
