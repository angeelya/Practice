package org.example.homework1;

/**
 * @author Angelina
 *
 * The `User` class represents a user with basic information such as name, age, and address.
 * It implements the `Comparable` interface to allow sorting based on the user's name.
 */

public class User implements Comparable<User>{
    private String name;
    private Integer age;
    private String address;

    public User() {
    }

    public User(String name, Integer age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public int compareTo(User user) {
        return this.name.compareTo(user.name);
    }
}
