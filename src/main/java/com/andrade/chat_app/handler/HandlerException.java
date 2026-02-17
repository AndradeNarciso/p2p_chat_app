package com.andrade.chat_app.handler;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.andrade.chat_app.dto.exception.NotFoundUserExceptionResponse;
import com.andrade.chat_app.exception.NotFoudUserException;

@ControllerAdvice
public class HandlerException {

    @ExceptionHandler(NotFoudUserException.class)
    public ResponseEntity<NotFoundUserExceptionResponse> handlerNotFoundUserException(NotFoudUserException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(NotFoundUserExceptionResponse.builder()
                        .message(ex.getMessage())
                        .title("NotFoundUserExceptio")
                        .errorType("BAD_REQUEST")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .timeStamp(Instant.now())
                        .build());
    }

}
