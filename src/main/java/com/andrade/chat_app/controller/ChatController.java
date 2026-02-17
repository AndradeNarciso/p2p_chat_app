package com.andrade.chat_app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.andrade.chat_app.domain.ChatMessage;
import com.andrade.chat_app.dto.chat.ChatMessageRequest;
import com.andrade.chat_app.service.ChatMessageService;
import com.andrade.chat_app.util.ChatNotification;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messageTemplate;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    public void processMessage(@Valid @Payload ChatMessageRequest chatMessageRequest) {
        ChatMessage chat = chatMessageService.saveChatMessage(chatMessageRequest);
        messageTemplate
                .convertAndSendToUser(chatMessageRequest.recipientId(), "queue/message",
                        ChatNotification.builder()
                                .id(chat.getId())
                                .senderId(chat.getSenderId())
                                .id(chat.getChatId())
                                .recipientId(chat.getRecipientId())
                                .content(chat.getContent())
                                .build());
    }

    @GetMapping("/message/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> getMessage(@PathVariable String senderId,
            @PathVariable String recipientId) {

        return ResponseEntity.ok(chatMessageService.getAllChatMessage(senderId, recipientId));
    }
}
