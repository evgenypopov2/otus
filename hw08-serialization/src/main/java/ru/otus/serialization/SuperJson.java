package ru.otus.serialization;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;

public class SuperJson {

    public String toJson(Object obj) throws IllegalAccessException {

        if (obj == null) {
            return "null";
        }

        String result = "";
        Class<?> clazz = obj.getClass();

        if (clazz.equals(Boolean.class)
                || clazz.equals(Integer.class)
                || clazz.equals(Long.class)
                || clazz.equals(Byte.class)
                || clazz.equals(Short.class)
                || clazz.equals(Float.class)
                || clazz.equals(Double.class)
        ) {
            result = obj.toString();
        } else if (clazz.equals(String.class) || clazz.equals(Character.class)) {
            result = "\"".concat(obj.toString().replace("'", "\\u0027")).concat("\"");
        } else if (clazz.isArray()) {
            result = array2Json(obj);
        } else if (obj instanceof Collection) {
            result = collection2Json(obj);
        } else {
            result = object2Json(clazz, obj);
        }
        return result;
    }

    private String object2Json(Class<?> clazz, Object obj) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder("{");

        for (Field field : clazz.getDeclaredFields()) {
            if (!Modifier.isTransient(field.getModifiers()) // not transient & not const
                    && !(Modifier.isFinal(field.getModifiers()) && Modifier.isStatic(field.getModifiers()))) {

                field.setAccessible(true);
                Object o = field.get(obj);

                if (o != null) {
                    sb.append(sb.length() > 1 ? ",\"" : "\"")
                            .append(field.getName())
                            .append("\":")
                            .append(toJson(o));
                }
            }
        }
        sb.append('}');
        return sb.toString();
    }

    private String array2Json(Object obj) throws IllegalAccessException {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0, length = Array.getLength(obj); i < length; i++) {
            result.append(result.length() > 1 ? "," : "").append(toJson(Array.get(obj, i)));
        }
        result.append(']');
        return result.toString();
    }

    private String collection2Json(Object obj) throws IllegalAccessException {
        StringBuilder result = new StringBuilder("[");
        for (Object o: (Collection) obj) {
            result.append(result.length() > 1 ? "," : "").append(toJson(o));
        }
        result.append(']');
        return result.toString();
    }
}
