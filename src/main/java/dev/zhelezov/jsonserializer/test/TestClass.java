package dev.zhelezov.jsonserializer.test;

import java.util.*;
import java.time.LocalDate;

public class TestClass {
    // Primitive types
    private int id;
    private String name;
    private boolean active;
    private double price;
    private LocalDate createdDate;

    // Collections
    private List<String> tags;
    private ArrayList<Integer> scores;
    private Set<Double> weights;
    private HashMap<String, Integer> properties;
    private Map<Integer, String> codeMap;

    // Arrays
    private String[] aliases;
    private int[] primeNumbers;

    // Nested objects
    private Address address;
    private List<PhoneNumber> phoneNumbers;
    private Map<String, Employee> teamMembers;

    // Static inner class
    public static class Address {
        private String street;
        private String city;
        private String country;
        private String postalCode;

        // Constructor, getters, setters would go here
    }

    // Another inner class
    public static class PhoneNumber {
        private String type; // "home", "work", etc.
        private String number;
        private boolean primary;

        // Constructor, getters, setters would go here
    }

    // Yet another nested class
    public static class Employee {
        private String employeeId;
        private String fullName;
        private Set<String> skills;

        // Constructor, getters, setters would go here
    }

    public static class Department {
        private String code;
        private String name;
        private Employee manager;

        // Constructor, getters, setters would go here
    }

    // Enum
    // public enum Status {
    //     ACTIVE, INACTIVE, PENDING, DELETED
    // }

    // private Status currentStatus;

    // Constructor
    public TestClass() {
        // Initialize collections to avoid null pointers
        tags = new ArrayList<>();
        scores = new ArrayList<>();
        weights = new HashSet<>();
        properties = new HashMap<>();
        codeMap = new TreeMap<>();
        phoneNumbers = new LinkedList<>();
        teamMembers = new LinkedHashMap<>();
    }

    // Getters and setters for all fields would go here
    // ...

    // Example method to populate with test data
    public void populateTestData() {
        this.id = 123;
        this.name = "Test Object";
        this.active = true;
        this.price = 19.99;
        this.createdDate = LocalDate.now();
        //this.currentStatus = Status.ACTIVE;

        // Collections
        this.tags = Arrays.asList("important", "test", "json");
        this.scores = new ArrayList<>(Arrays.asList(90, 85, 77));
        this.weights = new HashSet<>(Arrays.asList(1.2, 3.4, 5.6));
        this.properties = new HashMap<>();
        properties.put("width", 100);
        properties.put("height", 200);
        this.codeMap = new TreeMap<>();
        codeMap.put(1, "ONE");
        codeMap.put(2, "TWO");

        // Arrays
        this.aliases = new String[]{"alias1", "alias2"};
        this.primeNumbers = new int[]{2, 3, 5, 7, 11};

        // Nested objects
        this.address = new Address();
        address.street = "123 Main St";
        address.city = "Techville";
        address.country = "USA";
        address.postalCode = "12345";

        // Phone numbers
        PhoneNumber phone1 = new PhoneNumber();
        phone1.type = "mobile";
        phone1.number = "555-1234";
        phone1.primary = true;
        
        PhoneNumber phone2 = new PhoneNumber();
        phone2.type = "work";
        phone2.number = "555-5678";
        phone2.primary = false;
        
        this.phoneNumbers = Arrays.asList(phone1, phone2);

        // Team members
        Employee emp1 = new Employee();
        emp1.employeeId = "E100";
        emp1.fullName = "John Doe";
        emp1.skills = new HashSet<>(Arrays.asList("Java", "Spring"));
        
        Department dept1 = new Department();
        dept1.code = "DEV";
        dept1.name = "Development";
        dept1.manager = emp1;  // Circular reference
        
        
        Employee emp2 = new Employee();
        emp2.employeeId = "E200";
        emp2.fullName = "Jane Smith";
        emp2.skills = new HashSet<>(Arrays.asList("JavaScript", "React"));
        
        this.teamMembers = new HashMap<>();
        teamMembers.put("lead", emp1);
        teamMembers.put("senior", emp2);
    }
}
