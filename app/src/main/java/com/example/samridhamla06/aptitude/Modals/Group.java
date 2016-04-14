package com.example.samridhamla06.aptitude.Modals;

/**
 * Created by samridhamla06 on 14/03/16.
 */
public class Group {
    private String name;
    private String id;

    public Group(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
