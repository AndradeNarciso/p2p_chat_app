package com.andrade.chat_app.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ChatNotification {
    private String id;
    private String senderId;
    private String recipientId;
    private String content;
}
