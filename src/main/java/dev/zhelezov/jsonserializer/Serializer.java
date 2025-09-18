package dev.zhelezov.jsonserializer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

import dev.zhelezov.jsonserializer.annotations.JsonExclude;
import dev.zhelezov.jsonserializer.annotations.JsonProperty;
import dev.zhelezov.jsonserializer.exceptions.CircularReferenceException;

/**
 * Custom Java JSON serializer.
 * 
 * @author Krasimir Zhelezov
 * @version 1.0.0
 * @since 1.0.0
 */

public class Serializer {
    private static final Set<Class<?>> PRIMITIVE_WRAPPERS = Set.of(
        Integer.class, Long.class, Double.class, Float.class,
        Boolean.class, Character.class, Byte.class, Short.class, Void.class
    );

    private static Set<Object> visitedObjects = new HashSet<Object>();

    /**
     * Generate a JSON file in the specified path with the serialized object.
     * 
     * @param filePath The path where to save the JSON file.
     * @param obj      The object to serialize.
     * @throws IllegalArgumentException If the object is <code>null</code>.
     * @throws IllegalAccessException   If the object has fields that are not accessible.
     */
    public static void generateJsonFile(String filePath, Object obj) throws IllegalArgumentException, IllegalAccessException {
        String json = serialize(obj);

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("output/" + filePath))) {
            writer.write(json);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Serialize the specified object into a JSON string.
     * 
     * @param obj The object to serialize.
     * @return A JSON string representing the object.
     * @throws IllegalArgumentException If the object is <code>null</code>.
     * @throws IllegalAccessException   If the object has fields that are not accessible.
     */
    public static String serialize(Object obj) throws IllegalArgumentException, IllegalAccessException {
        if (obj == null) return "null";

        StringBuilder sb = new StringBuilder();

        Class<?> cls = obj.getClass();

        sb.append("{\n");

        Field[] fields = cls.getDeclaredFields();
        boolean firstField = true;

        for (Field field : fields) {
            int modifiers = field.getModifiers();

            if (Modifier.isTransient(modifiers) || Modifier.isFinal(modifiers) || field.isAnnotationPresent(JsonExclude.class)) {
                continue;
            }

            if (!firstField) {
                sb.append(",\n");
            } else {
                firstField = false;
            }

            field.setAccessible(true);
            String fieldName = field.isAnnotationPresent(JsonProperty.class) ? field.getAnnotation(JsonProperty.class).value() : field.getName();

            sb.append("\"").append(fieldName).append("\": ");

            Object fieldValue = field.get(obj);
            sb.append(serializeField(fieldValue));
        }

        sb.append("\n}");

        return sb.toString();
    }

    
    /**
     * Serialize a field into a JSON string.
     * 
     * @param obj The value of the field to serialize.
     * @return A JSON string representing the value of the field.
     * @throws IllegalArgumentException If the object is <code>null</code>.
     * @throws IllegalAccessException   If the object has fields that are not accessible.
     */
    private static String serializeField(Object obj) throws IllegalArgumentException, IllegalAccessException {
        if (obj == null) {
            return "null";
        }
        if (!PRIMITIVE_WRAPPERS.contains(obj.getClass()) && !(obj instanceof String)) {
            if (visitedObjects.contains(obj)) {
                throw new CircularReferenceException();
            }
            visitedObjects.add(obj);
        }

        

        StringBuilder sb = new StringBuilder();

        switch (obj) {
            case String s -> sb.append("\"").append(obj).append("\"");
            case Date d -> sb.append("\"").append(obj.toString()).append("\"");
            case Map <?, ?> m -> serializeMap(m, sb);
            case Collection<?> c -> serializeCollection(c, sb);
            case Object[] a -> serializeArray(a, sb);
            default -> {
                if (PRIMITIVE_WRAPPERS.contains(obj.getClass())) {
                    sb.append(obj);
                } else {
                    sb.append(serialize(obj));
                }
            }
        }

        visitedObjects.remove(obj);

        return sb.toString();
    }

    private static void serializeArray(Object obj, StringBuilder sb) throws IllegalArgumentException, IllegalAccessException {
        sb.append("[");
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            sb.append(serializeField(Array.get(obj, i)));
            if (i < length - 1) sb.append(",");
        }
        sb.append("]");
    }

    private static void serializeMap(Map<?, ?> map, StringBuilder sb) throws IllegalArgumentException, IllegalAccessException {
        sb.append("{\n");

        Iterator<? extends Map.Entry<?, ?>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<?, ?> entry = iterator.next();
            sb.append("\"").append(entry.getKey()).append("\": ");
            sb.append(serializeField(entry.getValue()));
            if (iterator.hasNext()) sb.append(",");
        }

        sb.append("\n}");
    }

    private static void serializeCollection(Collection<?> collection, StringBuilder sb) throws IllegalArgumentException, IllegalAccessException
    {
        sb.append("[");

        Iterator<?> iterator = collection.iterator();

        while (iterator.hasNext()) {
            sb.append(serializeField(iterator.next()));
            if (iterator.hasNext()) sb.append(",");
        }
        sb.append("]");
    }
}