package com.example.demo.util;

public class FilterUtil {

    public static boolean isNumber(Class<?> javaType) {
        return javaType == int.class || javaType == Integer.class
                || javaType == long.class || javaType == Long.class
                || javaType == float.class || javaType == Float.class
                || javaType == double.class || javaType == Double.class;
    }

    public static boolean isBoolean(Class<?> javaType) {
        return javaType == boolean.class || javaType == Boolean.class;
    }

    public static boolean isLocalDate(Class<?> javaType) {
        return javaType == java.time.LocalDate.class;
    }

    public static boolean isLocalTime(Class<?> javaType) {
        return javaType == java.time.LocalTime.class;
    }

    public static boolean isLocalDateTime(Class<?> javaType) {
        return javaType == java.time.LocalDateTime.class;
    }
}
