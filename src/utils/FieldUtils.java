package utils;

import exception.InvalidDataException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.util.*;

public class FieldUtils {

    private static String[] merge(String[] array1, String[] array2) {
        String[] merged = new String[array1.length + array2.length];
        System.arraycopy(array1, 0, merged, 0, array1.length);
        System.arraycopy(array2, 0, merged, array1.length, array2.length);
        return merged;
    }

    private static Field[] merge(Field[] array1, Field[] array2) {
        Field[] merged = new Field[array1.length + array2.length];
        System.arraycopy(array1, 0, merged, 0, array1.length);
        System.arraycopy(array2, 0, merged, array1.length, array2.length);
        return merged;
    }

    public static String[] getEditOptions(Class<?> clazz) {
        Field[] fields = merge(clazz.getSuperclass().getDeclaredFields(), clazz.getDeclaredFields());

        List<String> validFieldNames = new ArrayList<>();

        for (Field field : fields) {
            if (!field.isSynthetic() && !Modifier.isStatic(field.getModifiers())) {
                validFieldNames.add(field.getName());
            }
        }

        String[] subOptions = {"Delete", "Return to menu"};

        List<String> options = new ArrayList<>(validFieldNames);
        options.addAll(Arrays.asList(subOptions));

        return options.toArray(new String[0]);
    }

    public static String[] getFields(Class<?> clazz) {
        Field[] fields = merge(clazz.getSuperclass().getDeclaredFields(), clazz.getDeclaredFields());

        List<String> validFieldNames = new ArrayList<>();

        for (Field field : fields) {
            if (!field.isSynthetic() && !Modifier.isStatic(field.getModifiers())) {
                validFieldNames.add(field.getName());
            }
        }

        return validFieldNames.toArray(new String[0]);
    }

    public static Field getFieldByName(Class<?> clazz, String fieldName) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null) {
            return getFieldByName(superclass, fieldName);
        }
        return null;
    }

    public static Object convertValue(Object value, Class<?> targetType) throws ParseException, InvalidDataException {
        if (targetType == Date.class && value instanceof String) {
            return GlobalUtils.dateParse((String) value);
        } else if ((targetType == Double.class || targetType == double.class) && value instanceof String) {
            return Double.valueOf((String) value);
        } else if ((targetType == Integer.class || targetType == int.class) && value instanceof String) {
            return Integer.valueOf((String) value);
        } else if ((targetType == Boolean.class) || targetType == boolean.class && value instanceof String) {
            return Boolean.valueOf((String) value);
        }
        return null;
    }

    private static boolean isAssignable(Class<?> targetType, Class<?> valueType) {
        if (targetType.isAssignableFrom(valueType)) {
            return true;
        }
        if (targetType.isPrimitive()) {
            if (targetType == int.class && valueType == Integer.class) {
                return true;
            }
            if (targetType == double.class && valueType == Double.class) {
                return true;
            }
            if (targetType == boolean.class && valueType == Boolean.class) {
                return true;
            }
            if (targetType == long.class && valueType == Long.class) {
                return true;
            }
            if (targetType == float.class && valueType == Float.class) {
                return true;
            }
            if (targetType == char.class && valueType == Character.class) {
                return true;
            }
            if (targetType == short.class && valueType == Short.class) {
                return true;
            }
            if (targetType == byte.class && valueType == Byte.class) {
                return true;
            }
        }
        return false;
    }

    public static Map<String, Object> getFieldValueByName(Object obj, String fieldName, Object newValue) throws Exception {
        Field field = getFieldByName(obj.getClass(), fieldName);
        Map<String, Object> feildValue = new HashMap<>();
        if (field != null) {
            field.setAccessible(true);

            if (!isAssignable(field.getType(), newValue.getClass())) {

                Object convertedValue = convertValue(newValue, field.getType());
                if (convertedValue == null) {
                    String[] strArr = field.getType().toString().replace(".", ",").split(",");
                    throw new InvalidDataException("Invalid " + strArr[strArr.length - 1]);
                }
                newValue = convertedValue;
                feildValue.put(fieldName, newValue);
            }
            return feildValue;

        } else {
            throw new NoSuchFieldException("No field named " + fieldName + " found.");
        }
    }
}
