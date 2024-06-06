package org.example.homework2.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SearchRequest {
    @NotNull(message = "Key should be not null")
    @Size(max = 250)
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
