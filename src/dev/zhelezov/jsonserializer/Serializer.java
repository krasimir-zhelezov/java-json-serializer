package dev.zhelezov.jsonserializer;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Serializer {
    private static final Set<Class<?>> PRIMITIVE_WRAPPERS = Set.of(
        Integer.class, Long.class, Double.class, Float.class,
        Boolean.class, Character.class, Byte.class, Short.class, Void.class
    );

    public static String serialize(Object obj) throws IllegalArgumentException, IllegalAccessException {
        if (obj == null) return "null";

        StringBuilder sb = new StringBuilder();

        Class<?> cls = obj.getClass();

        sb.append("{\n");

        Field fieldArray[] = cls.getDeclaredFields();
        ArrayList<Field> fieldList = new ArrayList<Field>();

        for (int i = 0; i < fieldArray.length; i++) {
            if (Modifier.isTransient(fieldArray[i].getModifiers()) || Modifier.isFinal(fieldArray[i].getModifiers())) {
                continue;
            }

            fieldList.add(fieldArray[i]);
        }

        for (int i = 0; i < fieldList.size(); i++) {
            boolean isLastIteration = (i == fieldList.size() - 1);

            Field field = fieldList.get(i);

            if (Modifier.isTransient(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {
                continue;
            }

            field.setAccessible(true);

            sb.append("\"");
            sb.append(field.getName());
            sb.append("\": ");

            Object fieldValue = field.get(obj);

            sb.append(serializeField(fieldValue));

            if (!isLastIteration) {
                sb.append(",");
            }

            sb.append("\n");

            field.setAccessible(false);
        }

        sb.append("}");

        return sb.toString();
    }

    public static String serializeField(Object obj) throws IllegalArgumentException, IllegalAccessException {
        if (obj == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        if (obj instanceof String) {
            sb.append("\"");
            sb.append(obj);
            sb.append("\"");
        } else if (obj instanceof Collection) {
            sb.append("[");

            List<?> list;
            
            if (obj instanceof Set) {
                list = new ArrayList<>((Set) obj);
            } else {
                list = (List<?>) obj;
            }
            for (int i = 0; i < ((Collection<?>) obj).size(); i++) {
                boolean isLastIteration = (i == ((Collection<?>) obj).size() - 1);
                sb.append(serializeField(list.get(i)));

                if (!isLastIteration) {
                    sb.append(",");
                }
            }
            sb.append("]");
            
        } else if (obj.getClass().isArray()) {
            sb.append("[");
            int length = Array.getLength(obj);
            for (int i = 0; i < length; i++) {
                Object element = Array.get(obj, i);
                sb.append(serializeField(element));
                if (i != length - 1) {
                    sb.append(",");
                }
            }
            sb.append("]");
        } else if (obj instanceof Map) {
            sb.append("{\n");

            for (int i = 0; i < ((Map<?, ?>) obj).size(); i++) {
                boolean isLastIteration = (i == ((Map<?, ?>) obj).size() - 1);
                sb.append("\"");
                sb.append(((Map<?, ?>) obj).keySet().toArray()[i]);
                sb.append("\": ");
                sb.append(serializeField(((Map<?, ?>) obj).values().toArray()[i]));
                if (!isLastIteration) {
                    sb.append(",");
                }
            }

            sb.append("\n}");
        } else if (PRIMITIVE_WRAPPERS.contains(obj.getClass())) {
            sb.append(obj);
        } else {
            sb.append(serialize(obj));
        }

        return sb.toString();
    }
}