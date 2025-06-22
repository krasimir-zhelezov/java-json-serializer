package dev.zhelezov.jsonserializer;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class App {
    public static void main(String[] args) throws Exception {
        // User user = new User(1, null, true, "password", "username", new ArrayList<>(Arrays.asList("Hiking", "Dancing", "Programming")));
        TestData data = new TestData();
        data.setName("John Doe");
        data.setAge(30);
        data.setActive(true);
        data.setSalary(75000.50);
        data.setHobbies(Arrays.asList("Reading", "Hiking", "Photography"));
        
        // Map<String, Integer> scores = new HashMap<>();
        // scores.put("Math", 95);
        // scores.put("Science", 88);
        // scores.put("History", 92);
        // data.setScores(scores);
        
        TestData.Address address = new TestData.Address();
        address.setStreet("123 Main St");
        address.setCity("Springfield");
        address.setZipCode("12345");
        data.setAddress(address);

        System.out.println(serialize(data));
    }

    private static final Set<Class<?>> PRIMITIVE_WRAPPERS = Set.of(
        Integer.class, Long.class, Double.class, Float.class,
        Boolean.class, Character.class, Byte.class, Short.class, Void.class
    );

    public static String serialize(Object obj) throws IllegalArgumentException, IllegalAccessException {
        if (obj == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        Class<?> cls = obj.getClass();

        sb.append("{\n");

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
            
        } else if (PRIMITIVE_WRAPPERS.contains(obj.getClass())) {
            System.out.println("Primitive");
            sb.append(obj);
        } else {
            sb.append(serialize(obj));
        }

        return sb.toString();
    }
}
