package dev.zhelezov.jsonserializer;

import java.lang.reflect.*;

public class App {
    public static void main(String[] args) throws Exception {
        StringBuilder sb = new StringBuilder();

        User user = new User(1, null, true, "password", "username");  // Example object
        Class<?> cls = user.getClass();

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

            Object fieldValue = field.get(user);

            if (field.getType() == String.class && fieldValue != null) {
                sb.append("\"");
                sb.append(fieldValue);
                sb.append("\"");
            } else {
                sb.append(fieldValue);
            }
            if (!isLastIteration) {
                sb.append(",");
            }
            sb.append("\n");

            field.setAccessible(false);
        }

        sb.append("}\n}");

        System.out.println(sb.toString());
    }
}
