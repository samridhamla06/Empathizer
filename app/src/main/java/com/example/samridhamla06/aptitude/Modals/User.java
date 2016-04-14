package com.example.samridhamla06.aptitude.Modals;

public class User {
    private String name;
    private int age;
    private String location;
    private String id;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;

    }

    public String toString() {
        return name;
    }

    public User(String name,String location, String id) {
        this.name = name;
        this.age = age;
        this.location = location;
        this.id = id;
    }

    public static User createUser(String username, String location, String id) {
        return new User(username,location, id);
    }


    public int getAge() {
        return age;
    }

    public String getLocation() {
        return location;
    }
}
