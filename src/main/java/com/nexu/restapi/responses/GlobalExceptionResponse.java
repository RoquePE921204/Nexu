package com.nexu.restapi.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GlobalExceptionResponse implements Serializable {

    private String message;

    private Map<String, String> messages;

    public GlobalExceptionResponse(String message) {
        this.message = message;
    }

    public GlobalExceptionResponse(Map<String, String> messages) {
        this.messages = messages;
    }
}
