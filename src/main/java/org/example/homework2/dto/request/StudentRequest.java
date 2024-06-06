package org.example.homework2.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class StudentRequest {
    @Min(value = 1,message = "StudentId should be more than 1")
    @NotNull(message ="StudentId should be not null")
    private Long studentId;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
