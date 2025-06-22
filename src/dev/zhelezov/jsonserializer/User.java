package dev.zhelezov.jsonserializer;

public class User {
    private String username;
    private String email;
    private String password;
    private boolean isAdmin;
    private int age;

    public User(int age, String email, boolean isAdmin, String password, String username) {
        this.age = age;
        this.email = email;
        this.isAdmin = isAdmin;
        this.password = password;
        this.username = username;
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
