package org.example.homework2.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class TeachingRequest {
    @Min(value = 1,message = "GroupId should be more than 1")
    @NotNull(message ="GroupId should be not null")
    private Long groupId;
    @Min(value = 1,message = "TeacherId should be more than 1")
    @NotNull(message ="TeacherId should be not null")
    private Long teacherId;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
}
