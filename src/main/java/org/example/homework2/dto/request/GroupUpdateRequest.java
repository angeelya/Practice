package org.example.homework2.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.homework2.validation.GroupUnique;

public class GroupUpdateRequest {
    @Min(value = 1,message = "Id should be more than 1")
    @NotNull(message ="Id should be not null")
    private Long id;
    @NotNull(message = "Name should be not null")
    @GroupUnique
    @Size(min = 2, max = 250)
    private String name;

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
}
