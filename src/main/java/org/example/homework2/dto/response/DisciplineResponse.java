package org.example.homework2.dto.response;

import java.util.List;

public class DisciplineResponse {
    private Long id;
    private String discipline;
    private List<TeacherForDisciplineResponse> teacherDisciplineResponses;

    public DisciplineResponse() {
    }

    public DisciplineResponse(Long id, String discipline) {
        this.id = id;
        this.discipline = discipline;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public List<TeacherForDisciplineResponse> getTeacherDisciplineResponses() {
        return teacherDisciplineResponses;
    }

    public void setTeacherDisciplineResponses(List<TeacherForDisciplineResponse> teacherDisciplineResponses) {
        this.teacherDisciplineResponses = teacherDisciplineResponses;
    }
}
