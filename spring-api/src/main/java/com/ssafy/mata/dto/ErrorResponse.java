package com.ssafy.mata.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    private String message;
    public ErrorResponse(final String message) {
        this.message = message;
    }

    public ErrorResponse of(){
        return new ErrorResponse(message);
    }
}
