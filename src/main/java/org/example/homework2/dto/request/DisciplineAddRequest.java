package org.example.homework2.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.homework2.validation.DisciplineUnique;

public class DisciplineAddRequest {
    @NotNull(message = "Discipline should be not null")
    @DisciplineUnique
    @Size(min = 2, max = 250)
    private String discipline;

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }
}
