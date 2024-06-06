package org.example.homework2.dto.response;

import java.util.List;

public class TeacherResponse {
    private Long id;
    private String name;
    private String lastName;
    private String disciplineName;
    private List<GroupForTeacherResponse> groupForTeacherResponses;

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

    public String getDisciplineName() {
        return disciplineName;
    }

    public void setDisciplineName(String disciplineName) {
        this.disciplineName = disciplineName;
    }

    public List<GroupForTeacherResponse> getGroupForTeacherResponses() {
        return groupForTeacherResponses;
    }

    public void setGroupForTeacherResponses(List<GroupForTeacherResponse> groupForTeacherResponses) {
        this.groupForTeacherResponses = groupForTeacherResponses;
    }
}
