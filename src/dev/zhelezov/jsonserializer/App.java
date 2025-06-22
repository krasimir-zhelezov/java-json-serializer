package dev.zhelezov.jsonserializer;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        User user = new User(1, null, true, "password", "username", new ArrayList<>(Arrays.asList("Hiking", "Dancing", "Programming")));
        System.out.println(serialize(user));
    }

    public static String serialize(Object obj) throws IllegalArgumentException, IllegalAccessException {
        if (obj == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        Class<?> cls = obj.getClass();

        sb.append("{\n\"");
        sb.append(cls.getSimpleName());
        sb.append("\": {\n");

        Field fieldList[] = cls.getDeclaredFields();

        for (int i = 0; i < fieldList.length; i++) {
            boolean isLastIteration = (i == fieldList.length - 1);

            Field field = fieldList[i];

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

        sb.append("}\n}");

        return sb.toString();
    }

    public static String serializeField(Object obj) {
        StringBuilder sb = new StringBuilder();

        if (obj instanceof String) {
            sb.append("\"");
            sb.append(obj);
            sb.append("\"");
        } else if (obj instanceof Collection) {
            sb.append("[");
            if (obj instanceof List) {
                List<?> list = (List<?>) obj;
                for (int i = 0; i < ((Collection<?>) obj).size(); i++) {
                    boolean isLastIteration = (i == ((Collection<?>) obj).size() - 1);
                    sb.append(serializeField(list.get(i)));

                    if (!isLastIteration) {
                        sb.append(",");
                    }
                }
                sb.append("]");
            }
            
        } else {
            sb.append(obj);
        }

        return sb.toString();
    }
}
