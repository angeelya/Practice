package org.example.homework2.database.model;


public class Student {
    private Long id;
    private String name;
    private String lastName;
    private Group group;

    public Student() {
    }

    public Student(Long id, String name, String lastName, Group group) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.group = group;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
