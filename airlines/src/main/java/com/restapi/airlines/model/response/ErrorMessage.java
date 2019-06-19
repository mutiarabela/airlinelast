package com.restapi.airlines.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
public class ErrorMessage {
    private int status;
    private String message;
    private String[] errors;
}
