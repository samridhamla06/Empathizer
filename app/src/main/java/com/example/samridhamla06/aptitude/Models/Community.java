package com.example.samridhamla06.aptitude.Models;

import java.util.List;

public class Community {
    private String sufferingName;
    private int numberOfGroups;
    private int numberOfUsers;
    private List<Group> groups;



    public Community(String sufferingName) {
        this.sufferingName = sufferingName;
    }

    @Override
    public String toString() {
        return sufferingName;
    }

    @Override
    public boolean equals(Object o) {//BASED ON COMMUNITY NAME
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Community community = (Community) o;

        return !(sufferingName != null ? !sufferingName.equals(community.sufferingName) : community.sufferingName != null);

    }

}
