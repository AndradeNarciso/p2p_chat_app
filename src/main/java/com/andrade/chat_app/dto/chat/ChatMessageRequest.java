package com.andrade.chat_app.dto.chat;

import jakarta.validation.constraints.NotBlank;

public record ChatMessageRequest(
        @NotBlank String senderId,
        @NotBlank String recipientId,
        @NotBlank String content) {

}
