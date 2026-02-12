package com.andrade.chat_app.repository;

import java.util.List;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.andrade.chat_app.domain.ChatMessage;

public interface  ChatMessageRepository extends MongoRepository<ChatMessage,String>{
    
    List<ChatMessage> findByChatId(String chatId);
}
