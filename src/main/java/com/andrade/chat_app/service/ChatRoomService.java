package com.andrade.chat_app.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.andrade.chat_app.domain.ChatRoom;
import com.andrade.chat_app.repository.ChatRoomRepository;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Validated
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    public Optional<String> getChatRoomId(@NotBlank String senderId, @NotBlank String recipientId) {
        ChatRoom chatRoom = chatRoomRepository
                .findBySenderIdAndRecipientId(senderId, recipientId)
                .orElseGet(() -> {
                    return chatRoomRepository.save(ChatRoom.builder()
                            .senderId(senderId)
                            .recipientId(recipientId)
                            .build());

                });

        return Optional.of(chatRoom.getChatId());
    }

}
