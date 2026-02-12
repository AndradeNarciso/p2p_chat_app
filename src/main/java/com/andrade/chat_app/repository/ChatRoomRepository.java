package com.andrade.chat_app.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.andrade.chat_app.domain.ChatMessage;

public interface ChatRoomRepository extends MongoRepository<ChatMessage,String> {
    Optional<ChatMessage> findBySenderIdAndRecipientId(String senderId, String recipientID);
}
