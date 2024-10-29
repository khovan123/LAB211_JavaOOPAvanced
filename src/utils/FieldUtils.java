
package utils;

import exception.InvalidDataException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.*;

public class FieldUtils {

    private static Object[] merge(Object[] array1, Object[] array2) {
        Object[] mergeObj = new Object[array1.length + array2.length];
        System.arraycopy(array1, 0, mergeObj, 0, array1.length);
        System.arraycopy(array2, 0, mergeObj, array1.length, array2.length);
        return mergeObj;
    }

    public static String[] getEditOptions(Class<?> clazz) {
        Field[] fields = (Field[]) merge(clazz.getClass().getSuperclass().getDeclaredFields(), clazz.getClass().getDeclaredFields());
        String subOptions[] = {
            "Delete",
            "Return to menu",};
        String[] fieldsString = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            fieldsString[i] = fields[i].getName();
        }
        String[] editMenuOptions = (String[]) merge(fieldsString, subOptions);
        return editMenuOptions;
    }

    public static String[] getFields(Class<?> clazz) {
        Field[] fields = (Field[]) merge(clazz.getClass().getSuperclass().getDeclaredFields(), clazz.getClass().getDeclaredFields());
        String[] fieldsString = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            fieldsString[i] = fields[i].getName();
        }
        return fieldsString;
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
            return GlobalUtils.getDate((String) value);
        } else if ((targetType == Double.class || targetType == double.class) && value instanceof String) {
            return Double.valueOf((String) value);
        } else if ((targetType == Integer.class || targetType == int.class) && value instanceof String) {
            return Integer.valueOf((String) value);
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
