package org.example.homework2.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class TeacherRequest {
    @Min(value = 1,message = "TeacherId should be more than 1")
    @NotNull(message ="TeacherId should be not null")
    private Long teacherId;

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
}
