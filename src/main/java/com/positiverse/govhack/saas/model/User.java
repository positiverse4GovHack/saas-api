package com.positiverse.govhack.saas.model;

/**
 * Created by inst on 05.01.17.
 */
public class User implements Comparable<User> {

    private String address;
    private String name;

    public User(String address, String name) {
        this.address = address;
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{" +
                "address='" + address + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(User o) {
        return name.compareTo(o.name);
    }
}
