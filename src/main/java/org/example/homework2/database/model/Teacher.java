package org.example.homework2.database.model;

import java.util.List;

public class Teacher {
    private Long id;
    private String name;
    private String lastName;

    private Discipline discipline;
    private List<Group> groups;

    public Teacher() {
    }

    public Teacher(Long id, String name, String lastName, Discipline discipline) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.discipline = discipline;
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

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
