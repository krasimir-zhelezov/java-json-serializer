package dev.zhelezov.jsonserializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import dev.zhelezov.jsonserializer.test.TestClass;
import dev.zhelezov.jsonserializer.test.TestData;
import dev.zhelezov.jsonserializer.test.User;

public class App {
    public static void main(String[] args) throws Exception {
        User user = new User(1, null, true, "password", "username", new ArrayList<>(Arrays.asList("Hiking", "Dancing", "Programming")), new Date());
        TestData data = new TestData();
        data.setName("John Doe");
        data.setAge(30);
        data.setActive(true);
        data.setSalary(75000.50);
        data.setHobbies(Arrays.asList("Reading", "Hiking", "Photography"));
        data.setUsers(new User[] {
            new User(1, null, true, "password", "username", new ArrayList<>(Arrays.asList("Hiking", "Dancing", "Programming")), new Date()),
            new User(2, null, false, "password", "username", new ArrayList<>(Arrays.asList("Reading", "Dancing", "Programming")), new Date()),
            new User(3, null, true, "password", "username", new ArrayList<>(Arrays.asList("Reading", "Hiking", "Programming")),  new Date())
        });
        
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Math", 95);
        scores.put("Science", 88);
        scores.put("History", 92);
        data.setScores(scores);
        
        TestData.Address address = new TestData.Address();
        address.setStreet("123 Main St");
        address.setCity("Springfield");
        address.setZipCode("12345");
        data.setAddress(address);
        data.setBinaryData(new boolean[] {true, true, false});

        TestClass data2 = new TestClass();
        data2.populateTestData();

        //System.out.println(Serializer.serialize(user));
        // Serializer.generateJsonFile("user.json", user);
        //Serializer.generateJsonFile("data.json", data);
        Serializer.generateJsonFile("data2.json", data2);
    }
}