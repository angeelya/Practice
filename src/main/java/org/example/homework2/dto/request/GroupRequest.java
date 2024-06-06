package org.example.homework2.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class GroupRequest {
    @Min(value = 1,message = "GroupId should be more than 1")
    @NotNull(message ="GroupId should be not null")
    private Long groupId;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
