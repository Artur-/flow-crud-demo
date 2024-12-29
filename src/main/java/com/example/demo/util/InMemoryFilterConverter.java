package com.example.demo.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.vaadin.flow.data.binder.BeanPropertySet;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.internal.BeanUtil;
import com.vaadin.flow.spring.data.filter.AndFilter;
import com.vaadin.flow.spring.data.filter.Filter;
import com.vaadin.flow.spring.data.filter.OrFilter;
import com.vaadin.flow.spring.data.filter.PropertyStringFilter;

public class InMemoryFilterConverter {

    public static <T> SerializablePredicate<T> convert(Filter filter, Class<T> type) {
        return convert(filter, type, Locale.ENGLISH);
    }

    public static <T> SerializablePredicate<T> convert(Filter filter, Class<T> type, Locale locale) {
        if (filter == null) {
            return (item) -> true;
        }
        if (filter instanceof AndFilter andFilter) {
            List<SerializablePredicate<T>> children = (List) andFilter.getChildren().stream()
                    .map(f -> InMemoryFilterConverter.convert(f, type, locale)).collect(Collectors.toList());
            return product -> children.stream().allMatch(predicate -> predicate.test(product));
        } else if (filter instanceof OrFilter orFilter) {
            List<SerializablePredicate<T>> children = (List) orFilter.getChildren().stream()
                    .map(f -> InMemoryFilterConverter.convert(f, type, locale)).collect(Collectors.toList());
            return product -> children.stream().anyMatch(predicate -> predicate.test(product));
        } else if (filter instanceof PropertyStringFilter propertyStringFilter) {
            return createPropertyFilter(propertyStringFilter, type, locale);
        }

        throw new IllegalArgumentException("Unknown filter type: " + filter.getClass());
    }

    private static <T> SerializablePredicate<T> createPropertyFilter(PropertyStringFilter filter, Class<T> type,
            Locale locale) {
        BeanPropertySet.get(type);
        Class<?> javaType;
        try {
            javaType = BeanUtil.getPropertyType(type, filter.getPropertyId());
            PropertyDescriptor propDesc = BeanUtil.getPropertyDescriptor(type, filter.getPropertyId());
            String propertyPath = filter.getPropertyId();

            if (javaType == String.class) {
                return createStringFilter(filter, locale);
            } else if (FilterUtil.isNumber(javaType)) {
                return createNumberFilter(filter);
            }

        } catch (IntrospectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String value = filter.getFilterValue();

        return t -> true;
    }

    private static <T> SerializablePredicate<T> createNumberFilter(PropertyStringFilter filter) {
        return (T item) -> {
            Object value = getBeanValue(item, filter.getPropertyId());
            Object filterValue = null;
            if (value != null) {
                filterValue = convertToNumber(filter.getFilterValue(), value.getClass());
            }

            switch (filter.getMatcher()) {
                case EQUALS:
                    if (value == null) {
                        return filterValue == null;
                    }
                    return value.equals(filterValue);
                case CONTAINS:
                    throw new IllegalArgumentException(
                            "A number cannot be filtered using contains");
                case GREATER_THAN:
                    return greaterThan(value, filterValue);
                case LESS_THAN:
                    return lessThan(value, filterValue);
                default:
                    break;
            }
            throw new IllegalArgumentException(
                    "Unknown filter type: " + filter.getMatcher());

        };
    }

    private static Object convertToNumber(String filterValue, Class<? extends Object> class1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToNumber'");
    }

    private static boolean lessThan(Object value, Object filterValue) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'lessThan'");
    }

    private static boolean greaterThan(Object value, Object filterValue) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'greaterThan'");
    }

    private static <T> SerializablePredicate<T> createStringFilter(PropertyStringFilter filter, Locale locale) {
        return (T item) -> {
            String value = (String) getBeanValue(item, filter.getPropertyId());
            switch (filter.getMatcher()) {
                case EQUALS:
                    if (value == null || filter.getFilterValue() == null) {
                        return value == filter.getFilterValue();
                    }
                    return value.equalsIgnoreCase(filter.getFilterValue());
                case CONTAINS:
                    if (value == null || filter.getFilterValue() == null) {
                        return false;
                    }
                    return format(value, locale).contains(format(filter.getFilterValue(), locale));
                case GREATER_THAN:
                    throw new IllegalArgumentException(
                            "A string cannot be filtered using greater than");
                case LESS_THAN:
                    throw new IllegalArgumentException(
                            "A string cannot be filtered using less than");
                default:
                    break;
            }
            throw new IllegalArgumentException(
                    "Unknown filter type: " + filter.getMatcher());
        };
    }

    private static <T> String getBeanValue(T item, String propertyPath) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBeanValue'");
    }

    private static String format(String value, Locale locale) {
        if (value == null) {
            return null;
        }

        return value.toLowerCase(locale);
    }

    // public void foo() {
    // if (isBoolean(javaType)) {
    // Boolean booleanValue = Boolean.valueOf(value);
    // switch (filter.getMatcher()) {
    // case EQUALS:
    // return criteriaBuilder.equal(propertyPath, booleanValue);
    // case CONTAINS:
    // throw new IllegalArgumentException(
    // "A boolean cannot be filtered using contains");
    // case GREATER_THAN:
    // throw new IllegalArgumentException(
    // "A boolean cannot be filtered using greater than");
    // case LESS_THAN:
    // throw new IllegalArgumentException(
    // "A boolean cannot be filtered using less than");
    // default:
    // break;
    // }
    // } else if (isLocalDate(javaType)) {
    // var path = root.<LocalDate>get(filter.getPropertyId());
    // var dateValue = LocalDate.parse(value);
    // switch (filter.getMatcher()) {
    // case EQUALS:
    // return criteriaBuilder.equal(path, dateValue);
    // case CONTAINS:
    // throw new IllegalArgumentException(
    // "A date cannot be filtered using contains");
    // case GREATER_THAN:
    // return criteriaBuilder.greaterThan(path, dateValue);
    // case LESS_THAN:
    // return criteriaBuilder.lessThan(path, dateValue);
    // default:
    // break;
    // }
    // } else if (isLocalTime(javaType)) {
    // var path = root.<LocalTime>get(filter.getPropertyId());
    // var timeValue = LocalTime.parse(value);
    // switch (filter.getMatcher()) {
    // case EQUALS:
    // return criteriaBuilder.equal(path, timeValue);
    // case CONTAINS:
    // throw new IllegalArgumentException(
    // "A time cannot be filtered using contains");
    // case GREATER_THAN:
    // return criteriaBuilder.greaterThan(path, timeValue);
    // case LESS_THAN:
    // return criteriaBuilder.lessThan(path, timeValue);
    // default:
    // break;
    // }
    // } else if (isLocalDateTime(javaType)) {
    // var path = root.<LocalDateTime>get(filter.getPropertyId());
    // var dateValue = LocalDate.parse(value);
    // var minValue = LocalDateTime.of(dateValue, LocalTime.MIN);
    // var maxValue = LocalDateTime.of(dateValue, LocalTime.MAX);
    // switch (filter.getMatcher()) {
    // case EQUALS:
    // return criteriaBuilder.between(path, minValue, maxValue);
    // case CONTAINS:
    // throw new IllegalArgumentException(
    // "A datetime cannot be filtered using contains");
    // case GREATER_THAN:
    // return criteriaBuilder.greaterThan(path, maxValue);
    // case LESS_THAN:
    // return criteriaBuilder.lessThan(path, minValue);
    // default:
    // break;
    // }
    // } else if (javaType.isEnum()) {
    // var enumValue = Enum.valueOf(javaType.asSubclass(Enum.class),
    // value);

    // switch (filter.getMatcher()) {
    // case EQUALS:
    // return criteriaBuilder.equal(propertyPath, enumValue);
    // case CONTAINS:
    // throw new IllegalArgumentException(
    // "An enum cannot be filtered using contains");
    // case GREATER_THAN:
    // throw new IllegalArgumentException(
    // "An enum cannot be filtered using greater than");
    // case LESS_THAN:
    // throw new IllegalArgumentException(
    // "An enum cannot be filtered using less than");
    // default:
    // break;
    // }
    // }
    // throw new IllegalArgumentException("No implementation for " + javaType
    // + " using " + filter.getMatcher() + ".");
    // }

    // }

    private static Class<?> getJavaType(Class<?> type, String propertyId) {
        String[] parts = propertyId.split("\\.", 2);
        String firstPropertyId = parts[0];
        Class<?> firstPropertyType = null; // getType(type, firstPropertyId)

        if (parts.length > 1) {
            return getJavaType(firstPropertyType, parts[1]);
        }
        return firstPropertyType;

    }

}
