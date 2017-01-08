package com.positiverse.govhack.saas.model;

/**
 * Created by inst on 05.01.17.
 */
public class Organization implements Comparable<Organization> {

    private String address;
    private String name;
    private String description;
    private String logoFileName;

    public Organization(String address, String name, String description, String logoFileName) {
        this.address = address;
        this.name = name;
        this.description = description;
        this.logoFileName = logoFileName;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLogoFileName() {
        return logoFileName;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public int compareTo(Organization o) {
        return name.compareTo(o.name);
    }
}
