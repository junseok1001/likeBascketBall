package com.sourjelly.likebasketball.common.global;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String message;

    private final List<FieldErrorDetail> fieldErrors;

    @Getter
    @AllArgsConstructor
    public static class FieldErrorDetail{
        private String field;
        private String reason;
    }
}
