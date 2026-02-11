package com.andrade.chat_app.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @NotBlank String nickName,
        @NotBlank String fullName) {}
