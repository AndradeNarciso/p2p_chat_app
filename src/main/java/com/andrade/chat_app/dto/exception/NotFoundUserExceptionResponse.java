package com.andrade.chat_app.dto.exception;

import java.time.Instant;

import lombok.Builder;

@Builder
public record NotFoundUserExceptionResponse(
        String message,
        String title,
        int status,
        String errorType,
        Instant timeStamp) {

}
