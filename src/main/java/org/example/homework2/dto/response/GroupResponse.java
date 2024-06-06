package org.example.homework2.dto.response;

import java.util.List;

public class GroupResponse {
    private Long id;
    private String name;
    private List<StudentResponse> studentResponses;

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

    public List<StudentResponse> getStudentResponses() {
        return studentResponses;
    }

    public void setStudentResponses(List<StudentResponse> studentResponses) {
        this.studentResponses = studentResponses;
    }
}
