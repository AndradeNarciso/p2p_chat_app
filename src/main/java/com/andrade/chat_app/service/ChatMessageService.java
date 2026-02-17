package com.andrade.chat_app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.andrade.chat_app.domain.ChatMessage;
import com.andrade.chat_app.dto.chat.ChatMessageRequest;
import com.andrade.chat_app.repository.ChatMessageRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;

    public ChatMessage saveChatMessage(ChatMessageRequest chatMessageRequest) {
        ChatMessage newChatMessage = ChatMessage.builder()
                .chatId(chatRoomService.getChatRoomId(chatMessageRequest.senderId(), chatMessageRequest.recipientId())
                        .toString())
                // .content(chatMessageRequest.content())
                // .senderId(chatMessageRequest.senderId())
                // .recipientId(chatMessageRequest.recipientId())
                // .timeStamp(LocalDate.now())
                .build();

        return chatMessageRepository.save(newChatMessage);
    }

    public List<ChatMessage> getAllChatMessage(String senderId, String recipientId){
           String chatId= chatRoomService.getChatRoomId(senderId,recipientId).toString();
            return chatMessageRepository.findByChatId(chatId);
    }

}

