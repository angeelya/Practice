package org.example.homework2.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.homework2.validation.GroupUnique;

public class GroupAddRequest {
    @NotNull(message = "Name should be not null")
    @GroupUnique
    @Size(min = 2, max = 250)
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

