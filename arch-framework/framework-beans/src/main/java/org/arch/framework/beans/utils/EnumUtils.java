//package org.arch.framework.beans.utils;
//
//import java.util.Collection;
//import org.apache.commons.lang3.StringUtils;
//
//import javax.validation.constraints.NotNull;
//
//public final class EnumUtils {
//    private EnumUtils() {
//    }
//
//    @NotNull
//    public static <E extends Enum<E>> E getEnum(Collection<E> enumList, Class<E> eClass, String value) {
//        E e = getEnumWithoutException(enumList, value);
//        return e;
//    }
//
//    public static <E extends Enum<E> > E getEnumWithoutException(Collection<E> enumList, String value) {
//        return (Enum)enumList.stream().filter((n) -> {
//            return StringUtils.equals(n.getValue(), value);
//        }).findAny().orElse((Object)null);
//    }
//
//    @NotNull
//    public static <E extends Enum<E>> E getEnumFromInt(Collection<E> enumList, Class<E> eClass, int value) {
//        E t = getEnumFromIntWithoutException(enumList, value);
//        return t;
//    }
//
//    public static <E extends Enum<E>> E getEnumFromIntWithoutException(Collection<E> enumList, int value) {
//        return (Enum)enumList.stream().filter((n) -> {
//            return n.getValue() == value;
//        }).findAny().orElse((Object)null);
//    }
//
//    @NotNull
//    public static <E extends Enum<E>> E getEnumFromLong(Collection<E> enumList, Class<E> eClass, long value) {
//        E t = getEnumFromLongWithoutException(enumList, value);
//        return t;
//    }
//
//    public static <E extends Enum<E>> E getEnumFromLongWithoutException(Collection<E> enumList, long value) {
//        return (Enum)enumList.stream().filter((n) -> {
//            return n.getValue() == value;
//        }).findAny().orElse((Object)null);
//    }
//}
