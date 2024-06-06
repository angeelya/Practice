package org.example.homework2.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.homework2.validation.NameContent;

public class StudentUpdateRequest {
    @Min(value = 1,message = "Id should be more than 1")
    @NotNull(message ="Id should be not null")
    private Long id;
    @NotNull(message = "Name should be not null")
    @Size(min = 2, max = 250)
    @NameContent
    private String name;
    @NotNull(message = "LastName should be not null")
    @Size(min = 2, max = 250)
    @NameContent
    private String lastName;
    @Min(value = 1,message = "GroupId should be more than 1")
    @NotNull(message ="GroupId should be not null")
    private Long groupId;

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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
