package org.example.homework2.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class DisciplineRequest {
    @Min(value = 1,message = "DisciplineId should be more than 1")
    @NotNull(message ="DisciplineId should be not null")
    private Long disciplineId;
    public Long getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(Long disciplineId) {
        this.disciplineId = disciplineId;
    }
}
