package ru.otus.serialization;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;

public class SuperJson {

    public String toJson(Object obj) throws IllegalAccessException {

        if (obj == null) {
            return "null";
        }

        Class<?> clazz = obj.getClass();
        StringBuilder result = new StringBuilder("{");

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            result.append(result.length() > 1 ? ",\"" : "\"")
                    .append(field.getName())
                    .append("\":")
                    .append(attr2Json(field.get(obj)));
        }
        result.append('}');
        return result.toString();
    }

    private String attr2Json(Object obj) throws IllegalAccessException {

        if (obj == null) {
            return "null";
        }

        Class<?> clazz = obj.getClass();

        if (clazz.equals(Boolean.class)
                || clazz.equals(Integer.class)
                || clazz.equals(Long.class)
                || clazz.equals(Byte.class)
                || clazz.equals(Short.class)
        ) {
            return obj.toString();
        } else if (clazz.equals(String.class) || clazz.equals(Character.class)) {
            return "\"".concat(obj.toString().replace("'", "\\u0027")).concat("\"");
        } else if (clazz.isArray()) {
            return array2Json(obj);
        } else if (obj instanceof Collection) {
            return collection2Json(obj);
        }
        return "";
    }

    private String array2Json(Object obj) throws IllegalAccessException {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0, length = Array.getLength(obj); i < length; i++) {
            result.append(result.length() > 1 ? "," : "").append(attr2Json(Array.get(obj, i)));
        }
        result.append(']');
        return result.toString();
    }

    private String collection2Json(Object obj) throws IllegalAccessException {
        StringBuilder result = new StringBuilder("[");
        for (Object o: (Collection) obj) {
            result.append(result.length() > 1 ? "," : "").append(attr2Json(o));
        }
        result.append(']');
        return result.toString();
    }
}
