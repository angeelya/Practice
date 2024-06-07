package org.example.homework2.database.model;

import java.util.List;


public class Discipline {
    private Long id;
    private String disciplineName;
    List<Teacher> teachers;

    public Discipline() {
    }

    public Discipline(Long id, String disciplineName) {
        this.id = id;
        this.disciplineName = disciplineName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisciplineName() {
        return disciplineName;
    }

    public void setDisciplineName(String disciplineName) {
        this.disciplineName = disciplineName;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }
}
