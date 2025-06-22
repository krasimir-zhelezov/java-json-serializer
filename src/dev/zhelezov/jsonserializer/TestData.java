package dev.zhelezov.jsonserializer;

import java.util.List;
import java.util.Map;

public class TestData {
    private String name;
    private int age;
    private boolean isActive;
    private double salary;
    private List<String> hobbies;
    private Map<String, Integer> scores;
    private Address address;
    private User[] users;
    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    private boolean[] binaryData;
    public boolean[] getBinaryData() {
        return binaryData;
    }

    public void setBinaryData(boolean[] binaryData) {
        this.binaryData = binaryData;
    }

    private transient String temporaryData; // Should be ignored by serializer
    private static final String CLASS_VERSION = "1.0"; // Should be ignored
    
    // Nested class
    public static class Address {
        private String street;
        private String city;
        private String zipCode;
        
        public Address() {}
        
        public Address(String street, String city, String zipCode) {
            this.street = street;
            this.city = city;
            this.zipCode = zipCode;
        }
        
        // Getters and setters
        public String getStreet() { return street; }
        public void setStreet(String street) { this.street = street; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getZipCode() { return zipCode; }
        public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    }
    
    // Constructors
    public TestData() {}
    
    public TestData(String name, int age, boolean isActive, double salary, boolean[] binaryData) {
        this.name = name;
        this.age = age;
        this.isActive = isActive;
        this.salary = salary;
        this.binaryData = binaryData;
    }
    
    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
    public List<String> getHobbies() { return hobbies; }
    public void setHobbies(List<String> hobbies) { this.hobbies = hobbies; }
    public Map<String, Integer> getScores() { return scores; }
    public void setScores(Map<String, Integer> scores) { this.scores = scores; }
    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }
    public String getTemporaryData() { return temporaryData; }
    public void setTemporaryData(String temporaryData) { this.temporaryData = temporaryData; }
    
    @Override
    public String toString() {
        return "TestData{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", isActive=" + isActive +
                ", salary=" + salary +
                ", hobbies=" + hobbies +
                // ", scores=" + scores +
                ", address=" + address +
                '}';
    }
}