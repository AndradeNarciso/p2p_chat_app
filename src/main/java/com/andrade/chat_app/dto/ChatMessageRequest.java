package com.andrade.chat_app.dto;

import jakarta.validation.constraints.NotBlank;

public record ChatMessageRequest(
        @NotBlank String chatId,
        @NotBlank String senderId,
        @NotBlank String recipientId,
        @NotBlank String content) {

}
