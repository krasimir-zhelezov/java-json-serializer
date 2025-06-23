package dev.zhelezov.jsonserializer.test;

import java.util.List;

import dev.zhelezov.jsonserializer.annotations.JsonExclude;

public class User {
    private String username;
    private String email;
    @JsonExclude private String password;
    private boolean isAdmin;
    private int age;
    @JsonExclude private List<String> interests;

    public User(int age, String email, boolean isAdmin, String password, String username, List<String> interests) {
        this.age = age;
        this.email = email;
        this.isAdmin = isAdmin;
        this.password = password;
        this.username = username;
        this.interests = interests;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
